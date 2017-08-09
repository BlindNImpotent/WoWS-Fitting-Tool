package WoWSSSC.model.gameparams.commanders;

import WoWSSSC.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class GPCommander
{
    private CrewPersonality CrewPersonality;
    private Skills Skills;
    private UniqueSkills UniqueSkills;
    private Vanity Vanity;
    private int baseTrainingLevel;
    private int goldTrainingLevel;
    private long id;
    private String index;
    private int moneyTrainingLevel;
    private String name;
    private TypeInfo typeinfo;
}
