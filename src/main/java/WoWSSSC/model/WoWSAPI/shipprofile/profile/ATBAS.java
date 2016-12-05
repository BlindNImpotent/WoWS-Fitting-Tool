package WoWSSSC.model.WoWSAPI.shipprofile.profile;

import WoWSSSC.model.WoWSAPI.shipprofile.profile.atbas.ATBAS_Slots;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class ATBAS
{
    private float distance;
    private HashMap<String, ATBAS_Slots> slots = new HashMap<>();
}
