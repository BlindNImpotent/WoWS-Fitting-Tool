package WoWSSSC.model.shipprofile.profile;

import WoWSSSC.model.shipprofile.profile.torpedo_bomber.Torpedo_Bomber_Count_In_Squadron;
import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Torpedo_Bomber
{
    private long cruise_speed;
    private long gunner_damage;
    private long max_damage;
    private long max_health;
    private String name;
    private long plane_level;
    private long prepare_time;
    private long squadrons;
    private long torpedo_bomber_id;
    private String torpedo_bomber_id_str;
    private long torpedo_damage;
    private float torpedo_distance;
    private long torpedo_max_speed;
    private String torpedo_name;
    private Torpedo_Bomber_Count_In_Squadron count_in_squadron;
}
