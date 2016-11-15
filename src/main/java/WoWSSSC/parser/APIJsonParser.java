package WoWSSSC.parser;

import WoWSSSC.model.Ship;
import WoWSSSC.model.ShipData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
public class APIJsonParser
{
    @Autowired
    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private final RestTemplate restTemplate;

    private final static String APP_ID = "137f0721e1b1baf30d6dcd1968fc260c";
    private final static String usa = "usa";
    private final static String AirCarrier = "AirCarrier";


    public APIJsonParser()
    {
        restTemplate = restTemplateBuilder.build();
    }

    @Async
    private Future<ShipData> getNationShip(String nation, String type) throws IOException
    {
        String url = "https://api.worldofwarships.com/wows/encyclopedia/ships/?application_id=" + APP_ID + "&nation=" + nation + "&type=" + type + "&fields=-default_profile";
        ShipData result = restTemplate.getForObject(url, ShipData.class);

        return new AsyncResult<>(result);
    }

    public void setUp() throws IOException, ExecutionException, InterruptedException
    {
        HashMap<String, Ship> usaAirCarrier = getNationShip(usa, AirCarrier).get().getData();

    }
}
