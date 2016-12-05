package WoWSSSC.model.WoWSAPI.shipprofile.profile.atba;

import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class ATBA_Shells_AP
{
    private long bullet_mass;
    private long bullet_speed;
    private float burn_probability;
    private long damage;
    private String name;
    private String type;
}
