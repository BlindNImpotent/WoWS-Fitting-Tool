package WoWSSSC.config;

import WoWSSSC.model.WoWSAPI.APIAddress;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.parser.AsyncHashMap;
import WoWSSSC.utils.Sorter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016-10-15.
 */
@Configuration
@ComponentScan(basePackages = {"WoWSSSC"})
@EnableAsync
@EnableConfigurationProperties(CustomProperties.class)
public class TestConfig
{
    private APIJsonParser apiJsonParser = new APIJsonParser();

    private HashMap<String, Ship> shipHashMap = new HashMap<>();

    private HashMap<String, LinkedHashMap<String, LinkedHashMap>> data = new HashMap<>();

    private HashMap<String, HashMap<String, LinkedHashMap>> gameParamsCHM = new HashMap<>();

    private AsyncHashMap asyncHashMap = new AsyncHashMap();

    private LinkedHashMap<String, String> notification = new LinkedHashMap<>();

    private HashMap<String, HashMap<String, String>> nameToId = new HashMap<>();

    private HashMap<String, HashMap<String, String>> idToName = new HashMap<>();

    private HashMap<String, HashMap<String, Object>> global = new HashMap<>();

    private APIAddress apiAddress = new APIAddress();

    private HashMap<String, Integer> loadFinish = new HashMap<>();

    @Bean (value = "APP_ID")
    public String app_id()
    {
        return "137f0721e1b1baf30d6dcd1968fc260c";
    }

    @Bean (value = "APIAddress")
    public APIAddress getApiAddress()
    {
        return apiAddress;
    }

    @Bean (value = "nameToId")
    public HashMap<String, HashMap<String, String>> nameToId()
    {
        return nameToId;
    }

    @Bean (value = "idToName")
    public HashMap<String, HashMap<String, String>> idToName()
    {
        return idToName;
    }

    @Bean
    public LinkedHashMap<String, String> notification()
    {
        return notification;
    }

    @Bean
    public HashMap<String, Ship> shipHashMap()
    {
        return shipHashMap;
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public APIJsonParser apiJsonParser()
    {
        return apiJsonParser;
    }

    @Bean
    public HashMap<String, LinkedHashMap<String, LinkedHashMap>> data()
    {
        return data;
    }

    @Bean (value = "gameParamsCHM")
    public HashMap<String, HashMap<String, LinkedHashMap>> gameParamsCHM()
    {
        return gameParamsCHM;
    }

    @Bean
    public ThreadPoolTaskExecutor executor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setThreadNamePrefix("Thread-");
        executor.initialize();

        return executor;
    }

    @Bean (value = "global")
    public HashMap<String, HashMap<String, Object>> global()
    {
        return global;
    }

    @Bean
    public AsyncHashMap asyncHashMap()
    {
        return asyncHashMap;
    }

    @Bean
    public Sorter sorter()
    {
        return new Sorter();
    }

    @Bean
    public HashMap<String, Integer> loadFinish()
    {
        loadFinish.put("loadFinish", 0);
        return loadFinish;
    }
}
