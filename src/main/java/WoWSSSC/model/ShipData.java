package WoWSSSC.model;

import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private LinkedHashMap<String, HashMap> data = new LinkedHashMap<>();

    @JsonIgnore
    private Sorter sorter = new Sorter();

    public void setData(HashMap<String, HashMap> data)
    {
        for (Map.Entry<String, HashMap> d : data.entrySet())
        {
            String key = (String) d.getValue().get("name");
            HashMap value = d.getValue();
            this.data.put(key, value);
        }
        this.data = sorter.sortShips(this.data);
    }
}
