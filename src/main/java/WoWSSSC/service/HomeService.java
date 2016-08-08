package WoWSSSC.service;

import JAR.Calc;
import Parser.API_NameLists;
import Parser.GameParams_Parser;
import Parser.JSON_Parser;
import WoWSSSC.model.ShipNameList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by Aesis on 2016-08-04.
 */
@Service
public class HomeService
{
    public ShipNameList getNameList() throws IOException, ParseException
    {
        ShipNameList shipNameList = new ShipNameList();

        return shipNameList;
    }
}
