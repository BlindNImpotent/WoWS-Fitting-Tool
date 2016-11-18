package WoWSSSC.model.shipprofile.profile;

import WoWSSSC.model.shipprofile.profile.atba.ATBA_Shells;
import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class ATBA
{
    private float distance;
    private float gun_rate;
    private ATBA_Shells shells;
}
