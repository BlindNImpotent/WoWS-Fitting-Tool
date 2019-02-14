package WoWSFT.model.gameparams.commander;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commander
{
    private CrewPersonality CrewPersonality;
    private long id;
    private String index;
    private String name;
    private TypeInfo typeinfo;
    private List<List<Skill>> cSkills = new ArrayList<>();

    {
        cSkills.add(new ArrayList<>());
        cSkills.add(new ArrayList<>());
        cSkills.add(new ArrayList<>());
        cSkills.add(new ArrayList<>());

        for (List<Skill> s : cSkills) {
            for (int i = 0; i < 8; i++) {
                s.add(new Skill());
            }
        }
    }

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setSkills(String name, Object value)
    {
        if (name.equalsIgnoreCase("Skills")) {
            LinkedHashMap<String, Skill> temp = mapper.convertValue(value, new TypeReference<LinkedHashMap<String, Skill>>(){});

            temp.forEach((key, val) -> {
                val.setModifier(key);
                cSkills.get(val.getTier() - 1).set(val.getColumn(), val);
            });
        }
    }

    @JsonProperty("cSkills")
    public List<List<Skill>> getCSkills()
    {
        return cSkills;
    }
}
