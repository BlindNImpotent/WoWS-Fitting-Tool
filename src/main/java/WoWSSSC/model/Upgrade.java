package WoWSSSC.model;

import lombok.Data;
import org.json.simple.JSONObject;

/**
 * Created by Aesis on 2016-08-10.
 */
@Data
public class Upgrade
{
    private String name;
    private String index;
    private JSONObject json;

    public Upgrade(String name, String index, JSONObject json)
    {
        this.name = name;
        this.index = index;
        this.json = json;
    }
}
