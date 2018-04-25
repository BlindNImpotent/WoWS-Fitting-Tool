package WoWSSSC.model.gameparams.test.Values.ShipAbilities;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class AbilitySlot4
{
    private LinkedHashSet<List<String>> abils = new LinkedHashSet<>();
    private int slot;
}
