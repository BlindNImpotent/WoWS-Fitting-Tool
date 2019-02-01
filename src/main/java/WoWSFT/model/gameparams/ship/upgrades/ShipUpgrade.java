package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipUpgrade
{
    private ShipComponents components;
    private List<String> disabledAbilities;
    private List<String> nextShips = new ArrayList<>();
    private String name;
    private String prev;
    private String ucType;
    private int position;

    public String getUcTypeShort()
    {
        return StringUtils.isNotEmpty(ucType) ? StringUtils.uncapitalize(ucType.replace("_", "")) : ucType;
    }
}
