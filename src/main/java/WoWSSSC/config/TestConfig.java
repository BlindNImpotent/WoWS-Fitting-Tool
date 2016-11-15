package WoWSSSC.config;

import WoWSSSC.model.ShipData;
import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.parser.APIParser;
import WoWSSSC.parser.AsyncHashMap;
import org.json.simple.parser.ParseException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * Created by Aesis on 2016-10-15.
 */
@Configuration
@ComponentScan(basePackages = {"WoWSSSC"})
@EnableAsync
public class TestConfig
{
    private APIParser apiParser = new APIParser();

    private APIJsonParser apiJsonParser = new APIJsonParser();

    private AsyncHashMap asyncHashMap = new AsyncHashMap(apiJsonParser);

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
    public APIParser apiParser() throws IOException, ParseException
    {
        apiParser.setUp();

        return apiParser;
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
