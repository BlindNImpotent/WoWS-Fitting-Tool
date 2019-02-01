package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@WoWSFT
public class ShipUpgradeInfo
{
    private List<ShipUpgrade> artillery = new ArrayList<>();
    private List<ShipUpgrade> engine = new ArrayList<>();
    private List<ShipUpgrade> hull = new ArrayList<>();
    private List<ShipUpgrade> suo = new ArrayList<>();
    private List<ShipUpgrade> torpedoes = new ArrayList<>();
    private List<ShipUpgrade> flightControl = new ArrayList<>();
    private List<ShipUpgrade> diveBomber = new ArrayList<>();
    private List<ShipUpgrade> fighter = new ArrayList<>();
    private List<ShipUpgrade> torpedoBomber = new ArrayList<>();

    private HashMap<String, ShipUpgrade> upgrades = new HashMap<>();

    private int costCR;
    private int costGold;
    private int costSaleGold;
    private int costXP;
    private List<Object> lockedConfig;
    private int value;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setShipUpgrades(String name, Object value) {
        ShipUpgrade upgrade = mapper.convertValue(value, ShipUpgrade.class);
        upgrade.setName(name);
        if (StringUtils.isEmpty(upgrade.getPrev())) {
            upgrade.setPosition(1);
        }
        else if (CollectionUtils.isNotEmpty(upgrade.getNextShips())) {
            upgrade.setPosition(3);
        }
        else {
            upgrade.setPosition(2);
        }

        upgrades.put(upgrade.getName(), upgrade);

        switch (upgrade.getUcTypeShort()) {
            case "artillery": artillery.add(upgrade); break;
            case "engine": engine.add(upgrade); break;
            case "hull": hull.add(upgrade); break;
            case "suo": suo.add(upgrade); break;
            case "torpedoes": torpedoes.add(upgrade); break;
            case "flightControl": flightControl.add(upgrade); break;
            case "diveBomber": diveBomber.add(upgrade); break;
            case "fighter": fighter.add(upgrade); break;
            case "torpedoBomber": torpedoBomber.add(upgrade); break;
            default: break;
        }
    }
}
