package WoWSSSC.config;

import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.parser.AsyncHashMap;
import WoWSSSC.utils.Sorter;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedHashMap;

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

    @Bean
    public String app_id()
    {
        APP_ID = "137f0721e1b1baf30d6dcd1968fc260c";
        return APP_ID;
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

}
