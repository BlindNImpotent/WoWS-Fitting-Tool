package WoWSSSC.service;

import WoWSSSC.model.gameparams.ShipComponents.AA.AirDefense;
import WoWSSSC.model.gameparams.ShipComponents.AA.AntiAir;
import WoWSSSC.model.gameparams.ShipComponents.AA.Aura;
import WoWSSSC.model.gameparams.ShipComponents.ATBA.ATBA;
import WoWSSSC.model.gameparams.ShipComponents.ATBA.Secondary;
import WoWSSSC.model.gameparams.ShipComponents.ATBA.Shell;
import WoWSSSC.model.gameparams.ShipComponents.AirArmament;
import WoWSSSC.model.gameparams.ShipComponents.Artillery.Artillery;
import WoWSSSC.model.gameparams.ShipComponents.Artillery.ArtyShell;
import WoWSSSC.model.gameparams.ShipComponents.Artillery.Turret;
import WoWSSSC.model.gameparams.ShipComponents.DiveBomber.DiveBomber;
import WoWSSSC.model.gameparams.ShipComponents.DiveBomber.DiveBomberBomb;
import WoWSSSC.model.gameparams.ShipComponents.DiveBomber.DiveBomberPlane;
import WoWSSSC.model.gameparams.ShipComponents.Engine.Engine;
import WoWSSSC.model.gameparams.ShipComponents.FireControl.FireControl;
import WoWSSSC.model.gameparams.ShipComponents.Hull.Hull;
import WoWSSSC.model.gameparams.ShipComponents.ShipComponents;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.gameparams.Consumables.Consumable;
import WoWSSSC.model.gameparams.ShipComponents.TorpedoBomber.TorpedoBomber;
import WoWSSSC.model.gameparams.ShipComponents.TorpedoBomber.TorpedoBomberPlane;
import WoWSSSC.model.gameparams.ShipComponents.TorpedoBomber.TorpedoBomberTorpedo;
import WoWSSSC.model.gameparams.ShipComponents.Torpedoes.Torpedo;
import WoWSSSC.model.gameparams.ShipComponents.Torpedoes.Torpedoes;
import WoWSSSC.model.gameparams.ShipUpgradeInfo.Module.Components;
import WoWSSSC.model.gameparams.ShipUpgradeInfo.ShipUpgradeInfo;
import WoWSSSC.model.gameparams.Temporary;
import WoWSSSC.model.gameparams.test.Values.ShipAbilities.ShipAbilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Aesis on 2016-12-05.
 */
@Service
@Slf4j
public class GPService
{
    @Autowired
    private HashMap<String, LinkedHashMap<String, LinkedHashMap>> data;

    @Autowired
    @Qualifier (value = "gameParamsCHM")
    private HashMap<String, HashMap<String, LinkedHashMap>> gameParamsCHM;

    @Autowired
    @Qualifier(value = "nameToId")
    private HashMap<String, HashMap<String, String>> nameToId;

