package WoWSSSC.model.WoWSAPI.info.ShipTypeImages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

/**
 * Created by Aesis on 2017. 4. 22..
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ShipTypeImages
{
    private AirCarrier AirCarrier;
    private Battleship Battleship;
    private Cruiser Cruiser;
    private Destroyer Destroyer;
}
