package WoWSFT.parser;

import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Slf4j
public class JsonParser
{
    @Autowired
    @Qualifier (value = "notification")
    private LinkedHashMap<String, String> notification;

    @Autowired
    @Qualifier (value = "nameToId")
    private HashMap<String, String> nameToId;

    @Autowired
    @Qualifier (value = "idToName")
    private HashMap<String, String> idToName;

    @Autowired
    @Qualifier (value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, Object> global;

    private ObjectMapper mapper = new ObjectMapper();

    private static HashSet<String> excludeShipGroups = new HashSet<>(Arrays.asList("unavailable", "disabled", "preserved", "clan"));
    private static HashSet<String> excludeShipNations = new HashSet<>(Arrays.asList("Events", "disabled", "preserved", "clan"));
    private static HashSet<String> excludeShipSpecies = new HashSet<>(Arrays.asList("Auxiliary", "Submarine"));

    @Async
    public void setNotification() throws IOException
    {
        log.info("Setting up notification");

        Resource notificationFile = new ClassPathResource("/json/notification/notification.json");

        LinkedHashMap<String, String> temp = mapper.readValue(notificationFile.getURL(), new TypeReference<LinkedHashMap<String, String>>(){});

        notification.putAll(temp);
        temp.clear();
    }

    @Async
    public void setGlobal() throws IOException
    {
        log.info("Setting up Global");

        Resource GlobalFile = new ClassPathResource("/json/live/global.json");

        HashMap<String, Object> temp = mapper.readValue(GlobalFile.getInputStream(), new TypeReference<HashMap<String, Object>>(){});

        global.putAll(temp);
        temp.clear();
    }

    @Async
    public void setGameParams() throws IOException
    {
        log.info("Setting up GameParams");

        Resource GameParamsFile = new ClassPathResource("/json/live/GameParams.json");

        HashMap<String, LinkedHashMap> temp = mapper.readValue(GameParamsFile.getInputStream(), new TypeReference<HashMap<String, LinkedHashMap>>(){});

        temp.forEach((key, value) -> {
            TypeInfo typeInfo = mapper.convertValue(value.get("typeinfo"), TypeInfo.class);
            if (typeInfo != null && typeInfo.getType().equalsIgnoreCase("Ship")
                    && !excludeShipNations.contains(typeInfo.getNation()) && !excludeShipSpecies.contains(typeInfo.getSpecies())) {
                Ship ship = mapper.convertValue(value, Ship.class);
                if (!excludeShipGroups.contains(ship.getGroup()) && StringUtils.isEmpty(ship.getDefaultCrew())) {
                    addShips(ship);
                }


//                System.out.println(ship);
//                addShips(mapper.convertValue(value, Ship.class));
            }

//            nameToId.put(key, String.valueOf(value.get("id")));
//            idToName.put(String.valueOf(value.get("id")), key);
//            gameParamsHM.put(String.valueOf(value.get("id")), value);
        });

        temp.clear();
    }

    private void addShips(Ship ship)
    {
        if (ship.getIndex().equalsIgnoreCase("PASB018")) {
            sortShipUpgradeInfo(ship);
            sortComponents(ship);
//            System.out.println(ship);
            gameParamsHM.put(ship.getIndex(), ship);
        }
    }

    private void sortShipUpgradeInfo(Ship ship)
    {
        ship.getShipUpgradeInfo().getArtillery().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getEngine().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getHull().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getSuo().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getTorpedoes().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getFlightControl().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getDiveBomber().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getFighter().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
        ship.getShipUpgradeInfo().getTorpedoBomber().sort(Comparator.comparing(ShipUpgrade::getName).thenComparingInt(ShipUpgrade::getPosition));
    }

    private void sortComponents(Ship ship)
    {
        HashMap<String, Object> tempArtillery = new HashMap<>();
        ship.getShipUpgradeInfo().getArtillery().forEach(upgrade -> upgrade.getComponents().getArtillery().forEach(component -> tempArtillery.put(component, ship.getComponents().get(component))));
        ship.setArtillery(tempArtillery);

        HashMap<String, Object> tempEngine = new HashMap<>();
        ship.getShipUpgradeInfo().getEngine().forEach(upgrade -> upgrade.getComponents().getEngine().forEach(component -> tempEngine.put(component, ship.getComponents().get(component))));
        ship.setEngine(tempEngine);

        HashMap<String, Object> tempHull = new HashMap<>();
        ship.getShipUpgradeInfo().getHull().forEach(upgrade -> upgrade.getComponents().getHull().forEach(component -> tempHull.put(component, ship.getComponents().get(component))));
        ship.setHull(tempHull);

        ship.setComponents(null);
    }
}
