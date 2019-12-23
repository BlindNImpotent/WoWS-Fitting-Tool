package WoWSFT.parser;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static WoWSFT.model.Constant.*;

@Slf4j
public class JsonParser
{
    @Autowired
    @Qualifier(value = NOTIFICATION)
    private LinkedHashMap<String, LinkedHashMap<String, String>> notification;

    @Autowired
    @Qualifier(value = GLOBAL)
    private HashMap<String, HashMap<String, Object>> global;

    @Autowired
    @Qualifier(value = TYPE_SHIP)
    private ZipFile zShip;

    @Autowired
    @Qualifier(value = TYPE_CONSUMABLE)
    private LinkedHashMap<String, Consumable> consumables;

    @Autowired
    @Qualifier(value = TYPE_UPGRADE)
    private LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades;

    @Autowired
    @Qualifier(value = TYPE_COMMANDER)
    private LinkedHashMap<String, Commander> commanders;

    @Autowired
    @Qualifier(value = TYPE_SHIP_LIST)
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList;

    private ObjectMapper mapper = new ObjectMapper();

    @Async
    public CompletableFuture<String> setNotification()
    {
        log.info("Setting up notification");

        for (String language : globalLanguage) {
            Resource notificationFile = new ClassPathResource("/json/notification/notification-" + language + FILE_JSON);
            try {
                LinkedHashMap<String, String> temp = mapper.readValue(notificationFile.getURL(), new TypeReference<LinkedHashMap<String, String>>(){});
                notification.put(language, temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("Notification Done");

        return CompletableFuture.completedFuture("Done");
    }

    @Async
    public CompletableFuture<String> setGlobal()
    {
        log.info("Setting up Global");

        for (String language : globalLanguage) {
            Resource GlobalFile = new ClassPathResource("/json/live/global-" + language + FILE_JSON);
            try {
                HashMap<String, Object> temp = mapper.readValue(GlobalFile.getInputStream(), new TypeReference<HashMap<String, Object>>() {});
                global.put(language, temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("Global Done");

        return CompletableFuture.completedFuture("Done");
    }

    @Async
    public CompletableFuture<String> setMisc() throws IOException
    {
        log.info("Setting up Misc");

        LinkedHashMap<String, Consumable> tempConsumables =
                mapper.readValue(zShip.getInputStream(zShip.getEntry(TYPE_CONSUMABLE + FILE_JSON)), new TypeReference<LinkedHashMap<String, Consumable>>(){});
        consumables.putAll(tempConsumables);

        LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> tempUpgrades =
                mapper.readValue(zShip.getInputStream(zShip.getEntry(TYPE_UPGRADE + FILE_JSON)), new TypeReference<LinkedHashMap<Integer, LinkedHashMap<String, Modernization>>>(){});
        upgrades.putAll(tempUpgrades);

        LinkedHashMap<String, Commander> tempCommanders =
                mapper.readValue(zShip.getInputStream(zShip.getEntry(TYPE_COMMANDER + FILE_JSON)), new TypeReference<LinkedHashMap<String, Commander>>(){});
        commanders.putAll(tempCommanders);

        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> tempShipsList =
                mapper.readValue(zShip.getInputStream(zShip.getEntry(TYPE_SHIP_LIST + FILE_JSON)), new TypeReference<LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>>>(){});
        shipsList.putAll(tempShipsList);

        log.info("Misc Done");

        return CompletableFuture.completedFuture("Done");
    }
}
