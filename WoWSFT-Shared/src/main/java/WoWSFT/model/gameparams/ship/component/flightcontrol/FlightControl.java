package WoWSFT.model.gameparams.ship.component.flightcontrol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightControl
{
    private LinkedHashMap<String, Integer> consumablePlanesReserve;
    private List<Object> consumableSquadrons;
    private boolean hasConsumablePlanes;
    @JsonProperty("isGroundAttackEnabled")
    private boolean groundAttackEnabled;
    private LinkedHashMap<String, Integer> planesReserveAssignment;
    private double prepareTimeFactor;
    private List<String> squadrons;
}
