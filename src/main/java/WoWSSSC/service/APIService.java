package WoWSSSC.service;

import WoWSSSC.config.CustomProperties;
import WoWSSSC.model.WoWSAPI.APIAddress;
import WoWSSSC.model.WoWSAPI.ModuleId;
import WoWSSSC.model.WoWSAPI.info.Encyclopedia;
import WoWSSSC.model.bitly.Bitly;
import WoWSSSC.model.bitly.BitlyData;
import WoWSSSC.model.gameparams.ShipComponents.ShipComponents;
import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.shipprofile.ShipData;
import WoWSSSC.model.WoWSAPI.shipprofile.profile.artillery.Artillery_Slots;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.model.gameparams.Consumables.Consumable;
import WoWSSSC.model.gameparams.commanders.*;
import WoWSSSC.model.gameparams.test.TorpedoShip;
import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aesis on 2016-10-15.
 */
@Service
public class APIService
{
    @Autowired
    @Qualifier (value = "APP_ID")
    private String APP_ID;

    @Autowired
    @Qualifier (value = "APIAddress")
    private APIAddress apiAddress;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private APIJsonParser apiJsonParser;

    @Autowired
    private HashMap<String, LinkedHashMap<String, LinkedHashMap>> data;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    @Qualifier (value = "gameParamsCHM")
    private HashMap<String, HashMap<String, LinkedHashMap>> gameParamsCHM;

    @Autowired
    private GPService gpService;

    @Autowired
    private Sorter sorter;

    @Autowired
    private CustomProperties customProperties;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

//    @Cacheable (value = "shipAPI", key = "(#nation).concat(#shipType).concat(#ship).concat(#ship_id).concat(#artillery_id).concat(#dive_bomber_id).concat(#engine_id).concat(#fighter_id).concat(#fire_control_id).concat(#flight_control_id).concat(#hull_id).concat(#torpedo_bomber_id).concat(#torpedoes_id)")
    public String setShipAPI(
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
            List<String> modules
    ) throws IOException, ExecutionException, InterruptedException, IllegalAccessException
    {
        if (!ship_id.equals(""))
        {
            String key = "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                    + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id;

            String url = apiAddress.getAPI_Starter() + "/shipprofile/?application_id=" + APP_ID + key + "&language=" + customProperties.getLanguage();

            if (!shipHashMap.containsKey(key))
            {
                ShipData shipData = restTemplate.getForObject(url, ShipData.class);

                if (shipData.getStatus().equals("ok"))
                {
                    logger.info("Requested valid API for " + nation + " " + shipType + " " + ship + " - " + url);

                    if ("Saipan".equalsIgnoreCase(ship))
                    {
                        shipData.getData().get(ship_id).getFighters().getCount_in_squadron().setMin(3);
                        shipData.getData().get(ship_id).getFighters().getCount_in_squadron().setMax(3);
                        shipData.getData().get(ship_id).getTorpedo_bomber().getCount_in_squadron().setMin(3);
                        shipData.getData().get(ship_id).getTorpedo_bomber().getCount_in_squadron().setMax(3);
                    }

                    if (shipData.getData().get(ship_id).getAnti_aircraft() != null)
                    {
                        shipData.getData().get(ship_id).getAnti_aircraft().setSlots(sorter.sortAARange(shipData.getData().get(ship_id).getAnti_aircraft().getSlots()));
                    }

//                    ModuleId moduleId = new ModuleId();
//                    setModuleIds(shipData.getData().get(ship_id), moduleId);
//
//                    key = "&ship_id=" + ship_id + "&artillery_id=" + moduleId.getArtillery_id() + "&dive_bomber_id=" + moduleId.getDive_bomber_id() + "&engine_id=" + moduleId.getEngine_id()
//                            + "&fighter_id=" + moduleId.getFighter_id() + "&fire_control_id=" + moduleId.getFire_control_id() + "&flight_control_id=" + moduleId.getFlight_control_id() + "&hull_id=" + moduleId.getHull_id() + "&torpedo_bomber_id=" + moduleId.getTorpedo_bomber_id() + "&torpedoes_id=" + moduleId.getTorpedoes_id();

                    shipHashMap.put(key, shipData.getData().get(ship_id));
                }
                else
                {
                    logger.info("Requested invalid API for " + nation + " " + shipType + " " + ship + " - " + url);

                    shipHashMap.put(key, null);
                }
            }
            else
            {
                if (shipHashMap.get(key) == null)
                {
//                    apiJsonParser.checkShipData(url, key, ship_id, nation, shipType, ship);
                    logger.info("Requested invalid data for " + nation + " " + shipType + " " + ship + " - " + url);
                }
                else
                {
                    logger.info("Requested valid data for " + nation + " " + shipType + " " + ship + " - " + url);
                }

            }
            return key;
        }
        return null;
    }
    
    public void setModuleIds(Ship ship, ModuleId moduleId)
    {
        if (StringUtils.isEmpty(moduleId.getArtillery_id()) && ship.getArtillery() != null)
        {
            moduleId.setArtillery_id(String.valueOf(ship.getArtillery().getArtillery_id()));
        }
        if (StringUtils.isEmpty(moduleId.getDive_bomber_id()) && ship.getDive_bomber() != null)
        {
            moduleId.setDive_bomber_id(String.valueOf(ship.getDive_bomber().getDive_bomber_id()));
        }
        if (StringUtils.isEmpty(moduleId.getEngine_id()) && ship.getEngine() != null)
        {
            moduleId.setEngine_id(String.valueOf(ship.getEngine().getEngine_id()));
        }
        if (StringUtils.isEmpty(moduleId.getFighter_id()) && ship.getFighters() != null)
        {
            moduleId.setFighter_id(String.valueOf(ship.getFighters().getFighters_id()));
        }
        if (StringUtils.isEmpty(moduleId.getFire_control_id()) && ship.getFire_control() != null)
        {
            moduleId.setFire_control_id(String.valueOf(ship.getFire_control().getFire_control_id()));
        }
        if (StringUtils.isEmpty(moduleId.getFlight_control_id()) && ship.getFlight_control() != null)
        {
            moduleId.setFlight_control_id(String.valueOf(ship.getFlight_control().getFlight_control_id()));
        }
        if (StringUtils.isEmpty(moduleId.getHull_id()) && ship.getHull() != null)
        {
            moduleId.setHull_id(String.valueOf(ship.getHull().getHull_id()));
        }
        if (StringUtils.isEmpty(moduleId.getTorpedo_bomber_id()) && ship.getTorpedo_bomber() != null)
        {
            moduleId.setTorpedo_bomber_id(String.valueOf(ship.getTorpedo_bomber().getTorpedo_bomber_id()));
        }
        if (StringUtils.isEmpty(moduleId.getTorpedoes_id()) && ship.getTorpedoes() != null)
        {
            moduleId.setTorpedoes_id(String.valueOf(ship.getTorpedoes().getTorpedoes_id()));
        }
    }

    @CacheEvict(value = {"shipAPI", "shipAPI_GameParams", "gameParams"}, allEntries = true)
    public void cacheEvictShipHashMap()
    {
        logger.info("Evicting Ship API");
        shipHashMap.clear();
    }

