package WoWSSSC.model.warships;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Qualson-Lee on 2016-11-29.
 */
@Data
public class TotalWarshipData
{
    private String future;
    private HashMap<String, TotalWarship> data = new HashMap<>();
}
