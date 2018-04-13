package WoWSSSC.model.WoWSAPI.warships;

import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Warship
{
    @JsonIgnore
    private Sorter sorter = new Sorter();

    private String nation;
    private String type;
    private String defaultType;
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
//    private LinkedHashMap<String, LinkedHashMap> upgradesSpecial = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> warshipModulesTreeNew = new LinkedHashMap<>();
    private LinkedHashMap<String, List<WarshipModulesTree>> warshipModulesTreeTable = new LinkedHashMap<>();
    private List<Warship> nextWarship = new ArrayList<>();
    private Warship prevWarship;

    private boolean isFirst = true;
    private int maxTier = 10;
    private long nextShipXp;
    private boolean hasSecondLine;
    private int firstMinTier;
    private int firstMaxTier;
    private int secondMaxTier;
    private int secondMinTier;

    private long fromPreviousShipXp;

    public Warship(String nation, String type, String name, WarshipImages images, Warship prevWarship)
    {
        this.nation = nation;
        this.type = type;
        this.name = name;
        this.images = images;
        this.prevWarship = prevWarship;
    }

    public String getNationConvert()
    {
        if (StringUtils.isNotEmpty(nation)) {
            if (nation.equalsIgnoreCase("uk")) {
                return "United_Kingdom";
            }
            else if (nation.equalsIgnoreCase("ussr")) {
                return "Russia";
            }
            return nation;
        }
        return null;
    }

    public void setType(String type)
    {
        this.type = type;

        if (!type.equalsIgnoreCase("Premium") && !type.equalsIgnoreCase("Arpeggio") && !type.equalsIgnoreCase("HSF"))
        {
            this.defaultType = type;
        }
    }

    public boolean isIs_premium()
    {
        return is_premium;
    }

    public void setModules_tree(HashMap<String, WarshipModulesTree> modules_tree)
    {
        if (modules_tree.containsKey("3349131216"))
        {
            modules_tree.get("3349131216").setName("419 mm/45 Mk II");
        }

        HashSet<String> shiftLeftHashSet = new HashSet<>();
        HashSet<String> shiftRightHashSet = new HashSet<>();
        shiftLeftHashSet.add("Artillery");
        shiftLeftHashSet.add("FlightControl");
        shiftRightHashSet.add("Suo");
        shiftRightHashSet.add("Torpedoes");
        shiftRightHashSet.add("Engine");

        this.modules_tree = modules_tree;

        this.modules_tree.entrySet().forEach(mt ->
        {
            if (CollectionUtils.isNotEmpty(mt.getValue().getPrev_modules()) && mt.getValue().isIs_default())
            {
                mt.getValue().set_default(false);
            }

            if (mt.getValue().getNext_modules() != null)
            {
                mt.getValue().getNext_modules().forEach(nm ->
                {
                    modules_tree.get(String.valueOf(nm)).getPrev_modules().add(mt.getValue().getModule_id());
                    modules_tree.get(String.valueOf(nm)).setPrev_module_class(modules_tree.get(String.valueOf(nm)).getPrev_module_class() + " " + "prev_module_" +  mt.getValue().getModule_id());

                    if (modules_tree.get(String.valueOf(nm)).getType().equalsIgnoreCase(mt.getValue().getType())) {
                        mt.getValue().setShiftDown(true);
                        modules_tree.get(String.valueOf(nm)).setFromUp(true);
                        mt.getValue().setSameTypeCount(mt.getValue().getSameTypeCount() + 1);
                    }

                    if (mt.getValue().getType().equals("Hull") && shiftLeftHashSet.contains(modules_tree.get(String.valueOf(nm)).getType())) {
                        mt.getValue().setShiftLeft(true);
                        modules_tree.get(String.valueOf(nm)).setFromRight(true);
                    }
                    else if (mt.getValue().getType().equals("Hull") && shiftRightHashSet.contains(modules_tree.get(String.valueOf(nm)).getType())) {
                        mt.getValue().setShiftRight(true);
                        modules_tree.get(String.valueOf(nm)).setFromLeft(true);
                    }
                });
            }
        });

        this.modules_tree.entrySet().forEach(mt -> {
            String type = mt.getValue().getType();
            LinkedHashMap<String, WarshipModulesTree> tempModulesTree = new LinkedHashMap<>();
            int typeCount = (int) modules_tree.values().stream().filter(tempMT -> tempMT.getType().equals(type)).count();
            AtomicInteger index = new AtomicInteger(2);
            int countCheck = 2;
            boolean thirdPass = false;
            for (Map.Entry<String, WarshipModulesTree> modTree : this.modules_tree.entrySet()) {
                if (modTree.getValue().getType().equals(type))
                {
                    String tempName = "";

                    if (modTree.getValue().getPrev_modules().size() == 0)
                    {
                        tempName = modTree.getValue().getName() + "_1";
                        modTree.getValue().setIndex(1);
                    }
                    else if (modTree.getValue().getPrev_modules().size() > 0)
                    {
                        if (modTree.getValue().getNext_modules() != null || typeCount == countCheck) {
                            tempName = modTree.getValue().getName() + "_2";
                            modTree.getValue().setIndex(2);
                        }
                        else {
                            int tempCount = thirdPass ? 2 : 3;
                            tempName = modTree.getValue().getName() + "_" + String.valueOf(tempCount);
                            modTree.getValue().setIndex(tempCount);
                            if (!thirdPass) {
                                thirdPass = true;
                            }
                        }
                    }

                    tempModulesTree.put(tempName, modTree.getValue());
//                    tempModulesTree.put(modTree.getValue().getName(), modTree.getValue());
                }
            }
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
        int indexFirst = 0;

        for (LinkedHashMap<String, WarshipModulesTree> type : warshipModulesTreeNew.values())
        {
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

                            for (int j = indexFirst; j < shift.length; j++)
                            {
                                shift[j] = shift[j] + 1;
                            }
                        }
                    }
                }
            }
            indexFirst++;
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
                        int tempIndex = tempWSMTList.get(i).getSameTypeCount() - 1;
                        tempIndex = tempIndex >= 0 ? tempIndex : 0;
                        tempRow[j + shift[j] - tempIndex] = tempWSMTList.get(i);

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

            for (int j = 0; j < tempRow.length; j++)
            {
                for (List<WarshipModulesTree> warshipModulesTrees : warshipModulesTreeTable.values())
                {
                    for (WarshipModulesTree warshipModulesTree : warshipModulesTrees)
                    {
                        if (tempRow[j] != null && warshipModulesTree != null && tempRow[j].getModule_id() == warshipModulesTree.getModule_id())
                        {
                            tempRow[j] = null;
                        }
                    }
                }
            }
            warshipModulesTreeTable.put("Row" + String.valueOf(i + 1), Arrays.asList(tempRow));
        }
    }

    public void setUpgradesNew(LinkedHashMap<String, Consumables> unsorted)
    {
        LinkedHashMap<String, LinkedHashMap> temp = new LinkedHashMap<>();
        unsorted.entrySet().forEach(upgrade1 ->
        {
            long tempPrice = upgrade1.getValue().getPrice_credit();
            LinkedHashMap<String, Consumables> tempUpgrades = new LinkedHashMap<>();
            unsorted.entrySet().forEach(upgrade2 ->
            {
                if (upgrade2.getValue().getPrice_credit() == tempPrice)
                {
                    tempUpgrades.put(upgrade2.getValue().getName(), upgrade2.getValue());
                }
            });
            LinkedHashMap<String, Consumables> sortedUpgrade = sorter.sortUpgrades(tempUpgrades);
            temp.put(String.valueOf(tempPrice), sortedUpgrade);
        });
        upgradesNew = sorter.sortUpgradeType(temp);
    }
}
