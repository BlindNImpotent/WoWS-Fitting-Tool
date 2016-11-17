package WoWSSSC.model.upgrade;

import WoWSSSC.model.upgrade.profile.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpgradeProfile
{
    private HashMap<String, Anti_Aircraft> anti_aircraft = new HashMap<>();
    private HashMap<String, Artillery> artillery = new HashMap<>();
    private HashMap<String, ATBA> atba = new HashMap<>();
    private HashMap<String, Concealment> concealment = new HashMap<>();
    private HashMap<String, Damage_Control> damage_control = new HashMap<>();
    private HashMap<String, Engine> engine = new HashMap<>();
    private HashMap<String, Fire_Control> fire_control = new HashMap<>();
    private HashMap<String, Flight_Control> flight_control = new HashMap<>();
    private HashMap<String, Guidance> guidance = new HashMap<>();
    private HashMap<String, Mainweapon> mainweapon = new HashMap<>();
    private HashMap<String, Planes> planes = new HashMap<>();
    private HashMap<String, Powder> powder = new HashMap<>();
    private HashMap<String, Secondweapon> secondweapon = new HashMap<>();
    private HashMap<String, Spotting> spotting = new HashMap<>();
    private HashMap<String, HashMap> steering = new HashMap<>();
    private HashMap<String, Torpedoes> torpedoes = new HashMap<>();
}
