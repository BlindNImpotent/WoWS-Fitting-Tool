package WoWSSSC.model.gameparams.Ship;

import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class BattleLevel
{
    private List<Integer> pve;
    private List<Integer> pvp;
}
