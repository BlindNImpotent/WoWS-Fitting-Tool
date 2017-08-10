package WoWSSSC.model.gameparams.commanders;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class UniqueTemp
{
    private String name;
    private String description;
    private LinkedHashMap<String, UniqueSkillModifier> temp;
}
