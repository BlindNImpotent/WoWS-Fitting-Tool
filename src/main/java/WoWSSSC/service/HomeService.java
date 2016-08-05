package WoWSSSC.service;

import JAR.Calc;
import Parser.API_NameLists;
import WoWSSSC.model.ShipNameList;
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

    private API_NameLists apiNameLists;

    public HomeService() throws IOException, ParseException {
        apiNameLists = new API_NameLists();
    }

    public ShipNameList getNameList() throws IOException, ParseException
    {
        ShipNameList shipNameList = new ShipNameList();

        return shipNameList;
    }

    public Calc getCalc(String name) throws IOException, ParseException
    {
        if (!name.equals(""))
        {
            calc = new Calc(name);
        }

        return calc;
    }



}
