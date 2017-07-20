package WoWSSSC.model.WoWSAPI.commanders;

import WoWSSSC.model.WoWSAPI.Meta;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class CommandersData
{
    private String status;
    private Meta meta;
    private LinkedHashMap<String, Commanders> data = new LinkedHashMap<>();
}
