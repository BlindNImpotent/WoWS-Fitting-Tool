package WoWSFT.model.gameparams.ship.component.airarmament;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirArmament
{
    private float auraCoeff;
    private float deckPlaceCount;
    private boolean isIndependentLaunchpad;
    private float launchPrepareTime;
    private String launchpadType;
    private float planesReserveCapacity;
}
