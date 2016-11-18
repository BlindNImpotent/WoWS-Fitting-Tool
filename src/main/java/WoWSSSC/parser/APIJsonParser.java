package WoWSSSC.parser;

import WoWSSSC.model.shipprofile.ShipData;
import WoWSSSC.model.warships.WarshipData;
import WoWSSSC.model.upgrade.UpgradeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

    private static final Logger logger = LoggerFactory.getLogger(APIJsonParser.class);

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
}
