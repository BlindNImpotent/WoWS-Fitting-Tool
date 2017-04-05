package WoWSSSC.service;

import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.shipprofile.ShipData;
import WoWSSSC.model.WoWSAPI.shipprofile.profile.anti_aircraft.Anti_Aircraft_Slot;
import WoWSSSC.model.WoWSAPI.shipprofile.profile.artillery.Artillery_Slots;
import WoWSSSC.model.WoWSAPI.upgrade.Upgrade;
import WoWSSSC.model.WoWSAPI.upgrade.UpgradeProfile;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.gameparams.Temporary;
import WoWSSSC.parser.APIJsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

    public void setShipAPI(
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
            String torpedoes_id
    ) throws IOException, ExecutionException, InterruptedException
    {
        if (!ship_id.equals(""))
        {
            String url = "https://api.worldofwarships.com/wows/encyclopedia/shipprofile/?application_id=" + APP_ID + "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                    + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id;

            String key = "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                    + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id;

            if (!shipHashMap.containsKey(key))
            {
                ShipData shipData = restTemplate.getForObject(url, ShipData.class);

                if (shipData.getStatus().equals("ok"))
                {
                    logger.info("Requested API for " + nation + " " + shipType + " " + ship + " - " + url);

                    shipHashMap.put(key, shipData.getData().get(ship_id));
                }
            }
            else
            {
                logger.info("Requesting data for " + nation + " " + shipType + " " + ship + " - " + url);

                apiJsonParser.checkShipData(url, key, ship_id, nation, shipType, ship);
            }
        }
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

        if (ship.getHull() != null && gameParamsCHM.get(String.valueOf(ship.getHull().getHull_id())) != null)
        {
            String tGPHullName = (String) gameParamsCHM.get(String.valueOf(ship.getHull().getHull_id())).get("name");

            List<String> tGPShipHullNameList = mapper.convertValue(gameParamsCHM.get(ship_id), Temporary.class).getShipUpgradeInfo().getModules().get(tGPHullName).getComponents().getHull();
//            List<String> tGPShipHullNameList = (List<String>) ((HashMap<String, HashMap>) ((HashMap<String, HashMap>) gameParamsCHM.get(ship_id).get("ShipUpgradeInfo")).get(tGPHullName).get("components")).get("hull");
            if (tGPShipHullNameList.size() == 1)
            {
                String tGPShipHullName = tGPShipHullNameList.get(0);

                ship.getConcealment().setVisibilityCoefGK(Float.parseFloat(new DecimalFormat("#").format((double) ((HashMap) gameParamsCHM.get(ship_id).get(tGPShipHullName)).get("visibilityCoefGK"))));
            }
        }
    }

    public Ship getUpgradeSkillStats(String key, String nation, String shipType, String shipName, String ship_id, HashMap<String, List> upgradesSkills)
    {
        if (shipHashMap.get(key) == null)
        {
            return null;
        }

        Cloner cloner = new Cloner();
        Ship ship = cloner.deepClone(shipHashMap.get(key));

        setCustomValues(ship_id, ship);

        LinkedHashMap<String, LinkedHashMap> nationLHM = (LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation);
        Warship warship = (Warship) nationLHM.get(shipType).get(shipName);

        if (upgradesSkills.get("camouflage") != null)
        {
            boolean camouflage = (boolean) upgradesSkills.get("camouflage").get(0);
            if (camouflage)
            {
                if (ship.getConcealment() != null && !warship.isIs_premium())
                {
                    ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * 0.97f);
                }
            }
            else
            {
                if (ship.getConcealment() != null && warship.isIs_premium())
                {
                    ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() / 0.97f);
                }
            }
        }

