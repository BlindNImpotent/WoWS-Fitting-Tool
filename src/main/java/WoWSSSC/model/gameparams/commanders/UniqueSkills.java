package WoWSSSC.model.gameparams.commanders;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class UniqueSkills
{
    private LinkedHashMap<String, LinkedHashMap> uniqueSkills = new LinkedHashMap<>();

    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setUniqueSkills(String name, Object value)
    {
        LinkedHashMap uniqueSkills = mapper.convertValue(value, LinkedHashMap.class);
        this.uniqueSkills.put(name, uniqueSkills);
    }
}