    private void setCustomValues(String ship_id, Ship ship)
    {
        if (ship.getArtillery() != null)
        {
            long totalGuns = 0;

            int i = 0;
            Artillery_Slots tempSlot = null;
            HashMap<String, Artillery_Slots> tempSlots = new HashMap<>();
            for (Artillery_Slots slots : ship.getArtillery().getSlots().values())
            {
                boolean isMatch = false;

                if (tempSlot == null) {
                    tempSlot = slots;
                }
                else if (tempSlot.getBarrels() == slots.getBarrels() && tempSlot.getGuns() == slots.getGuns() && tempSlot.getName().equalsIgnoreCase(slots.getName())) {
                    isMatch = true;
                }

                if (!isMatch) {
                    totalGuns = totalGuns + (slots.getBarrels() * slots.getGuns());
                    tempSlots.put(String.valueOf(i), slots);
                    i++;
                }
            }

            ship.getArtillery().setSlots(tempSlots);
            ship.getArtillery().setTotalGuns(totalGuns);
        }

//        if (ship.getHull() != null && gameParamsCHM.get(String.valueOf(ship.getHull().getHull_id())) != null)
//        {
//            String tGPHullName = (String) gameParamsCHM.get(String.valueOf(ship.getHull().getHull_id())).get("name");
//
//            List<String> tGPShipHullNameList = mapper.convertValue(gameParamsCHM.get(ship_id), Temporary.class).getShipUpgradeInfo().getModules().get(tGPHullName).getComponents().getHull();
////            List<String> tGPShipHullNameList = (List<String>) ((HashMap<String, HashMap>) ((HashMap<String, HashMap>) gameParamsCHM.get(ship_id).get("ShipUpgradeInfo")).get(tGPHullName).get("components")).get("hull");
//            if (tGPShipHullNameList.size() == 1)
//            {
//                String tGPShipHullName = tGPShipHullNameList.get(0);
//
//                ship.getConcealment().setVisibilityCoefGK(Float.parseFloat(new DecimalFormat("#").format((double) ((HashMap) gameParamsCHM.get(ship_id).get(tGPShipHullName)).get("visibilityCoefGK"))));
//            }
//        }
    }

//    @Cacheable (value = "shipAPI_GameParams", key = "#key.concat(#upgradesSkills.toString()).concat(#adrenalineValue.toString()).concat(#getTorpedoVisibilities.toString()).concat(#isLive.toString())")
    public Ship getUpgradeSkillStats(String key, String nation, String shipType, String shipName, String ship_id, String artillery_id, String dive_bomber_id, String engine_id, String fighter_id, String fire_control_id, String flight_control_id, String hull_id, String torpedo_bomber_id, String torpedoes_id, List<String> modules, HashMap<String, List> upgradesSkills, int adrenalineValue, boolean isStock, boolean getTorpedoVisibilities, boolean isLive) throws Exception
    {
        String serverParam = isLive ? "live" : "test";

        if (shipHashMap.get(key) == null)
        {
            return null;
        }

        Cloner cloner = new Cloner();
        Ship ship = cloner.deepClone(shipHashMap.get(key));

        Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(shipName);

        ShipComponents shipComponents = gpService.setShipGP(nation, shipType, shipName, ship_id, artillery_id, dive_bomber_id, engine_id, fighter_id, fire_control_id, flight_control_id, hull_id, torpedo_bomber_id, torpedoes_id, modules, isLive);

        ship.setShipComponents(shipComponents);

        setCustomValues(ship_id, ship);

        if (ship.getAnti_aircraft() != null)
        {
            ship.getAnti_aircraft().getSlots().values().forEach(aa ->
            {
                ship.getShipComponents().getAuraFarList().forEach(auraFar ->
                {
                    if (aa.getName().equalsIgnoreCase(auraFar.getRealName()))
                    {
                        aa.setName(String.valueOf(auraFar.getNumBarrels()) + " " + aa.getName());
                    }
                });

                ship.getShipComponents().getAuraMediumList().forEach(auraMedium ->
                {
                    if (aa.getName().equalsIgnoreCase(auraMedium.getRealName()))
                    {
                        aa.setName(String.valueOf(auraMedium.getNumBarrels()) + " " + aa.getName());
                    }
                });

                ship.getShipComponents().getAuraNearList().forEach(auraNear ->
                {
                    if (aa.getName().equalsIgnoreCase(auraNear.getRealName()))
                    {
                        aa.setName(String.valueOf(auraNear.getNumBarrels()) + " " + aa.getName());
                    }
                });
            });
        }

//        if (getTorpedoVisibilities && warship.getTier() > 1)
//        {
//            setTorpedoVisibility(ship, warship.getTier(), serverParam);
//        }

//        LinkedHashMap<String, LinkedHashMap> nationLHM = (LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation);
//        Warship warship = (Warship) nationLHM.get(shipType).get(shipName);

        if (CollectionUtils.isEmpty(upgradesSkills))
        {
            return ship;
        }

        if (upgradesSkills.get("camouflage") != null)
        {
            boolean camouflage = (boolean) upgradesSkills.get("camouflage").get(0);
            if (camouflage)
            {
//                if (ship.getConcealment() != null && !warship.isIs_premium())
//                {
                    ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * 0.97f);
//                }
            }
//            else
//            {
//                if (ship.getConcealment() != null && warship.isIs_premium())
//                {
//                    ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() / 0.97f);
//                }
//            }
        }

        List<String> flags = upgradesSkills.get("flags");
        if (flags != null)
        {
            flags.forEach(flag ->
            {
                if (!StringUtils.isEmpty(flag))
                {
                    Consumables temp = (Consumables) data.get(serverParam).get("upgrades").get(flag);

                    setFlagsModernization(ship, temp);
                }
            });
        }

        List<String> upgrades = upgradesSkills.get("upgrades");
        if (upgrades != null)
        {
            upgrades.forEach(upgrade -> {
                if (!StringUtils.isEmpty(upgrade))
                {
                    Consumables temp = (Consumables) data.get(serverParam).get("upgrades").get(upgrade);

                    setFlagsModernization(ship, temp);
                }
            });
        }

        List<HashMap> skills = upgradesSkills.get("skills");
        List<String> uSkills = upgradesSkills.get("uSkills");
        String commander = upgradesSkills.get("commander") != null ? (String) upgradesSkills.get("commander").get(0) : "default";
        Skills commanderSkill = ((LinkedHashMap<String, GPCommander>) data.get(serverParam).get("commanders").get(nation)).get(commander).getSkills();
        LinkedHashMap<String, UniqueTemp> uSkillModifier = ((LinkedHashMap<String, GPCommander>) data.get(serverParam).get("commanders").get(nation)).get(commander).getUniqueSkills().getModifier();

