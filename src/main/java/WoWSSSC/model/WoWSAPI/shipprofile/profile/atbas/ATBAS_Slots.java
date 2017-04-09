package WoWSSSC.model.WoWSAPI.shipprofile.profile.atbas;

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
    private float default_shot_delay;

    public void setShot_delayWithoutDefault(float shot_delay)
    {
        this.shot_delay = shot_delay;
    }

    public void setShot_delay(float shot_delay)
    {
        this.shot_delay = shot_delay;
        this.default_shot_delay = shot_delay;
    }
}
