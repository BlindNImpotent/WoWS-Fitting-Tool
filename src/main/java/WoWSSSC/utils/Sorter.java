package WoWSSSC.utils;

import WoWSSSC.model.ship.Ship;
import WoWSSSC.model.ship.ShipModulesTree;

import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-16.
 */
public class Sorter
{
    public LinkedHashMap<String, Ship> sortShips(LinkedHashMap<String, Ship> unsorted)
    {
        LinkedHashMap<String, Ship> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, Ship>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Ship>>() {
            @Override
            public int compare(Map.Entry<String, Ship> o1, Map.Entry<String, Ship> o2) {
                int tierDiff = (int) (o1.getValue().getTier() - o2.getValue().getTier());

                if (tierDiff == 0)
                {
                    return (o1.getValue().getName().compareTo(o2.getValue().getName()));
                }
                return tierDiff;
            }
        });

        for (Map.Entry<String, Ship> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, ShipModulesTree> sortShipModules(LinkedHashMap<String, ShipModulesTree> unsorted)
    {
        LinkedHashMap<String, ShipModulesTree> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, ShipModulesTree>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, ShipModulesTree>>() {
            @Override
            public int compare(Map.Entry<String, ShipModulesTree> o1, Map.Entry<String, ShipModulesTree> o2) {
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

        for (Map.Entry<String, ShipModulesTree> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }
}