//        List<HashMap> flags = upgradesSkills.get("flags");
//        flags.forEach(flag -> {
//            Exterior exterior = (Exterior) data.get("exterior").get(flag.get("flag"));
//            TTC_Coef coef = exterior.getTtc_coef();
//
//
//        });

        List<HashMap> skills = upgradesSkills.get("skills");

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

                }
                else if (skill.get("type_id").equals("1"))
                {

                }
                else if (skill.get("type_id").equals("2"))
                {
                    if (ship.getArtillery() != null)
                    {
                        String[] splitName = ship.getArtillery().getSlots().get("0").getName().split("mm");
                        int caliber = Integer.parseInt(splitName[0].trim());
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
                }
                else if (skill.get("type_id").equals("6"))
                {

                }
                else if (skill.get("type_id").equals("7"))
                {

                }
            }
            else if (skill.get("tier").equals("3"))
            {
                if (skill.get("type_id").equals("0"))
                {

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
                        String[] splitName = ship.getArtillery().getSlots().get("0").getName().split("mm");
                        int caliber = Integer.parseInt(splitName[0].trim());

                        if (caliber <= 139)
                        {
                            ship.getArtillery().setShot_delay(ship.getArtillery().getShot_delay() * 0.9f);
                        }
                    }
                    if (ship.getAnti_aircraft() != null)
                    {
                        ship.getAnti_aircraft().getSlots().values().forEach(value -> value.setAvg_damage(value.getAvg_damage() * 1.2f));
                    }
                }
                else if (skill.get("type_id").equals("5"))
                {

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
                }
                else if (skill.get("type_id").equals("7"))
                {

                }
            }
            else if (skill.get("tier").equals("4"))
            {
                if (skill.get("type_id").equals("0"))
                {

                }
                else if (skill.get("type_id").equals("1"))
                {

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
                        String[] splitName = ship.getArtillery().getSlots().get("0").getName().split("mm");
                        int caliber = Integer.parseInt(splitName[0].trim());

                        if (caliber <= 139)
                        {
                            ship.getArtillery().setDistance(ship.getArtillery().getDistance() * 1.2f);
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

                        if (warship.getType().equals("AirCarrier"))
                        {
                            detect_coef = 0.84f;
                        }
                        else if (warship.getType().equals("Battleship"))
                        {
                            detect_coef = 0.86f;
                        }
                        else if (warship.getType().equals("Cruiser"))
                        {
                            detect_coef = 0.88f;
                        }
                        else if (warship.getType().equals("Destroyer"))
                        {
                            detect_coef = 0.90f;
                        }
                        ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * detect_coef);
                        ship.getConcealment().setDetect_distance_by_plane(ship.getConcealment().getDetect_distance_by_plane() * detect_coef);
                    }
                }
            }
        });

        List<String> upgrades = upgradesSkills.get("upgrades");
        upgrades.forEach(upgrade -> {
            if (!upgrade.equals(""))
            {
                Upgrade temp = (Upgrade) data.get("upgrades").get(upgrade);
                UpgradeProfile tempProfile = temp.getProfile();

                if (tempProfile.getAnti_aircraft() != null)
                {
                    if (ship.getAnti_aircraft() != null && ship.getAnti_aircraft().getSlots() != null)
                    {
                        for (Anti_Aircraft_Slot aas : ship.getAnti_aircraft().getSlots().values())
                        {
                            if (tempProfile.getAnti_aircraft().getDistance_coef() != 0)
                            {
                                aas.setDistance(aas.getDistance() * tempProfile.getAnti_aircraft().getDistance_coef());
                            }
                            if (tempProfile.getAnti_aircraft().getEfficiency_coef() != 0)
                            {
                                aas.setAvg_damage(aas.getAvg_damage() * tempProfile.getAnti_aircraft().getEfficiency_coef());
                            }
                            if (tempProfile.getAnti_aircraft().getHealth_coef() != 0)
                            {

                            }
                        }
                    }
                }
                else if (tempProfile.getArtillery() != null)
                {
                    if (ship.getArtillery() != null)
                    {
                        if (tempProfile.getArtillery().getReload_time_coef() != 0)
                        {
                            ship.getArtillery().setShot_delay(ship.getArtillery().getShot_delay() * tempProfile.getArtillery().getReload_time_coef());
                        }
                        if (tempProfile.getArtillery().getRotation_time_coef() != 0)
                        {
                            ship.getArtillery().setRotation_time(ship.getArtillery().getRotation_time() / tempProfile.getArtillery().getRotation_time_coef());
                        }
                        if (tempProfile.getArtillery().getAmmo_critical_damage_chance_coef() != 0)
                        {

                        }
                        if (tempProfile.getArtillery().getAmmo_detonation_chance_coef() != 0)
                        {

                        }
                        if (tempProfile.getArtillery().getAmmo_repair_time_coef() != 0)
                        {

                        }
                        if (tempProfile.getArtillery().getCritical_damage_chance_coef() != 0)
                        {

                        }
                        if (tempProfile.getArtillery().getRepair_time_coef() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getAtba() != null)
                {
                    if (ship.getAtbas() != null)
                    {
                        if (tempProfile.getAtba().getAccuracy_coef() != 0)
                        {

                        }
                        if (tempProfile.getAtba().getDistance_coef() != 0)
                        {
                            ship.getAtbas().setDistance(ship.getAtbas().getDistance() * tempProfile.getAtba().getDistance_coef());
                        }
                        if (tempProfile.getAtba().getHealth_coef() != 0)
                        {

                        }
                        if (tempProfile.getAtba().getReload_time_coef() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getConcealment() != null)
                {
                    if (ship.getConcealment() != null)
                    {
                        if (tempProfile.getConcealment().getDetect_distance_coef() != 0)
                        {
                            ship.getConcealment().setDetect_distance_by_ship(ship.getConcealment().getDetect_distance_by_ship() * tempProfile.getConcealment().getDetect_distance_coef());
                            ship.getConcealment().setDetect_distance_by_plane(ship.getConcealment().getDetect_distance_by_plane() * tempProfile.getConcealment().getDetect_distance_coef());
                        }
                    }
                }
                else if (tempProfile.getDamage_control() != null)
                {

                }
                else if (tempProfile.getEngine() != null)
                {
                    if (ship.getEngine() != null)
                    {
                        if (tempProfile.getEngine().getCritical_damage_chance_coef() != 0)
                        {

                        }
                        if (tempProfile.getEngine().getMax_backward_power_coef() != 0)
                        {

                        }
                        if (tempProfile.getEngine().getMax_forward_power_coef() != 0)
                        {

                        }
                        if (tempProfile.getEngine().getRepair_time_coef() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getFire_control() != null)
                {
                    if (ship.getArtillery() != null)
                    {
                        if (tempProfile.getFire_control().getAccuracy_coef() != 0)
                        {
                            ship.getArtillery().setMax_dispersion(ship.getArtillery().getMax_dispersion() * tempProfile.getFire_control().getAccuracy_coef());
                        }
                        if (tempProfile.getFire_control().getDistance_coef() != 0)
                        {
                            ship.getArtillery().setDistance(ship.getArtillery().getDistance() * tempProfile.getFire_control().getDistance_coef());
                        }
                    }
                }
                else if (tempProfile.getFlight_control() != null)
                {
                    if (ship.getDive_bomber() != null)
                    {
                        if (tempProfile.getFlight_control().getPrepare_time_coef() != 0)
                        {
                            ship.getDive_bomber().setPrepare_time(ship.getDive_bomber().getPrepare_time() * tempProfile.getFlight_control().getPrepare_time_coef());
                        }
                        if (tempProfile.getFlight_control().getSpeed_coef() != 0)
                        {
                            ship.getDive_bomber().setCruise_speed(ship.getDive_bomber().getCruise_speed() * tempProfile.getFlight_control().getSpeed_coef());
                        }
                    }
                    if (ship.getFighters() != null)
                    {
                        if (tempProfile.getFlight_control().getPrepare_time_coef() != 0)
                        {
                            ship.getFighters().setPrepare_time(ship.getFighters().getPrepare_time() * tempProfile.getFlight_control().getPrepare_time_coef());
                        }
                        if (tempProfile.getFlight_control().getSpeed_coef() != 0)
                        {
                            ship.getFighters().setCruise_speed(ship.getFighters().getCruise_speed() * tempProfile.getFlight_control().getSpeed_coef());
                        }
                    }
                    if (ship.getTorpedo_bomber() != null)
                    {
                        if (tempProfile.getFlight_control().getPrepare_time_coef() != 0)
                        {
                            ship.getTorpedo_bomber().setPrepare_time(ship.getTorpedo_bomber().getPrepare_time() * tempProfile.getFlight_control().getPrepare_time_coef());
                        }
                        if (tempProfile.getFlight_control().getSpeed_coef() != 0)
                        {
                            ship.getTorpedo_bomber().setCruise_speed(ship.getTorpedo_bomber().getCruise_speed() * tempProfile.getFlight_control().getSpeed_coef());
                        }
                    }
                }
                else if (tempProfile.getGuidance() != null)
                {
                    if (ship.getArtillery() != null)
                    {
                        if (tempProfile.getGuidance().getArtillery_rotation_speed() != 0)
                        {
                            ship.getArtillery().setRotation_time(ship.getArtillery().getRotation_time() / tempProfile.getGuidance().getArtillery_rotation_speed());
                        }
                        if (tempProfile.getGuidance().getArtillery_shoot_accuracy() != 0)
                        {
                            ship.getArtillery().setMax_dispersion(ship.getArtillery().getMax_dispersion() * tempProfile.getGuidance().getArtillery_shoot_accuracy());
                        }
                    }
                    if (ship.getAtbas() != null)
                    {
                        if (tempProfile.getGuidance().getAtba_max_dist() != 0)
                        {
                            ship.getAtbas().setDistance(ship.getAtbas().getDistance() * tempProfile.getGuidance().getAtba_max_dist());
                        }
                        if (tempProfile.getGuidance().getAtba_rotation_speed() != 0)
                        {

                        }
                        if (tempProfile.getGuidance().getAtba_shoot_accuracy() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getMainweapon() != null)
                {
                    if (ship.getArtillery() != null)
                    {
                        if (tempProfile.getMainweapon().getArtillery_damage_prob() != 0)
                        {

                        }
                        if (tempProfile.getMainweapon().getArtillery_max_hp() != 0)
                        {

                        }
                        if (tempProfile.getMainweapon().getArtillery_repair_time() != 0)
                        {

                        }
                    }
                    if (ship.getTorpedoes() != null)
                    {
                        if (tempProfile.getMainweapon().getTpd_damage_prob() != 0)
                        {

                        }
                        if (tempProfile.getMainweapon().getTpd_max_hp() != 0)
                        {

                        }
                        if (tempProfile.getMainweapon().getTpd_repair_time() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getPlanes() != null)
                {
                    if (ship.getDive_bomber() != null)
                    {
                        if (tempProfile.getPlanes().getBomber_health_coef() != 0)
                        {
                            ship.getDive_bomber().setMax_health(ship.getDive_bomber().getMax_health() * tempProfile.getPlanes().getBomber_health_coef());
                        }
                        if (tempProfile.getPlanes().getEfficiency_coef() != 0)
                        {
                            ship.getDive_bomber().setGunner_damage(ship.getDive_bomber().getGunner_damage() * tempProfile.getPlanes().getEfficiency_coef());
                        }
                        if (tempProfile.getPlanes().getFighter_health_coef() != 0)
                        {

                        }
                    }
                    if (ship.getFighters() != null)
                    {
                        if (tempProfile.getPlanes().getBomber_health_coef() != 0)
                        {

                        }
                        if (tempProfile.getPlanes().getEfficiency_coef() != 0)
                        {
                            ship.getFighters().setGunner_damage(ship.getFighters().getGunner_damage() * tempProfile.getPlanes().getEfficiency_coef());
                        }
                        if (tempProfile.getPlanes().getFighter_health_coef() != 0)
                        {
                            ship.getFighters().setMax_health(ship.getFighters().getMax_health() * tempProfile.getPlanes().getFighter_health_coef());
                        }
                    }
                    if (ship.getTorpedo_bomber() != null)
                    {
                        if (tempProfile.getPlanes().getBomber_health_coef() != 0)
                        {

                        }
                        if (tempProfile.getPlanes().getEfficiency_coef() != 0)
                        {

                        }
                        if (tempProfile.getPlanes().getFighter_health_coef() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getPowder() != null)
                {
                    if (ship.getArtillery() != null)
                    {
                        if (tempProfile.getPowder().getDetonation_prob() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getSecondweapon() != null)
                {
                    if (ship.getAnti_aircraft() != null)
                    {
                        if (tempProfile.getSecondweapon().getAir_defense_max_hp() != 0)
                        {

                        }
                    }
                    if (ship.getAtbas() != null)
                    {
                        if (tempProfile.getSecondweapon().getAtba_max_hp() != 0)
                        {

                        }
                    }
                }
                else if (tempProfile.getSpotting() != null)
                {

                }
                else if (tempProfile.getSteering() != null)
                {
                    if (ship.getEngine() != null)
                    {
                        if (tempProfile.getSteering().getCritical_damage_chance_coef() != 0)
                        {

                        }
                        if (tempProfile.getSteering().getRepair_time_coef() != 0)
                        {

                        }
                        if (tempProfile.getSteering().getRudder_time_coef() != 0)
                        {

                        }
                    }
                    if (ship.getMobility() != null)
                    {
                        if (tempProfile.getSteering().getCritical_damage_chance_coef() != 0)
                        {

                        }
                        if (tempProfile.getSteering().getRepair_time_coef() != 0)
                        {

                        }
                        if (tempProfile.getSteering().getRudder_time_coef() != 0)
                        {
                            ship.getMobility().setRudder_time(ship.getMobility().getRudder_time() * tempProfile.getSteering().getRudder_time_coef());
                        }
                    }
                }
                else if (tempProfile.getTorpedoes() != null)
                {
                    if (ship.getTorpedoes() != null)
                    {
                        if (tempProfile.getTorpedoes().getCritical_damage_chance_coef() != 0)
                        {

                        }
                        if (tempProfile.getTorpedoes().getReload_time_coef() != 0)
                        {
                            ship.getTorpedoes().setReload_time(ship.getTorpedoes().getReload_time() * tempProfile.getTorpedoes().getReload_time_coef());
                        }
                        if (tempProfile.getTorpedoes().getRepair_time_coef() != 0)
                        {

                        }
                        if (tempProfile.getTorpedoes().getRotation_time_coef() != 0)
                        {
                            ship.getTorpedoes().setRotation_time(ship.getTorpedoes().getRotation_time() / tempProfile.getTorpedoes().getRotation_time_coef());
                        }
                    }
                }
            }
        });

        return ship;
    }
}
