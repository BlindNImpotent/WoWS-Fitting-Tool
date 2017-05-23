package WoWSSSC.service;

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
import WoWSSSC.model.gameparams.Temporary;
import WoWSSSC.model.gameparams.test.Values.ShipModernization.Modernization;
import WoWSSSC.parser.APIJsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aesis on 2016-10-15.
 */
@Service
public class APIService
{
    @Autowired
    private String APP_ID;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private APIJsonParser apiJsonParser;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    @Qualifier (value = "gameParamsCHM")
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    private GPService gpService;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

    @Cacheable(value = "shipAPI", key = "#nation#shipType#ship#ship_id#artillery_id#dive_bomber_id#engine_id#fighter_id#fire_control_id#flight_control_id#hull_id#torpedo_bomber_id#torpedoes_id")
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
            String url = "https://api.worldofwarships.com/wows/encyclopedia/shipprofile/?application_id=" + APP_ID + "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                    + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id + "&language=en";

            String key = "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                    + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id;

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
                    }

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

    @CacheEvict(value = "shipAPI", allEntries = true)
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

            for (Artillery_Slots slots : ship.getArtillery().getSlots().values())
            {
                totalGuns = totalGuns + (slots.getBarrels() * slots.getGuns());
            }

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

    public Ship getUpgradeSkillStats(String key, String nation, String shipType, String shipName, String ship_id, String artillery_id, String dive_bomber_id, String engine_id, String fighter_id, String fire_control_id, String flight_control_id, String hull_id, String torpedo_bomber_id, String torpedoes_id, List<String> modules, HashMap<String, List> upgradesSkills, int adrenalineValue) throws Exception
    {
        if (shipHashMap.get(key) == null)
        {
            return null;
        }

        Cloner cloner = new Cloner();
        Ship ship = cloner.deepClone(shipHashMap.get(key));

        Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(shipName);

        ShipComponents shipComponents = gpService.setShipGP(nation, shipType, shipName, ship_id, artillery_id, dive_bomber_id, engine_id, fighter_id, fire_control_id, flight_control_id, hull_id, torpedo_bomber_id, torpedoes_id, modules);
        if (shipComponents.getArtillery() != null)
        {
            shipComponents.getArtillery().setPenetrationHEWithNation(warship.getNation(), warship.getDefaultType(), warship.getName());
        }
        ship.setShipComponents(shipComponents);

        setCustomValues(ship_id, ship);

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
                    Consumables temp = (Consumables) data.get("upgrades").get(flag);

                    setFlagsModernization(ship, shipComponents, temp);
                }
            });
        }

        List<String> upgrades = upgradesSkills.get("upgrades");
        if (upgrades != null)
        {
            upgrades.forEach(upgrade -> {
                if (!StringUtils.isEmpty(upgrade))
                {
                    Consumables temp = (Consumables) data.get("upgrades").get(upgrade);

                    setFlagsModernization(ship, shipComponents, temp);
                }
            });
        }

        List<HashMap> skills = upgradesSkills.get("skills");
        if (skills != null)
        {
            skills.forEach(skill ->
            {
                if (skill.get("tier").equals("1"))
                {
                    if (skill.get("type_id").equals("0"))
                    {

                    }
                    else if (skill.get("type_id").equals("1"))
                    {

                    }
                    else if (skill.get("type_id").equals("2"))
                    {

                    }
                    else if (skill.get("type_id").equals("3"))
                    {
                        float hp_coef = 1.05f;
                        float prep_coef = 0.9f;

                        if (ship.getDive_bomber() != null)
                        {
                            ship.getDive_bomber().setMax_health(ship.getDive_bomber().getMax_health() * hp_coef);
                            ship.getDive_bomber().setPrepare_time(ship.getDive_bomber().getPrepare_time() * prep_coef);
                        }
                        if (ship.getFighters() != null)
                        {
                            ship.getFighters().setMax_health(ship.getFighters().getMax_health() * hp_coef);
                            ship.getFighters().setPrepare_time(ship.getFighters().getPrepare_time() * prep_coef);
                        }
                        if (ship.getTorpedo_bomber() != null)
                        {
                            ship.getTorpedo_bomber().setMax_health(ship.getTorpedo_bomber().getMax_health() * hp_coef);
                            ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * prep_coef);
                        }
                    }
                    else if (skill.get("type_id").equals("4"))
                    {

                    }
                    else if (skill.get("type_id").equals("5"))
                    {

                    }
                    else if (skill.get("type_id").equals("6"))
                    {

                    }
                    else if (skill.get("type_id").equals("7"))
                    {

                    }
                }
                else if (skill.get("tier").equals("2"))
                {
                    if (skill.get("type_id").equals("0"))
                    {

                        ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                        {
                            Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                            if (tempConsumable.getName().contains("CrashCrew"))
                            {
                                tempConsumable.getTypes().values().forEach(cType -> cType.setReloadTime(cType.getReloadTime() * 0.90f));
                            }
                            ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                        });
                    }
                    else if (skill.get("type_id").equals("1"))
                    {
                        ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                        {
                            Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                            tempConsumable.getTypes().values().forEach(cType -> cType.setReloadTime(cType.getReloadTime() * 0.95f));

                            ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                        });
                    }
                    else if (skill.get("type_id").equals("2"))
                    {
                        if (ship.getArtillery() != null)
                        {
//                            String[] splitName = ship.getArtillery().getSlots().get("0").getName().split("mm");
//                            int caliber = Integer.parseInt(splitName[0].trim());
                            int caliber = shipComponents.getArtillery().getBarrelDiameter();
                            float timeToDeg = 180 / ship.getArtillery().getRotation_time();

                            if (caliber <= 139)
                            {
                                timeToDeg = timeToDeg + 2.5f;
                            }
                            else
                            {
                                timeToDeg = timeToDeg + 0.7f;
                            }
                            ship.getArtillery().setRotation_time(180 / timeToDeg);
                        }
                    }
                    else if (skill.get("type_id").equals("3"))
                    {
                        if (ship.getTorpedoes() != null)
                        {
                            ship.getTorpedoes().setTorpedo_speed(ship.getTorpedoes().getTorpedo_speed() + 5);
                            ship.getTorpedoes().setDistance(ship.getTorpedoes().getDistance() * 0.8f);
                        }

                        if (ship.getTorpedo_bomber() != null)
                        {
                            ship.getTorpedo_bomber().setTorpedo_max_speed(ship.getTorpedo_bomber().getTorpedo_max_speed() + 5);
                            ship.getTorpedo_bomber().setTorpedo_distance(ship.getTorpedo_bomber().getTorpedo_distance() * 0.8f);
                        }
                    }
                    else if (skill.get("type_id").equals("4"))
                    {

                    }
                    else if (skill.get("type_id").equals("5"))
                    {
                        if (ship.getDive_bomber() != null)
                        {
                            ship.getDive_bomber().setGunner_damage(ship.getDive_bomber().getGunner_damage() * 1.1f);
                        }

                        if (ship.getTorpedo_bomber() != null)
                        {
                            ship.getTorpedo_bomber().setGunner_damage(ship.getTorpedo_bomber().getGunner_damage() * 1.1f);
                        }
                    }
                    else if (skill.get("type_id").equals("6"))
                    {
                        float coef = 1 - ((100 - adrenalineValue) * 0.2f / 100);

                        if (ship.getArtillery() != null)
                        {
                            ship.getArtillery().setGun_rate(ship.getArtillery().getGun_rate() / coef);
                        }

                        if (ship.getTorpedoes() != null)
                        {
                            ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * coef);
                        }

                        if (ship.getAtbas() != null)
                        {
                            ship.getAtbas().getSlots().values().forEach(slot -> slot.setShot_delay(slot.getShot_delay() * coef));
                        }
                    }
                    else if (skill.get("type_id").equals("7"))
                    {

                    }
                }
                else if (skill.get("tier").equals("3"))
                {
                    if (skill.get("type_id").equals("0"))
                    {
                        if (ship.getBurn() > 0)
                        {
                            ship.setBurn(ship.getBurn() * 0.85f);
                        }
                        if (ship.getFlood() > 0)
                        {
                            ship.setFlood(ship.getFlood() * 0.85f);
                        }

                        ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                        {
                            Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                            if (tempConsumable.getName().contains("CrashCrew"))
                            {
                                tempConsumable.getTypes().values().forEach(cType -> cType.setReloadTime(cType.getReloadTime() * 0.85f));
                            }

                            ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                        });
                    }
                    else if (skill.get("type_id").equals("1"))
                    {
                        if (ship.getHull() != null)
                        {
                            ship.getHull().setHealth(ship.getHull().getHealth() + warship.getTier() * 350);
                        }
                    }
                    else if (skill.get("type_id").equals("2"))
                    {
                        if (ship.getTorpedoes() != null)
                        {
                            ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * 0.9f);
                        }
                        if (ship.getTorpedo_bomber() != null)
                        {
                            ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * 0.8f);
                        }
                    }
                    else if (skill.get("type_id").equals("3"))
                    {

                    }
                    else if (skill.get("type_id").equals("4"))
                    {
                        if (ship.getArtillery() != null)
                        {
//                            String[] splitName = ship.getArtillery().getSlots().get("0").getName().split("mm");
//                            int caliber = Integer.parseInt(splitName[0].trim());
                            int caliber = shipComponents.getArtillery().getBarrelDiameter();

                            if (caliber <= 139)
                            {
                                ship.getArtillery().setGun_rate(ship.getArtillery().getGun_rate() * 1.1f);
                            }
                        }
                        if (ship.getAnti_aircraft() != null)
                        {
                            ship.getAnti_aircraft().getSlots().values().forEach(value -> value.setAvg_damage(value.getAvg_damage() * 1.2f));
                        }
                        if (ship.getAtbas() != null)
                        {
                            ship.getAtbas().getSlots().entrySet().forEach(entry -> entry.getValue().setShot_delayWithoutDefault(entry.getValue().getShot_delay() * 0.9f));
                        }
                    }
                    else if (skill.get("type_id").equals("5"))
                    {
                        ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                        {
                            Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                            tempConsumable.getTypes().values().forEach(cType -> cType.setNumConsumables(cType.getNumConsumables() + 1));

                            ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                        });
                    }
                    else if (skill.get("type_id").equals("6"))
                    {
                        if (ship.getArtillery() != null)
                        {
                            ship.getArtillery().getShells().values().forEach(value ->
                            {
                                if (value != null && value.getBurn_probability() != 0)
                                {
                                    value.setBurn_probability(value.getBurn_probability() + 2);
                                }
                            });
                        }

                        if (ship.getAtbas() != null)
                        {
                            ship.getAtbas().getSlots().values().forEach(slot -> slot.setBurn_probability(slot.getBurn_probability() + 2));
                        }

                        if (ship.getDive_bomber() != null)
                        {
                            ship.getDive_bomber().setBomb_burn_probability(ship.getDive_bomber().getBomb_burn_probability() + 2);
                        }
                    }
                    else if (skill.get("type_id").equals("7"))
                    {
                        ship.getShipComponents().getAbilities().entrySet().forEach(entry ->
                        {
                            Consumable tempConsumable = mapper.convertValue(entry.getValue(), Consumable.class);

                            if (tempConsumable.getName().contains("SonarSearch"))
                            {
                                tempConsumable.getTypes().values().forEach(cType -> cType.setDistTorpedo(cType.getDistTorpedo() * 1.25f));
                            }

                            ship.getShipComponents().getAbilities().put(entry.getKey(), tempConsumable);
                        });
                    }
                }
                else if (skill.get("tier").equals("4"))
                {
                    if (skill.get("type_id").equals("0"))
                    {

                    }
                    else if (skill.get("type_id").equals("1"))
                    {
                        if (ship.getShipComponents() != null)
                        {
                            ship.getShipComponents().getHull().setBurnChanceReduction(ship.getShipComponents().getHull().getBurnChanceReduction() / 0.9);
                            ship.getShipComponents().getHull().setBurnNodesSize(ship.getShipComponents().getHull().getBurnNodesSize() - 1);
                        }

                    }
                    else if (skill.get("type_id").equals("2"))
                    {
                        if (ship.getArtillery() != null)
                        {
                            ship.getArtillery().getShells().values().forEach(value ->
                            {
                                if (value != null && value.getBurn_probability() != 0)
                                {
                                    value.setBurn_probability(value.getBurn_probability() - 3 > 0 ? value.getBurn_probability() - 3 : 0);
                                }
                            });
                        }

                        if (ship.getShipComponents().getArtillery() != null)
                        {
                            ship.getShipComponents().getArtillery().setPenetrationHE(Math.round(ship.getShipComponents().getArtillery().getPenetrationHEFloat() * 1.3));
                        }
                    }
                    else if (skill.get("type_id").equals("3"))
                    {
                        if (ship.getDive_bomber() != null)
                        {
                            ship.getDive_bomber().getCount_in_squadron().setMax(ship.getDive_bomber().getCount_in_squadron().getMax() + 1);
                        }
                        if (ship.getFighters() != null)
                        {
                            ship.getFighters().getCount_in_squadron().setMax(ship.getFighters().getCount_in_squadron().getMax() + 1);
                        }
                    }
                    else if (skill.get("type_id").equals("4"))
                    {
                        if (ship.getArtillery() != null)
                        {
//                            String[] splitName = ship.getArtillery().getSlots().get("0").getName().split("mm");
//                            int caliber = Integer.parseInt(splitName[0].trim());
                            int caliber = shipComponents.getArtillery().getBarrelDiameter();
                            if (caliber <= 139)
                            {
                                float tempRatio = ship.getArtillery().getDistance() / ship.getArtillery().getMax_dispersion();
                                ship.getArtillery().setDistance(ship.getArtillery().getDistance() * 1.2f);
                                ship.getArtillery().setMax_dispersion(ship.getArtillery().getDistance() / tempRatio);
                            }
                        }
                        if (ship.getAtbas() != null)
                        {
                            ship.getAtbas().setDistance(ship.getAtbas().getDistance() * 1.2f);
                        }
                        if (ship.getAnti_aircraft() != null)
                        {
                            ship.getAnti_aircraft().getSlots().values().forEach(value -> value.setDistance(value.getDistance() * 1.2f));
                        }
                    }
                    else if (skill.get("type_id").equals("5"))
                    {
                        if (ship.getAnti_aircraft() != null)
                        {
                            ship.getAnti_aircraft().getSlots().values().forEach(value ->
                            {
                                if (value.getCaliber() > 85)
                                {
                                    value.setAvg_damage(value.getAvg_damage() * 2);
                                }
                            });
                        }
                    }
                    else if (skill.get("type_id").equals("6"))
                    {

                    }
                    else if (skill.get("type_id").equals("7"))
                    {
                        if (ship.getConcealment() != null)
                        {
                            float detect_coef = 0;

                            if (warship.getDefaultType().equals("AirCarrier"))
                            {
                                detect_coef = 0.84f;
                            }
                            else if (warship.getDefaultType().equals("Battleship"))
                            {
                                detect_coef = 0.86f;
                            }
                            else if (warship.getDefaultType().equals("Cruiser"))
                            {
                                detect_coef = 0.88f;
                            }
                            else if (warship.getDefaultType().equals("Destroyer"))
                            {
                                detect_coef = 0.90f;
                            }

                            ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * detect_coef);
                            ship.getConcealment().setDetect_distance_by_plane(ship.getConcealment().getDetect_distance_by_plane() * detect_coef);
                        }
                    }
                }
            });
        }

        return ship;
    }

    private void setFlagsModernization(Ship ship, ShipComponents shipComponents, Consumables consumables)
    {
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

        if (ship.getArtillery() != null)
        {
            int caliber = shipComponents.getArtillery().getBarrelDiameter();
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
                ship.getArtillery().setDistance(ship.getArtillery().getDistance() * consumables.getProfile().getGMMaxDist().getValue());
                ship.getArtillery().setMax_dispersion(ship.getArtillery().getDistance() / tempRatio);
            }

            if (consumables.getProfile().getGMRotationSpeed() != null)
            {
                ship.getArtillery().setRotation_time(ship.getArtillery().getRotation_time() / consumables.getProfile().getGMRotationSpeed().getValue());
            }

            if (consumables.getProfile().getGMShotDelay() != null)
            {
                ship.getArtillery().setGun_rate(ship.getArtillery().getGun_rate() / consumables.getProfile().getGMShotDelay().getValue());
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

        if (ship.getConcealment() != null)
        {
            if (consumables.getProfile().getVisibilityDistCoeff() != null)
            {
                ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * consumables.getProfile().getVisibilityDistCoeff().getValue());
                ship.getConcealment().setDetect_distance_by_plane(ship.getConcealment().getDetect_distance_by_plane() * consumables.getProfile().getVisibilityDistCoeff().getValue());
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

        if (ship.getBurn() > 0)
        {
            if (consumables.getProfile().getBurnTime() != null)
            {
                ship.setBurn(ship.getBurn() * consumables.getProfile().getBurnTime().getValue());
            }
        }

        if (ship.getFlood() > 0)
        {
            if (consumables.getProfile().getFloodTime() != null)
            {
                ship.setFlood(ship.getFlood() * consumables.getProfile().getFloodTime().getValue());
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
                if (shipComponents.getDiveBomber() != null)
                {
                    shipComponents.getDiveBomber().getBomb().setBurnProb(shipComponents.getDiveBomber().getBomb().getBurnProb() + 0.01f);
                }
            }

            if (consumables.getProfile().getBurnProb() != null)
            {
                shipComponents.getHull().setBurnChanceReduction(shipComponents.getHull().getBurnChanceReduction() + 0.05);
            }

            if (consumables.getProfile().getEngineForwardUpTime() != null)
            {
                shipComponents.getEngine().setForwardEngineUpTime(shipComponents.getEngine().getForwardEngineUpTime() * consumables.getProfile().getEngineForwardUpTime().getValue());
            }

            if (consumables.getProfile().getFloodChanceFactor() != null)
            {
                if (ship.getShipComponents().getTorpedoes() != null)
                {
                    ship.getShipComponents().getTorpedoes().getTorpedo().setUwCritical(ship.getShipComponents().getTorpedoes().getTorpedo().getUwCritical() + 0.04f);
                }

                if (ship.getShipComponents().getTorpedoBomber() != null)
                {
                    ship.getShipComponents().getTorpedoBomber().getTorpedo().setUwCritical(ship.getShipComponents().getTorpedoBomber().getTorpedo().getUwCritical() + 0.04f);
                }
            }

            if (consumables.getProfile().getFloodProb() != null)
            {
                shipComponents.getHull().setFloodChance(shipComponents.getHull().getFloodChance() - 0.03);
            }
        }
    }

    public long getXp(List<String> shipList)
    {
        String tempString1 = shipList.get(0);
        String tempString2 = shipList.get(1);

        Warship top;
        Warship bottom;

        if (Integer.parseInt(tempString1.split("_")[0]) > Integer.parseInt(tempString2.split("_")[0]))
        {
            top = (Warship) data.get("rawShipData").get(tempString1.split("_")[1]);
            bottom = (Warship) data.get("rawShipData").get(tempString2.split("_")[1]);
        }
        else
        {
            top = (Warship) data.get("rawShipData").get(tempString2.split("_")[1]);
            bottom = (Warship) data.get("rawShipData").get(tempString1.split("_")[1]);
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
            tempTop = (Warship) data.get("rawShipData").get(tempTop.getPrevWarship().getName());

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

            temp = (Warship) data.get("rawShipData").get(temp.getPrevWarship().getName());

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
}
