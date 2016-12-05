package WoWSSSC.model.WoWSAPI.shipprofile.profile;

import WoWSSSC.model.WoWSAPI.shipprofile.profile.armour.*;
import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Armour
{
    private long flood_damage;
    private long flood_prob;
    private long health;
    private long total;
    private Armour_Casemate casemate;
    private Armour_Citadel citadel;
    private Armour_Deck deck;
    private Armour_Extremities extremities;
    private Armour_Range range;
}
