package WoWSSSC.service;

import WoWSSSC.parser.APIParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by Aesis on 2016-10-15.
 */
@Service
public class APIService
{
    @Autowired
    private APIParser apiParser;

    public JSONObject getWarships() throws IOException, ParseException
    {
        return apiParser.getShipListJSON();
    }

    public List<String> getNationsList()
    {
        JSONObject shipListJSON = apiParser.getShipListJSON();
        List<String> nationsList = new ArrayList<>(shipListJSON.keySet());
        nationsList.sort((o1, o2) -> o1.compareTo(o2));

        return nationsList;
    }

    public List<String> getTypesList()
    {
        JSONObject shipListJSON = apiParser.getShipListJSON();
        List<String> nationsList = new ArrayList<>(shipListJSON.keySet());
        Set<String> typesSet = new HashSet<>();
        nationsList.forEach(n ->
        {
            JSONObject jsonObject = (JSONObject) shipListJSON.get(n);
            typesSet.addAll(jsonObject.keySet());
        });

        List<String> typesList = new ArrayList<>(typesSet);
        typesList.sort((o1, o2) -> o1.compareTo(o2));

        return typesList;
    }

}
