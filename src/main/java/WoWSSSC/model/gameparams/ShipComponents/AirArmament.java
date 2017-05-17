package WoWSSSC.model.gameparams.ShipComponents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017-05-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirArmament
{
    private int planesReserveCapacity;
}
