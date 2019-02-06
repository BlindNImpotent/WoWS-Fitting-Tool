package WoWSFT.service;

import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static WoWSFT.model.Constant.*;

@Service
public class ParserService
{
    @Autowired
    @Qualifier(value = TYPE_SHIP)
    private LinkedHashMap<String, Ship> ships;

    public void parseModules(Model model, String index, String bits)
    {
        Ship ship = ships.get(index);
        HashMap<String, String> modules = new HashMap<>();
        HashMap<String, String> baseModules = new HashMap<>();
        HashMap<String, Integer> positions = new HashMap<>();
        HashMap<String, Integer> basePositions = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        LinkedHashMap<String, ShipUpgrade> shipUpgrades = new LinkedHashMap<>();

        if (StringUtils.isNotEmpty(bits)) {
            for (int i = 0; i < bits.length(); i++) {
                list.add(Character.getNumericValue(bits.charAt(i)));
            }
        }

        AtomicInteger pos = new AtomicInteger();
        ship.getShipUpgradeInfo().getComponents().forEach((type, value) -> {
            if (CollectionUtils.isNotEmpty(value)) {
                int position = pos.getAndIncrement();
                basePositions.put(type, 1);
                value.get(0).getComponents().forEach((x, y) -> {
                    if (CollectionUtils.isNotEmpty(y)) {
                        baseModules.put(x, y.get(0));
                    }
                });

                if (CollectionUtils.isNotEmpty(list)) {
                    positions.put(type, list.get(position));
                    shipUpgrades.put(type, value.get(list.get(position) - 1));
                }
            }
        });

        shipUpgrades.forEach((type, upgrade) -> {
            if (!type.equalsIgnoreCase(hull)) {
                if (CollectionUtils.isEmpty(shipUpgrades.get(hull).getComponents().get(type))) {
                    if (shipUpgrades.get(upgrade.getPrevType()).getPosition() >= upgrade.getPrevPosition()) {
                        modules.put(type, upgrade.getComponents().get(type).get(0));
                    }
                } else if (upgrade.getComponents().get(type).contains(shipUpgrades.get(hull).getComponents().get(type).get(0))) {
                    modules.put(type, shipUpgrades.get(hull).getComponents().get(type).get(0));
                }
            } else {
                modules.put(type, shipUpgrades.get(hull).getComponents().get(type).get(0));
            }
        });

        if (bits.length() > 0 && modules.size() == bits.length()) {
            shipUpgrades.get(hull).getComponents().forEach((x, y) -> {
                if (!modules.containsKey(x) && CollectionUtils.isNotEmpty(y)) {
                    modules.put(x, y.get(0));
                }
            });
            model.addAttribute(TYPE_MODULE, modules);
            model.addAttribute("positions", positions);
        } else {
            model.addAttribute(TYPE_MODULE, baseModules);
            model.addAttribute("positions", basePositions);
        }
    }
}
