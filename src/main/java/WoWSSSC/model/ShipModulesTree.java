package WoWSSSC.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipModulesTree
{
    private String type;
    private String name;
    private String module_id_str;
    private long module_id;
    private long price_xp;
    private long price_credit;
    private boolean is_default;
    private List next_modules;
    private List next_ships;

    public boolean isIs_default()
    {
        return is_default;
    }
}
