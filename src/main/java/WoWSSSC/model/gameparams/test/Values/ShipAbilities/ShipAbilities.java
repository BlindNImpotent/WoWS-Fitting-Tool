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
    private AbilitySlot0 AbilitySlot0;
    @JsonProperty(value = "AbilitySlot1")
    private AbilitySlot1 AbilitySlot1;
    @JsonProperty(value = "AbilitySlot2")
    private AbilitySlot2 AbilitySlot2;
    @JsonProperty(value = "AbilitySlot3")
    private AbilitySlot3 AbilitySlot3;
}