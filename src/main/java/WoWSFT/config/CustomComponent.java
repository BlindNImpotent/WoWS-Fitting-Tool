package WoWSFT.config;

import WoWSFT.parser.JsonParser;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016-10-15.
 */
@Component
@EnableConfigurationProperties(CustomProperties.class)
public class CustomComponent
{
    private JsonParser jsonParser = new JsonParser();

    private LinkedHashMap<String, String> notification = new LinkedHashMap<>();

    private HashMap<String, String> nameToId = new HashMap<>();

    private HashMap<String, String> idToName = new HashMap<>();

    private HashMap<String, HashMap<String, Object>> global = new HashMap<>();

    private HashMap<String, Object> gameParamsHM = new HashMap<>();

    private HashMap<String, Integer> loadFinish = new HashMap<>();

    @Bean (value = "jsonParser")
    public JsonParser jsonParser()
    {
        return jsonParser;
    }

    @Bean (value = "nameToId")
    public HashMap<String, String> nameToId()
    {
        return nameToId;
    }

    @Bean (value = "idToName")
    public HashMap<String, String> idToName()
    {
        return idToName;
    }

    @Bean (value = "notification")
    public LinkedHashMap<String, String> notification()
    {
        return notification;
    }

    @Bean (value = "global")
    public HashMap<String, HashMap<String, Object>> global()
    {
        return global;
    }

    @Bean (value = "gameParamsHM")
    public HashMap<String, Object> gameParamsHM()
    {
        return gameParamsHM;
    }

    @Bean (name = "loadFinish")
    public HashMap<String, Integer> loadFinish()
    {
        loadFinish.put("loadFinish", 0);
        return loadFinish;
    }
}
