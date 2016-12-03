package WoWSSSC.model.gameparams.Ship.ShipModule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class PlanesReserveAssignment
{
    @JsonProperty(value = "Bomber")
    private int Bomber;
    @JsonProperty(value = "Dive")
    private int Dive;
    @JsonProperty(value = "Fighter")
    private int Fighter;
    @JsonProperty(value = "Scout")
    private int Scout;
}
