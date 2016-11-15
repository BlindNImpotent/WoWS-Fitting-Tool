package WoWSSSC;

import WoWSSSC.config.WebConfig;
import WoWSSSC.parser.APIParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

/**
 * Created by Qualson-Lee on 2016-08-04.
 */
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan
@EnableAsync
//@EnableCaching
public class Application extends AsyncConfigurerSupport
{
    public static void main(String[] args) throws IOException, ParseException
    {
        SpringApplication.run(Application.class, args);
    }
}