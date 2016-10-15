package WoWSSSC.service;

import WoWSSSC.parser.APIParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Aesis on 2016-10-15.
 */
@Service
public class APIService
{
    private APIParser apiParser = new APIParser();
    private JSONObject ship;
    private JSONObject modules;
    private JSONArray artillery;
    private JSONArray dive_bomber;
    private JSONArray engine;
    private JSONArray fighter;
    private JSONArray fire_control;
    private JSONArray flight_control;
    private JSONArray hull;
    private JSONArray torpedoes;
    private JSONArray torpedo_bomber;
    private JSONArray upgrades;

    public JSONObject getWarship(String name) throws IOException, ParseException
    {
        apiParser.setUp();
        ship = apiParser.getAPI_WarshipsHashMap().get(name);

//        modules = (JSONObject) ship.get("modules");
//        artillery = (JSONArray) modules.get("artillery");
//        dive_bomber = (JSONArray) modules.get("dive_bomber");
//        engine = (JSONArray) modules.get("engine");
//        fighter = (JSONArray) modules.get("fighter");
//        fire_control = (JSONArray) modules.get("fire_control");
//        flight_control = (JSONArray) modules.get("flight_control");
//        hull = (JSONArray) modules.get("hull");
//        torpedoes = (JSONArray) modules.get("torpedoes");
//        torpedo_bomber = (JSONArray) modules.get("torpedo_bomber");
//        upgrades = (JSONArray) ship.get("upgrades");
//
//        setArtillery();
//        setDiveBomber();
//        setEngine();
//        setFighter();
//        setFireControl();
//        setFlightControl();
//        setHull();
//        setTorpedoBomber();
//        setTorpedoes();
//        setUpgrades();

        return ship;
    }

    public JSONObject getWarships() throws IOException, ParseException
    {
        apiParser.setUp();

        return apiParser.getShipListJSON();
    }


}
