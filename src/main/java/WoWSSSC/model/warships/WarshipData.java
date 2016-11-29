package WoWSSSC.model.warships;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.*;
import java.util.concurrent.Future;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarshipData
{
    private String status;
    private LinkedHashMap<String, Warship> data = new LinkedHashMap<>();

    public void setData(LinkedHashMap<String, Warship> data)
    {
        this.data = data;
    }

//    @JsonIgnore
//    private Sorter sorter = new Sorter();
//
//    public void setData(HashMap<String, Warship> data)
//    {
//        for (Map.Entry<String, Warship> d : data.entrySet())
//        {
//            String key = d.getValue().getName();
//            Warship value = d.getValue();
//            this.data.put(key, value);
//        }
//        this.data = sorter.sortShips(this.data);
//    }
}
