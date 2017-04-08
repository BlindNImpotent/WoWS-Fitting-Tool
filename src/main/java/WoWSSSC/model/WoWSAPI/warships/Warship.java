package WoWSSSC.model.WoWSAPI.warships;

import WoWSSSC.model.WoWSAPI.upgrade.Upgrade;
import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@NoArgsConstructor
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
    private LinkedHashMap<String, List<WarshipModulesTree>> warshipModulesTreeTable = new LinkedHashMap<>();
    private List<Warship> nextWarship = new ArrayList<>();

    public Warship(String nation, String type, String name, WarshipImages images)
    {
        this.nation = nation;
        this.type = type;
        this.name = name;
        this.images = images;
    }

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
                    String tempName = "";

                    if (modTree.getValue().isIs_default())
                    {
                        tempName = modTree.getValue().getName() + "_Stock";
                    }
                    else
                    {
                        tempName = modTree.getValue().getName();
                    }

                    tempModulesTree.put(tempName, modTree.getValue());
//                    tempModulesTree.put(modTree.getValue().getName(), modTree.getValue());
                }
            });
            LinkedHashMap<String, WarshipModulesTree> tmt = sorter.sortShipModules(tempModulesTree);
            warshipModulesTreeNew.put(type, tmt);
        });

        warshipModulesTreeNew = sorter.sortWarshipModulesTreeNew(warshipModulesTreeNew);
        setWarshipModulesTreeTable();
    }

    private void setWarshipModulesTreeTable()
    {
        int typeSizeColumn = warshipModulesTreeNew.size();
        int[] shift = new int[warshipModulesTreeNew.size()];
        int maxModuleSizeRow = 1;

        for (LinkedHashMap<String, WarshipModulesTree> type : warshipModulesTreeNew.values())
        {
            int i = 0;

            if (maxModuleSizeRow < type.values().size())
            {
                maxModuleSizeRow = type.values().size();
            }

            for (WarshipModulesTree module : type.values())
            {
                if (module.getNext_modules() != null)
                {
                    if (module.getNext_modules().size() > 1)
                    {
                        int yes = 0;

                        for (long nm : module.getNext_modules())
                        {
                            if (modules_tree.get(String.valueOf(nm)).getType().equals(module.getType()))
                            {
                                yes = yes + 1;
                            }
                        }

                        if (module.getNext_modules().size() == yes)
                        {
                            typeSizeColumn = typeSizeColumn + 1;

                            maxModuleSizeRow = type.values().size() - module.getNext_modules().size() + 1;

                            for (int j = i; j < shift.length; j++)
                            {
                                shift[j] = shift[j] + 1;
                            }
                        }
                    }
                }
            }
            i++;
        }

        List<LinkedHashMap> tempTypesList = new ArrayList<>(warshipModulesTreeNew.values());

        for (int i = 0; i < maxModuleSizeRow; i++)
        {
            WarshipModulesTree[] tempRow = new WarshipModulesTree[typeSizeColumn];

            for (int j = 0; j < typeSizeColumn; j++)
            {
                if (j < tempTypesList.size())
                {
                    List<WarshipModulesTree> tempWSMTList = new ArrayList<>(tempTypesList.get(j).values());

                    if (i < tempWSMTList.size())
                    {
                        tempRow[j + shift[j]] = tempWSMTList.get(i);

                        if (tempWSMTList.get(i).getPrev_modules() != null)
                        {
                            for (long pm : tempWSMTList.get(i).getPrev_modules())
                            {
                                if (modules_tree.get(String.valueOf(pm)).getNext_modules() != null)
                                {
                                    int yes = 0;

                                    for (long nm : modules_tree.get(String.valueOf(pm)).getNext_modules())
                                    {
                                        if (modules_tree.get(String.valueOf(nm)).getType().equals(modules_tree.get(String.valueOf(pm)).getType()))
                                        {
                                            yes = yes + 1;
                                        }
                                    }

                                    if (yes == modules_tree.get(String.valueOf(pm)).getNext_modules().size())
                                    {
                                        int index = modules_tree.get(String.valueOf(pm)).getNext_modules().size() - 1;

                                        for (long nm : modules_tree.get(String.valueOf(pm)).getNext_modules())
                                        {
                                            tempRow[j + shift[j] - index] = modules_tree.get(String.valueOf(nm));
                                            index = index - 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            warshipModulesTreeTable.put("Row" + String.valueOf(i + 1), Arrays.asList(tempRow));
        }
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
