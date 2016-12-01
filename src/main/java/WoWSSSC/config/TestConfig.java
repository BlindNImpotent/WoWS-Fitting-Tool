package WoWSSSC.config;

import WoWSSSC.model.shipprofile.Ship;
import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.parser.AsyncHashMap;
import WoWSSSC.utils.Sorter;
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
public class TestConfig
{
    private APIJsonParser apiJsonParser = new APIJsonParser();

    private HashMap<String, Ship> shipHashMap = new HashMap<>();

    private LinkedHashMap<String, LinkedHashMap> data = new LinkedHashMap<>();

    private HashMap<String, HashMap> gameParamsCHM = new HashMap<>();

    private AsyncHashMap asyncHashMap = new AsyncHashMap();

    private LinkedHashMap<String, String> notification = new LinkedHashMap<>();

    @Bean
    public String app_id()
    {
        return "137f0721e1b1baf30d6dcd1968fc260c";
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
    public LinkedHashMap<String, LinkedHashMap> data()
    {
        return data;
    }

    @Bean
    public HashMap<String, HashMap> gameParamsCHM()
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

}
