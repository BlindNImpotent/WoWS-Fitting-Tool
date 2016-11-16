package WoWSSSC.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipData
{
    private String status;
    private LinkedHashMap<String, Ship> data = new LinkedHashMap<>();

    public void setData(HashMap<String, Ship> data)
    {
        for (Map.Entry<String, Ship> d : data.entrySet())
        {
            String key = d.getValue().getName();
            Ship value = d.getValue();
            this.data.put(key, value);
        }
        this.data = sortShips(this.data);
    }

    private static LinkedHashMap<String, Ship> sortShips(LinkedHashMap<String, Ship> unsorted)
    {
        List<Map.Entry<String, Ship>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Ship>>() {
            @Override
            public int compare(Map.Entry<String, Ship> o1, Map.Entry<String, Ship> o2) {
                long tierDiff = o1.getValue().getTier() - o2.getValue().getTier();

                if (tierDiff == 0)
                {
                    return o1.getValue().getName().compareTo(o2.getValue().getName());
                }
                else
                {
                    return (int) tierDiff;
                }
            }
        });

        LinkedHashMap<String, Ship> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Ship> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }
}
