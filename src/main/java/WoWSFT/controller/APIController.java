package WoWSFT.controller;

import WoWSFT.model.gameparams.modernization.Upgrades;
import WoWSFT.model.gameparams.ship.Ship;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIController extends ExceptionController
{
    @Autowired
    @Qualifier(value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    private ObjectMapper mapper = new ObjectMapper();

    @ResponseBody
    @GetMapping(value = "/data")
    public Object tester(@RequestParam String type,
                         @RequestParam(required = false) String nation, @RequestParam(required = false) String shipType, @RequestParam(required = false) String ship)
    {
        if ("ships".equalsIgnoreCase(type)) {
            HashMap<String, HashMap<String, HashMap<String, Ship>>> tempShips = mapper.convertValue(gameParamsHM.get(type), new TypeReference<HashMap<String, HashMap<String, HashMap<String, Ship>>>>(){});
            if (StringUtils.isNotEmpty(nation)) {
                if (StringUtils.isNotEmpty(shipType)) {
                    if (StringUtils.isNotEmpty(ship)) {
                        return tempShips.get(nation).get(shipType).get(ship);
                    }
                    return tempShips.get(nation).get(shipType);
                }
                return tempShips.get(nation);
            }
            return tempShips;
        }
        return gameParamsHM.get(type);
    }
}