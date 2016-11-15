package WoWSSSC.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipModule
{
    private String type;
    private String name;
    private String module_id_str;
    private int module_id;
    private int price_xp;
    private int price_credit;
    private boolean is_default;
    private Arrays next_modules;
    private Arrays next_ships;
}
