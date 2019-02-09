package WoWSFT.service;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.component.artillery.Shell;
import WoWSFT.model.gameparams.ship.component.atba.ATBA;
import WoWSFT.model.gameparams.ship.component.engine.Engine;
import WoWSFT.model.gameparams.ship.component.firecontrol.FireControl;
import WoWSFT.model.gameparams.ship.component.hull.Hull;
import WoWSFT.model.gameparams.ship.component.torpedo.Torpedo;
import WoWSFT.model.gameparams.ship.component.torpedo.TorpedoAmmo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static WoWSFT.model.Constant.*;

/**
 * Created by Aesis on 2016-12-05.
 */
@Service
@Slf4j
public class GPService
{
    @Autowired
    @Qualifier(value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    @Autowired
    @Qualifier (value = TYPE_SHIP)
    private LinkedHashMap<String, Ship> ships;

    @Autowired
    @Qualifier (value =  TYPE_CONSUMABLE)
    private LinkedHashMap<String, Consumable> consumables;

    @Autowired
    @Qualifier (value = TYPE_SHIP_LIST)
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList;

    @Autowired
    @Qualifier (value = TYPE_UPGRADE)
    private LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades;

    @Autowired
    @Qualifier (value = TYPE_COMMANDER)
    private LinkedHashMap<String, Commander> commanders;

    @Autowired
    private ParserService parserService;

    private ObjectMapper mapper = new ObjectMapper();

    public Ship getShip(String index, String language, String bits) throws Exception
    {
        Ship ship = mapper.readValue(mapper.writeValueAsString(ships.get(index)), Ship.class);
        List<List<Consumable>> consumablesList = new ArrayList<>();

        if (ship != null) {
            for (Map.Entry<String, AbilitySlot> entry : ship.getShipAbilities().entrySet()) {
                for (List<String> consumable : entry.getValue().getAbils()) {
                    if (!consumable.get(0).contains("Super")) {
                        Consumable tempConsumable = mapper.readValue(mapper.writeValueAsString(consumables.get(consumable.get(0))), Consumable.class);
                        tempConsumable.getSubConsumables().entrySet().removeIf(e -> !e.getKey().equalsIgnoreCase(consumable.get(1)));

                        Object consDesc = global.get(language).get(IDS + CONSUME + DESCRIPTION + tempConsumable.getName().toUpperCase());
                        if (consDesc != null) {
                            tempConsumable.setDescription(tempConsumable.getDescription() + consDesc.toString().replace("\n\n", "\n") + "\n\n");
                        }

                        tempConsumable.getSubConsumables().get(consumable.get(1)).getBonus().forEach((id, val) -> {
                            Object desc = global.get(language).get(IDS + id);
                            if (desc != null) {
                                tempConsumable.setDescription(tempConsumable.getDescription() + desc.toString() + ": " + val + "\n");
                            }
                        });

                        if (consumablesList.size() < entry.getValue().getSlot() + 1) {
                            consumablesList.add(new ArrayList<>());
                        }
                        consumablesList.get(entry.getValue().getSlot()).add(tempConsumable);
                    }
                }
            }
            ship.setConsumables(consumablesList);

            setShipModules(index, bits, ship);
            setShipAmmo(ship);
        }

        return ship;
    }

    private void setShipModules(String index, String bits, Ship ship)
    {
        parserService.setModules(index, bits, ship.getModules(), ship.getPositions());

        ship.getModules().forEach((cKey, value) -> {
            if (cKey.equalsIgnoreCase(artillery)) {
                LinkedHashMap<String, Artillery> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getArtillery().get(value));
                ship.getComponents().setArtillery(tComponent);
            } else if (cKey.equalsIgnoreCase(airDefense)) {
                LinkedHashMap<String, AirDefense> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getAirDefense().get(value));
                ship.getComponents().setAirDefense(tComponent);
            } else if (cKey.equalsIgnoreCase(atba)) {
                LinkedHashMap<String, ATBA> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getAtba().get(value));
                ship.getComponents().setAtba(tComponent);
            } else if (cKey.equalsIgnoreCase(engine)) {
                LinkedHashMap<String, Engine> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getEngine().get(value));
                ship.getComponents().setEngine(tComponent);
            } else if (cKey.equalsIgnoreCase(suo)) {
                LinkedHashMap<String, FireControl> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getSuo().get(value));
                ship.getComponents().setSuo(tComponent);
            } else if (cKey.equalsIgnoreCase(hull)) {
                LinkedHashMap<String, Hull> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getHull().get(value));
                ship.getComponents().setHull(tComponent);
            } else if (cKey.equalsIgnoreCase(torpedoes)) {
                LinkedHashMap<String, Torpedo> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getTorpedoes().get(value));
                ship.getComponents().setTorpedoes(tComponent);
            } else if (cKey.equalsIgnoreCase(airArmament)) {
                LinkedHashMap<String, Object> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getAirArmament().get(value));
                ship.getComponents().setAirArmament(tComponent);
            } else if (cKey.equalsIgnoreCase(flightControl)) {
                LinkedHashMap<String, Object> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getFlightControl().get(value));
                ship.getComponents().setFlightControl(tComponent);
            } else if (cKey.equalsIgnoreCase(fighter)) {
                LinkedHashMap<String, Object> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getFighter().get(value));
                ship.getComponents().setFighter(tComponent);
            } else if (cKey.equalsIgnoreCase(diveBomber)) {
                LinkedHashMap<String, Object> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getDiveBomber().get(value));
                ship.getComponents().setDiveBomber(tComponent);
            } else if (cKey.equalsIgnoreCase(torpedoBomber)) {
                LinkedHashMap<String, Object> tComponent = new LinkedHashMap<>();
                tComponent.put(value, ship.getComponents().getTorpedoBomber().get(value));
                ship.getComponents().setTorpedoBomber(tComponent);
            }
        });
    }

    private void setShipAmmo(Ship ship)
    {
        if (ship != null && ship.getComponents() != null) {
            if (ship.getComponents().getArtillery().size() > 0) {

            }

            if (ship.getComponents().getTorpedoes().size() > 0) {
                TorpedoAmmo ammo = mapper.convertValue(gameParamsHM.get(ship.getComponents().getTorpedoes().get(ship.getModules().get(torpedoes)).getLaunchers().get(0).getAmmoList().get(0)), TorpedoAmmo.class);
                ship.getComponents().getTorpedoes().get(ship.getModules().get(torpedoes)).setAmmo(ammo);
            }
        }
    }

    public LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> getUpgrades(String language) throws Exception
    {
        LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgradesCopy = mapper.readValue(mapper.writeValueAsString(upgrades), new TypeReference<LinkedHashMap<Integer, LinkedHashMap<String, Modernization>>>(){});
        upgradesCopy.forEach((slot, upgrades) -> upgrades.forEach((key, upgrade) -> {
            Object uDesc = global.get(language).get(IDS + DESC + upgrade.getName().toUpperCase());
            if (uDesc != null) {
                upgrade.setDescription(upgrade.getDescription() + uDesc.toString().replace("\n\n", "\n") + "\n\n");
            }

            upgrade.getBonus().forEach((id, val) -> {
                Object desc = global.get(language).get(IDS + id);
                if (desc == null) {
                    desc = global.get(language).get(IDS + id.replace("_MODERNIZATION", ""));
                }

                if (desc != null) {
                    upgrade.setDescription(upgrade.getDescription() + desc.toString() + ": " + val + "\n");
                }
            });
        }));

        return upgradesCopy;
    }

    public Commander getCommander(String language) throws Exception
    {
        Commander commander = mapper.readValue(mapper.writeValueAsString(commanders.get("PAW001_DefaultCrew")), Commander.class);

        commander.getCSkills().forEach(row -> row.forEach(skill -> {
            Object skillDesc = global.get(language).get(IDS + "SKILL_DESC_" + skill.getModifier().toUpperCase());
            if (skillDesc != null) {
                skill.setDescription(skill.getDescription() + skillDesc.toString().replace("\n\n", "\n") + "\n\n");
            }

            skill.getBonus().forEach((id, val) -> {
                Object desc = global.get(language).get(IDS + id);
                if (desc != null) {
                    skill.setDescription(skill.getDescription() + desc.toString() + ": " + val + "\n");
                }
            });
        }));

        return commander;
    }

    private void setPenetration(Shell ArtyShell, float maxVertAngle, float minDistV, float maxDist, boolean apShell)
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

        List<BigDecimal> alpha = linspace(0f, 0.001f, (float) Math.PI * maxVertAngle / 180f);

        float dt = 0.1f; // TIME STEP

        LinkedHashMap<Float, Float> penetration = new LinkedHashMap<>();
        LinkedHashMap<Float, Float> flightTime = new LinkedHashMap<>();
        LinkedHashMap<Float, Float> impactAngle = new LinkedHashMap<>();
        LinkedHashMap<Float, Float> vertPlus = new LinkedHashMap<>();
        LinkedHashMap<Float, Float> vertMinus = new LinkedHashMap<>();
        List<Float> distanceList = new ArrayList<>();

        float maxDistCalc = 0f;
        float maxDistTemp_1 = 0f;
        float maxDistTemp_2 = 0f;
        float maxDistTemp_Mid = 0f;
        BigDecimal maxDistAngle_1 = new BigDecimal(0);
        BigDecimal maxDistAngle_2 = new BigDecimal(0);
        int index_1 = 0;
        int index_2 = 0;
        int indexJ = -1;
        float arcDef = 0f;
        float tempX = 0f;
        for (int i = 0; i < alpha.size(); i++) // for each alpha angle do:
        {
            float v_x = (float) Math.cos(alpha.get(i).floatValue()) * V_0;
            float v_y = (float) Math.sin(alpha.get(i).floatValue()) * V_0;
            float y = 0f;
            float x = 0f;
            float t = 0f;

            float tX_1 = 0f;
            float tX_2 = 0f;
            float tY_1 = 0f;
            float tY_2 = 0f;
            boolean tempNext = true;

            while (tempNext) // follow flight path until shell hits ground again
            {
                tempNext = y >= 0f;
                if (tempNext) {
                    tX_1 = x;
                    tY_1 = y;
                }
                else {
                    tX_2 = x;
                    tY_2 = y;
                }

                x = x + dt * v_x;
                y = y + dt * v_y;

                float T = T_0 - L * y;
                float p = p_0 * (float) Math.pow(1 - L * y / T_0, (a * M / (R * L)));
                float rho = p * M / (R * T);

                v_x = v_x - dt * k * rho * (cw_1 * (float) Math.pow(v_x, 2) + cw_2 * v_x);
                v_y = v_y - dt * a - dt * k * rho * (cw_1 * (float) Math.pow(v_y, 2) + cw_2 * Math.abs(v_y)) * Math.signum(v_y);

                t = t + dt;
            }

            float v_total = (float) Math.pow((Math.pow(v_y, 2f) + Math.pow(v_x, 2f)), 0.5f);
            float p_athit = C * (float) Math.pow(v_total, 1.1f) * (float) Math.pow(W, 0.55f) / (float)Math.pow(D * 1000f, 0.65f); // PENETRATION FORMULA
            float IA = (float) Math.atan(Math.abs(v_y) / Math.abs(v_x)); // IMPACT ANGLE ON BELT ARMOR

            if (x > 40000) {
                break;
            }

            if (x > tempX && x > maxDistCalc)
            {
                tempX = x;
                maxDistCalc = getMidAtY(tX_1, tY_1, tX_2, tY_2, 0f);
                flightTime.put(maxDistCalc, t / 3f);

                if (apShell) {
                    penetration.put(maxDistCalc, (float) Math.cos(IA) * p_athit);
                    impactAngle.put(maxDistCalc, IA * 180f / ((float) Math.PI));
                    distanceList.add(maxDistCalc);

                    if (maxDistCalc <= maxDist) {
                        maxDistAngle_1 = alpha.get(i);
                        index_1 = i;
                        maxDistTemp_1 = maxDistCalc;
                    }
                    else if (maxDistCalc > maxDist && maxDistAngle_2.equals(new BigDecimal(0))) {
                        maxDistAngle_2 = alpha.get(i);
                        index_2 = i;
                        maxDistTemp_2 = maxDistCalc;
                    }

                    if (maxDistTemp_1 > 0f && maxDistTemp_2 > 0f && indexJ == -1) {
                        maxDistTemp_Mid = getMidAtY(maxDistAngle_1.floatValue(), maxDistTemp_1, maxDistAngle_2.floatValue(), maxDistTemp_2, maxDist);
                    }

                    if (maxDistAngle_1.compareTo(new BigDecimal(0)) > 0 && maxDistAngle_2.compareTo(new BigDecimal(0)) > 0 && indexJ == -1) {
                        for (int j = index_1; j < alpha.size(); j++) {
                            float y_high = minDistV / 2f;
                            float v_x_2 = (float) Math.cos(alpha.get(j).floatValue()) * V_0;
                            float v_y_2 = (float) Math.sin(alpha.get(j).floatValue()) * V_0;
                            float y_2 = 0f;
                            float x_2 = 0f;
                            float t_2 = 0f;

                            boolean tempNext_2 = true;

                            float y1 = 0f;
                            float y2 = 0f;
                            boolean first = true;

                            while (tempNext_2) // follow flight path until shell hits ground again
                            {
                                tempNext_2 = y_2 >= 0f;

                                if (x_2 > maxDistCalc) {
                                    if (y_2 >= y_high) {
                                        first = false;
                                        y2 = y_2;
                                    }
                                    else {
                                        if (!first && y1 == 0f) {
                                            y1 = y_2;
                                        }
                                    }
                                }

                                x_2 = x_2 + dt * v_x_2;
                                y_2 = y_2 + dt * v_y_2;

                                float T = T_0 - L * y_2;
                                float p = p_0 * (float) Math.pow(1 - L * y_2 / T_0, (a * M / (R * L)));
                                float rho = p * M / (R * T);

                                v_x_2 = v_x_2 - dt * k * rho * (cw_1 * (float) Math.pow(v_x_2, 2) + cw_2 * v_x_2);
                                v_y_2 = v_y_2 - dt * a - dt * k * rho * (cw_1 * (float) Math.pow(v_y_2, 2) + cw_2 * Math.abs(v_y_2)) * Math.signum(v_y_2);

                                t_2 = t_2 + dt;
                            }

                            if (y1 != 0f && y2 != 0f) {
                                indexJ = j;
                                arcDef = alpha.get(indexJ).floatValue() - maxDistTemp_Mid;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (apShell) {
            float maxDistCalc_3 = 0f;
            for (int i = 0; i < alpha.size(); i++) // for each alpha angle do:
            {
                float x = getDistanceAtAngle(alpha.get(i).floatValue(), V_0, dt, T_0, L, p_0, a, M, R, k, cw_1, cw_2);
                if (x > maxDistCalc_3) {
                    maxDistCalc_3 = x;
                    vertPlus.put(maxDistCalc_3, (getDistanceAtAngle(alpha.get(i).floatValue() + arcDef, V_0, dt, T_0, L, p_0, a, M, R, k, cw_1, cw_2) - maxDistCalc_3));
                    vertMinus.put(maxDistCalc_3, (maxDistCalc_3 - getDistanceAtAngle(alpha.get(i).floatValue() - arcDef, V_0, dt, T_0, L, p_0, a, M, R, k, cw_1, cw_2)));
                }
            }
            ArtyShell.setShell(penetration, flightTime, impactAngle, vertPlus, vertMinus, distanceList, apShell);
        } else {
            ArtyShell.setShell(null, flightTime, null, null, null, null, apShell);
        }
    }

    private float getMidAtY(float x1, float y1, float x2, float y2, float yAxis)
    {
        float a = (y2 - y1) / (x2 - x1);
        float c = y1 - (a * x1);

        return (yAxis - c) / a;
    }

    private float getDistanceAtAngle(float angle, float V_0, float dt, float T_0, float L, float p_0, float a, float M, float R, float k, float cw_1, float cw_2)
    {
        float v_x = (float) Math.cos(angle) * V_0;
        float v_y = (float) Math.sin(angle) * V_0;
        float y = 0f;
        float x = 0f;
        float t = 0f;

        float tX_1 = 0f;
        float tX_2 = 0f;
        float tY_1 = 0f;
        float tY_2 = 0f;
        boolean tempNext = true;

        while (tempNext) // follow flight path until shell hits ground again
        {
            tempNext = y >= 0f;
            if (tempNext) {
                tX_1 = x;
                tY_1 = y;
            }
            else {
                tX_2 = x;
                tY_2 = y;
            }

            x = x + dt * v_x;
            y = y + dt * v_y;

            float T = T_0 - L * y;
            float p = p_0 * (float) Math.pow(1 - L * y / T_0, (a * M / (R * L)));
            float rho = p * M / (R * T);

            v_x = v_x - dt * k * rho * (cw_1 * (float) Math.pow(v_x, 2) + cw_2 * v_x);
            v_y = v_y - dt * a - dt * k * rho * (cw_1 * (float) Math.pow(v_y, 2) + cw_2 * Math.abs(v_y)) * Math.signum(v_y);

            t = t + dt;
        }
        return getMidAtY(tX_1, tY_1, tX_2, tY_2, 0f);
    }

    private List<BigDecimal> linspace(float start, float incremental, float end)
    {
        List<BigDecimal> alpha = new ArrayList<>();
        BigDecimal incrementalBig = new BigDecimal(incremental);
        BigDecimal begin = new BigDecimal(start);
        BigDecimal finish = new BigDecimal(end);
        while (begin.compareTo(finish) <= 0)
        {
            alpha.add(begin);
            begin = begin.add(incrementalBig);
        }

//        Float[] temp = new Float[alpha.size()];
//        temp = alpha.toArray(temp);
//        return temp;
        return alpha;
    }
}
