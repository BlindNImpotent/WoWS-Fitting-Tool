package WoWSSSC.model.WoWSAPI.shipprofile.profile;

import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Flight_Control
{
    private long bomber_squadrons;
    private long fighter_squadrons;
    private long flight_control_id;
    private String flight_control_id_str;
    private long torpedo_squadrons;
}
