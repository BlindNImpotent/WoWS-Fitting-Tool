package WoWSFT.model.gameparams.ship.component.airarmament;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirArmament
{
    private double auraCoeff;
    private double deckPlaceCount;
    private boolean isIndependentLaunchpad;
    private double launchPrepareTime;
    private String launchpadType;
    private double planesReserveCapacity;
}
