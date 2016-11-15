package WoWSSSC.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private String nation;
    private String type;
    private String name;
    private String ship_id_str;
    private String description;
    private long tier;
    private long ship_id;
    private long price_gold;
    private long price_credit;
    private long mod_slots;
    private boolean is_premium;
    private HashMap<String, String> images = new HashMap<>();
    private HashMap<String, List> modules = new HashMap<>();
    private HashMap<String, ShipModulesTree> modules_tree = new HashMap<>();
    private HashMap<String, Long> next_ships = new HashMap<>();
    private List upgrades;
}
