package WoWSSSC.model.gameparams.test.Values.ShipAbilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class ShipAbilities
{
    @JsonProperty(value = "AbilitySlot0")
    private AbilitySlot0 abilitySlot0;
    @JsonProperty(value = "AbilitySlot1")
    private AbilitySlot1 abilitySlot1;
    @JsonProperty(value = "AbilitySlot2")
    private AbilitySlot2 abilitySlot2;
    @JsonProperty(value = "AbilitySlot3")
    private AbilitySlot3 abilitySlot3;
    @JsonProperty(value = "AbilitySlot4")
    private AbilitySlot4 abilitySlot4;
}
