package WoWSFT.model.gameparams;

import WoWSFT.config.WoWSFT;
import lombok.Data;

@Data
@WoWSFT
public class TypeInfo
{
    private String nation;
    private String species;
    private String type;
}
