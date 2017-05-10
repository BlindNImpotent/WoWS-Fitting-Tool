package WoWSSSC.service;

import WoWSSSC.model.gameparams.ShipComponents.Artillery.Artillery;
import WoWSSSC.model.gameparams.ShipComponents.Artillery.APShell;
import WoWSSSC.model.gameparams.ShipComponents.Engine.Engine;
import WoWSSSC.model.gameparams.ShipComponents.Hull.Hull;
import WoWSSSC.model.gameparams.ShipComponents.ShipComponents;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.gameparams.Consumables.Consumable;
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
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    @Qualifier(value = "nameToId")
    private HashMap<String, String> nameToId;

    @Autowired
    @Qualifier(value = "idToName")
    private HashMap<String, String> idToName;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    private static final Logger logger = LoggerFactory.getLogger(GPService.class);

    ObjectMapper mapper = new ObjectMapper();

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
            List<String> modules
    ) throws IllegalAccessException
    {
        if (StringUtils.isEmpty(ship_id))
        {
            ship_id = String.valueOf(((((LinkedHashMap<String, LinkedHashMap<String, Warship>>) data.get("nations").get(nation)).get(shipType)).get(ship)).getShip_id());
        }

        if (StringUtils.isNotEmpty(ship_id))
        {
            ShipComponents shipComponents = new ShipComponents();
            Field[] fields = shipComponents.getClass().getDeclaredFields();

            Temporary tempShip = mapper.convertValue(gameParamsCHM.get(ship_id), Temporary.class);
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

            moduleNames.add(idToName.get(artillery_id));
            moduleNames.add(idToName.get(dive_bomber_id));
            moduleNames.add(idToName.get(engine_id));
            moduleNames.add(idToName.get(fighter_id));
            moduleNames.add(idToName.get(fire_control_id));
            moduleNames.add(idToName.get(flight_control_id));
            moduleNames.add(idToName.get(hull_id));
            moduleNames.add(idToName.get(torpedo_bomber_id));
            moduleNames.add(idToName.get(torpedoes_id));

            if (CollectionUtils.isNotEmpty(modules))
            {
                modules.forEach(module -> moduleNames.add(idToName.get(module)));
            }

            moduleNames.remove(null);

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
                                Artillery artillery = mapper.convertValue(gameParamsCHM.get(ship_id).get(tempList.get(0)), Artillery.class);
                                field.set(shipComponents, artillery);

                                shipComponents.getArtillery().getTurrets().values().forEach(value -> value.getAmmoList().forEach(ammo ->
                                {
                                    String id = nameToId.get(ammo);
                                    APShell APShell = mapper.convertValue(gameParamsCHM.get(id), APShell.class);
                                    if ("AP".equalsIgnoreCase(APShell.getAmmoType()) && shipComponents.getArtillery().getAPShell() == null)
                                    {
                                        setAPPenetration(APShell);
                                        shipComponents.getArtillery().setAPShell(APShell);
                                    }
                                }));
                            }
                            else if (field.getName().equalsIgnoreCase("Engine"))
                            {
                                field.set(shipComponents, mapper.convertValue(gameParamsCHM.get(ship_id).get(tempList.get(0)), Engine.class));
                            }
                            else if (field.getName().equalsIgnoreCase("Hull"))
                            {
                                field.set(shipComponents, mapper.convertValue(gameParamsCHM.get(ship_id).get(tempList.get(0)), Hull.class));
                            }
                            else
                            {
                                field.set(shipComponents, gameParamsCHM.get(ship_id).get(tempList.get(0)));
                            }
                            field.setAccessible(false);
                        }
                    }
                }
            }
            setShipAbilities(shipComponents, ship_id);

            shipComponents.getHull().setWeight(tempShip.getWeight());

            return shipComponents;
        }
        return null;
    }

    private void setShipAbilities(ShipComponents shipComponents, String ship_id)
    {
        ShipAbilities shipAbilities = mapper.convertValue(gameParamsCHM.get(ship_id), Temporary.class).getShipAbilities();

        HashMap<String, Consumable> abilities = new HashMap<>();

        shipAbilities.getAbilitySlot0().getAbils().forEach(list ->
        {
            abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(list.get(0)), Consumable.class));
        });
        shipAbilities.getAbilitySlot1().getAbils().forEach(list ->
        {
            abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(list.get(0)), Consumable.class));
        });
        shipAbilities.getAbilitySlot2().getAbils().forEach(list ->
        {
            abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(list.get(0)), Consumable.class));
        });
        shipAbilities.getAbilitySlot3().getAbils().forEach(list ->
        {
            abilities.put(list.get(0), mapper.convertValue(gameParamsCHM.get(list.get(0)), Consumable.class));
        });

        shipComponents.setShipAbilities(shipAbilities);
        shipComponents.setAbilities(abilities);
    }

    private void setAPPenetration(APShell APShell)
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
        float W = APShell.getBulletMass(); // SHELL WEIGHT
        float D = APShell.getBulletDiametr(); // SHELL DIAMETER
        float c_D = APShell.getBulletAirDrag();  // SHELL DRAG
        float V_0 = APShell.getBulletSpeed(); // SHELL MUZZLE VELOCITY
        float K = APShell.getBulletKrupp(); // SHELL KRUPP

        float cw_1 = 1; // QUADRATIC DRAG COEFFICIENT
        float cw_2 = 100 + 1000 / 3 * D; // LINEAR DRAG COEFFICIENT

        C = C * K / 2400; // KRUPP INCLUSION
        float k = 0.5f * c_D * (float) Math.pow((D / 2), 2) * (float) Math.PI / W; // CONSTANTS TERMS OF DRAG

//        float[] alpha = [0 : 0.001 : 15 / 360 * 2 * Math.PI]; // ELEV. ANGLES 0...15

        Float[] alpha = linspace(0f, 0.001f, (float) Math.PI * 15 / 360 * 2);

        float dt = 0.1f; // TIME STEP

        LinkedHashMap<Float, Float> penetration = new LinkedHashMap<>();

        for (int i = 0; i < alpha.length; i++) // for each alpha angle do:
        {
            float v_x = (float) Math.cos(alpha[i]) * V_0;
            float v_y = (float) Math.sin(alpha[i]) * V_0;
            float y = 0;
            float x = 0;
            float t = 0;

            while (y >= 0) // follow flight path until shell hits ground again
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

            float v_total = (float) Math.pow((Math.pow(v_y, 2) + Math.pow(v_x, 2)), 0.5);
            float p_athit = C * (float) Math.pow(v_total, 1.1) * (float) Math.pow(W, 0.55) / (float)Math.pow(D * 1000, 0.65); // PENETRATION FORMULA
            float IA = (float) Math.atan(Math.abs(v_y) / Math.abs(v_x)); // IMPACT ANGLE ON BELT ARMOR

            penetration.put(x, (float) Math.cos(IA) * p_athit);
        }
        APShell.setPenetration(penetration);
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
