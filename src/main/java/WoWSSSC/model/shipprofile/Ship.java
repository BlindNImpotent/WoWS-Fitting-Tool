package WoWSSSC.model.shipprofile;

import WoWSSSC.model.shipprofile.profile.*;
import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Ship
{
    private long battle_level_range_max;
    private long battle_level_range_min;
    private Anti_Aircraft anti_aircraft;
    private Armour armour;
    private Artillery artillery;
    private ATBA atba;
    private ATBAS atbas;
    private Concealment concealment;
    private Dive_Bomber dive_bomber;
    private Engine engine;
    private Fighters fighters;
    private Fire_Control fire_control;
    private Flight_Control flight_control;
    private Hull hull;
    private Mobility mobility;
    private Torpedo_Bomber torpedo_bomber;
    private Torpedoes torpedoes;
    private Weaponry weaponry;
}
