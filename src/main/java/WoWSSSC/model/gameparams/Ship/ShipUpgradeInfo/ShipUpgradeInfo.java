package WoWSSSC.model.gameparams.Ship.ShipUpgradeInfo;

import WoWSSSC.model.gameparams.Ship.ShipUpgradeInfo.Module.Module;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class ShipUpgradeInfo
{
    private HashMap<String, Module> modules = new HashMap<>();
    private long costCR;
    private long costGold;
    private long costXP;
    private List lockedConfig;
    private long rentCostCR;
    private long rentCostGold;

    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setModules(String name, Object value)
    {
        modules.put(name, mapper.convertValue(value, Module.class));
    }
}
