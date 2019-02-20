package WoWSFT.service;

import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static WoWSFT.model.Constant.*;

@Service
public class ParserService
{
    @Autowired
    @Qualifier(value = "nameToId")
    private HashMap<String, String> nameToId;

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

                if (CollectionUtils.isNotEmpty(list) && value.size() >= list.get(position)) {
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
            for (int i = 0; i < ship.getUpgrades().size(); i++) {
                if (i < bits.length() && Character.isDigit(bits.charAt(i)) && Character.getNumericValue(bits.charAt(i)) <= ship.getUpgrades().get(i).size()) {
                    list.add(Character.getNumericValue(bits.charAt(i)));
                } else {
                    list.add(0);
                }
            }

            ship.setSelectUpgrades(list);
        }
    }

    public void parseConsumables(Ship ship, String bits)
    {
        List<Integer> list = new ArrayList<>();

        if (StringUtils.isNotEmpty(bits)) {
            for (int i = 0; i < ship.getConsumables().size(); i++) {
                if (i < bits.length() && Character.isDigit(bits.charAt(i)) && Character.getNumericValue(bits.charAt(i)) <= ship.getConsumables().get(i).size()) {
                    list.add(Character.getNumericValue(bits.charAt(i)));
                } else {
                    list.add(1);
                }
            }

            ship.setSelectConsumables(list);
        }
    }

    public void parseSkills(Ship ship, long skill, int ar)
    {
        List<Integer> list = new ArrayList<>();
        String bits = Long.toBinaryString(skill);

        int pts = 0;
        for (int i = bits.length() - 1; i >= 0; i--) {
            int check = Character.getNumericValue(bits.charAt(i));
            int tempPts = (bits.length() - 1 - i) / 8 + 1;
            if (check == 1 && tempPts + pts <= 19) {
                pts += tempPts;
                list.add(Character.getNumericValue(bits.charAt(i)));

                // Adrenaline Rush
                if (bits.length() - 1 - i == 14) {
                    ship.setArUse(Character.getNumericValue(bits.charAt(i)) == 1);
                    ship.setAdrenaline((100 - ar) / 100f);
                }
            } else {
                list.add(0);
            }
        }

        if (bits.length() < 32) {
            for (int i = 0; i < 32 - bits.length(); i++) {
                list.add(0);
            }
        }

        ship.setSelectSkills(list);
        ship.setSelectSkillPts(pts);
    }

    public String parseLegacyUrl(HttpServletRequest request) throws Exception
    {
        String url = "/ship";

        if (request.getParameterMap() != null && request.getParameterMap().size() > 0) {
            String shipName = getParameter(request.getParameterMap().get("ship"));
            if (StringUtils.isNotEmpty(shipName)) {
                String index = nameToId.get(shipName.replace("+", " "));

                if (StringUtils.isNotEmpty(index)) {
                    url = url.concat("?index=" + index.toUpperCase());

                    String modules = getParameter(request.getParameterMap().get("moduleN"));
                    url = url.concat(StringUtils.isNotEmpty(modules) ? "&modules=" + modules : "");

                    String upgrades = getParameter(request.getParameterMap().get("upgradeN"));
                    url = url.concat(StringUtils.isNotEmpty(upgrades) ? "&upgrades=" + upgrades : "");

                    String skills = getParameter(request.getParameterMap().get("skillN"));
                    if (StringUtils.isNotEmpty(skills)) {
                        String bits = "";
                        for (int i = (skills.length() > 32 ? 32 : skills.length()) - 1; i >= 0; i--) {
                            if (Character.isDigit(skills.charAt(i))) {
                                int tempBit = Character.getNumericValue(skills.charAt(i));
                                if (tempBit == 1) {
                                    bits = bits.concat(String.valueOf(tempBit));
                                } else {
                                    bits = bits.concat(String.valueOf(0));
                                }
                            } else {
                                break;
                            }
                        }

                        if (StringUtils.isNotEmpty(bits)) {
                            url = url.concat("&skills=" + new BigInteger(bits, 2).longValue());
                        }
                    }
                }
            }
        }

        return url;
    }

    private String getParameter(String[] temp) throws Exception
    {
        if (temp != null && temp.length > 0 && StringUtils.isNotEmpty(temp[0])) {
            return URLDecoder.decode(temp[0], StandardCharsets.UTF_8.name());
        }
        return "";
    }
}
