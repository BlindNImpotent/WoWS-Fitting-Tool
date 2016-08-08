package WoWSSSC.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Aesis on 2016-08-08.
 */
@Service
public class JSONService
{
    public JSONObject getGameParams() throws IOException, ParseException
    {
        JSONParser JSONParser = new JSONParser();
        File GameParamsFile = new File("src/main/java/GP_JSON/GameParams.json");
        JSONObject GameParams;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(GameParamsFile),"UTF8"));
        GameParams = (JSONObject) JSONParser.parse(reader);

        return GameParams;
    }

    public String test() throws IOException, ParseException {
        JSONParser JSONParser = new JSONParser();
        File GameParamsFile = new File("src/main/java/GP_JSON/GameParams.json");
        JSONObject GameParams;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(GameParamsFile),"UTF8"));
        GameParams = (JSONObject) JSONParser.parse(reader);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(GameParams.toString().getBytes());
        gzip.close();
        String outStr = out.toString("UTF-8");
        return outStr;
    }
}
