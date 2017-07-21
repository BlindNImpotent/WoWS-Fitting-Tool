package WoWSSSC.model.WoWSAPI.shipprofile.profile;

import WoWSSSC.model.WoWSAPI.shipprofile.profile.torpedo_bomber.Torpedo_Bomber_Count_In_Squadron;
import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Torpedo_Bomber
{
    private float cruise_speed;
    private float gunner_damage;
    private int max_damage;
    private float max_health;
    private String name;
    private int plane_level;
    private float prepare_time;
    private int squadrons;
    private long torpedo_bomber_id;
    private String torpedo_bomber_id_str;
    private int torpedo_damage;
    private float torpedo_distance;
    private float torpedo_max_speed;
    private String torpedo_name;
    private Torpedo_Bomber_Count_In_Squadron count_in_squadron;
}
