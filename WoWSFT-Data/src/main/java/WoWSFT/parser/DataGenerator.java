package WoWSFT.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static WoWSFT.model.Constant.JSON_PARSER;

@Slf4j
@Component
public class DataGenerator implements CommandLineRunner, ApplicationContextAware
{
    private JsonParser jsonParser;
    private ApplicationContext context;

    public DataGenerator(@Qualifier(value = JSON_PARSER) JsonParser jsonParser)
    {
        this.jsonParser = jsonParser;
    }

    @Override
    public void run(String... strings) throws IOException
    {
        log.info("Start");

        jsonParser.setGlobal();
        jsonParser.setGameParams();
        jsonParser.generateShipData();
        jsonParser.generateShellPenetration();

        log.info("finish");

        ((ConfigurableApplicationContext) context).close();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        this.context = context;
    }
}
