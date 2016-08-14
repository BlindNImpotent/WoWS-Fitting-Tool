package WoWSSSC.model;

import lombok.Data;
import org.json.simple.JSONObject;

/**
 * Created by Aesis on 2016-08-15.
 */
@Data
public class Flags
{
    private String code;
    private String name;

    public Flags(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

}
