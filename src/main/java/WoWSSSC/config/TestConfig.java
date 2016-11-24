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
    private APIJsonParser apiJsonParser = new APIJsonParser();

    private AsyncHashMap asyncHashMap = new AsyncHashMap();

    private HashMap<String, Ship> shipHashMap = new HashMap<>();

    private LinkedHashMap<String, LinkedHashMap> data = new LinkedHashMap<>();

    @Bean
    public String app_id()
    {
        return "137f0721e1b1baf30d6dcd1968fc260c";
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
    public AsyncHashMap asyncHashMap() throws Exception
    {
        return asyncHashMap;
    }

    @Bean
    public LinkedHashMap<String, LinkedHashMap> data()
    {
        return data;
    }

    @Bean
    public ThreadPoolTaskExecutor executor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setThreadNamePrefix("Thread-");

        return executor;
    }

    @Bean
    public Sorter sorter()
    {
        return new Sorter();
    }

}
