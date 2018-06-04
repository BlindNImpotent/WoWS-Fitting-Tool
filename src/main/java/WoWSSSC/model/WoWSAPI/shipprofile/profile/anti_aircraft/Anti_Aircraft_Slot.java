package WoWSSSC.model.WoWSAPI.shipprofile.profile.anti_aircraft;

import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Anti_Aircraft_Slot
{
    private float avg_damage;
    private float caliber;
    private float distance;
    private long guns;
    private String name;
    private String auraType;
}
