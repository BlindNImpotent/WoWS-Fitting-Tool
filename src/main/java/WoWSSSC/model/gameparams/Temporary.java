package WoWSSSC.model.gameparams;

import WoWSSSC.model.gameparams.ShipUpgradeInfo.ShipUpgradeInfo;
import WoWSSSC.model.gameparams.test.Values.ShipAbilities.ShipAbilities;
import WoWSSSC.model.gameparams.test.Values.ShipModernization.ShipModernization;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temporary
{
    private long id;
    private String name;
    private TypeInfo typeinfo;

    @JsonProperty(defaultValue = "ShipAbilities")
    private ShipAbilities ShipAbilities;
    @JsonProperty(defaultValue = "ShipModernization")
    private ShipModernization ShipModernization;
    @JsonProperty(defaultValue = "ShipUpgradeInfo")
    private ShipUpgradeInfo ShipUpgradeInfo;
}
