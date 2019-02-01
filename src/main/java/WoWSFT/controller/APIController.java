package WoWSFT.controller;

import WoWSFT.model.gameparams.ship.Ship;
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

    @ResponseBody
    @GetMapping(value = "/data")
    public Object tester(@RequestParam String nation, @RequestParam String shipType, @RequestParam String ship)
    {
        return ((HashMap<String, HashMap<String, HashMap<String, Ship>>>) gameParamsHM.get("ships")).get(nation).get(shipType).get(ship);
    }
}