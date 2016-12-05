package WoWSSSC.model.WoWSAPI.exterior;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Qualson-Lee on 2016-11-25.
 */
@Data
public class ExteriorData
{
    private String status;
    private LinkedHashMap<String, Exterior> data = new LinkedHashMap<>();
}
