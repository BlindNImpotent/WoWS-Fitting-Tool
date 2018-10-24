package WoWSSSC.model.WoWSAPI.shipprofile.profile;

import WoWSSSC.model.WoWSAPI.shipprofile.profile.artillery.Artillery_Shells;
import WoWSSSC.model.WoWSAPI.shipprofile.profile.artillery.Artillery_Slots;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Artillery
{
    private long artillery_id;
    private String artillery_id_str;
    private float distance;
    private float gun_rate;
    private float max_dispersion;
    private float rotation_time;
    private float shot_delay;
    private HashMap<String, Artillery_Shells> shells = new HashMap<>();
    private HashMap<String, Artillery_Slots> slots = new HashMap<>();

    private long totalGuns;
    private float dConstant;
    private float dModifier;
    private float dispModifier = 1f;
}
