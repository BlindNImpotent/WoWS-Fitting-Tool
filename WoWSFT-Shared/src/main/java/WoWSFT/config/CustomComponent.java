package WoWSFT.config;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.flag.Flag;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static WoWSFT.model.Constant.*;

@Component
@EnableConfigurationProperties(CustomProperties.class)
public class CustomComponent
{
    private LinkedHashMap<String, LinkedHashMap<String, String>> notification = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap<String, String>> translation = new LinkedHashMap<>();
    private HashMap<String, String> nameToId = new HashMap<>();
    private HashMap<String, String> idToName = new HashMap<>();
    private HashMap<String, HashMap<String, Object>> global = new HashMap<>();
    private HashMap<String, Object> gameParamsHM = new HashMap<>();
    private HashMap<String, Integer> loadFinish = new HashMap<>();
    private LinkedHashMap<String, Ship> ships = new LinkedHashMap<>();
    private LinkedHashMap<String, Consumable> consumables = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList = new LinkedHashMap<>();
    private LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades = new LinkedHashMap<>();
    private LinkedHashMap<String, Commander> commanders = new LinkedHashMap<>();
    private LinkedHashMap<String, Flag> flags = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> misc = new LinkedHashMap<>();

    @Bean(value = TYPE_MISC)
    public LinkedHashMap<String, Object> misc()
    {
        return misc;
    }

    @Bean(value = TYPE_SHIP)
    public LinkedHashMap<String, Ship> ships()
    {
        return ships;
    }

    @Bean(value = TYPE_CONSUMABLE)
    public LinkedHashMap<String, Consumable> consumables()
    {
        return consumables;
    }

    @Bean(value = TYPE_SHIP_LIST)
    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList()
    {
        return shipsList;
    }

    @Bean(value = TYPE_UPGRADE)
    public LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades()
    {
        return upgrades;
    }

    @Bean(value = TYPE_COMMANDER)
    public LinkedHashMap<String, Commander> commanders()
    {
        return commanders;
    }

    @Bean(value = TYPE_FLAG)
    public LinkedHashMap<String, Flag> flags()
    {
        return flags;
    }

    @Bean(value = "nameToId")
    public HashMap<String, String> nameToId()
    {
        return nameToId;
    }

    @Bean(value = "idToName")
    public HashMap<String, String> idToName()
    {
        return idToName;
    }

    @Bean(value = "notification")
    public LinkedHashMap<String, LinkedHashMap<String, String>> notification()
    {
        return notification;
    }

    @Bean(value = "translation")
    public LinkedHashMap<String, LinkedHashMap<String, String>> translation()
    {
        return translation;
    }

    @Bean(value = "global")
    public HashMap<String, HashMap<String, Object>> global()
    {
        return global;
    }

    @Bean(value = "gameParamsHM")
    public HashMap<String, Object> gameParamsHM()
    {
        return gameParamsHM;
    }

    @Bean(name = "loadFinish")
    public HashMap<String, Integer> loadFinish()
    {
        loadFinish.put("loadFinish", 0);
        return loadFinish;
    }
}
