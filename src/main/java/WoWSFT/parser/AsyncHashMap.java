package WoWSFT.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Qualifier (value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    @Autowired
    @Qualifier (value = "idToName")
    private HashMap<String, String> idToName;

    @Autowired
    @Qualifier (value = "nameToId")
    private HashMap<String, String> nameToId;

    @Autowired
    @Qualifier (value = "loadFinish")
    private HashMap<String, Integer> loadFinish;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run(String... strings) throws IOException
    {
        log.info("test");

        jsonParser.setNotification();
        jsonParser.setGlobal();
        jsonParser.setGameParams();

        loadFinish.put("loadFinish", 1);
        log.info("finish");
    }
}
