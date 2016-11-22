package WoWSSSC.model.warships;

import WoWSSSC.model.upgrade.Upgrade;
import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Warship
{
    @JsonIgnore
    private Sorter sorter = new Sorter();

    private String nation;
    private String type;
    private String name;
    private String ship_id_str;
    private String description;
    private long tier;
    private long ship_id;
    private long price_gold;
    private long price_credit;
    private long mod_slots;
    private boolean is_premium;
    private WarshipImages images;
    private WarshipModules modules;
    private HashMap<String, WarshipModulesTree> modules_tree;
    private HashMap<String, Long> next_ships;
    private List<Long> upgrades;

    private LinkedHashMap<String, LinkedHashMap> upgradesNew = new LinkedHashMap<>();

    private LinkedHashMap<String, LinkedHashMap> warshipModulesTreeNew = new LinkedHashMap<>();

    private static final String Artillery = "Artillery";
    private static final String DiveBomber = "DiveBomber";
    private static final String Engine = "Engine";
    private static final String Fighter = "Fighter";
    private static final String FlightControl = "FlightControl";
    private static final String Hull = "Hull";
    private static final String Suo = "Suo";
    private static final String Torpedoes = "Torpedoes";
    private static final String TorpedoBomber = "TorpedoBomber";
    
    public boolean isIs_premium()
    {
        return is_premium;
    }

    public void setModules_tree(HashMap<String, WarshipModulesTree> modules_tree)
    {
        this.modules_tree = modules_tree;

        this.modules_tree.entrySet().forEach(mt ->
        {
            if (mt.getValue().getNext_modules() != null)
            {
                mt.getValue().getNext_modules().forEach(nm ->
                {
                    modules_tree.get(String.valueOf(nm)).getPrev_modules().add(mt.getValue().getModule_id());
                    modules_tree.get(String.valueOf(nm)).setPrev_module_class(modules_tree.get(String.valueOf(nm)).getPrev_module_class() + " " + "prev_module_" +  mt.getValue().getModule_id());
                });
            }

            String type = mt.getValue().getType();
            LinkedHashMap<String, WarshipModulesTree> tempModulesTree = new LinkedHashMap<>();
            this.modules_tree.entrySet().forEach(modTree ->
            {
                if (modTree.getValue().getType().equals(type))
                {
                    tempModulesTree.put(modTree.getValue().getName(), modTree.getValue());
                }
            });
            LinkedHashMap<String, WarshipModulesTree> tmt = sorter.sortShipModules(tempModulesTree);
            warshipModulesTreeNew.put(type, tmt);
        });

        warshipModulesTreeNew = sorter.sortWarshipModulesTreeNew(warshipModulesTreeNew);
    }

    public void setUpgradesNew(LinkedHashMap<String, Upgrade> unsorted)
    {
        LinkedHashMap<String, LinkedHashMap> temp = new LinkedHashMap<>();
        unsorted.entrySet().forEach(upgrade1 ->
        {
            long tempPrice = upgrade1.getValue().getPrice_credit();
            LinkedHashMap<String, Upgrade> tempUpgrades = new LinkedHashMap<>();
            unsorted.entrySet().forEach(upgrade2 ->
            {
                if (upgrade2.getValue().getPrice_credit() == tempPrice)
                {
                    tempUpgrades.put(upgrade2.getValue().getName(), upgrade2.getValue());
                }
            });
            LinkedHashMap<String, Upgrade> sortedUpgrade = sorter.sortUpgrades(tempUpgrades);
            temp.put(String.valueOf(tempPrice), sortedUpgrade);
        });
        upgradesNew = sorter.sortUpgradeType(temp);
    }
}
