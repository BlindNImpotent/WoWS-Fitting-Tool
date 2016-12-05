package WoWSSSC.model.WoWSAPI.shipprofile.profile;

import WoWSSSC.model.WoWSAPI.shipprofile.profile.anti_aircraft.Anti_Aircraft_Slot;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Anti_Aircraft
{
    private LinkedHashMap<String, Anti_Aircraft_Slot> slots = new LinkedHashMap<>();
}
