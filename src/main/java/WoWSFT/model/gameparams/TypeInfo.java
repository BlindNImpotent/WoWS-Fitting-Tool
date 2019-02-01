package WoWSFT.model.gameparams;

import WoWSFT.config.WoWSFT;
import lombok.Data;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@WoWSFT
public class TypeInfo
{
    private String nation;
    private String species;
    private String type;
}
