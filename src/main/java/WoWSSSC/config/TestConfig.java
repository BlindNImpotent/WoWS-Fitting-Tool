package WoWSSSC.config;

import WoWSSSC.parser.APIParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by Aesis on 2016-10-15.
 */
@Configuration
@ComponentScan(basePackages = {"WoWSSSC"})
public class TestConfig
{
    private APIParser apiParser = new APIParser();

    @Bean
    public APIParser apiParser() throws IOException, ParseException
    {
        apiParser.setUp();

        return apiParser;
    }

}
