package WoWSSSC.parser;

import WoWSSSC.config.CustomProperties;
import WoWSSSC.model.WoWSAPI.APIAddress;
import WoWSSSC.model.WoWSAPI.commanders.CommandersData;
import WoWSSSC.model.WoWSAPI.commanders.CommandersRankData;
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
import org.springframework.beans.factory.annotation.Value;
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
    @Qualifier(value = "APIAddress")
    private APIAddress apiAddress;

    @Autowired
    @Qualifier (value = "APP_ID")
    private String APP_ID;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    @Qualifier (value = "gameParamsCHM")
    private HashMap<String, HashMap<String, LinkedHashMap>> gameParamsCHM;

    @Autowired
    private LinkedHashMap<String, String> notification;

    @Autowired
    @Qualifier (value = "nameToId")
    private HashMap<String, HashMap<String, String>> nameToId;

    @Autowired
    @Qualifier (value = "idToName")
    private HashMap<String, HashMap<String, String>> idToName;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    @Autowired
    private CustomProperties customProperties;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(APIJsonParser.class);

    public HashMap<String, Warship> getTotalWarships() throws IOException
    {
        logger.info("Looking up all ships");

        String url = apiAddress.getAPI_Starter() + "/ships/?application_id=" + APP_ID + "&page_no=1&language=" + customProperties.getLanguage();

        WarshipData result = restTemplate.getForObject(url, WarshipData.class);
        if (result.getMeta().getPage_total() > 1)
        {
            for (int i = 2; i <= result.getMeta().getPage_total(); i++)
            {
                url = apiAddress.getAPI_Starter() + "/ships/?application_id=" + APP_ID + "&page_no=" + i + "&language=" + customProperties.getLanguage();
                WarshipData temp = restTemplate.getForObject(url, WarshipData.class);
                result.getData().putAll(temp.getData());
            }
        }
        logger.info("Looked up " + result.getData().size() + " ships");

        return result.getData();
    }

    @Async
    public CompletableFuture<EncyclopediaData> getEncyclopedia_NA() throws IOException
    {
        logger.info("Looking up encyclopedia NA");
        String url = apiAddress.getAPI_NA() + "/info/?application_id=" + APP_ID + "&language=" + customProperties.getLanguage();

        EncyclopediaData result = restTemplate.getForObject(url, EncyclopediaData.class);
        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<EncyclopediaData> getEncyclopedia_RU() throws IOException
    {
        logger.info("Looking up encyclopedia RU");
        String url = apiAddress.getAPI_RU() + "/info/?application_id=" + APP_ID + "&language=" + customProperties.getLanguage();

        EncyclopediaData result = restTemplate.getForObject(url, EncyclopediaData.class);
        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<EncyclopediaData> getEncyclopedia_ASIA() throws IOException
    {
        logger.info("Looking up encyclopedia ASIA");
        String url = apiAddress.getAPI_ASIA() + "/info/?application_id=" + APP_ID + "&language=" + customProperties.getLanguage();

        EncyclopediaData result = restTemplate.getForObject(url, EncyclopediaData.class);
        return CompletableFuture.completedFuture(result);
    }

    @Async
    public void setNotification() throws IOException
    {
        logger.info("Setting up notification");
        ObjectMapper mapper = new ObjectMapper();

        Resource notificationFile = new ClassPathResource("/json/notification/notification.json");
        notification.clear();
        LinkedHashMap<String, String> temp = mapper.readValue(notificationFile.getURL(), new TypeReference<LinkedHashMap<String, String>>(){});

        temp.entrySet().forEach(entry -> notification.put(entry.getKey(), entry.getValue()));
    }

    @Async
    public void setGameParams() throws IOException, IllegalAccessException
    {
        logger.info("Setting up GameParams");

        ObjectMapper mapper = new ObjectMapper();

        String liveGameParams = "/json/live/GameParams.json";
//        String testGameParams = "/json/test/GameParams.json";

        gameParamsCHM.clear();
        for (int i = 0; i < 1; i++)
        {
            String serverParams = i == 0 ? "live" : "test";

            Resource GameParamsFile = new ClassPathResource(liveGameParams);
            HashMap<String, LinkedHashMap> temp = mapper.readValue(GameParamsFile.getInputStream(), new TypeReference<HashMap<String, LinkedHashMap>>(){});
            HashMap<String, LinkedHashMap> tempGameParamsCHM = new HashMap<>();
            HashMap<String, String> tempNameToId = new HashMap<>();
            HashMap<String, String> tempIdToName = new HashMap<>();

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

                    shipAbilities.getAbilitySlot0().getAbils().forEach(list -> tempGameParamsCHM.put(list.get(0), temp.get(list.get(0))));
                    shipAbilities.getAbilitySlot1().getAbils().forEach(list -> tempGameParamsCHM.put(list.get(0), temp.get(list.get(0))));
                    shipAbilities.getAbilitySlot2().getAbils().forEach(list -> tempGameParamsCHM.put(list.get(0), temp.get(list.get(0))));
                    shipAbilities.getAbilitySlot3().getAbils().forEach(list -> tempGameParamsCHM.put(list.get(0), temp.get(list.get(0))));
                }
                tempNameToId.put(value.getKey(), String.valueOf(value.getValue().get("id")));
                tempIdToName.put(String.valueOf(value.getValue().get("id")), value.getKey());
                tempGameParamsCHM.put(String.valueOf(value.getValue().get("id")), value.getValue());
            });
            gameParamsCHM.put(serverParams, tempGameParamsCHM);
            nameToId.put(serverParams, tempNameToId);
            idToName.put(serverParams, tempIdToName);
            temp.clear();
        }
    }

    @Async
    public void setGlobal() throws IOException
    {
        logger.info("Setting up Global");

        for (int i = 0; i < 1; i++)
        {
            String serverParams = i == 0 ? "live" : "test";

            String liveGlobal = "/json/live/global.json";
            String krGlobal = "/json/live/global-kr.json";
//            String testGlobal = "/json/test/global.json";

            String finalGlobal = "kr".equalsIgnoreCase(customProperties.getGlobalLanguage()) ? krGlobal : liveGlobal;
            Resource GlobalFile = new ClassPathResource(finalGlobal);

            HashMap<String, Object> temp = mapper.readValue(GlobalFile.getInputStream(), new TypeReference<HashMap<String, Object>>(){});

            global.put(serverParams, temp);
        }
    }

    @Async
    public CompletableFuture<WarshipData> getNationShip(String nation, String type) throws IOException
    {
        logger.info("Looking up " + nation + " " + type);
        String url = apiAddress.getAPI_Starter() + "/ships/?application_id=" + APP_ID + "&nation=" + nation + "&type=" + type + "&fields=-default_profile&language=" + customProperties.getLanguage();
        WarshipData result = restTemplate.getForObject(url, WarshipData.class);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<CrewSkillsData> getCrewSkills() throws IOException
    {
        logger.info("Looking up crew skills");
        String url = apiAddress.getAPI_Starter() + "/crewskills/?application_id=" + APP_ID + "&language=" + customProperties.getLanguage();
        CrewSkillsData result = restTemplate.getForObject(url, CrewSkillsData.class);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<ConsumablesData> getConsumables() throws IOException
    {
        logger.info("Looking up all consumables");
        String url = apiAddress.getAPI_Starter() + "/consumables/?application_id=" + APP_ID + "&page_no=1&language=" + customProperties.getLanguage();
        ConsumablesData result = restTemplate.getForObject(url, ConsumablesData.class);
        if (result.getMeta().getPage_total() > 1)
        {
            for (int i = 2; i < result.getMeta().getPage_total(); i++)
            {
                url = apiAddress.getAPI_Starter() + "/consumables/?application_id=" + APP_ID + "&page_no=" + i + "&language=" + customProperties.getLanguage();
                ConsumablesData temp = restTemplate.getForObject(url, ConsumablesData.class);
                result.getData().putAll(temp.getData());
            }
        }
        logger.info("Looked up " + result.getData().size() + " consumables");

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<CommandersData> getCommanders() throws IOException
    {
        logger.info("Looking up all commanders");
        String url = apiAddress.getAPI_Starter() + "/crews/?application_id=" + APP_ID + "&language=" + customProperties.getLanguage();
        CommandersData result = restTemplate.getForObject(url, CommandersData.class);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<CommandersRankData> getCommandersRanks()
    {
        logger.info("Looking up all commanders' ranks");
        String url = apiAddress.getAPI_Starter() + "/crewranks/?application_id=" + APP_ID + "&language=" + customProperties.getLanguage();
        CommandersRankData result = restTemplate.getForObject(url, CommandersRankData.class);

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
