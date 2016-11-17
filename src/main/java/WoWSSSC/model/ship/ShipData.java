package WoWSSSC.model.ship;

import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipData
{
    private String status;
    private LinkedHashMap<String, Ship> data = new LinkedHashMap<>();

    @JsonIgnore
    private Sorter sorter = new Sorter();

    public void setData(HashMap<String, Ship> data)
    {
        for (Map.Entry<String, Ship> d : data.entrySet())
        {
            String key = d.getValue().getName();
            Ship value = d.getValue();
            this.data.put(key, value);
        }
        this.data = sorter.sortShips(this.data);
    }
}
