package WoWSFT.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static WoWSFT.model.Constant.*;

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
                         @RequestParam(required = false) String ship)
    {
        HashMap<String, Object> typeReturn = mapper.convertValue(gameParamsHM.get(type), new TypeReference<HashMap<String, Object>>(){});

        if (TYPE_SHIP.equalsIgnoreCase(type)) {
            return typeReturn.get(ship);
        }
        return typeReturn;
    }
}