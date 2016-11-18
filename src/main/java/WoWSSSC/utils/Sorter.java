package WoWSSSC.utils;

import WoWSSSC.model.warships.Warship;
import WoWSSSC.model.warships.WarshipModulesTree;
import WoWSSSC.model.upgrade.Upgrade;

import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-16.
 */
public class Sorter
{
    public LinkedHashMap<String, Warship> sortShips(LinkedHashMap<String, Warship> unsorted)
    {
        LinkedHashMap<String, Warship> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, Warship>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Warship>>() {
            @Override
            public int compare(Map.Entry<String, Warship> o1, Map.Entry<String, Warship> o2) {
                int tierDiff = (int) (o1.getValue().getTier() - o2.getValue().getTier());

                if (tierDiff == 0)
                {
                    return (o1.getValue().getName().compareTo(o2.getValue().getName()));
                }
                return tierDiff;
            }
        });

        for (Map.Entry<String, Warship> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, WarshipModulesTree> sortShipModules(LinkedHashMap<String, WarshipModulesTree> unsorted)
    {
        LinkedHashMap<String, WarshipModulesTree> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, WarshipModulesTree>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, WarshipModulesTree>>() {
            @Override
            public int compare(Map.Entry<String, WarshipModulesTree> o1, Map.Entry<String, WarshipModulesTree> o2) {
                if (o1.getValue().isIs_default() && !o2.getValue().isIs_default())
                {
                    return -1;
                }
                else if (!o1.getValue().isIs_default() && o2.getValue().isIs_default())
                {
                    return 1;
                }
                else
                {
                    return o1.getValue().getName().compareTo(o2.getValue().getName());
                }
            }
        });

        for (Map.Entry<String, WarshipModulesTree> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, Upgrade> sortUpgrades(LinkedHashMap<String, Upgrade> unsorted)
    {
        LinkedHashMap<String, Upgrade> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, Upgrade>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Upgrade>>() {
            @Override
            public int compare(Map.Entry<String, Upgrade> o1, Map.Entry<String, Upgrade> o2) {
                return o1.getValue().getTag().compareTo(o2.getValue().getTag());
            }
        });

        for (Map.Entry<String, Upgrade> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }
}
