package WoWSFT.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class AsyncHashMap implements CommandLineRunner
{
    private final JsonParser jsonParser;
    private final HashMap<String, Integer> loadFinish;

    public AsyncHashMap(
            @Qualifier(value = "jsonParser") JsonParser jsonParser,
            @Qualifier(value = "loadFinish") HashMap<String, Integer> loadFinish)
    {
        this.jsonParser = jsonParser;
        this.loadFinish = loadFinish;
    }

    @Override
    public void run(String... strings) throws IOException
    {
        log.info("Start");

        CompletableFuture<String> translation = jsonParser.setTranslation();
        CompletableFuture<String> notification = jsonParser.setNotification();
        CompletableFuture<String> global = jsonParser.setGlobal();
        CompletableFuture<String> misc = jsonParser.setMisc();

        while (true) {
            if (translation.isDone() && notification.isDone() && global.isDone() && misc.isDone()) {
                loadFinish.put("loadFinish", 1);
                log.info("finish");
                break;
            }
        }
    }
}
