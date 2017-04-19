package WoWSSSC.parser;

import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.consumables.ConsumablesData;
import WoWSSSC.model.WoWSAPI.exterior.ExteriorData;
import WoWSSSC.model.WoWSAPI.info.EncyclopediaData;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.shipprofile.ShipData;
import WoWSSSC.model.WoWSAPI.skills.CrewSkillsData;
import WoWSSSC.model.WoWSAPI.warships.TotalWarship;
import WoWSSSC.model.WoWSAPI.warships.TotalWarshipData;
import WoWSSSC.model.WoWSAPI.warships.WarshipData;
import WoWSSSC.model.WoWSAPI.upgrade.UpgradeData;
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

    ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(APIJsonParser.class);

    public HashMap<String, TotalWarship> getTotalWarships() throws IOException
    {
        logger.info("Looking up all ships");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/ships/?application_id=" + APP_ID;
        TotalWarshipData result = restTemplate.getForObject(url, TotalWarshipData.class);

        return result.getData();
    }

    @Async
    public CompletableFuture<WarshipData> getNationShip(String nation, String type) throws IOException
    {
        logger.info("Looking up " + nation + " " + type);
        String url = "https://api.worldofwarships.com/wows/encyclopedia/ships/?application_id=" + APP_ID + "&nation=" + nation + "&type=" + type + "&fields=-default_profile";
        WarshipData result = restTemplate.getForObject(url, WarshipData.class);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<ConsumablesData> getConsumables() throws IOException
    {
        logger.info("Looking up consumables");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/consumables/?application_id=" + APP_ID;
        ConsumablesData result = restTemplate.getForObject(url, ConsumablesData.class);

        return CompletableFuture.completedFuture(result);
    }

//    @Async
//    public CompletableFuture<UpgradeData> getUpgrades() throws IOException
//    {
//        logger.info("Looking up upgrades");
//        String url = "https://api.worldofwarships.com/wows/encyclopedia/upgrades/?application_id=" + APP_ID;
//        UpgradeData result = restTemplate.getForObject(url, UpgradeData.class);
//
//        return CompletableFuture.completedFuture(result);
//    }

    public EncyclopediaData getEncyclopedia() throws IOException
    {
        logger.info("Looking up encyclopedia");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/info/?application_id=" + APP_ID;

        return restTemplate.getForObject(url, EncyclopediaData.class);
    }

    @Async
    public CompletableFuture<CrewSkillsData> getCrewSkills() throws IOException
    {
        logger.info("Looking up crew skills");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/crewskills/?application_id=" + APP_ID;
        CrewSkillsData result = restTemplate.getForObject(url, CrewSkillsData.class);

        return CompletableFuture.completedFuture(result);
    }

//    @Async
//    public CompletableFuture<ExteriorData> getExteriorData() throws IOException
//    {
//        logger.info("Looking up exterior");
//        String url = "https://api.worldofwarships.com/wows/encyclopedia/exterior/?application_id=" + APP_ID;
//        ExteriorData result = restTemplate.getForObject(url, ExteriorData.class);
//
//        return CompletableFuture.completedFuture(result);
//    }

    @Async
    public void checkShipData(String url, String key, String ship_id, String nation, String shipType, String ship) throws IOException
    {
        ShipData futureShipData = restTemplate.getForObject(url, ShipData.class);

        if (futureShipData.getStatus().equals("ok"))
        {
            if (!shipHashMap.get(key).equals(futureShipData.getData().get(ship_id)))
            {
                logger.info("Replacing data for " + nation + " " + shipType + " " + ship + " - " + url);
                shipHashMap.replace(key, futureShipData.getData().get(ship_id));
            }
        }
    }

    @Async
    public void setGameParams() throws IOException, IllegalAccessException {
        logger.info("Setting up GameParams");

        ObjectMapper mapper = new ObjectMapper();

        // For local testing
        Resource GameParamsFile = new ClassPathResource("static/json/GameParams.json");
        HashMap<String, LinkedHashMap> temp = mapper.readValue(GameParamsFile.getFile(), new TypeReference<HashMap<String, LinkedHashMap>>(){});
//
//        // For AWS
////        Resource GameParamsFile = new UrlResource("https://s3.amazonaws.com/wowsft/GameParams.json");
////        HashMap<String, HashMap> temp;
////
////        if (!GameParamsFile.exists())
////        {
////            GameParamsFile = new ClassPathResource("static/json/GameParams.json");
////            temp = mapper.readValue(GameParamsFile.getFile(), new TypeReference<HashMap<String, HashMap>>(){});
////        }
////        else
////        {
////            temp = mapper.readValue(GameParamsFile.getURL(), new TypeReference<HashMap<String, HashMap>>(){});
////        }
//
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


        // For parsing GameParams
//        Resource GameParamsFile = new ClassPathResource("static/json/GameParams.json");



//        HashMap<String, GameParamsValues> temp = mapper.readValue(GameParamsFile.getFile(), new TypeReference<HashMap<String, GameParamsValues>>(){});
//
//        HashMap<String, Temporary> temporaryHashMap;
//        temporaryHashMap = mapper.readValue(GameParamsFile.getFile(), new TypeReference<HashMap<String, Temporary>>(){});
//        temporaryHashMap.values().forEach(value ->
//        {
//            HashMap<String, GameParamsValues> tempGPVHM = new HashMap<>();
//            String type = value.getTypeinfo().getType();
//
//            temporaryHashMap.entrySet().forEach(entry2 ->
//            {
//                if (type.equals(entry2.getValue().getTypeinfo().getType()))
//                {
//                    GameParamsValues tempGPV = temp.get(entry2.getKey());
//                    tempGPVHM.put(String.valueOf(tempGPV.getId()), tempGPV);
//                }
//            });
//            gp.put(type, tempGPVHM);
//        });
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
}
