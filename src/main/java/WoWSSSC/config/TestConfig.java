package WoWSSSC.config;

import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.parser.AsyncHashMap;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

    private final static String france = "france";
    private final static String germany = "germany";
    private final static String japan = "japan";
    private final static String pan_asia = "pan_asia";
    private final static String poland = "poland";
    private final static String uk = "uk";
    private final static String usa = "usa";
    private final static String ussr = "ussr";

    private final static String AirCarrier = "AirCarrier";
    private final static String Battleship = "Battleship";
    private final static String Cruiser = "Cruiser";
    private final static String Destroyer = "Destroyer";

    private final static String[] nations = {france, germany, japan, pan_asia, poland, uk, usa, ussr};
    private final static String[] shipTypes = {AirCarrier, Battleship, Cruiser, Destroyer};

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

}
