package WoWSSSC.parser;

import WoWSSSC.model.exterior.ExteriorData;
import WoWSSSC.model.info.EncyclopediaData;
import WoWSSSC.model.shipprofile.ShipData;
import WoWSSSC.model.skills.CrewSkillsData;
import WoWSSSC.model.warships.TotalWarship;
import WoWSSSC.model.warships.TotalWarshipData;
import WoWSSSC.model.warships.Warship;
import WoWSSSC.model.warships.WarshipData;
import WoWSSSC.model.upgrade.UpgradeData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

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
    private HashMap<String, HashMap> gameParamsCHM;

    private static final Logger logger = LoggerFactory.getLogger(APIJsonParser.class);

    public HashMap<String, TotalWarship> getTotalWarships() throws IOException
    {
        logger.info("Looking up all ships");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/ships/?application_id=" + APP_ID;
        TotalWarshipData result = restTemplate.getForObject(url, TotalWarshipData.class);

        return result.getData();
    }

    @Async
    public Future<WarshipData> getNationShip(String nation, String type) throws IOException
    {
        logger.info("Looking up " + nation + " " + type);
        String url = "https://api.worldofwarships.com/wows/encyclopedia/ships/?application_id=" + APP_ID + "&nation=" + nation + "&type=" + type + "&fields=-default_profile";
        WarshipData result = restTemplate.getForObject(url, WarshipData.class);

        return new AsyncResult<>(result);
    }

    @Async
    public Future<UpgradeData> getUpgrades() throws IOException
    {
        logger.info("Looking up upgrades");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/upgrades/?application_id=" + APP_ID;
        UpgradeData result = restTemplate.getForObject(url, UpgradeData.class);

        return new AsyncResult<>(result);
    }

    public EncyclopediaData getEncyclopedia() throws IOException
    {
        logger.info("Looking up encyclopedia");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/info/?application_id=" + APP_ID;

        return restTemplate.getForObject(url, EncyclopediaData.class);
    }

    @Async
    public Future<CrewSkillsData> getCrewSkills() throws IOException
    {
        logger.info("Looking up crew skills");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/crewskills/?application_id=" + APP_ID;
        CrewSkillsData result = restTemplate.getForObject(url, CrewSkillsData.class);

        return new AsyncResult<>(result);
    }

    @Async
    public Future<ExteriorData> getExteriorData() throws IOException
    {
        logger.info("Looking up exterior");
        String url = "https://api.worldofwarships.com/wows/encyclopedia/exterior/?application_id=" + APP_ID;
        ExteriorData result = restTemplate.getForObject(url, ExteriorData.class);

        return new AsyncResult<>(result);
    }

    public void setGameParams() throws IOException
    {
        HashMap<String, HashMap> temp;
        ObjectMapper mapper = new ObjectMapper();

        // For local testing
        Resource GameParamsFile = new ClassPathResource("static/json/GameParams.json");
        temp = mapper.readValue(GameParamsFile.getFile(), new TypeReference<HashMap<String, HashMap>>(){});

        // For AWS
//        Resource GameParamsFile = new UrlResource("https://s3.amazonaws.com/wowsft/GameParams.json");
//
//        if (!GameParamsFile.exists())
//        {
//            GameParamsFile = new ClassPathResource("static/json/GameParams.json");
//            temp = mapper.readValue(GameParamsFile.getFile(), new TypeReference<HashMap<String, HashMap>>(){});
//        }
//        else
//        {
//            temp = mapper.readValue(GameParamsFile.getURL(), new TypeReference<HashMap<String, HashMap>>(){});
//        }

        gameParamsCHM.clear();
        temp.entrySet().forEach(entry -> gameParamsCHM.put(String.valueOf(entry.getValue().get("id")), entry.getValue()));
        temp.clear();
    }
}
