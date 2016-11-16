package WoWSSSC;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by Qualson-Lee on 2016-08-04.
 */
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAsync
@SpringBootApplication
//@EnableCaching
public class Application extends AsyncConfigurerSupport
{
    public static void main(String[] args) throws IOException, ParseException
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public Executor getAsyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setThreadNamePrefix("Thread-");
        executor.initialize();

        return executor;
    }
}