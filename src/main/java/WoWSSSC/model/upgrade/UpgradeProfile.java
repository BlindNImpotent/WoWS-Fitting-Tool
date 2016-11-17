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
    private HashMap<String, Anti_Aircraft> anti_aircraft;
    private HashMap<String, Artillery> artillery;
    private HashMap<String, ATBA> atba;
    private HashMap<String, Concealment> concealment;
    private HashMap<String, Damage_Control> damage_control;
    private HashMap<String, Engine> engine;
    private HashMap<String, Fire_Control> fire_control;
    private HashMap<String, Flight_Control> flight_control;
    private HashMap<String, Guidance> guidance;
    private HashMap<String, Mainweapon> mainweapon;
    private HashMap<String, Planes> planes;
    private HashMap<String, Powder> powder;
    private HashMap<String, Secondweapon> secondweapon;
    private HashMap<String, Spotting> spotting;
    private HashMap<String, Steering> steering;
    private HashMap<String, Torpedoes> torpedoes;
}
