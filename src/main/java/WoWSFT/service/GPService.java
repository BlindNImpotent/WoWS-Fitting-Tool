package WoWSFT.service;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    private ObjectMapper mapper = new ObjectMapper();

    public LinkedHashMap<String, Consumable> getConsumables()
    {
        return mapper.convertValue(gameParamsHM.get(TYPE_CONSUMABLE), new TypeReference<LinkedHashMap<String, Consumable>>(){});
    }

    public Ship getShip(Ship oShip, LinkedHashMap<String, Consumable> consumableHM, String language) throws Exception
    {
        Ship ship = mapper.readValue(mapper.writeValueAsString(oShip), Ship.class);

        List<List<Consumable>> consumables = new ArrayList<>();
        if (ship != null) {
            for (Map.Entry<String, AbilitySlot> entry : ship.getShipAbilities().entrySet()) {
                for (List<String> consumable : entry.getValue().getAbils()) {
                    if (!consumable.get(0).contains("Super")) {
                        Consumable tempConsumable = mapper.readValue(mapper.writeValueAsString(consumableHM.get(consumable.get(0))), Consumable.class);
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

                        if (consumables.size() < entry.getValue().getSlot() + 1) {
                            consumables.add(new ArrayList<>());
                        }
                        consumables.get(entry.getValue().getSlot()).add(tempConsumable);
                    }
                }
            }
            ship.setConsumables(consumables);
        }

        return ship;
    }

    public LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> getUpgrades(String language) throws Exception
    {
        LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgradesCopy = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(TYPE_UPGRADE)), new TypeReference<LinkedHashMap<Integer, LinkedHashMap<String, Modernization>>>(){});
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
        LinkedHashMap<String, Commander> cCopy = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(TYPE_COMMANDER)), new TypeReference<LinkedHashMap<String, Commander>>(){});
        Commander commander = cCopy.get("PAW001_DefaultCrew");

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
}
