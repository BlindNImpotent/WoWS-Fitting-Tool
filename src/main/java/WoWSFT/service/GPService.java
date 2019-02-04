package WoWSFT.service;

import WoWSFT.model.gameparams.modernization.Modernization;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
    private HashMap<String, Object> global;

    private ObjectMapper mapper = new ObjectMapper();

    public LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> getUpgrades() throws Exception
    {
        LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgradesCopy = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(TYPE_UPGRADE)), new TypeReference<LinkedHashMap<Integer, LinkedHashMap<String, Modernization>>>(){});
        upgradesCopy.forEach((slot, upgrades) -> upgrades.forEach((key, upgrade) -> upgrade.getBonus().forEach((ids, val) -> {
            Object desc = global.get(IDS + ids);
            if (desc == null) {
                desc = global.get(IDS + ids.replace("_MODERNIZATION", ""));
            }

            if (desc != null) {
                upgrade.setDescription(upgrade.getDescription() + desc.toString() + ": " + val + "\n");
            }
        })));
        return upgradesCopy;
    }
}
