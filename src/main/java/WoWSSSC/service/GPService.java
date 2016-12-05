package WoWSSSC.service;

import WoWSSSC.model.gameparams.ShipUpgradeInfo.ShipUpgradeInfo;
import WoWSSSC.model.gameparams.Temporary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016-12-05.
 */
@Service
public class GPService
{
    @Autowired
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    @Qualifier(value = "nameToId")
    private HashMap<String, String> nameToId;

    @Autowired
    @Qualifier(value = "idToName")
    private HashMap<String, String> idToName;

    ObjectMapper mapper = new ObjectMapper();

    public HashMap<String, LinkedHashMap> setShipGP(
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
    )
    {
        HashMap<String, LinkedHashMap> temp = new HashMap<>();
        if (!ship_id.equals(""))
        {
            ShipUpgradeInfo shipUpgradeInfo = mapper.convertValue(gameParamsCHM.get(ship_id), Temporary.class).getShipUpgradeInfo();

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
            moduleNames.remove(null);

            for (String name : moduleNames)
            {
                String prev = shipUpgradeInfo.getModules().get(name).getPrev();
                if (!prev.equals(""))
                {
                    HashSet<String> prevNext = shipUpgradeInfo.getModules().get(prev).getNext();

                    if (!moduleNames.contains(prev) && prevNext.stream().filter(nm -> moduleNames.contains(nm)).count() == 0)
                    {
                        return null;
                    }
                }
                shipUpgradeInfo.getModules().get(name).getComponents().getShipComponents().entrySet().forEach(sc -> sc.getValue().forEach(scv -> temp.put(sc.getKey(), ((HashMap<String, LinkedHashMap>) gameParamsCHM.get(ship_id)).get(scv))));
            }
            return temp;
        }
        return null;
    }
}
