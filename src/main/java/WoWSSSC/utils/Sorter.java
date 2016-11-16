package WoWSSSC.utils;

import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-16.
 */
public class Sorter
{
    public static LinkedHashMap<String, HashMap> sortShips(LinkedHashMap<String, HashMap> unsorted)
    {
        List<Map.Entry<String, HashMap>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, HashMap>>() {
            @Override
            public int compare(Map.Entry<String, HashMap> o1, Map.Entry<String, HashMap> o2) {
                int tierDiff = (int) o1.getValue().get("tier") - (int) o2.getValue().get("tier");

                if (tierDiff == 0)
                {
                    return ((String) o1.getValue().get("name")).compareTo((String) o2.getValue().get("name"));
                }
                else
                {
                    return tierDiff;
                }
            }
        });

        LinkedHashMap<String, HashMap> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, HashMap> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }
}
