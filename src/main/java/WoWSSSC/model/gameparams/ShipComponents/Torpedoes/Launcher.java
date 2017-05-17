package WoWSSSC.model.gameparams.ShipComponents.Torpedoes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2017-05-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Launcher
{
    private List<String> ammoList;
}
