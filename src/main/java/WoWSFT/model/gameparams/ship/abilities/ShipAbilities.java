package WoWSFT.model.gameparams.ship.abilities;

import WoWSFT.config.WoWSFT;
import lombok.Data;

@Data
@WoWSFT
public class ShipAbilities
{
    private AbilitySlot AbilitySlot0;
    private AbilitySlot AbilitySlot1;
    private AbilitySlot AbilitySlot2;
    private AbilitySlot AbilitySlot3;
    private AbilitySlot AbilitySlot4;
}
