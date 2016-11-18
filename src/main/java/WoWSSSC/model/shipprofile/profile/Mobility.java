package WoWSSSC.model.shipprofile.profile;

import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Mobility
{
    private float max_speed;
    private float rudder_time;
    private long total;
    private long turning_radius;
}
