package WoWSSSC.parser;

import WoWSSSC.model.ShipData;
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
    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(APIJsonParser.class);

    private final static String APP_ID = "137f0721e1b1baf30d6dcd1968fc260c";

    public APIJsonParser()
    {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public Future<ShipData> getNationShip(String nation, String type) throws IOException
    {
        logger.info("Looking up " + nation + " " + type);
        String url = "https://api.worldofwarships.com/wows/encyclopedia/ships/?application_id=" + APP_ID + "&nation=" + nation + "&type=" + type + "&fields=-default_profile";
        ShipData result = restTemplate.getForObject(url, ShipData.class);

        return new AsyncResult<>(result);
    }
}
