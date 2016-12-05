package WoWSSSC.model.gameparams.test.Values.ShipModernization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class ShipModernization
{
    @JsonProperty(value = "ModernizationSlot1")
    private ModernizationSlot1 ModernizationSlot1;
    @JsonProperty(value = "ModernizationSlot2")
    private ModernizationSlot2 ModernizationSlot2;
    @JsonProperty(value = "ModernizationSlot3")
    private ModernizationSlot3 ModernizationSlot3;
    @JsonProperty(value = "ModernizationSlot4")
    private ModernizationSlot4 ModernizationSlot4;
    @JsonProperty(value = "ModernizationSlot5")
    private ModernizationSlot5 ModernizationSlot5;
    @JsonProperty(value = "ModernizationSlot6")
    private ModernizationSlot6 ModernizationSlot6;
}