    @Autowired
    @Qualifier(value = "idToName")
    private HashMap<String, HashMap<String, String>> idToName;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    private static final Logger logger = LoggerFactory.getLogger(GPService.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Cacheable (value = "gameParams", key = "(#nation).concat(#shipType).concat(#ship).concat(#ship_id).concat(#artillery_id).concat(#dive_bomber_id).concat(#engine_id).concat(#fighter_id).concat(#fire_control_id).concat(#flight_control_id).concat(#hull_id).concat(#torpedo_bomber_id).concat(#torpedoes_id).concat(#isLive.toString())")
    public ShipComponents setShipGP(
            String nation,
            String shipType,
            String ship,
            String ship_id,
            String artillery_id,
            String dive_bomber_id,
            String engine_id,
            String fighter_id,
            String fire_control_id,
            String flight_control_id,
            String hull_id,
            String torpedo_bomber_id,
            String torpedoes_id,
            List<String> modules,
            boolean isLive
    ) throws IllegalAccessException
    {
        String serverParam = isLive ? "live" : "test";

        if (StringUtils.isEmpty(ship_id))
        {
            ship_id = String.valueOf(((((LinkedHashMap<String, LinkedHashMap<String, Warship>>) data.get("nations").get(nation)).get(shipType)).get(ship)).getShip_id());
        }

        if (StringUtils.isNotEmpty(ship_id))
        {
            ShipComponents shipComponents = new ShipComponents();
            Field[] fields = shipComponents.getClass().getDeclaredFields();

            Temporary tempShip = mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id), Temporary.class);
            ShipUpgradeInfo shipUpgradeInfo;
            try
            {
                shipUpgradeInfo = tempShip.getShipUpgradeInfo();
            }
            catch (Exception e)
            {
                return null;
            }

            HashSet<String> moduleNames = new HashSet<>();

            moduleNames.add(idToName.get(serverParam).get(artillery_id));
            moduleNames.add(idToName.get(serverParam).get(dive_bomber_id));
            moduleNames.add(idToName.get(serverParam).get(fighter_id));
            moduleNames.add(idToName.get(serverParam).get(torpedo_bomber_id));
            moduleNames.add(idToName.get(serverParam).get(torpedoes_id));
            moduleNames.add(idToName.get(serverParam).get(flight_control_id));
            moduleNames.add(idToName.get(serverParam).get(fire_control_id));
            moduleNames.add(idToName.get(serverParam).get(engine_id));
            moduleNames.add(idToName.get(serverParam).get(hull_id));

            if (CollectionUtils.isNotEmpty(modules))
            {
                modules.forEach(module -> moduleNames.add(idToName.get(serverParam).get(module)));
            }

            moduleNames.remove(null);

            Components artilleryComponents = null;
            Components torpedoesComponents = null;
            Components torpedoBomberComponents = null;
            Components diveBomberComponents = null;
            Components hullComponents = null;
            List<String> disabledAbilities = new ArrayList<>();

            for (String name : moduleNames)
            {
                for (Field cField : shipUpgradeInfo.getModules().get(name).getComponents().getClass().getDeclaredFields())
                {
                    cField.setAccessible(true);
                    if (cField.get(shipUpgradeInfo.getModules().get(name).getComponents()) != null && CollectionUtils.isNotEmpty(shipUpgradeInfo.getModules().get(name).getComponents().getArtillery()) && shipUpgradeInfo.getModules().get(name).getUcType().equalsIgnoreCase("_Artillery"))
                    {
                        artilleryComponents = shipUpgradeInfo.getModules().get(name).getComponents();
                    }
                    else if (cField.get(shipUpgradeInfo.getModules().get(name).getComponents()) != null && CollectionUtils.isNotEmpty(shipUpgradeInfo.getModules().get(name).getComponents().getTorpedoes()) && shipUpgradeInfo.getModules().get(name).getUcType().equalsIgnoreCase("_Torpedoes"))
                    {
                        torpedoesComponents = shipUpgradeInfo.getModules().get(name).getComponents();
                    }
                    else if (cField.get(shipUpgradeInfo.getModules().get(name).getComponents()) != null && CollectionUtils.isNotEmpty(shipUpgradeInfo.getModules().get(name).getComponents().getTorpedoBomber()) && shipUpgradeInfo.getModules().get(name).getUcType().equalsIgnoreCase("_TorpedoBomber"))
                    {
                        torpedoBomberComponents = shipUpgradeInfo.getModules().get(name).getComponents();
                    }
                    else if (cField.get(shipUpgradeInfo.getModules().get(name).getComponents()) != null && CollectionUtils.isNotEmpty(shipUpgradeInfo.getModules().get(name).getComponents().getDiveBomber()) && shipUpgradeInfo.getModules().get(name).getUcType().equalsIgnoreCase("_DiveBomber"))
                    {
                        diveBomberComponents = shipUpgradeInfo.getModules().get(name).getComponents();
                    }
                    else if (cField.get(shipUpgradeInfo.getModules().get(name).getComponents()) != null && CollectionUtils.isNotEmpty(shipUpgradeInfo.getModules().get(name).getComponents().getHull()) && shipUpgradeInfo.getModules().get(name).getUcType().equalsIgnoreCase("_Hull"))
                    {
                        disabledAbilities = shipUpgradeInfo.getModules().get(name).getDisabledAbilities();
                        hullComponents = shipUpgradeInfo.getModules().get(name).getComponents();
                    }
                    cField.setAccessible(false);
                }
            }

            for (String name : moduleNames)
            {
//                String prev = shipUpgradeInfo.getModules().get(name).getPrev();
//
//                if (!prev.equals(""))
//                {
//                    HashSet<String> prevNext = shipUpgradeInfo.getModules().get(prev).getNext();
//
//                    if (!moduleNames.contains(prev) && prevNext.stream().filter(nm -> moduleNames.contains(nm)).count() != prevNext.size())
//                    {
//                        return null;
//                    }
//                }

                for (Field cField : shipUpgradeInfo.getModules().get(name).getComponents().getClass().getDeclaredFields())
                {
                    cField.setAccessible(true);
                    List<String> tempList = (List<String>) cField.get(shipUpgradeInfo.getModules().get(name).getComponents());
                    cField.setAccessible(false);

                    for (Field field : fields)
                    {
                        if (tempList != null && tempList.size() > 0 && cField.getName().equals(field.getName()))
                        {
                            field.setAccessible(true);
                            if (field.getName().equalsIgnoreCase("Artillery"))
                            {
                                String tempArtillery = "";

                                for (String s : tempList)
                                {
                                    if (artilleryComponents != null && artilleryComponents.getArtillery().contains(s) && hullComponents.getArtillery().contains(s))
                                    {
                                        tempArtillery = s;
                                    }
                                }

                                if (shipComponents.getArtillery() == null)
                                {
                                    Artillery artillery = mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempArtillery), Artillery.class);
                                    field.set(shipComponents, artillery);

                                    HashSet<String> auraFarList = new HashSet<>();
                                    if (shipComponents.getArtillery().getAuraFar() != null)
                                    {
                                        shipComponents.getArtillery().getAuraFar().getGuns().forEach(gun ->
                                        {
                                            auraFarList.add(shipComponents.getArtillery().getTurrets().get(gun).getName());
                                        });
                                    }

                                    for (String auraFarString : auraFarList)
                                    {
                                        int count = 0;
                                        AntiAir auraFar = null;
                                        for (String value : shipComponents.getArtillery().getAuraFar().getGuns())
                                        {
                                            if (auraFarString.equalsIgnoreCase(shipComponents.getArtillery().getTurrets().get(value).getName()))
                                            {
                                                count = count + 1;

                                                if (auraFar == null)
                                                {
                                                    Turret tempSecondary = shipComponents.getArtillery().getTurrets().get(value);

                                                    auraFar = new AntiAir();
                                                    auraFar.setAntiAirAuraDistance(tempSecondary.getAntiAirAuraDistance());
                                                    auraFar.setAntiAirAuraStrength(tempSecondary.getAntiAirAuraStrength());
                                                    auraFar.setBarrelDiameter(tempSecondary.getBarrelDiameter());
                                                    auraFar.setNumBarrels(tempSecondary.getNumBarrels());
                                                    auraFar.setShotDelay(shipComponents.getArtillery().getAuraFar().getShotDelay());
                                                    auraFar.setName(tempSecondary.getName());
                                                    auraFar.setRealName((String) global.get(serverParam).get("IDS_" + tempSecondary.getName().toUpperCase()));
                                                }
                                            }
                                        }
                                        auraFar.setCount(count);
                                        shipComponents.getAuraFarList().add(auraFar);
                                    }

                                    shipComponents.getArtillery().getTurrets().values().forEach(value ->
                                    {
                                        float maxVertAngle = value.getVertSector().get(1);
                                        value.getAmmoList().forEach(ammo ->
                                        {
                                            String id = nameToId.get(serverParam).get(ammo);
                                            ArtyShell ArtyShell = mapper.convertValue(gameParamsCHM.get(serverParam).get(id), ArtyShell.class);
                                            if ("AP".equalsIgnoreCase(ArtyShell.getAmmoType()) && shipComponents.getArtillery().getAPShell() == null)
                                            {
                                                setAPPenetration(ArtyShell, maxVertAngle);
                                                shipComponents.getArtillery().setAPShell(ArtyShell);
                                            }
                                            else if ("HE".equalsIgnoreCase(ArtyShell.getAmmoType()) && shipComponents.getArtillery().getHEShell() == null)
                                            {
                                                setHEPenetration(ArtyShell, maxVertAngle);
                                                shipComponents.getArtillery().setHEShell(ArtyShell);
                                            }
                                        });
                                    });
                                }
                            }
                            else if (field.getName().equalsIgnoreCase("Torpedoes"))
                            {
                                String tempTorpedoes = "";

                                for (String s : tempList)
                                {
                                    if (torpedoesComponents != null && torpedoesComponents.getTorpedoes().contains(s) && hullComponents.getTorpedoes().contains(s))
                                    {
                                        tempTorpedoes = s;
                                    }
                                }

                                Torpedoes torpedoes = mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempTorpedoes), Torpedoes.class);
                                field.set(shipComponents, torpedoes);

                                shipComponents.getTorpedoes().getLaunchers().values().forEach(value -> value.getAmmoList().forEach(ammo ->
                                {
                                    String id = nameToId.get(serverParam).get(ammo);
                                    Torpedo torpedo = mapper.convertValue(gameParamsCHM.get(serverParam).get(id), Torpedo.class);
                                    if (shipComponents.getTorpedoes().getTorpedo() == null)
                                    {
                                        shipComponents.getTorpedoes().setTorpedo(torpedo);
                                    }
                                }));
                            }
                            else if (field.getName().equalsIgnoreCase("AirArmament"))
                            {
                                AirArmament airArmament = mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempList.get(0)), AirArmament.class);
                                field.set(shipComponents, airArmament);
                            }
                            else if (field.getName().equalsIgnoreCase("DiveBomber"))
                            {
                                String tempDiveBomber = "";

                                for (String s : tempList)
                                {
                                    if (diveBomberComponents != null && diveBomberComponents.getDiveBomber().contains(s))
                                    {
                                        tempDiveBomber = s;
                                    }
                                }

                                DiveBomber diveBomber = mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempDiveBomber), DiveBomber.class);
                                field.set(shipComponents, diveBomber);

                                String id = nameToId.get(serverParam).get(shipComponents.getDiveBomber().getPlaneType());
                                DiveBomberPlane diveBomberPlane = mapper.convertValue(gameParamsCHM.get(serverParam).get(id), DiveBomberPlane.class);

                                shipComponents.getDiveBomber().setPlane(diveBomberPlane);

                                String bombId = nameToId.get(serverParam).get(diveBomberPlane.getBombName());
                                DiveBomberBomb bomb = mapper.convertValue(gameParamsCHM.get(serverParam).get(bombId), DiveBomberBomb.class);

                                shipComponents.getDiveBomber().setBomb(bomb);
                            }
                            else if (field.getName().equalsIgnoreCase("TorpedoBomber"))
                            {
                                String tempTorpedoBomber = "";

                                for (String s : tempList)
                                {
                                    if (torpedoBomberComponents != null && torpedoBomberComponents.getTorpedoBomber().contains(s))
                                    {
                                        tempTorpedoBomber = s;
                                    }
                                }

                                TorpedoBomber torpedoBomber = mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempTorpedoBomber), TorpedoBomber.class);
                                field.set(shipComponents, torpedoBomber);

                                String id = nameToId.get(serverParam).get(shipComponents.getTorpedoBomber().getPlaneType());
                                TorpedoBomberPlane torpedoBomberPlane = mapper.convertValue(gameParamsCHM.get(serverParam).get(id), TorpedoBomberPlane.class);

                                String torpedoId = nameToId.get(serverParam).get(torpedoBomberPlane.getBombName());
                                TorpedoBomberTorpedo torpedo = mapper.convertValue(gameParamsCHM.get(serverParam).get(torpedoId), TorpedoBomberTorpedo.class);

                                shipComponents.getTorpedoBomber().setTorpedo(torpedo);
                            }
                            else if (field.getName().equalsIgnoreCase("ATBA"))
                            {
                                field.set(shipComponents, mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempList.get(0)), ATBA.class));

                                HashSet<String> auraFarList = new HashSet<>();
                                if (shipComponents.getAtba().getAuraFar() != null)
                                {
                                    shipComponents.getAtba().getAuraFar().getGuns().forEach(gun ->
                                    {
                                        auraFarList.add(shipComponents.getAtba().getSecondaries().get(gun).getName());
                                    });
                                }

                                for (String auraFarString : auraFarList)
                                {
                                    int count = 0;
                                    AntiAir auraFar = null;
                                    for (String value : shipComponents.getAtba().getAuraFar().getGuns())
                                    {
                                        if (auraFarString.equalsIgnoreCase(shipComponents.getAtba().getSecondaries().get(value).getName()))
                                        {
                                            count = count + 1;

                                            if (auraFar == null)
                                            {
                                                Secondary tempSecondary = shipComponents.getAtba().getSecondaries().get(value);

                                                auraFar = new AntiAir();
                                                auraFar.setAntiAirAuraDistance(tempSecondary.getAntiAirAuraDistance());
                                                auraFar.setAntiAirAuraStrength(tempSecondary.getAntiAirAuraStrength());
                                                auraFar.setBarrelDiameter(tempSecondary.getBarrelDiameter());
                                                auraFar.setNumBarrels(tempSecondary.getNumBarrels());
                                                auraFar.setShotDelay(shipComponents.getAtba().getAuraFar().getShotDelay());
                                                auraFar.setName(tempSecondary.getName());
                                                auraFar.setRealName((String) global.get(serverParam).get("IDS_" + tempSecondary.getName().toUpperCase()));
                                            }
                                        }
                                    }
                                    auraFar.setCount(count);
                                    shipComponents.getAuraFarList().add(auraFar);
                                }

                                HashSet<String> indexNameList = new HashSet<>();
                                shipComponents.getAtba().getSecondaries().values().forEach(value ->
                                {
                                    indexNameList.add(value.getName());
                                });

                                for (String indexName : indexNameList)
                                {
                                    int count = 0;
                                    Secondary secondary = null;
                                    for (Secondary value : shipComponents.getAtba().getSecondaries().values())
                                    {
                                        if (indexName.equalsIgnoreCase(value.getName()))
                                        {
                                            count = count + 1;
                                            secondary = value;
                                        }
                                    }
                                    secondary.setCount(count);
//                                    System.out.println(secondary.getName());
                                    secondary.setRealName((String) global.get(serverParam).get("IDS_" + secondary.getName().toUpperCase()));
                                    String id = nameToId.get(serverParam).get(secondary.getAmmoList().get(0));
                                    Shell shell = mapper.convertValue(gameParamsCHM.get(serverParam).get(id), Shell.class);
                                    secondary.setShell(shell);

                                    shipComponents.getAtba().getSecondariesList().add(secondary);
                                }
                                Collections.sort(shipComponents.getAtba().getSecondariesList(), (o1, o2) -> o1.getBarrelDiameterReal() - o2.getBarrelDiameterReal());
                            }
                            else if (field.getName().equalsIgnoreCase("Engine"))
                            {
                                field.set(shipComponents, mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempList.get(0)), Engine.class));
                            }
                            else if (field.getName().equalsIgnoreCase("Hull"))
                            {
                                field.set(shipComponents, mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempList.get(0)), Hull.class));
                            }
                            else if (field.getName().equalsIgnoreCase("FireControl"))
                            {
                                field.set(shipComponents, mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempList.get(0)), FireControl.class));
                            }
                            else if (field.getName().equalsIgnoreCase("AirDefense"))
                            {
                                field.set(shipComponents, mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id).get(tempList.get(0)), AirDefense.class));

                                HashSet<String> auraFarList = new HashSet<>();
                                shipComponents.getAirDefense().getAuraTypes().forEach(type ->
                                {
                                    if (type.contains("AuraFar"))
                                    {
                                        shipComponents.getAirDefense().getAuras().get(type).getGuns().forEach(gun ->
                                        {
                                            auraFarList.add(shipComponents.getAirDefense().getAntiAirGuns().get(gun).getName());
                                        });
                                    }
                                });

                                for (String auraFarString : auraFarList)
                                {
                                    int count = 0;
                                    AntiAir auraFar = null;

                                    for (Aura aura : shipComponents.getAirDefense().getAuras().values())
                                    {
                                        if (aura.getAuraType().contains("AuraFar"))
                                        {
                                            for (String value : shipComponents.getAirDefense().getAuras().get(aura.getAuraType()).getGuns())
                                            {
                                                if (auraFarString.equalsIgnoreCase(shipComponents.getAirDefense().getAntiAirGuns().get(value).getName()))
                                                {
                                                    count = count + 1;

                                                    if (auraFar == null)
                                                    {
                                                        Secondary tempSecondary = shipComponents.getAirDefense().getAntiAirGuns().get(value);

                                                        auraFar = new AntiAir();
                                                        auraFar.setAntiAirAuraDistance(tempSecondary.getAntiAirAuraDistance());
                                                        auraFar.setAntiAirAuraStrength(tempSecondary.getAntiAirAuraStrength());
                                                        auraFar.setBarrelDiameter(tempSecondary.getBarrelDiameter());
                                                        auraFar.setNumBarrels(tempSecondary.getNumBarrels());
                                                        auraFar.setShotDelay(shipComponents.getAirDefense().getAuras().get(aura.getAuraType()).getShotDelay());
                                                        auraFar.setName(tempSecondary.getName());
                                                        auraFar.setRealName((String) global.get(serverParam).get("IDS_" + tempSecondary.getName().toUpperCase()));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    auraFar.setCount(count);
                                    shipComponents.getAuraFarList().add(auraFar);
                                }

                                HashSet<String> auraMediumList = new HashSet<>();
                                shipComponents.getAirDefense().getAuraTypes().forEach(type ->
                                {
                                    if (type.contains("AuraMedium"))
                                    {
                                        shipComponents.getAirDefense().getAuras().get(type).getGuns().forEach(gun ->
                                        {
                                            auraMediumList.add(shipComponents.getAirDefense().getAntiAirGuns().get(gun).getName());
                                        });
                                    }
                                });

                                for (String auraMediumString : auraMediumList)
                                {
                                    int count = 0;
                                    AntiAir auraMedium = null;

                                    for (Aura aura : shipComponents.getAirDefense().getAuras().values())
                                    {
                                        if (aura.getAuraType().contains("AuraMedium"))
                                        {
                                            for (String value : shipComponents.getAirDefense().getAuras().get(aura.getAuraType()).getGuns())
                                            {
                                                if (auraMediumString.equalsIgnoreCase(shipComponents.getAirDefense().getAntiAirGuns().get(value).getName()))
                                                {
                                                    count = count + 1;

                                                    if (auraMedium == null)
                                                    {
                                                        Secondary tempSecondary = shipComponents.getAirDefense().getAntiAirGuns().get(value);

                                                        auraMedium = new AntiAir();
                                                        auraMedium.setAntiAirAuraDistance(tempSecondary.getAntiAirAuraDistance());
                                                        auraMedium.setAntiAirAuraStrength(tempSecondary.getAntiAirAuraStrength());
                                                        auraMedium.setBarrelDiameter(tempSecondary.getBarrelDiameter());
                                                        auraMedium.setNumBarrels(tempSecondary.getNumBarrels());
                                                        auraMedium.setShotDelay(shipComponents.getAirDefense().getAuras().get(aura.getAuraType()).getShotDelay());
                                                        auraMedium.setName(tempSecondary.getName());
                                                        auraMedium.setRealName((String) global.get(serverParam).get("IDS_" + tempSecondary.getName().toUpperCase()));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    auraMedium.setCount(count);
                                    shipComponents.getAuraMediumList().add(auraMedium);
                                }

                                HashSet<String> auraNearList = new HashSet<>();
                                shipComponents.getAirDefense().getAuraTypes().forEach(type ->
                                {
                                    if (type.contains("AuraNear"))
                                    {
                                        shipComponents.getAirDefense().getAuras().get(type).getGuns().forEach(gun ->
                                        {
                                            auraNearList.add(shipComponents.getAirDefense().getAntiAirGuns().get(gun).getName());
                                        });
                                    }
                                });

                                for (String auraNearString : auraNearList)
                                {
                                    int count = 0;
                                    AntiAir auraNear = null;

                                    for (Aura aura : shipComponents.getAirDefense().getAuras().values())
                                    {
                                        if (aura.getAuraType().contains("AuraNear"))
                                        {
                                            for (String value : shipComponents.getAirDefense().getAuras().get(aura.getAuraType()).getGuns())
                                            {
                                                if (auraNearString.equalsIgnoreCase(shipComponents.getAirDefense().getAntiAirGuns().get(value).getName()))
                                                {
                                                    count = count + 1;

                                                    if (auraNear == null)
                                                    {
                                                        Secondary tempSecondary = shipComponents.getAirDefense().getAntiAirGuns().get(value);

                                                        auraNear = new AntiAir();
                                                        auraNear.setAntiAirAuraDistance(tempSecondary.getAntiAirAuraDistance());
                                                        auraNear.setAntiAirAuraStrength(tempSecondary.getAntiAirAuraStrength());
                                                        auraNear.setBarrelDiameter(tempSecondary.getBarrelDiameter());
                                                        auraNear.setNumBarrels(tempSecondary.getNumBarrels());
                                                        auraNear.setShotDelay(shipComponents.getAirDefense().getAuras().get(aura.getAuraType()).getShotDelay());
                                                        auraNear.setName(tempSecondary.getName());
                                                        auraNear.setRealName((String) global.get(serverParam).get("IDS_" + tempSecondary.getName().toUpperCase()));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    auraNear.setCount(count);
                                    shipComponents.getAuraNearList().add(auraNear);
                                }
                            }
                            else
                            {
                                field.set(shipComponents, gameParamsCHM.get(serverParam).get(ship_id).get(tempList.get(0)));
                            }
                            field.setAccessible(false);
                        }
                    }
                }
            }
            setShipAbilities(shipComponents, ship_id, disabledAbilities, serverParam);

            shipComponents.getHull().setWeight(tempShip.getWeight());

            if (shipComponents.getArtillery() != null && shipComponents.getFireControl() != null)
            {
                shipComponents.getArtillery().setMaxDist(shipComponents.getArtillery().getMaxDist() * shipComponents.getFireControl().getMaxDistCoef());
            }

            return shipComponents;
        }
        return null;
    }

    private void setShipAbilities(ShipComponents shipComponents, String ship_id, List<String> disabledAbilities, String serverParam)
    {
        ShipAbilities shipAbilities = mapper.convertValue(gameParamsCHM.get(serverParam).get(ship_id), Temporary.class).getShipAbilities();

        HashMap<String, Consumable> abilities = new HashMap<>();

        shipAbilities.getAbilitySlot0().getAbils().forEach(list ->
        {
            if (CollectionUtils.isEmpty(disabledAbilities) || !disabledAbilities.contains(list.get(0)))
            {
                abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(serverParam).get(list.get(0)), Consumable.class));
            }
        });
        shipAbilities.getAbilitySlot1().getAbils().forEach(list ->
        {
            if (CollectionUtils.isEmpty(disabledAbilities) || !disabledAbilities.contains(list.get(0)))
            {
                abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(serverParam).get(list.get(0)), Consumable.class));
            }
        });
        shipAbilities.getAbilitySlot2().getAbils().forEach(list ->
        {
            if (CollectionUtils.isEmpty(disabledAbilities) || !disabledAbilities.contains(list.get(0)))
            {
                abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(serverParam).get(list.get(0)), Consumable.class));
            }
        });
        shipAbilities.getAbilitySlot3().getAbils().forEach(list ->
        {
            if (CollectionUtils.isEmpty(disabledAbilities) || !disabledAbilities.contains(list.get(0)))
            {
                abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(serverParam).get(list.get(0)), Consumable.class));
            }
        });

        shipComponents.setShipAbilities(shipAbilities);
        shipComponents.setAbilities(abilities);
    }

    private void setAPPenetration(ArtyShell ArtyShell, float maxVertAngle)
    {
        // SHELL CONSTANTS
        float C = 0.5561613f; // PENETRATION
        float a = 9.80665f; // GRAVITY
        float T_0 = 288f; // TEMPERATURE AT SEA LEVEL
        float L = 0.0065f; // TEMPERATURE LAPSE RATE
        float p_0 = 101325f; // PRESSURE AT SEA LEVEL
        float R = 8.31447f; // UNIV GAS CONSTANT
        float M = 0.0289644f; // MOLAR MASS OF AIR

        // SHELL CONSTANTS
        float W = ArtyShell.getBulletMass(); // SHELL WEIGHT
        float D = ArtyShell.getBulletDiametr(); // SHELL DIAMETER
        float c_D = ArtyShell.getBulletAirDrag();  // SHELL DRAG
        float V_0 = ArtyShell.getBulletSpeed(); // SHELL MUZZLE VELOCITY
        float K = ArtyShell.getBulletKrupp(); // SHELL KRUPP

        float cw_1 = 1f; // QUADRATIC DRAG COEFFICIENT
        float cw_2 = 100f + (1000f / 3f) * D; // LINEAR DRAG COEFFICIENT

        C = C * K / 2400f; // KRUPP INCLUSION
        float k = 0.5f * c_D * (float) Math.pow((D / 2f), 2f) * (float) Math.PI / W; // CONSTANTS TERMS OF DRAG

//        float[] alpha = [0 : 0.001 : 15 / 360 * 2 * Math.PI]; // ELEV. ANGLES 0...15

        Float[] alpha = linspace(0f, 0.001f, (float) Math.PI * maxVertAngle / 180f);

        float dt = 0.1f; // TIME STEP

        LinkedHashMap<Float, Float> penetration = new LinkedHashMap<>();
        LinkedHashMap<Float, Float> flightTime = new LinkedHashMap<>();

        for (int i = 0; i < alpha.length; i++) // for each alpha angle do:
        {
            float v_x = (float) Math.cos(alpha[i]) * V_0;
            float v_y = (float) Math.sin(alpha[i]) * V_0;
            float y = 0f;
            float x = 0f;
            float t = 0f;

            while (y >= 0f) // follow flight path until shell hits ground again
            {
                x = x + dt * v_x;
                y = y + dt * v_y;

                float T = T_0 - L*y;
                float p = p_0 * (float) Math.pow(1 - L * y / T_0, (a * M / (R * L)));
                float rho = p * M / (R * T);

                v_x = v_x - dt * k * rho * (cw_1 * (float) Math.pow(v_x, 2) + cw_2 * v_x);
                v_y = v_y - dt * a - dt * k * rho * (cw_1 * (float) Math.pow(v_y, 2) + cw_2 * Math.abs(v_y)) * Math.signum(v_y);

                t = t + dt;
            }

            float v_total = (float) Math.pow((Math.pow(v_y, 2f) + Math.pow(v_x, 2f)), 0.5f);
            float p_athit = C * (float) Math.pow(v_total, 1.1f) * (float) Math.pow(W, 0.55f) / (float)Math.pow(D * 1000f, 0.65f); // PENETRATION FORMULA
            float IA = (float) Math.atan(Math.abs(v_y) / Math.abs(v_x)); // IMPACT ANGLE ON BELT ARMOR

            penetration.put(x, (float) Math.cos(IA) * p_athit);
            flightTime.put(x, t / 3f);
        }
        ArtyShell.setAPShell(penetration, flightTime);
    }

    private void setHEPenetration(ArtyShell ArtyShell, float maxVertAngle)
    {
        // SHELL CONSTANTS
        float C = 0.5561613f; // PENETRATION
        float a = 9.80665f; // GRAVITY
        float T_0 = 288f; // TEMPERATURE AT SEA LEVEL
        float L = 0.0065f; // TEMPERATURE LAPSE RATE
        float p_0 = 101325f; // PRESSURE AT SEA LEVEL
        float R = 8.31447f; // UNIV GAS CONSTANT
        float M = 0.0289644f; // MOLAR MASS OF AIR

        // SHELL CONSTANTS
        float W = ArtyShell.getBulletMass(); // SHELL WEIGHT
        float D = ArtyShell.getBulletDiametr(); // SHELL DIAMETER
        float c_D = ArtyShell.getBulletAirDrag();  // SHELL DRAG
        float V_0 = ArtyShell.getBulletSpeed(); // SHELL MUZZLE VELOCITY
        float K = ArtyShell.getBulletKrupp(); // SHELL KRUPP

        float cw_1 = 1f; // QUADRATIC DRAG COEFFICIENT
        float cw_2 = 100f + (1000f / 3f) * D; // LINEAR DRAG COEFFICIENT

        C = C * K / 2400f; // KRUPP INCLUSION
        float k = 0.5f * c_D * (float) Math.pow((D / 2f), 2f) * (float) Math.PI / W; // CONSTANTS TERMS OF DRAG

//        float[] alpha = [0 : 0.001 : 15 / 360 * 2 * Math.PI]; // ELEV. ANGLES 0...15

        Float[] alpha = linspace(0f, 0.001f, (float) Math.PI * maxVertAngle / 180f);

        float dt = 0.1f; // TIME STEP

        LinkedHashMap<Float, Float> flightTime = new LinkedHashMap<>();

        for (int i = 0; i < alpha.length; i++) // for each alpha angle do:
        {
            float v_x = (float) Math.cos(alpha[i]) * V_0;
            float v_y = (float) Math.sin(alpha[i]) * V_0;
            float y = 0f;
            float x = 0f;
            float t = 0f;

            while (y >= 0f) // follow flight path until shell hits ground again
            {
                x = x + dt * v_x;
                y = y + dt * v_y;

                float T = T_0 - L*y;
                float p = p_0 * (float) Math.pow(1 - L * y / T_0, (a * M / (R * L)));
                float rho = p * M / (R * T);

                v_x = v_x - dt * k * rho * (cw_1 * (float) Math.pow(v_x, 2) + cw_2 * v_x);
                v_y = v_y - dt * a - dt * k * rho * (cw_1 * (float) Math.pow(v_y, 2) + cw_2 * Math.abs(v_y)) * Math.signum(v_y);

                t = t + dt;
            }
            flightTime.put(x, t / 3f);
        }
        ArtyShell.setHEShell(flightTime);
    }

    private Float[] linspace(float start, float incremental, float end)
    {
        List<Float> alpha = new ArrayList<>();

        float begin = start;
        while (begin <= end)
        {
            alpha.add(begin);
            begin = begin + incremental;
        }

        Float[] temp = new Float[alpha.size()];
        temp = alpha.toArray(temp);

        return temp;
    }
}
