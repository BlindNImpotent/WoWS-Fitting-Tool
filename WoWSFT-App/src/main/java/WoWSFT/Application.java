package WoWSFT;

import WoWSFT.parser.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.TimeZone;
import java.util.zip.ZipFile;

import static WoWSFT.model.Constant.*;

@EnableAsync
@SpringBootApplication
public class Application extends SpringBootServletInitializer
{
    @PostConstruct
    void started()
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
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

    @Bean(value = TYPE_SHIP)
    public ZipFile zShip() throws IOException
    {
        return new ZipFile(new ClassPathResource("/json/live/files.zip").getFile().getPath());
    }

    @Bean(value = TYPE_SHELL)
    public ZipFile zShell() throws IOException
    {
        return new ZipFile(new ClassPathResource("/json/live/shells.zip").getFile().getPath());
    }

    @Bean(value = JSON_PARSER)
    public JsonParser jsonParser()
    {
        return new JsonParser();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}