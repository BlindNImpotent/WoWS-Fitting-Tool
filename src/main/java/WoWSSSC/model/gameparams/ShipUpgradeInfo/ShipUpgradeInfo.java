package WoWSSSC.model.gameparams.ShipUpgradeInfo;

import WoWSSSC.model.gameparams.ShipUpgradeInfo.Module.Module;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipUpgradeInfo
{
    private HashMap<String, Module> modules = new HashMap<>();
    private long costCR;
    private long costGold;
    private long costXP;
    private List lockedConfig;
    private long rentCostCR;
    private long rentCostGold;
    private long value;

    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setModules(String name, Object value)
    {
        modules.put(name, mapper.convertValue(value, Module.class));
    }
}
