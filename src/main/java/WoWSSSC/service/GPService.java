package WoWSSSC.service;

import WoWSSSC.model.ShipComponents;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.gameparams.ShipUpgradeInfo.ShipUpgradeInfo;
import WoWSSSC.model.gameparams.Temporary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Aesis on 2016-12-05.
 */
@Service
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

            ShipUpgradeInfo shipUpgradeInfo;
            try
            {
                shipUpgradeInfo = mapper.convertValue(gameParamsCHM.get(ship_id), Temporary.class).getShipUpgradeInfo();
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
//            modules.forEach(module -> moduleNames.add(idToName.get(module)));
            moduleNames.remove(null);

            for (String name : moduleNames)
            {
                String prev = shipUpgradeInfo.getModules().get(name).getPrev();
                if (!prev.equals(""))
                {
                    HashSet<String> prevNext = shipUpgradeInfo.getModules().get(prev).getNext();

//                    if (!moduleNames.contains(prev) && prevNext.stream().filter(nm -> moduleNames.contains(nm)).count() != prevNext.size())
//                    {
//                        return null;
//                    }
                }

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
                            field.set(shipComponents, gameParamsCHM.get(ship_id).get(tempList.get(0)));
                            field.setAccessible(false);
                        }
                    }
                }
            }
            return shipComponents;
        }
        return null;
    }
}
