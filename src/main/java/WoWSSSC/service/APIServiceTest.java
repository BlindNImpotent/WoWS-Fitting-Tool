package WoWSSSC.service;

import WoWSSSC.model.ShipComponents;
import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.shipprofile.profile.artillery.Artillery_Slots;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.model.gameparams.Consumables.Consumable;
import WoWSSSC.model.gameparams.Temporary;
import WoWSSSC.parser.APIJsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Aesis on 2017-04-24.
 */
@Service
public class APIServiceTest
{
    @Autowired
    private String APP_ID;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private APIJsonParser apiJsonParser;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    private GPService gpService;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

    public List<String> setModules(
            Warship warship,
            String nation,
            String shipType,
            String ship,
            String ship_id,
            String Artillery,
            String DiveBomber,
            String Engine,
            String Fighter,
            String Suo,
            String FlightControl,
            String Hull,
            String TorpedoBomber,
            String Torpedoes)
    {
        List<String> modules = new ArrayList<>();

        warship.getModules_tree().values().forEach(value ->
        {
            if (value.getType().equalsIgnoreCase("Artillery"))
            {
                if (StringUtils.isEmpty(Artillery) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(Artillery))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("DiveBomber"))
            {
                if (StringUtils.isEmpty(DiveBomber) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(DiveBomber))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("Engine"))
            {
                if (StringUtils.isEmpty(Engine) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(Engine))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("Fighter"))
            {
                if (StringUtils.isEmpty(Fighter) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(Fighter))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("Suo"))
            {
                if (StringUtils.isEmpty(Suo) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(Suo))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("FlightControl"))
            {
                if (StringUtils.isEmpty(FlightControl) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(FlightControl))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("Hull"))
            {
                if (StringUtils.isEmpty(Hull) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(Hull))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("TorpedoBomber"))
            {
                if (StringUtils.isEmpty(TorpedoBomber) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(TorpedoBomber))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }

            if (value.getType().equalsIgnoreCase("Torpedoes"))
            {
                if (StringUtils.isEmpty(Torpedoes) && value.isIs_default())
                {
                    modules.add(String.valueOf(value.getModule_id()));
                }
                else
                {
                    if (String.valueOf(value.getModule_id()).equalsIgnoreCase(Torpedoes))
                    {
                        modules.add(String.valueOf(value.getModule_id()));
                    }
                }
            }
        });

        return modules;
    }
}
