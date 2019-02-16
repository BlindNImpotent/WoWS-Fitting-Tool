package WoWSFT.service;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static WoWSFT.model.Constant.*;

@Service
public class ParserService
{
    public void parseModules(Ship ship, String bits)
    {
        ship.setModules(new LinkedHashMap<>());
        ship.setPositions(new LinkedHashMap<>());
        LinkedHashMap<String, String> baseModules = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> basePositions = new LinkedHashMap<>();
        List<Integer> list = new ArrayList<>();
        LinkedHashMap<String, ShipUpgrade> shipUpgrades = new LinkedHashMap<>();

        if (StringUtils.isNotEmpty(bits)) {
            for (int i = 0; i < bits.length(); i++) {
                if (Character.isDigit(bits.charAt(i))) {
                    list.add(Character.getNumericValue(bits.charAt(i)));
                } else{
                    list.add(1);
                }
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

    public void parseUpgrades(Ship ship, String bits)
    {
        List<Integer> list = new ArrayList<>();

        if (StringUtils.isNotEmpty(bits)) {
            for (int i = 0; i < bits.length() && i < ship.getUpgrades().size(); i++) {
                if (Character.isDigit(bits.charAt(i))) {
                    list.add(Character.getNumericValue(bits.charAt(i)));
                } else{
                    list.add(0);
                }
            }

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > ship.getUpgrades().get(i).size()) {
                    list.set(i, 0);
                }
            }
            ship.setSUpgrades(list);
        }
    }

    public void parseSkills(Ship ship, long skill)
    {
        List<Integer> list = new ArrayList<>();
        String bits = Long.toBinaryString(skill);

        if (bits.length() < 32) {
            for (int i = 0; i < 32 - bits.length(); i++) {
                list.add(0);
            }
        }

        int pts = 0;
        for (int i = bits.length() - 1; i >= 0; i--) {
            int check = Character.getNumericValue(bits.charAt(i));
            int tempPts = (bits.length() - i) / 8 + 1;

            if (check == 1 && tempPts + pts <= 19) {
                pts += tempPts;
                list.add(Character.getNumericValue(bits.charAt(i)));
            } else {
                list.add(0);
            }
        }

        ship.setSSkills(list);
    }
}
