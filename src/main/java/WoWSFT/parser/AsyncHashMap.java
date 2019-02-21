package WoWSFT.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Aesis on 2016-11-15.
 */
@Slf4j
@Component
public class AsyncHashMap implements CommandLineRunner
{
    @Autowired
    @Qualifier (value = "jsonParser")
    private JsonParser jsonParser;

    @Autowired
    @Qualifier (value = "loadFinish")
    private HashMap<String, Integer> loadFinish;

    @Override
    public void run(String... strings) throws IOException
    {
        log.info("Start");

        jsonParser.setTranslation();
        jsonParser.setNotification();
        jsonParser.setGlobal();
        jsonParser.setGameParams();

        loadFinish.put("loadFinish", 1);
        log.info("finish");
    }
}
