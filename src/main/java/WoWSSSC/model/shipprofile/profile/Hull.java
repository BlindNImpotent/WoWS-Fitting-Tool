package WoWSSSC.model.shipprofile.profile;

import WoWSSSC.model.shipprofile.profile.hull.Hull_Range;
import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Hull
{
    private long anti_aircraft_barrels;
    private long artillery_barrels;
    private long atba_barrels;
    private long health;
    private long hull_id;
    private String hull_id_str;
    private long planes_amount;
    private long torpedoes_barrels;
    private Hull_Range range;
}
