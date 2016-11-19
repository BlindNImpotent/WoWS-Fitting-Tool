package WoWSSSC.service;

import WoWSSSC.model.shipprofile.Ship;
import WoWSSSC.model.shipprofile.ShipData;
import WoWSSSC.model.warships.Warship;
import WoWSSSC.parser.AsyncHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by Aesis on 2016-10-15.
 */
@Service
public class APIService
{
    @Autowired
    private AsyncHashMap asyncHashMap;

    @Autowired
    private String APP_ID;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

    public LinkedHashMap<String, LinkedHashMap> getData()
    {
        return asyncHashMap.getData();
    }

    public Warship getWarship(String nation, String shipType, String ship)
    {
        if (!nation.equals("") && !shipType.equals("") && !ship.equals(""))
        {
            LinkedHashMap<String, LinkedHashMap> shipTypes = (LinkedHashMap<String, LinkedHashMap>) asyncHashMap.getData().get("nations").get(nation);
            LinkedHashMap<String, Warship> warships = shipTypes.get(shipType);
            return warships.get(ship);
        }
        return null;
    }

    public Ship getShipAPI(
            String ship_id,
            String artillery_id,
            String dive_bomber_id,
            String engine_id,
            String fighter_id,
            String fire_control_id,
            String flight_control_id,
            String hull_id,
            String torpedo_bomber_id,
            String torpedoes_id
    )
    {
        String url = "https://api.worldofwarships.com/wows/encyclopedia/shipprofile/?application_id=" + APP_ID + "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id;
        logger.info(url);
        ShipData shipData = restTemplate.getForObject(url, ShipData.class);
        return shipData.getData().get(ship_id);
    }

}
