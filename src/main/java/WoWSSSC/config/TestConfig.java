package WoWSSSC.config;

import WoWSSSC.parser.APIJsonParser;
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
public class TestConfig
{
    private APIJsonParser apiJsonParser = new APIJsonParser();

    private LinkedHashMap<String, String> notification = new LinkedHashMap<>();

    private HashMap<String, String> nameToId = new HashMap<>();

    private HashMap<String, String> idToName = new HashMap<>();

    private HashMap<String, Object> global = new HashMap<>();

    private HashMap<String, LinkedHashMap> gameParamsCHM = new HashMap<>();

    private HashMap<String, Integer> loadFinish = new HashMap<>();

    @Bean
    public APIJsonParser apiJsonParser()
    {
        return apiJsonParser;
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
    public HashMap<String, Object> global()
    {
        return global;
    }

    @Bean (value = "gameParamsCHM")
    public HashMap<String, LinkedHashMap> gameParamsCHM()
    {
        return gameParamsCHM;
    }

    @Bean
    public HashMap<String, Integer> loadFinish()
    {
        loadFinish.put("loadFinish", 0);
        return loadFinish;
    }
}
