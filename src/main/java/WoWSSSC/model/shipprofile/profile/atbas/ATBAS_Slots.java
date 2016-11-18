package WoWSSSC.model.shipprofile.profile.atbas;

import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class ATBAS_Slots
{
    private long bullet_mass;
    private long bullet_speed;
    private float burn_probability;
    private long damage;
    private float gun_rate;
    private String name;
    private float shot_delay;
    private String type;
}
