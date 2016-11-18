package WoWSSSC.model.shipprofile.profile;

import WoWSSSC.model.shipprofile.profile.anti_aircraft.Anti_Aircraft_Slot;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Anti_Aircraft
{
    private HashMap<String, Anti_Aircraft_Slot> slots = new HashMap<>();
}
