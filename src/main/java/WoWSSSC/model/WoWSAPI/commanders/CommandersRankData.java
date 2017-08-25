package WoWSSSC.model.WoWSAPI.commanders;

import WoWSSSC.model.WoWSAPI.Meta;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class CommandersRankData
{
    private String status;
    private Meta meta;
    private LinkedHashMap<String, List<CommandersRank>> data = new LinkedHashMap<>();
}
