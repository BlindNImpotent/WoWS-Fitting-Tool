package WoWSFT.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static WoWSFT.model.Constant.*;

@Slf4j
@Component
public class AsyncHashMap implements CommandLineRunner
{
    private final JsonParser jsonParser;
    private final HashMap<String, Integer> loadFinish;

    public AsyncHashMap(
            @Qualifier(value = JSON_PARSER) JsonParser jsonParser,
            @Qualifier(value = LOAD_FINISH) HashMap<String, Integer> loadFinish)
    {
        this.jsonParser = jsonParser;
        this.loadFinish = loadFinish;
    }

    @Override
    public void run(String... strings) throws Exception
    {
        log.info("Start");

        CompletableFuture<String> notification = jsonParser.setNotification();
        CompletableFuture<String> global = jsonParser.setGlobal();
        CompletableFuture<String> misc = jsonParser.setMisc();

        notification.get();
        global.get();
        misc.get();

        loadFinish.put(LOAD_FINISH, 1);
        log.info("finish");
    }
}
