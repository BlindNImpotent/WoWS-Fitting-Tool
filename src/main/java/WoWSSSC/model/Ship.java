package WoWSSSC.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;

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
    private int tier;
    private int ship_id;
    private int price_gold;
    private int price_credit;
    private int mod_slots;
    private boolean is_premium;
    private HashMap<String, String> images = new HashMap<>();
    private HashMap<String, Arrays> modules = new HashMap<>();
    private HashMap<String, ShipModule> modules_tree = new HashMap<>();
    private HashMap<String, Integer> next_ships = new HashMap<>();
    private Arrays upgrades;
}
