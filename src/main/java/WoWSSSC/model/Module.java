package WoWSSSC.model;

import lombok.Data;
import org.json.simple.JSONObject;

/**
 * Created by Aesis on 2016-08-10.
 */
@Data
public class Module
{
    private String name;
    private String index;
    private long moduleId;
    private boolean isDefault;
    private JSONObject json;

    public Module(String name, String index, long moduleId, boolean isDefault, JSONObject json)
    {
        this.name = name;
        this.index = index;
        this.moduleId = moduleId;
        this.isDefault = isDefault;
        this.json = json;
    }
}