        if (uSkills != null && uSkills.size() > 0)
        {
            uSkills.forEach(uSkill ->
            {
                uSkillModifier.get(uSkill).getTemp().values().forEach(value ->
                {
                    if (value.getNumConsumables() > 0)
                    {
                        ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                        {
                            Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);
                            tempConsumable.getTypes().values().forEach(cType -> cType.setNumConsumables(cType.getNumConsumables() + value.getNumConsumables()));

                            ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                        });
                    }

                    if (value.getReloadCoeff() > 0)
                    {
                        if (ship.getShipComponents().getArtillery() != null)
                        {
                            ship.getShipComponents().getArtillery().setShotDelay(ship.getShipComponents().getArtillery().getShotDelay() * value.getReloadCoeff());
                        }

                        if (ship.getTorpedoes() != null)
                        {
                            ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * 0.84f);
                        }

                        if (ship.getDive_bomber() != null)
                        {
                            ship.getDive_bomber().setPrepare_time(ship.getDive_bomber().getPrepare_time() * 0.84f);
                        }

                        if (ship.getFighters() != null)
                        {
                            ship.getFighters().setPrepare_time(ship.getFighters().getPrepare_time() * 0.84f);
                        }

                        if (ship.getTorpedo_bomber() != null)
                        {
                            ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * 0.84f);
                        }
                    }
                    else if (value.getAirplaneReloadCoeff() != 0 && value.getArtilleryReloadCoeff() != 0 && value.getTorpedoReloadCoeff() != 0)
                    {
                        if (ship.getShipComponents().getArtillery() != null)
                        {
                            ship.getShipComponents().getArtillery().setShotDelay(ship.getShipComponents().getArtillery().getShotDelay() * value.getArtilleryReloadCoeff());
                        }

                        if (ship.getTorpedoes() != null)
                        {
                            ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * value.getTorpedoReloadCoeff());
                        }

                        if (ship.getDive_bomber() != null)
                        {
                            ship.getDive_bomber().setPrepare_time(ship.getDive_bomber().getPrepare_time() * value.getAirplaneReloadCoeff());
                        }

                        if (ship.getFighters() != null)
                        {
                            ship.getFighters().setPrepare_time(ship.getFighters().getPrepare_time() * value.getAirplaneReloadCoeff());
                        }

                        if (ship.getTorpedo_bomber() != null)
                        {
                            ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * value.getAirplaneReloadCoeff());
                        }
                    }
                });
            });
        }

        if (skills != null)
        {
            skills.forEach(skill ->
            {
                String skillType = skill.get("tier") + "_" + skill.get("type_id");
                SkillModifier modifier = commanderSkill.getModifiers().get(skillType);

                if (modifier.getVitalityCoefficient() != 0 && modifier.getDiveBombersPrepareCoefficient() != 0 && modifier.getFightersPrepareCoefficient() != 0 && modifier.getTorpedoBombersPrepareCoefficient() != 0) // 1_3
                {
                    if (ship.getDive_bomber() != null)
                    {
                        ship.getDive_bomber().setMax_health(ship.getDive_bomber().getMax_health() * modifier.getVitalityCoefficient());
                        ship.getDive_bomber().setPrepare_time(ship.getDive_bomber().getPrepare_time() * modifier.getDiveBombersPrepareCoefficient());
                    }
                    if (ship.getFighters() != null)
                    {
                        ship.getFighters().setMax_health(ship.getFighters().getMax_health() * modifier.getVitalityCoefficient());
                        ship.getFighters().setPrepare_time(ship.getFighters().getPrepare_time() * modifier.getFightersPrepareCoefficient());
                    }
                    if (ship.getTorpedo_bomber() != null)
                    {
                        ship.getTorpedo_bomber().setMax_health(ship.getTorpedo_bomber().getMax_health() * modifier.getVitalityCoefficient());
                        ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * modifier.getTorpedoBombersPrepareCoefficient());
                    }
                }
                else if (modifier.getReloadCoefficient() != 0) // 2_0, 2_1
                {
                    ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                    {
                        Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                        if (skillType.equals("2_0"))
                        {
                            if (tempConsumable.getName().contains("CrashCrew"))
                            {
                                tempConsumable.getTypes().values().forEach(cType -> cType.setReloadTime(cType.getReloadTime() * modifier.getReloadCoefficient()));
                            }
                        }
                        else if (skillType.equals("2_1"))
                        {
                            tempConsumable.getTypes().values().forEach(cType -> cType.setReloadTime(cType.getReloadTime() * modifier.getReloadCoefficient()));
                        }

                        ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                    });
                }
                else if (modifier.getBigGunBonus() != 0 && modifier.getSmallGunBonus() != 0) // 2_2
                {
                    if (ship.getShipComponents().getArtillery() != null)
                    {
                        int caliber = ship.getShipComponents().getArtillery().getBarrelDiameter();
                        float deg = ship.getShipComponents().getArtillery().getRotationDeg();

                        if (caliber <= 139)
                        {
                            deg = deg + modifier.getSmallGunBonus();
                        }
                        else
                        {
                            deg = deg + modifier.getBigGunBonus();
                        }
                        ship.getShipComponents().getArtillery().setRotationDeg(deg);
                    }
                }
                else if (modifier.getTorpedoRangeCoefficient() != 0 && modifier.getTorpedoSpeedBonus() != 0) // 2_3
                {
                    if (ship.getTorpedoes() != null)
                    {
                        ship.getTorpedoes().setTorpedo_speed(ship.getTorpedoes().getTorpedo_speed() + modifier.getTorpedoSpeedBonus());
                        ship.getTorpedoes().setDistance(ship.getTorpedoes().getDistance() * modifier.getTorpedoRangeCoefficient());
                    }

                    if (ship.getTorpedo_bomber() != null)
                    {
                        ship.getTorpedo_bomber().setTorpedo_max_speed(ship.getTorpedo_bomber().getTorpedo_max_speed() + modifier.getTorpedoSpeedBonus());
                        ship.getTorpedo_bomber().setTorpedo_distance(ship.getTorpedo_bomber().getTorpedo_distance() * modifier.getTorpedoRangeCoefficient());
                    }
                }
                else if (modifier.getFightersPassiveEfficiencyCoefficient() != 0) // 2_5
                {
                    if (ship.getDive_bomber() != null)
                    {
                        ship.getDive_bomber().setGunner_damage(ship.getDive_bomber().getGunner_damage() * modifier.getFightersPassiveEfficiencyCoefficient());
                    }

                    if (ship.getTorpedo_bomber() != null)
                    {
                        ship.getTorpedo_bomber().setGunner_damage(ship.getTorpedo_bomber().getGunner_damage() * modifier.getFightersPassiveEfficiencyCoefficient());
                    }
                }
                else if (modifier.getTimeStep() != 0) // 2_6
                {
                    float coef = 1 - ((100 - adrenalineValue) * modifier.getTimeStep() / 100);

                    if (ship.getShipComponents().getArtillery() != null)
                    {
                        ship.getShipComponents().getArtillery().setShotDelay(ship.getShipComponents().getArtillery().getShotDelay() * coef);
                    }

                    if (ship.getTorpedoes() != null)
                    {
                        ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * coef);
                    }

                    if (ship.getAtbas() != null)
                    {
                        ship.getAtbas().getSlots().values().forEach(slot -> slot.setShot_delay(slot.getShot_delay() * coef));
                    }

                    if (ship.getShipComponents().getAtba() != null)
                    {
                        ship.getShipComponents().getAtba().getSecondariesList().forEach(secondary -> secondary.setShotDelay(secondary.getShotDelay() * coef));
                    }
                }
                else if (modifier.getCritTimeCoefficient() != 0) // 3_0
                {
                    if (ship.getShipComponents().getHull().getBurnDuration() > 0)
                    {
                        ship.getShipComponents().getHull().setBurnDuration(ship.getShipComponents().getHull().getBurnDuration() * modifier.getCritTimeCoefficient());
                    }
                    if (ship.getShipComponents().getHull().getFloodDuration() > 0)
                    {
                        ship.getShipComponents().getHull().setFloodDuration(ship.getShipComponents().getHull().getFloodDuration() * modifier.getCritTimeCoefficient());
                    }

//                    ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
//                    {
//                        Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);
//
//                        if (tempConsumable.getName().contains("CrashCrew"))
//                        {
//                            tempConsumable.getTypes().values().forEach(cType -> cType.setReloadTime(cType.getReloadTime() * modifier.getCritTimeCoefficient()));
//                        }
//
//                        ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
//                    });
                }
                else if (modifier.getHealthPerLevel() != 0) // 3_1
                {
                    if (ship.getHull() != null)
                    {
                        ship.getHull().setHealth(ship.getHull().getHealth() + warship.getTier() * modifier.getHealthPerLevel());
                    }
                }
                else if (modifier.getLauncherCoefficient() != 0 && modifier.getBomberCoefficient() != 0) // 3_2
                {
                    if (ship.getTorpedoes() != null)
                    {
                        ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * modifier.getLauncherCoefficient());
                    }
                    if (ship.getTorpedo_bomber() != null)
                    {
                        ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * modifier.getBomberCoefficient());
                    }
                }
                else if (modifier.getAirDefenceEfficiencyCoefficient() != 0 && modifier.getSmallGunReloadCoefficient() != 0) // 3_4
                {
                    if (ship.getShipComponents().getArtillery() != null)
                    {
                        int caliber = ship.getShipComponents().getArtillery().getBarrelDiameter();

                        if (caliber <= 139)
                        {
                            ship.getShipComponents().getArtillery().setShotDelay(ship.getShipComponents().getArtillery().getShotDelay() * modifier.getSmallGunReloadCoefficient());
                        }
                    }
                    if (ship.getAnti_aircraft() != null)
                    {
                        ship.getAnti_aircraft().getSlots().values().forEach(value -> value.setAvg_damage(value.getAvg_damage() * modifier.getAirDefenceEfficiencyCoefficient()));
                    }
                    if (ship.getAtbas() != null)
                    {
                        ship.getAtbas().getSlots().entrySet().forEach(entry -> entry.getValue().setShot_delayWithoutDefault(entry.getValue().getShot_delay() * modifier.getSmallGunReloadCoefficient()));
                    }

                    if (ship.getShipComponents().getAtba() != null)
                    {
                        ship.getShipComponents().getAtba().getSecondariesList().forEach(secondary -> secondary.setShotDelay(secondary.getShotDelay() * modifier.getSmallGunReloadCoefficient()));
                    }
                }
                else if (modifier.getAdditionalConsumables() != 0) // 3_5
                {
                    ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                    {
                        Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                        tempConsumable.getTypes().values().forEach(cType -> cType.setNumConsumables(cType.getNumConsumables() + modifier.getAdditionalConsumables()));

                        ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                    });
                }
                else if (modifier.getProbabilityBonus() != 0) // 3_6
                {
                    float burnHundred = modifier.getProbabilityBonus() * 100;

                    if (ship.getArtillery() != null)
                    {
                        ship.getArtillery().getShells().values().forEach(value ->
                        {
                            if (value != null && "HE".equalsIgnoreCase(value.getType()))
                            {
                                value.setBurn_probability(value.getBurn_probability() + burnHundred);
                            }
                        });
                    }

                    if (ship.getAtbas() != null)
                    {
                        ship.getAtbas().getSlots().values().forEach(slot -> slot.setBurn_probability(slot.getBurn_probability() + burnHundred));
                    }

                    if (ship.getDive_bomber() != null)
                    {
                        ship.getDive_bomber().setBomb_burn_probability(ship.getDive_bomber().getBomb_burn_probability() + burnHundred);
                    }

                    if (ship.getShipComponents().getAtba() != null)
                    {
                        ship.getShipComponents().getAtba().getSecondariesList().forEach(secondary ->
                        {
                            if ("HE".equalsIgnoreCase(secondary.getShell().getAmmoType()))
                            {
                                secondary.getShell().setBurnProb(secondary.getShell().getBurnProb() + modifier.getProbabilityBonus());
                            }
                        });
                    }
                }
                else if (modifier.getRangeCoefficient() != 0) // 3_7
                {
                    ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                    {
                        Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                        if (tempConsumable.getName().contains("SonarSearch"))
                        {
                            tempConsumable.getTypes().values().forEach(cType -> cType.setDistTorpedo(cType.getDistTorpedo() * modifier.getRangeCoefficient()));
                        }

                        ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                    });

                    ship.getTorpedoVisibilities().values().forEach(v1 -> v1.values().forEach(v2 -> v2.values().forEach(v3 -> v3.forEach(ts -> ts.getTorpedoes().forEach(tv -> tv.setVisibility(tv.getVisibility() * modifier.getRangeCoefficient()))))));
                }
                else if (modifier.getProbabilityCoefficient() != 0) // 4_1
                {
                    if (ship.getShipComponents() != null)
                    {
                        ship.getShipComponents().getHull().setBurnChanceReduction(ship.getShipComponents().getHull().getBurnChanceReduction() / modifier.getProbabilityCoefficient());
                        ship.getShipComponents().getHull().setBurnNodesSize(ship.getShipComponents().getHull().getBurnNodesSize() - 1);
                    }
                }
                else if ((modifier.getChanceToSetOnFireBonus() != 0 && modifier.getThresholdPenetrationCoefficient() != 0)
                        || (modifier.getChanceToSetOnFireBonusBig() != 0 && modifier.getChanceToSetOnFireBonusSmall() != 0 && modifier.getThresholdPenetrationCoefficientBig() != 0 && modifier.getThresholdPenetrationCoefficientSmall() != 0)) // 4_2
                {
                    float burnHundred = modifier.getChanceToSetOnFireBonus() * 100;
                    float burnHundredBig = modifier.getChanceToSetOnFireBonusBig() * 100;
                    float burnHundredSmall = modifier.getChanceToSetOnFireBonusSmall() * 100;

                    if (ship.getArtillery() != null)
                    {
                        int caliber = shipComponents.getArtillery().getBarrelDiameter();

                        ship.getArtillery().getShells().values().forEach(value ->
                        {
                            if (value != null && "HE".equalsIgnoreCase(value.getType()))
                            {
                                if (caliber <= 139 && burnHundredSmall != 0)
                                {
                                    value.setBurn_probability(value.getBurn_probability() + burnHundredSmall > 0 ? value.getBurn_probability() + burnHundredSmall : 0);
                                }
                                else if (caliber > 139 && burnHundredBig != 0)
                                {
                                    value.setBurn_probability(value.getBurn_probability() + burnHundredBig > 0 ? value.getBurn_probability() + burnHundredBig : 0);
                                }
                                else
                                {
                                    value.setBurn_probability(value.getBurn_probability() + burnHundred > 0 ? value.getBurn_probability() + burnHundred : 0);
                                }
                            }
                        });
                    }

                    if (ship.getAtbas() != null)
                    {
                        ship.getAtbas().getSlots().values().forEach(slot ->
                        {
                            if (slot.getBurn_probability() > 0)
                            {
                                if (burnHundred != 0)
                                {
                                    slot.setBurn_probability(slot.getBurn_probability() + burnHundred);
                                }
                                else if (burnHundredBig != 0 && burnHundredSmall != 0)
                                {
                                    slot.setBurn_probability(slot.getBurn_probability() + burnHundredSmall);
                                }
                            }
                        });
                    }

                    if (ship.getShipComponents().getArtillery() != null && ship.getShipComponents().getArtillery().getHEShell() != null)
                    {
                        int caliber = shipComponents.getArtillery().getBarrelDiameter();

                        if (modifier.getThresholdPenetrationCoefficient() != 0)
                        {
                            ship.getShipComponents().getArtillery().getHEShell().setPenetrationIFHE(Math.round((ship.getShipComponents().getArtillery().getHEShell().getAlphaPiercingHE() + 1.0f) * modifier.getThresholdPenetrationCoefficient() - 1.0f));
                        }
                        else if (modifier.getThresholdPenetrationCoefficientBig() != 0 && caliber > 139)
                        {
                            ship.getShipComponents().getArtillery().getHEShell().setPenetrationIFHE(Math.round((ship.getShipComponents().getArtillery().getHEShell().getAlphaPiercingHE() + 1.0f) * modifier.getThresholdPenetrationCoefficientBig() - 1.0f));
                        }
                        else if (modifier.getThresholdPenetrationCoefficientSmall() != 0 && caliber <= 139)
                        {
                            ship.getShipComponents().getArtillery().getHEShell().setPenetrationIFHE(Math.round((ship.getShipComponents().getArtillery().getHEShell().getAlphaPiercingHE() + 1.0f) * modifier.getThresholdPenetrationCoefficientSmall() - 1.0f));
                        }
                    }

                    if (ship.getShipComponents().getAtba() != null)
                    {
                        ship.getShipComponents().getAtba().getSecondariesList().forEach(secondary ->
                        {
                            if ("HE".equalsIgnoreCase(secondary.getShell().getAmmoType()))
                            {
                                if (burnHundred != 0)
                                {
                                    secondary.getShell().setBurnProb(secondary.getShell().getBurnProb() + modifier.getChanceToSetOnFireBonus());
                                }
                                else if (burnHundredBig != 0 && burnHundredSmall != 0)
                                {
                                    secondary.getShell().setBurnProb(secondary.getShell().getBurnProb() + modifier.getChanceToSetOnFireBonusSmall());
                                }
                            }
                        });
                    }
                }
                else if (modifier.getDiveBomber() != 0 || modifier.getFighter() != 0 || modifier.getTorpedoBomber() != 0) // 4_3
                {
                    if (ship.getDive_bomber() != null)
                    {
                        ship.getDive_bomber().getCount_in_squadron().setMax(ship.getDive_bomber().getCount_in_squadron().getMax() + modifier.getDiveBomber());
                    }
                    if (ship.getFighters() != null)
                    {
                        ship.getFighters().getCount_in_squadron().setMax(ship.getFighters().getCount_in_squadron().getMax() + modifier.getFighter());
                    }
                    if (ship.getTorpedo_bomber() != null)
                    {
                        ship.getTorpedo_bomber().getCount_in_squadron().setMax(ship.getTorpedo_bomber().getCount_in_squadron().getMax() + modifier.getTorpedoBomber());
                    }
                }
                else if (modifier.getAirDefenceRangeCoefficient() != 0 && modifier.getSmallGunRangeCoefficient() != 0) // 4_4
                {
                    if (ship.getShipComponents().getArtillery() != null)
                    {
                        int caliber = ship.getShipComponents().getArtillery().getBarrelDiameter();
                        if (caliber <= 139)
                        {
                            float tempRatio = ship.getShipComponents().getArtillery().getMaxDist() / 1000f /  ship.getArtillery().getMax_dispersion();
                            ship.getShipComponents().getArtillery().setMaxDist(ship.getShipComponents().getArtillery().getMaxDist() * modifier.getSmallGunRangeCoefficient());
                            ship.getArtillery().setMax_dispersion(ship.getArtillery().getDistance() / tempRatio);
                        }
                    }
                    if (ship.getAtbas() != null)
                    {
                        ship.getAtbas().setDistance(ship.getAtbas().getDistance() * modifier.getSmallGunRangeCoefficient());
                    }
                    if (ship.getAnti_aircraft() != null)
                    {
                        ship.getAnti_aircraft().getSlots().values().forEach(value -> value.setDistance(value.getDistance() * modifier.getAirDefenceRangeCoefficient()));
                    }

                    if (ship.getShipComponents().getAtba() != null)
                    {
                        ship.getShipComponents().getAtba().setMaxDist(ship.getShipComponents().getAtba().getMaxDist() * modifier.getSmallGunRangeCoefficient());
                    }
                }
                else if (modifier.getAirDefenceSelectedTargetCoefficient() != 0) // 4_5
                {
                    if (ship.getAnti_aircraft() != null)
                    {
                        ship.getAnti_aircraft().getSlots().values().forEach(value ->
                        {
                            if (value.getCaliber() > 85)
                            {
                                value.setAvg_damage(value.getAvg_damage() * modifier.getAirDefenceSelectedTargetCoefficient());
                            }
                        });
                    }
                }
                else if (modifier.getAircraftCarrierCoefficient() != 0 && modifier.getBattleshipCoefficient() != 0 && modifier.getCruiserCoefficient() != 0 && modifier.getDestroyerCoefficient() != 0) // 4_7
                {
                    if (ship.getConcealment() != null)
                    {
                        float detect_coef = 0;

                        if (warship.getDefaultType().equals("AirCarrier"))
                        {
                            detect_coef = modifier.getAircraftCarrierCoefficient();
                        }
                        else if (warship.getDefaultType().equals("Battleship"))
                        {
                            detect_coef = modifier.getBattleshipCoefficient();
                        }
                        else if (warship.getDefaultType().equals("Cruiser"))
                        {
                            detect_coef = modifier.getCruiserCoefficient();
                        }
                        else if (warship.getDefaultType().equals("Destroyer"))
                        {
                            detect_coef = modifier.getDestroyerCoefficient();
                        }

                        ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * detect_coef);
                        ship.getConcealment().setDetect_distance_by_plane(ship.getConcealment().getDetect_distance_by_plane() * detect_coef);

//                        if (ship.getShipComponents().getHull().getVisibilityCoefGKInSmoke() != 0)
//                        {
//                            ship.getShipComponents().getHull().setVisibilityCoefGKInSmoke(ship.getShipComponents().getHull().getVisibilityCoefGKInSmoke() * detect_coef);
//                        }
                    }
                }
            });
        }

        if (ship.getShipComponents().getArtillery() != null)
        {
            if (ship.getShipComponents().getArtillery().getAPShell() != null)
            {
                ship.getShipComponents().getArtillery().getAPShell().setMaxDist(ship.getShipComponents().getArtillery().getMaxDist());
            }
            if (ship.getShipComponents().getArtillery().getHEShell() != null)
            {
                ship.getShipComponents().getArtillery().getHEShell().setMaxDist(ship.getShipComponents().getArtillery().getMaxDist());
            }
        }

        return ship;
    }

    private void setFlagsModernization(Ship ship, Consumables consumables)
    {
        if (ship.getTorpedoVisibilities().size() > 0)
        {
            if (consumables.getProfile().getVisionTorpedoCoeff() != null)
            {
                ship.getTorpedoVisibilities().values().forEach(v1 -> v1.values().forEach(v2 -> v2.values().forEach(v3 -> v3.forEach(ts -> ts.getTorpedoes().forEach(tv -> tv.setVisibility(tv.getVisibility() * consumables.getProfile().getVisionTorpedoCoeff().getValue()))))));
            }
        }

        if (ship.getAnti_aircraft() != null)
        {
            if (consumables.getProfile().getAAAura() != null)
            {
                ship.getAnti_aircraft().getSlots().values().forEach(aa -> aa.setAvg_damage(aa.getAvg_damage() * consumables.getProfile().getAAAura().getValue()));
            }
            else if (consumables.getProfile().getAAMaxDist() != null)
            {
                ship.getAnti_aircraft().getSlots().values().forEach(aa -> aa.setDistance(aa.getDistance() * consumables.getProfile().getAAMaxDist().getValue()));
            }
            else if (consumables.getProfile().getADMaxHP() != null)
            {

            }
        }

        if (ship.getShipComponents().getArtillery() != null)
        {
            int caliber = ship.getShipComponents().getArtillery().getBarrelDiameter();
            if (consumables.getProfile().getBurnChanceFactorBig() != null)
            {
                if (caliber >= 160)
                {
                    ship.getArtillery().getShells().values().forEach(value ->
                    {
                        if (value != null && value.getBurn_probability() != 0)
                        {
                            value.setBurn_probability(value.getBurn_probability() + 1);
                        }
                    });
                }
            }

            if (consumables.getProfile().getBurnChanceFactorSmall() != null)
            {
                if (caliber < 160)
                {
                    ship.getArtillery().getShells().values().forEach(value ->
                    {
                        if (value != null && value.getBurn_probability() != 0)
                        {
                            value.setBurn_probability(value.getBurn_probability() + 0.5f);
                        }
                    });
                }
            }

            if (consumables.getProfile().getGMIdealRadius() != null)
            {
                ship.getArtillery().setMax_dispersion(ship.getArtillery().getMax_dispersion() * consumables.getProfile().getGMIdealRadius().getValue());
            }

            if (consumables.getProfile().getGMMaxDist() != null)
            {
                float tempRatio = ship.getArtillery().getDistance() / ship.getArtillery().getMax_dispersion();
                ship.getShipComponents().getArtillery().setMaxDist(ship.getShipComponents().getArtillery().getMaxDist() * consumables.getProfile().getGMMaxDist().getValue());
                ship.getArtillery().setMax_dispersion(ship.getShipComponents().getArtillery().getMaxDist() / 1000f / tempRatio);
            }

            if (consumables.getProfile().getGMRotationSpeed() != null)
            {
                ship.getShipComponents().getArtillery().setRotationDeg(ship.getShipComponents().getArtillery().getRotationDeg() * consumables.getProfile().getGMRotationSpeed().getValue());
            }

            if (consumables.getProfile().getGMShotDelay() != null)
            {
                ship.getShipComponents().getArtillery().setShotDelay(ship.getShipComponents().getArtillery().getShotDelay() * consumables.getProfile().getGMShotDelay().getValue());
            }
        }

        if (ship.getAtbas() != null)
        {
            if (consumables.getProfile().getBurnChanceFactorSmall() != null)
            {
                ship.getAtbas().getSlots().values().forEach(sg ->
                {
                    if (sg.getBurn_probability() != 0)
                    {
                        sg.setBurn_probability(sg.getBurn_probability() + 0.5f);
                    }
                });
            }

            if (consumables.getProfile().getGSMaxDist() != null)
            {
                ship.getAtbas().setDistance(ship.getAtbas().getDistance() * consumables.getProfile().getGSMaxDist().getValue());
            }

            if (consumables.getProfile().getGSShotDelay() != null)
            {
                ship.getAtbas().getSlots().values().forEach(sg -> sg.setShot_delay(sg.getShot_delay() * consumables.getProfile().getGSShotDelay().getValue()));
            }
        }

        if (ship.getShipComponents().getAtba() != null)
        {
            if (consumables.getProfile().getBurnChanceFactorSmall() != null)
            {
                ship.getShipComponents().getAtba().getSecondariesList().forEach(secondary ->
                {
                    if ("HE".equalsIgnoreCase(secondary.getShell().getAmmoType()))
                    {
                        secondary.getShell().setBurnProb(secondary.getShell().getBurnProb() + 0.005f);
                    }
                });
            }

            if (consumables.getProfile().getGSMaxDist() != null)
            {
                ship.getShipComponents().getAtba().setMaxDist(ship.getShipComponents().getAtba().getMaxDist() * consumables.getProfile().getGSMaxDist().getValue());
            }

            if (consumables.getProfile().getGSShotDelay() != null)
            {
                ship.getShipComponents().getAtba().getSecondariesList().forEach(secondary -> secondary.setShotDelay(secondary.getShotDelay() * consumables.getProfile().getGSShotDelay().getValue()));
            }
        }

        if (ship.getConcealment() != null)
        {
            if (consumables.getProfile().getVisibilityDistCoeff() != null)
            {
                ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * consumables.getProfile().getVisibilityDistCoeff().getValue());
                ship.getConcealment().setDetect_distance_by_plane(ship.getConcealment().getDetect_distance_by_plane() * consumables.getProfile().getVisibilityDistCoeff().getValue());

//                if (ship.getShipComponents().getHull().getVisibilityCoefGKInSmoke() != 0)
//                {
//                    ship.getShipComponents().getHull().setVisibilityCoefGKInSmoke(ship.getShipComponents().getHull().getVisibilityCoefGKInSmoke() * consumables.getProfile().getVisibilityDistCoeff().getValue());
//                }
            }
        }

        if (ship.getDive_bomber() != null)
        {
            if (consumables.getProfile().getAAPassiveAura() != null)
            {
                ship.getDive_bomber().setGunner_damage(ship.getDive_bomber().getGunner_damage() * consumables.getProfile().getAAPassiveAura().getValue());
            }

            if (consumables.getProfile().getAirplanesAntiAirAura() != null)
            {
                ship.getDive_bomber().setGunner_damage(ship.getDive_bomber().getGunner_damage() * consumables.getProfile().getAirplanesAntiAirAura().getValue());
            }

            if (consumables.getProfile().getAirplanesPrepareTime() != null)
            {
                ship.getDive_bomber().setPrepare_time(ship.getDive_bomber().getPrepare_time() * consumables.getProfile().getAirplanesPrepareTime().getValue());
            }

            if (consumables.getProfile().getAirplanesSpeed() != null)
            {
                ship.getDive_bomber().setCruise_speed(ship.getDive_bomber().getCruise_speed() * consumables.getProfile().getAirplanesSpeed().getValue());
            }

            if (consumables.getProfile().getAirplanesBomberVitalityTime() != null)
            {
                ship.getDive_bomber().setMax_health((int) (ship.getDive_bomber().getMax_health() * consumables.getProfile().getAirplanesBomberVitalityTime().getValue()));
            }
        }

        if (ship.getEngine() != null)
        {

        }

        if (ship.getFighters() != null)
        {
            if (consumables.getProfile().getAirplanesAntiAirAura() != null)
            {
                ship.getFighters().setAvg_damage((int) (ship.getFighters().getAvg_damage() * consumables.getProfile().getAirplanesAntiAirAura().getValue()));
            }

            if (consumables.getProfile().getAirplanesPrepareTime() != null)
            {
                ship.getFighters().setPrepare_time(ship.getFighters().getPrepare_time() * consumables.getProfile().getAirplanesPrepareTime().getValue());
            }

            if (consumables.getProfile().getAirplanesSpeed() != null)
            {
                ship.getFighters().setCruise_speed(ship.getFighters().getCruise_speed() * consumables.getProfile().getAirplanesSpeed().getValue());
            }

            if (consumables.getProfile().getAirplanesFighterVitalityTime() != null)
            {
                ship.getFighters().setMax_health(ship.getFighters().getMax_health() * consumables.getProfile().getAirplanesFighterVitalityTime().getValue());
            }

            if (consumables.getProfile().getAirplanesAmmoCount() != null)
            {
                ship.getFighters().setMax_ammo((int) (ship.getFighters().getMax_ammo() * consumables.getProfile().getAirplanesAmmoCount().getValue()));
            }
        }

        if (ship.getFire_control() != null)
        {

        }

        if (ship.getFlight_control() != null)
        {

        }

        if (ship.getHull() != null)
        {

        }

        if (ship.getMobility() != null)
        {
            if (consumables.getProfile().getSpeedCoef() != null)
            {
                ship.getMobility().setMax_speed(ship.getMobility().getMax_speed() * consumables.getProfile().getSpeedCoef().getValue());
            }

            if (consumables.getProfile().getSGRudderTime() != null)
            {
                ship.getMobility().setRudder_time(ship.getMobility().getRudder_time() * consumables.getProfile().getSGRudderTime().getValue());
            }
        }

        if (ship.getTorpedo_bomber() != null)
        {
            if (consumables.getProfile().getAAPassiveAura() != null)
            {
                ship.getTorpedo_bomber().setGunner_damage(ship.getTorpedo_bomber().getGunner_damage() * consumables.getProfile().getAAPassiveAura().getValue());
            }

            if (consumables.getProfile().getAirplanesAntiAirAura() != null)
            {
                ship.getTorpedo_bomber().setGunner_damage(ship.getTorpedo_bomber().getGunner_damage() * consumables.getProfile().getAirplanesAntiAirAura().getValue());
            }

            if (consumables.getProfile().getAirplanesPrepareTime() != null)
            {
                ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * consumables.getProfile().getAirplanesPrepareTime().getValue());
            }

            if (consumables.getProfile().getAirplanesSpeed() != null)
            {
                ship.getTorpedo_bomber().setCruise_speed(ship.getTorpedo_bomber().getCruise_speed() * consumables.getProfile().getAirplanesSpeed().getValue());
            }

            if (consumables.getProfile().getAirplanesBomberVitalityTime() != null)
            {
                ship.getTorpedo_bomber().setMax_health((int) (ship.getTorpedo_bomber().getMax_health() * consumables.getProfile().getAirplanesBomberVitalityTime().getValue()));
            }
        }

        if (ship.getTorpedoes() != null)
        {
            if (consumables.getProfile().getGTShotDelay() != null)
            {
                ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * consumables.getProfile().getGTShotDelay().getValue());
            }
        }

        if (ship.getShipComponents().getHull().getBurnDuration() > 0)
        {
            if (consumables.getProfile().getBurnTime() != null)
            {
                ship.getShipComponents().getHull().setBurnDuration(ship.getShipComponents().getHull().getBurnDuration() * consumables.getProfile().getBurnTime().getValue());
            }
        }

        if (ship.getShipComponents().getHull().getFloodDuration() > 0)
        {
            if (consumables.getProfile().getFloodTime() != null)
            {
                ship.getShipComponents().getHull().setFloodDuration(ship.getShipComponents().getHull().getFloodDuration() * consumables.getProfile().getFloodTime().getValue());
            }
        }

        if (ship.getShipComponents() != null)
        {
            if (consumables.getProfile().getAbilReloadTimeFactor() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    tempConsumable.getTypes().values().forEach(cType -> cType.setReloadTime(cType.getReloadTime() * consumables.getProfile().getAbilReloadTimeFactor().getValue()));

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getAirDefenseDispWorkTime() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("AirDefenseDisp"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setWorkTime(cType.getWorkTime() * consumables.getProfile().getAirDefenseDispWorkTime().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getCrashCrewWorkTime() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("CrashCrew"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setWorkTime(cType.getWorkTime() * consumables.getProfile().getCrashCrewWorkTime().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getRegenerationHPSpeed() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("RegenCrew"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setRegenerationHPSpeed(cType.getRegenerationHPSpeed() * consumables.getProfile().getRegenerationHPSpeed().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getRlsSearchWorkTime() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("RLSSearch"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setWorkTime(cType.getWorkTime() * consumables.getProfile().getRlsSearchWorkTime().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getSmokeGeneratorWorkTime() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("SmokeGenerator"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setWorkTime(cType.getWorkTime() * consumables.getProfile().getSmokeGeneratorWorkTime().getValue()));
                        tempConsumable.getTypes().values().forEach(cType -> cType.setLifeTime(cType.getLifeTime() * consumables.getProfile().getSmokeGeneratorLifeTime().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getScoutWorkTime() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("Spotter"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setWorkTime(cType.getWorkTime() * consumables.getProfile().getScoutWorkTime().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getSonarSearchWorkTime() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("SonarSearch"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setWorkTime(cType.getWorkTime() * consumables.getProfile().getSonarSearchWorkTime().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getSpeedBoosterWorkTime() != null)
            {
                ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                {
                    Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                    if (tempConsumable.getName().contains("SpeedBooster"))
                    {
                        tempConsumable.getTypes().values().forEach(cType -> cType.setWorkTime(cType.getWorkTime() * consumables.getProfile().getSpeedBoosterWorkTime().getValue()));
                    }

                    ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                });
            }

            if (consumables.getProfile().getBurnChanceFactorBig() != null)
            {
                if (ship.getShipComponents().getDiveBomber() != null)
                {
                    ship.getShipComponents().getDiveBomber().getBomb().setBurnProb(ship.getShipComponents().getDiveBomber().getBomb().getBurnProb() + 0.01f);
                }
            }

            if (consumables.getProfile().getBurnProb() != null)
            {
                ship.getShipComponents().getHull().setBurnChanceReduction(ship.getShipComponents().getHull().getBurnChanceReduction() + (1 - consumables.getProfile().getBurnProb().getValue()));
            }

            if (consumables.getProfile().getEngineForwardUpTime() != null)
            {
                ship.getShipComponents().getEngine().setForwardEngineUpTime(ship.getShipComponents().getEngine().getForwardEngineUpTime() * consumables.getProfile().getEngineForwardUpTime().getValue());
            }

            if (consumables.getProfile().getFloodChanceFactor() != null)
            {
                if (ship.getShipComponents().getTorpedoes() != null)
                {
                    ship.getShipComponents().getTorpedoes().getTorpedo().setUwCritical(ship.getShipComponents().getTorpedoes().getTorpedo().getUwCritical() + (consumables.getProfile().getFloodChanceFactor().getValue() - 1));
                }

                if (ship.getShipComponents().getTorpedoBomber() != null)
                {
                    ship.getShipComponents().getTorpedoBomber().getTorpedo().setUwCritical(ship.getShipComponents().getTorpedoBomber().getTorpedo().getUwCritical() + (consumables.getProfile().getFloodChanceFactor().getValue() - 1));
                }
            }

            if (consumables.getProfile().getFloodProb() != null)
            {
                if (ship.getArmour().getFlood_prob() > 0)
                {
                    ship.getArmour().setFlood_prob((long) (100 - (100 - ship.getArmour().getFlood_prob()) * 0.97));
                    ship.getShipComponents().getHull().setFloodChance(ship.getShipComponents().getHull().getFloodChance() - (1 - consumables.getProfile().getFloodProb().getValue()));
                }
            }
        }
    }

    public long getXp(List<String> shipList, boolean isLive)
    {
        String serverParam = isLive ? "live" : "test";

        String tempString1 = shipList.get(0);
        String tempString2 = shipList.get(1);

        Warship top;
        Warship bottom;

        if (Integer.parseInt(tempString1.split("_")[0]) > Integer.parseInt(tempString2.split("_")[0]))
        {
            top = (Warship) data.get(serverParam).get("rawShipData").get(tempString1.split("_")[1]);
            bottom = (Warship) data.get(serverParam).get("rawShipData").get(tempString2.split("_")[1]);
        }
        else
        {
            top = (Warship) data.get(serverParam).get("rawShipData").get(tempString2.split("_")[1]);
            bottom = (Warship) data.get(serverParam).get("rawShipData").get(tempString1.split("_")[1]);
        }

        if (top.getTier() == bottom.getTier())
        {
            if (!top.getPrevWarship().getName().equalsIgnoreCase(bottom.getName()) && bottom.getPrevWarship().getName().equalsIgnoreCase(top.getName()))
            {
                Warship tempSave = top;
                top = bottom;
                bottom = tempSave;
            }
            else if (!top.getPrevWarship().getName().equalsIgnoreCase(bottom.getName()) && !bottom.getPrevWarship().getName().equalsIgnoreCase(top.getName()))
            {
                return -1;
            }
        }

        boolean isMatch = false;
        Warship tempTop = top;
        int index1 = 0;
        while (tempTop.getTier() >= bottom.getTier())
        {
            if (tempTop.getName().equalsIgnoreCase(bottom.getName()))
            {
                isMatch = true;
                break;
            }
            tempTop = (Warship) data.get(serverParam).get("rawShipData").get(tempTop.getPrevWarship().getName());

            index1++;
            if (index1 > 50)
            {
                return -1;
            }
        }

        if (!isMatch)
        {
            return -1;
        }

        long requiredShipXp = 0;
        long requiredModuleXp = 0;

        Warship temp = top;
        Warship tempNext = top;
        int index2 = 0;
        while (!temp.getName().equalsIgnoreCase(bottom.getName()))
        {
            requiredShipXp = requiredShipXp + temp.getPrevWarship().getNextShipXp();

            temp = (Warship) data.get(serverParam).get("rawShipData").get(temp.getPrevWarship().getName());

            for (Map.Entry<String, WarshipModulesTree> entry : temp.getModules_tree().entrySet())
            {
                if (entry.getValue().getNext_ships() != null && entry.getValue().getNext_ships().contains(tempNext.getShip_id()))
                {
                    if (!entry.getValue().isIs_default())
                    {
                        requiredModuleXp = requiredModuleXp + entry.getValue().getPrice_xp();
                        WarshipModulesTree prevModule = temp.getModules_tree().get(String.valueOf(entry.getValue().getPrev_modules().get(0)));

                        int index3 = 0;
                        while (prevModule != null && !prevModule.isIs_default())
                        {
                            requiredModuleXp = requiredModuleXp + prevModule.getPrice_xp();
                            prevModule = temp.getModules_tree().get(prevModule.getPrev_modules().get(0));

                            index3++;
                            if (index3 > 50)
                            {
                                return -1;
                            }
                        }
                    }
                }
            }
            tempNext = temp;

            index2++;
            if (index2 > 50)
            {
                return -1;
            }
        }

        return requiredShipXp + requiredModuleXp;
    }

    public String shortenUrl(String longUrl) throws Exception
    {
        String accessToken = "c1443fd5d0d9d1fee1ac665995c594c01612f595";
        String bitly = "https://api-ssl.bitly.com/v3/shorten?access_token=" + accessToken + "&longUrl=" + longUrl + "&format=json";

        BitlyData bitlyData = mapper.readValue(new URL(bitly), BitlyData.class);

        if (bitlyData.getStatus_code() == 200)
        {
            Bitly bitlyClass = mapper.convertValue(bitlyData.getData(), Bitly.class);
            return bitlyClass.getUrl();
        }
        return "";
    }

    private void setTorpedoVisibility(Ship ship, long tier, String serverParam)
    {
//        1-1, 2-3, 3-4, 4-5, 5-7, 6-8, 7-9, 8-10, 9-10, 10-10
//        1-1, 2-3, 2-4, 3-5, 4-7, 5-8, 5-9, 6-10, 7-10, 8-10
//        int minTier;
//        int maxTier;
//
//        if (tier == 2)
//        {
//            minTier = 2;
//            maxTier = 3;
//        }
//        else if (tier == 3)
//        {
//            minTier = 2;
//            maxTier = 4;
//        }
//        else if (tier == 4)
//        {
//            minTier = 3;
//            maxTier = 5;
//        }
//        else if (tier == 5)
//        {
//            minTier = 4;
//            maxTier = 7;
//        }
//        else if (tier == 6)
//        {
//            minTier = 5;
//            maxTier = 8;
//        }
//        else
//        {
//            minTier = (int) tier - 2;
//            maxTier = (int) tier + 2 <= 10 ? (int) tier + 2 : 10;
//        }

//        for (int i = minTier; i <= maxTier; i++)
//        {
//            LinkedHashMap<String, LinkedHashMap<String, List<TorpedoShip>>> temp = mapper.convertValue(data.get("torpedoVisibility").get(String.valueOf(i)), new TypeReference<LinkedHashMap<String, LinkedHashMap<String, List<TorpedoShip>>>>(){});
//            ship.getTorpedoVisibilities().put("Tier " + i, temp);
//        }

//        for (int i = 2; i <= 10; i++)
//        {
//            LinkedHashMap<String, LinkedHashMap<String, List<TorpedoShip>>> temp = mapper.convertValue(data.get(serverParam).get("torpedoVisibility").get(String.valueOf(i)), new TypeReference<LinkedHashMap<String, LinkedHashMap<String, List<TorpedoShip>>>>(){});
//            ship.getTorpedoVisibilities().put("Tier " + i, temp);
//        }
    }
}
