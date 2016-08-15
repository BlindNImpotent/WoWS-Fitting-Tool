package WoWSSSC.model;

import lombok.Data;
import org.json.simple.JSONObject;

/**
 * Created by Aesis on 2016-08-15.
 */
@Data
public class Skills
{
    private String code;
    private String name;
    private JSONObject json;

    public Skills(String code, String name, JSONObject json)
    {
        this.code = code;
        this.name = name;
        this.json = json;
    }

}
