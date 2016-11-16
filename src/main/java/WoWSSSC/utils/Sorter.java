package WoWSSSC.utils;

import WoWSSSC.model.Ship;

import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-16.
 */
public class Sorter
{
    public static LinkedHashMap<String, Ship> sortShips(LinkedHashMap<String, Ship> unsorted)
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
}
