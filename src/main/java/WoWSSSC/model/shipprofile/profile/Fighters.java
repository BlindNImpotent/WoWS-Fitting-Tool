package WoWSSSC.model.shipprofile.profile;

import WoWSSSC.model.shipprofile.profile.fighters.Fighters_Count_In_Squadron;
import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Fighters
{
    private long avg_damage;
    private float cruise_speed;
    private long fighters_id;
    private String fighters_id_str;
    private float gunner_damage;
    private long max_ammo;
    private float max_health;
    private String name;
    private long plane_level;
    private float prepare_time;
    private long squadrons;
    private Fighters_Count_In_Squadron count_in_squadron;
}
