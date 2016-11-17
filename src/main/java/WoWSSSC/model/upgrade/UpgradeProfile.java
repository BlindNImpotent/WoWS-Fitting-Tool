package WoWSSSC.model.upgrade;

import WoWSSSC.model.upgrade.profile.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpgradeProfile
{
    private Anti_Aircraft anti_aircraft;
    private Artillery artillery;
    private ATBA atba;
    private Concealment concealment;
    private Damage_Control damage_control;
    private Engine engine;
    private Fire_Control fire_control;
    private Flight_Control flight_control;
    private Guidance guidance;
    private Mainweapon mainweapon;
    private Planes planes;
    private Powder powder;
    private Secondweapon secondweapon;
    private Spotting spotting;
    private Steering steering;
    private Torpedoes torpedoes;
}
