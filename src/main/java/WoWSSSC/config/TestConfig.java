package WoWSSSC.config;

import WoWSSSC.parser.APIJsonParser;
import WoWSSSC.parser.APIParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aesis on 2016-10-15.
 */
@Configuration
@ComponentScan(basePackages = {"WoWSSSC"})
public class TestConfig
{
    private APIParser apiParser = new APIParser();

    private APIJsonParser apiJsonParser = new APIJsonParser();

    @Bean
    public APIParser apiParser() throws IOException, ParseException
    {
        apiParser.setUp();

        return apiParser;
    }

    @Bean
    public APIJsonParser apiJsonParser() throws IOException, ExecutionException, InterruptedException
    {
        apiJsonParser.setUp();

        return apiJsonParser;
    }

}
