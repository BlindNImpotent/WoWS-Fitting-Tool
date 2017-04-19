package WoWSSSC.model.WoWSAPI.consumables;

import WoWSSSC.model.WoWSAPI.consumables.factors.*;
import lombok.Data;

/**
 * Created by Aesis on 2017. 4. 20..
 */
@Data
public class ConsumablesProfile
{
    private AfterBattleRepair afterBattleRepair;
    private CreditsFactor creditsFactor;
    private CrewExpFactor crewExpFactor;
    private ExpFactor expFactor;
    private FreeExpFactor freeExpFactor;
    private RLSSearchWorkTime rlsSearchWorkTime;
    private ShootShift shootShift;
    private VisibilityDistCoeff visibilityDistCoeff;
    private VisibilityFactor visibilityFactor;

}
