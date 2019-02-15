package WoWSFT.service;

import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    public void setModules(Ship ship, String bits)
    {
        ship.setModules(new LinkedHashMap<>());
        ship.setPositions(new LinkedHashMap<>());
        LinkedHashMap<String, String> baseModules = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> basePositions = new LinkedHashMap<>();
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
                    ship.getPositions().put(type, list.get(position));
                    shipUpgrades.put(type, value.get(list.get(position) - 1));
                }
            }
        });

        shipUpgrades.forEach((type, upgrade) -> {
            if (!type.equalsIgnoreCase(hull)) {
                if (CollectionUtils.isEmpty(shipUpgrades.get(hull).getComponents().get(type))) {
                    if (shipUpgrades.get(upgrade.getPrevType()).getPosition() >= upgrade.getPrevPosition()) {
                        ship.getModules().put(type, upgrade.getComponents().get(type).get(0));
                    }
                } else {
                    shipUpgrades.get(hull).getComponents().get(type).forEach(x -> {
                        if (upgrade.getComponents().get(type).contains(x)) {
                            ship.getModules().put(type, x);
                        }
                    });
                }
            } else {
                ship.getModules().put(type, shipUpgrades.get(hull).getComponents().get(type).get(0));
            }
        });

        if (bits.length() > 0 && ship.getModules().size() == bits.length()) {
            shipUpgrades.get(hull).getComponents().forEach((x, y) -> {
                if (!ship.getModules().containsKey(x) && CollectionUtils.isNotEmpty(y)) {
                    ship.getModules().put(x, y.get(0));
                }
            });
        } else {
            ship.getModules().clear();
            ship.getModules().putAll(baseModules);
            ship.getPositions().clear();
            ship.getPositions().putAll(basePositions);
        }
    }

    public void setUpgrades(Ship ship, String bits)
    {
        int slots = ship.getUpgrades().size();
        List<Integer> list = new ArrayList<>();

        if (StringUtils.isNotEmpty(bits)) {
            for (int i = 0; i < bits.length(); i++) {
                list.add(Character.getNumericValue(bits.charAt(i)));
            }
        }
    }
}
