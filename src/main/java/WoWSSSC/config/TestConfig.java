package WoWSSSC.config;

import WoWSSSC.model.shipprofile.Ship;
import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.parser.AsyncHashMap;
import WoWSSSC.utils.Sorter;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;

/**
 * Created by Aesis on 2016-10-15.
 */
@Configuration
@ComponentScan(basePackages = {"WoWSSSC"})
@EnableAsync
public class TestConfig
{
    private String APP_ID;

    private RestTemplate restTemplate;

    private APIJsonParser apiJsonParser = new APIJsonParser();

    private AsyncHashMap asyncHashMap = new AsyncHashMap();

    private HashMap<String, Ship> shipHashMap = new HashMap<>();

    @Bean
    public String app_id()
    {
        APP_ID = "137f0721e1b1baf30d6dcd1968fc260c";
        return APP_ID;
    }

    @Bean
    public HashMap<String, Ship> shipHashMap()
    {
        return shipHashMap;
    }

    @Bean
    public RestTemplate restTemplate()
    {
        restTemplate = new RestTemplateBuilder().build();
        return restTemplate;
    }

    @Bean
    public APIJsonParser apiJsonParser()
    {
        return apiJsonParser;
    }

    @Bean
    public AsyncHashMap asyncHashMap() throws Exception
    {
        return asyncHashMap;
    }

    @Bean
    public LinkedHashMap<String, LinkedHashMap> data()
    {
        return asyncHashMap.getData();
    }

    @Bean
    public Executor getAsyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setThreadNamePrefix("Thread-");
        executor.initialize();

        return executor;
    }

}
