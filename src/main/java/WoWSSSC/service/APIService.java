package WoWSSSC.service;

import WoWSSSC.model.shipprofile.Ship;
import WoWSSSC.model.shipprofile.ShipData;
import WoWSSSC.model.shipprofile.profile.anti_aircraft.Anti_Aircraft_Slot;
import WoWSSSC.model.upgrade.Upgrade;
import WoWSSSC.model.upgrade.UpgradeProfile;
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
    private String APP_ID;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

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
        if (!ship_id.equals(""))
        {
            String url = "https://api.worldofwarships.com/wows/encyclopedia/shipprofile/?application_id=" + APP_ID + "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                    + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id;
            ShipData shipData = restTemplate.getForObject(url, ShipData.class);

            if (shipData.getStatus().equals("ok"))
            {
                logger.info(url);
                return shipData.getData().get(ship_id);
            }
        }
        return null;
    }

    public void setUpgradeStats(Ship ship, List<String> upgrades)
    {
        for (String x : upgrades)
        {
            if (!x.equals(""))
            {
                Upgrade temp = (Upgrade) data.get("upgrades").get(x);
                UpgradeProfile tempProfile = temp.getProfile();

                if (tempProfile.getAnti_aircraft() != null)
                {
                    for (Anti_Aircraft_Slot aas : ship.getAnti_aircraft().getSlots().values())
                    {
                        if (tempProfile.getAnti_aircraft().getDistance_coef() != 0)
                        {
                            aas.setDistance(aas.getDistance() * tempProfile.getAnti_aircraft().getDistance_coef());
                        }
                        if (tempProfile.getAnti_aircraft().getEfficiency_coef() != 0)
                        {
                            aas.setAvg_damage(aas.getAvg_damage() * tempProfile.getAnti_aircraft().getEfficiency_coef());
                        }
                    }
                }
                else if (tempProfile.getArtillery() != null)
                {
                    if (tempProfile.getArtillery() != null)
                    {

                    }
                }
                else if (tempProfile.getAtba() != null)
                {

                }
                else if (tempProfile.getConcealment() != null)
                {

                }
                else if (tempProfile.getDamage_control() != null)
                {

                }
                else if (tempProfile.getEngine() != null)
                {

                }
                else if (tempProfile.getFire_control() != null)
                {

                }
                else if (tempProfile.getFlight_control() != null)
                {

                }
                else if (tempProfile.getGuidance() != null)
                {

                }
                else if (tempProfile.getMainweapon() != null)
                {

                }
                else if (tempProfile.getPlanes() != null)
                {

                }
                else if (tempProfile.getPowder() != null)
                {

                }
                else if (tempProfile.getSecondweapon() != null)
                {

                }
                else if (tempProfile.getSpotting() != null)
                {

                }
                else if (tempProfile.getSteering() != null)
                {

                }
                else if (tempProfile.getTorpedoes() != null)
                {

                }
            }
        }
    }
}
