package WoWSSSC.service;

import JAR.Calc;
import WoWSSSC.model.Ship;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Aesis on 2016-08-04.
 */
@Service
public class HomeService
{
    private Calc calc;

    public Calc getCalc(Ship ship) throws IOException, ParseException
    {
        calc = new Calc(ship.getName());
        calc = new Calc(ship.getName(), ship.getTurret(), ship.getHull(), ship.getEngine(), ship.getRadar(), ship.getTorpedo(), ship.getConsume1(), ship.getConsume2(), ship.getConsume3(), ship.getConsume4());

        return calc;
    }

}
