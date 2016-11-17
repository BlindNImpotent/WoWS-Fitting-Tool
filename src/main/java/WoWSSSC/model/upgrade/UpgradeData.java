package WoWSSSC.model.upgrade;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
public class UpgradeData
{
    private String status;
    private LinkedHashMap<String, Upgrade> data = new LinkedHashMap<>();
}
