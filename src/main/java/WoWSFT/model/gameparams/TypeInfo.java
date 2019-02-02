package WoWSFT.model.gameparams;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonSetter;
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

    @JsonSetter
    public void setNation(String nation)
    {
        this.nation = nation.replaceAll("_", " ");
    }
}
