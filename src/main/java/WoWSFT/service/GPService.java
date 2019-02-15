package WoWSFT.service;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.consumable.ConsumableSub;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.component.artillery.Shell;
import WoWSFT.model.gameparams.ship.component.artillery.Turret;
import WoWSFT.model.gameparams.ship.component.atba.ATBA;
import WoWSFT.model.gameparams.ship.component.atba.Secondary;
import WoWSFT.model.gameparams.ship.component.engine.Engine;
import WoWSFT.model.gameparams.ship.component.firecontrol.FireControl;
import WoWSFT.model.gameparams.ship.component.hull.Hull;
import WoWSFT.model.gameparams.ship.component.torpedo.Launcher;
import WoWSFT.model.gameparams.ship.component.torpedo.Torpedo;
import WoWSFT.model.gameparams.ship.component.torpedo.TorpedoAmmo;
import WoWSFT.utils.CommonUtils;
import WoWSFT.utils.PenetrationUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

import static WoWSFT.model.Constant.*;

/**
 * Created by Aesis on 2016-12-05.
 */
@Service
@Slf4j
public class GPService
{
    @Autowired
    @Qualifier(value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    @Autowired
    @Qualifier (value = TYPE_SHIP)
    private LinkedHashMap<String, Ship> ships;

    @Autowired
    @Qualifier (value =  TYPE_CONSUMABLE)
    private LinkedHashMap<String, Consumable> consumables;

    @Autowired
    @Qualifier (value = TYPE_SHIP_LIST)
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList;

    @Autowired
    @Qualifier (value = TYPE_UPGRADE)
    private LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades;

    @Autowired
    @Qualifier (value = TYPE_COMMANDER)
    private LinkedHashMap<String, Commander> commanders;

    @Autowired
    private ParserService parserService;

    private ObjectMapper mapper = new ObjectMapper();

    @Cacheable(value = "ship", key = "#index.concat('_').concat(#bits)")
    public Ship getShip(String index, String bits) throws Exception
    {
        Ship ship = mapper.readValue(mapper.writeValueAsString(ships.get(index)), Ship.class);

        if (ship != null) {
            setUpgrades(ship);
            setConsumables(ship);
            setShipAmmo(ship);

            return ship;
        }
        throw new NullPointerException();
    }

    private void setUpgrades(Ship ship)
    {
        List<List<Modernization>> upgradesList = new ArrayList<>();

        upgrades.forEach((slot, upgrades) -> upgrades.forEach((key, upgrade) -> {
            if ((!upgrade.getExcludes().contains(ship.getName()) && upgrade.getGroup().contains(ship.getGroup()) && upgrade.getNation().contains(ship.getTypeinfo().getNation())
                && upgrade.getShiptype().contains(ship.getTypeinfo().getSpecies()) && upgrade.getShiplevel().contains(ship.getLevel())) || upgrade.getShips().contains(ship.getName())) {
                if (upgradesList.size() < upgrade.getSlot() + 1) {
                    upgradesList.add(new ArrayList<>());
                }
                upgradesList.get(upgrade.getSlot()).add(upgrade);
            }
        }));

        int maxRows = upgradesList.stream().mapToInt(List::size).filter(slot -> slot > 0).max().orElse(0);
        ship.setUpgradesRow(maxRows);
        ship.setUpgrades(upgradesList);
    }

    private void setConsumables(Ship ship) throws Exception
    {
        List<List<Consumable>> consumablesList = new ArrayList<>();
        for (Map.Entry<String, AbilitySlot> entry : ship.getShipAbilities().entrySet()) {
            for (List<String> consumable : entry.getValue().getAbils()) {
                if (!consumable.get(0).contains("Super")) {
                    Consumable tempConsumable = mapper.readValue(mapper.writeValueAsString(consumables.get(consumable.get(0))), Consumable.class);
                    tempConsumable.getSubConsumables().entrySet().removeIf(e -> !e.getKey().equalsIgnoreCase(consumable.get(1)));
                    if (consumablesList.size() < entry.getValue().getSlot() + 1) {
                        consumablesList.add(new ArrayList<>());
                    }
                    consumablesList.get(entry.getValue().getSlot()).add(tempConsumable);
                }
            }
        }
        ship.setConsumables(consumablesList);
    }

    public Commander getCommander(String commander) throws Exception
    {
        return mapper.readValue(mapper.writeValueAsString(commanders.get("PAW001_DefaultCrew")), Commander.class);
    }

    private void setShipAmmo(Ship ship) throws Exception
    {
        if (ship != null && ship.getComponents() != null) {
            if (ship.getComponents().getArtillery().size() > 0) {
                for (Map.Entry<String, Artillery> entry : ship.getComponents().getArtillery().entrySet()) {
                    for (String ammo : entry.getValue().getTurrets().get(0).getAmmoList()) {
                        Shell shell = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(ammo)), Shell.class);
                        PenetrationUtils.setPenetration(shell, entry.getValue().getTurrets().get(0).getVertSector().get(1),
                                entry.getValue().getMinDistV(), entry.getValue().getMaxDist(), "AP".equalsIgnoreCase(shell.getAmmoType().toLowerCase()));
                        entry.getValue().getShells().put(ammo, shell);
                    }
                }
            }

            if (ship.getComponents().getTorpedoes().size() > 0) {
                for (Map.Entry<String, Torpedo> entry : ship.getComponents().getTorpedoes().entrySet()) {
                    TorpedoAmmo ammo = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(entry.getValue().getLaunchers().get(0).getAmmoList().get(0))), TorpedoAmmo.class);
                    entry.getValue().setAmmo(ammo);
                }
            }

            if (ship.getComponents().getAtba().size() > 0) {
                for (Map.Entry<String, ATBA> entry : ship.getComponents().getAtba().entrySet()) {
                    for (Map.Entry<String, Secondary> secondary : entry.getValue().getSecondaries().entrySet()) {
                        Shell ammo = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(secondary.getValue().getAmmoList().get(0))), Shell.class);
                        secondary.getValue().setAlphaDamage(ammo.getAlphaDamage());
                        secondary.getValue().setAlphaPiercingHE(ammo.getAlphaPiercingHE());
                        secondary.getValue().setAmmoType(ammo.getAmmoType());
                        secondary.getValue().setBulletSpeed(ammo.getBulletSpeed());
                        secondary.getValue().setBurnProb(ammo.getBurnProb());
                    }
                }
            }
        }
    }
}
