package WoWSFT.service;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import WoWSFT.model.gameparams.ship.component.artillery.Shell;
import WoWSFT.model.gameparams.ship.component.atba.Secondary;
import WoWSFT.model.gameparams.ship.component.planes.Plane;
import WoWSFT.model.gameparams.ship.component.torpedo.TorpedoAmmo;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
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
    private ParamService paramService;

    private ObjectMapper mapper = new ObjectMapper();

    @Cacheable(value = "ship", key = "#index")
    public Ship getShip(String index) throws Exception
    {
        Ship ship = mapper.readValue(mapper.writeValueAsString(ships.get(index)), Ship.class);

        if (ship != null) {
            setUpgrades(ship);
            setConsumables(ship);

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

    @Cacheable(value = "commander", key = "#commander")
    public Commander getCommander(String commander) throws Exception
    {
        if (commanders.get(commander) == null) {
            commander = "PCW001";
        }

        Commander tempCommander = mapper.readValue(mapper.writeValueAsString(commanders.get(commander)), Commander.class);
        tempCommander.getCSkills().forEach(r -> r.forEach(s -> s.setBonus(paramService.getBonus(mapper.convertValue(s, new TypeReference<LinkedHashMap<String, Object>>(){})))));

        return tempCommander;
    }

    public void setShipAmmo(Ship ship) throws Exception
    {
        if (ship.getComponents().getArtillery().size() > 0) {
            for (String ammo : ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getTurrets().get(0).getAmmoList()) {
                Shell shell = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(ammo)), Shell.class);
//                PenetrationUtils.setPenetration(shell,
//                        ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getTurrets().get(0).getVertSector().get(1),
//                        ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getMinDistV(),
//                        ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getMaxDist(),
//                        "AP".equalsIgnoreCase(shell.getAmmoType().toLowerCase()));

                ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getShells()
                        .put(ammo, shell);
            }
        }

        if (ship.getComponents().getTorpedoes().size() > 0) {
            String ammo = ship.getComponents().getTorpedoes().get(ship.getModules().get(torpedoes)).getLaunchers().get(0).getAmmoList().get(0);
            ship.getComponents().getTorpedoes().get(ship.getModules().get(torpedoes))
                    .setAmmo(mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(ammo)), TorpedoAmmo.class));
        }

        if (ship.getComponents().getAtba().size() > 0) {
            for (Map.Entry<String, Secondary> secondary : ship.getComponents().getAtba().get(ship.getModules().get(atba)).getSecondaries().entrySet()) {
                Shell ammo = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(secondary.getValue().getAmmoList().get(0))), Shell.class);
                secondary.getValue().setAlphaDamage(ammo.getAlphaDamage());
                secondary.getValue().setAlphaPiercingHE(ammo.getAlphaPiercingHE());
                secondary.getValue().setAmmoType(ammo.getAmmoType());
                secondary.getValue().setBulletSpeed(ammo.getBulletSpeed());
                secondary.getValue().setBurnProb(ammo.getBurnProb());
            }
        }

        if (ship.getPlanes().size() > 0) {
            if (ship.getModules().get(diveBomber) != null && ship.getPlanes().get(ship.getModules().get(diveBomber)) != null) {
                Plane dbPlane = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(ship.getPlanes().get(ship.getModules().get(diveBomber)))), Plane.class);
                dbPlane.setBomb(mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(dbPlane.getBombName())), Shell.class));
                setPlaneConsumables(dbPlane);
                ship.getComponents().getDiveBomber().put(ship.getModules().get(diveBomber), dbPlane);
            }

            if (ship.getModules().get(fighter) != null && ship.getPlanes().get(ship.getModules().get(fighter)) != null) {
                Plane fPlane = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(ship.getPlanes().get(ship.getModules().get(fighter)))), Plane.class);
                fPlane.setRocket(mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(fPlane.getBombName())), Shell.class));
                setPlaneConsumables(fPlane);
                ship.getComponents().getFighter().put(ship.getModules().get(fighter), fPlane);
            }

            if (ship.getModules().get(torpedoBomber) != null && ship.getPlanes().get(ship.getModules().get(torpedoBomber)) != null) {
                Plane tbPlane = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(ship.getPlanes().get(ship.getModules().get(torpedoBomber)))), Plane.class);
                tbPlane.setTorpedo(mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(tbPlane.getBombName())), TorpedoAmmo.class));
                setPlaneConsumables(tbPlane);
                ship.getComponents().getTorpedoBomber().put(ship.getModules().get(torpedoBomber), tbPlane);
            }
        }
    }

    private void setPlaneConsumables(Plane plane) throws Exception
    {
        List<Consumable> consumablesList = new ArrayList<>();

        for (Map.Entry<String, AbilitySlot> entry : plane.getPlaneAbilities().entrySet()) {
            for (List<String> consumable : entry.getValue().getAbils()) {
                if (!consumable.get(0).contains("Super")) {
                    Consumable tempConsumable = mapper.readValue(mapper.writeValueAsString(consumables.get(consumable.get(0))), Consumable.class);
                    tempConsumable.getSubConsumables().entrySet().removeIf(e -> !e.getKey().equalsIgnoreCase(consumable.get(1)));
                    consumablesList.add(tempConsumable);
                }
            }
        }
        plane.setConsumables(consumablesList);
    }

    public Shell getArtyAmmoOnly(String index, String artyId) throws Exception
    {
        if (ships.get(index) != null && ships.get(index).getShipUpgradeInfo().getComponents().get(artillery).size() > 0) {
            for (ShipUpgrade su : ships.get(index).getShipUpgradeInfo().getComponents().get(artillery)) {
                if (su.getName().equalsIgnoreCase(artyId)) {
                    String tempId = su.getComponents().get(artillery).get(su.getComponents().get(artillery).size() - 1);

                    for (String ammo : ships.get(index).getComponents().getArtillery().get(tempId).getTurrets().get(0).getAmmoList()) {
                        Shell shell = mapper.readValue(mapper.writeValueAsString(gameParamsHM.get(ammo)), Shell.class);
                        if ("AP".equalsIgnoreCase(shell.getAmmoType())) {
                            PenetrationUtils.setPenetration(shell,
                                    ships.get(index).getComponents().getArtillery().get(tempId).getTurrets().get(0).getVertSector().get(1),
                                    ships.get(index).getComponents().getArtillery().get(tempId).getMinDistV(),
                                    ships.get(index).getComponents().getArtillery().get(tempId).getMaxDist(),
                                    "AP".equalsIgnoreCase(shell.getAmmoType().toLowerCase()));

                            return shell;
                        }
                    }
                }
            }
        }
        return null;
    }
}
