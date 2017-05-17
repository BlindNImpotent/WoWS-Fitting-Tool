package WoWSSSC.parser;

import WoWSSSC.model.WoWSAPI.consumables.ConsumablesData;
import WoWSSSC.model.WoWSAPI.info.EncyclopediaData;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkillsData;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipData;
import WoWSSSC.model.gameparams.ShipUpgradeInfo.*;
import WoWSSSC.model.gameparams.test.Values.ShipAbilities.ShipAbilities;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
public class APIJsonParser
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private String APP_ID;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    private LinkedHashMap<String, String> notification;

    @Autowired
    @Qualifier(value = "nameToId")
    private HashMap<String, String> nameToId;

    @Autowired
    @Qualifier(value = "idToName")
    private HashMap<String, String> idToName;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(APIJsonParser.class);

    public HashMap<String, Warship> getTotalWarships() throws IOException
    {
        logger.info("Looking up all ships");
        String url = "https://api.worldofwarships.ru/wows/encyclopedia/ships/?application_id=" + APP_ID + "&page_no=1&language=en";
        WarshipData result = restTemplate.getForObject(url, WarshipData.class);
        if (result.getMeta().getPage_total() > 1)
        {
            for (int i = 2; i <= result.getMeta().getPage_total(); i++)
            {
                url = "https://api.worldofwarships.ru/wows/encyclopedia/ships/?application_id=" + APP_ID + "&page_no=" + i + "&language=en";
                WarshipData temp = restTemplate.getForObject(url, WarshipData.class);
                result.getData().putAll(temp.getData());
            }
        }
        logger.info("Looked up " + result.getData().size() + " ships");

        return result.getData();
    }

    public EncyclopediaData getEncyclopedia() throws IOException
    {
        logger.info("Looking up encyclopedia");
        String url = "https://api.worldofwarships.ru/wows/encyclopedia/info/?application_id=" + APP_ID + "&language=en";

        return restTemplate.getForObject(url, EncyclopediaData.class);
    }

    @Async
    public void setNotification() throws IOException
    {
        logger.info("Setting up notification");
        ObjectMapper mapper = new ObjectMapper();

        Resource notificationFile = new UrlResource("https://s3.amazonaws.com/wowsft/notification.json");
        notification.clear();
        LinkedHashMap<String, String> temp = mapper.readValue(notificationFile.getURL(), new TypeReference<LinkedHashMap<String, String>>(){});

        temp.entrySet().forEach(entry -> notification.put(entry.getKey(), entry.getValue()));
    }

    @Async
    public void setGameParams() throws IOException, IllegalAccessException
    {
        logger.info("Setting up GameParams");

        ObjectMapper mapper = new ObjectMapper();

        Resource GameParamsFile = new ClassPathResource("static/json/GameParams.json");
        HashMap<String, LinkedHashMap> temp = mapper.readValue(GameParamsFile.getFile(), new TypeReference<HashMap<String, LinkedHashMap>>(){});

        gameParamsCHM.clear();
        temp.entrySet().forEach(value ->
        {
            if (value.getValue().get("ShipUpgradeInfo") != null)
            {
                ShipUpgradeInfo shipUpgradeInfo = mapper.convertValue(value.getValue().get("ShipUpgradeInfo"), ShipUpgradeInfo.class);

                shipUpgradeInfo.getModules().entrySet().forEach(entry ->
                {
                    String key = entry.getKey();
                    HashSet<String> next = new HashSet<>();

                    shipUpgradeInfo.getModules().entrySet().forEach(mv ->
                    {
                        if (mv.getValue().getPrev().equals(key))
                        {
                            next.add(mv.getKey());
                        }
                    });

                    ((LinkedHashMap<String, LinkedHashMap>) value.getValue().get("ShipUpgradeInfo")).get(key).put("next", next);
                });

                ShipAbilities shipAbilities = mapper.convertValue(value.getValue().get("ShipAbilities"), ShipAbilities.class);

                shipAbilities.getAbilitySlot0().getAbils().forEach(list -> gameParamsCHM.put(list.get(0), temp.get(list.get(0))));
                shipAbilities.getAbilitySlot1().getAbils().forEach(list -> gameParamsCHM.put(list.get(0), temp.get(list.get(0))));
                shipAbilities.getAbilitySlot2().getAbils().forEach(list -> gameParamsCHM.put(list.get(0), temp.get(list.get(0))));
                shipAbilities.getAbilitySlot3().getAbils().forEach(list -> gameParamsCHM.put(list.get(0), temp.get(list.get(0))));
            }
            nameToId.put(value.getKey(), String.valueOf(value.getValue().get("id")));
            idToName.put(String.valueOf(value.getValue().get("id")), value.getKey());
            gameParamsCHM.put(String.valueOf(value.getValue().get("id")), value.getValue());
        });
        temp.clear();
    }

    @Async
    public CompletableFuture<WarshipData> getNationShip(String nation, String type) throws IOException
    {
        logger.info("Looking up " + nation + " " + type);
        String url = "https://api.worldofwarships.ru/wows/encyclopedia/ships/?application_id=" + APP_ID + "&nation=" + nation + "&type=" + type + "&fields=-default_profile&language=en";
        WarshipData result = restTemplate.getForObject(url, WarshipData.class);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<CrewSkillsData> getCrewSkills() throws IOException
    {
        logger.info("Looking up crew skills");
        String url = "https://api.worldofwarships.ru/wows/encyclopedia/crewskills/?application_id=" + APP_ID + "&language=en";
        CrewSkillsData result = restTemplate.getForObject(url, CrewSkillsData.class);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<ConsumablesData> getConsumables() throws IOException
    {
        logger.info("Looking up all consumables");
        String url = "https://api.worldofwarships.ru/wows/encyclopedia/consumables/?application_id=" + APP_ID + "&page_no=1&language=en";
        ConsumablesData result = restTemplate.getForObject(url, ConsumablesData.class);
        if (result.getMeta().getPage_total() > 1)
        {
            for (int i = 2; i < result.getMeta().getPage_total(); i++)
            {
                url = "https://api.worldofwarships.ru/wows/encyclopedia/consumables/?application_id=" + APP_ID + "&page_no=" + i + "&language=en";
                ConsumablesData temp = restTemplate.getForObject(url, ConsumablesData.class);
                result.getData().putAll(temp.getData());
            }
        }
        logger.info("Looked up " + result.getData().size() + " consumables");

        return CompletableFuture.completedFuture(result);
    }

//    @Async
//    public void checkShipData(String url, String key, String ship_id, String nation, String shipType, String ship) throws IOException
//    {
//        ShipData futureShipData = restTemplate.getForObject(url, ShipData.class);
//
//        if (futureShipData.getStatus().equals("ok"))
//        {
//            if (!shipHashMap.get(key).equals(futureShipData.getData().get(ship_id)))
//            {
//                logger.info("Replacing data for " + nation + " " + shipType + " " + ship + " - " + url);
//                shipHashMap.replace(key, futureShipData.getData().get(ship_id));
//            }
//        }
//    }
}
