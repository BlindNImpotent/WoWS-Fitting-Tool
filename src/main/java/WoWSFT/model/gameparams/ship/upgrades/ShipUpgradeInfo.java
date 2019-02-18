package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static WoWSFT.model.Constant.*;

@Data
@WoWSFT
@Accessors(chain = true)
public class ShipUpgradeInfo
{
    private LinkedHashMap<String, List<ShipUpgrade>> components = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> cols = new LinkedHashMap<>();
    private int maxRows;

    private int costCR;
    private int costGold;
    private int costSaleGold;
    private int costXP;
    private List<Object> lockedConfig;
    private int value;

    {
        componentsList.forEach(c -> components.put(c, new ArrayList<>()));
    }

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setShipUpgrades(String name, Object value)
    {
        ShipUpgrade upgrade = mapper.convertValue(value, ShipUpgrade.class);
        upgrade.setName(name);

        if (StringUtils.isEmpty(upgrade.getPrev())) {
            upgrade.setPosition(1);
        } else {
            upgrade.setPosition(2);
        }

        components.get(upgrade.getUcTypeShort()).add(upgrade);
    }
}
