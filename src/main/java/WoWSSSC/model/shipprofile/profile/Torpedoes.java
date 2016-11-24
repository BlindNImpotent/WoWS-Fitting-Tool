package WoWSSSC.model.shipprofile.profile;

import WoWSSSC.model.shipprofile.profile.torpedoes.Torpedoes_Slots;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Torpedoes
{
    private float distance;
    private long max_damage;
    private float reload_time;
    private float rotation_time;
    private String torpedo_name;
    private long torpedo_speed;
    private long torpedoes_id;
    private String torpedoes_id_str;
    private float visibility_dist;
    private HashMap<String, Torpedoes_Slots> slots = new HashMap<>();
}
