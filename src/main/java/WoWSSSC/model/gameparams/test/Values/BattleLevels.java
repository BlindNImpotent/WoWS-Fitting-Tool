package WoWSSSC.model.gameparams.test.Values;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class BattleLevels
{
    private List<Integer> COOPERATIVE;
    private List<Integer> PVP;
}
