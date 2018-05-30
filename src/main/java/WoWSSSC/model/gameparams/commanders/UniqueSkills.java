package WoWSSSC.model.gameparams.commanders;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class UniqueSkills
{
    private LinkedHashMap<String, UniqueTemp> modifier = new LinkedHashMap<>();

    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setModifier(String name, Object value)
    {
        LinkedHashMap<String, UniqueSkillModifier> modifier = mapper.convertValue(value, new TypeReference<LinkedHashMap<String, UniqueSkillModifier>>(){});

        UniqueTemp ut = new UniqueTemp();
        if (name.equalsIgnoreCase("PCH016_FirstBlood"))
        {
            ut.setName("Concealed Reserves");
            ut.setDescription("This talent is activated when a player earns the \"First Blood\" achievement, and improves the performance of the current ship under the command of Yamamoto Isoroku by adding one charge for each consumable. Such additional charges can only be spent in the current battle.");
        }
        else if (name.equalsIgnoreCase("PCH023_Warrior"))
        {
            ut.setName("Second Wind");
            ut.setDescription("This talent is activated when a player earns the \"Kraken Unleashed!\" achievement while using a ship commanded by Yamamoto Isoroku. In this case, the ship enjoys -34% to the main battery reloading time and -16% to torpedo tube reloading time and aircraft servicing time till the end of the battle. The the ship's HP can also restore up to 48% of her restorable HP within 120 seconds. This talent functions just like the \"Repair Party\" consumable, which restores ship and module damage. This talent's effect can also be used conjointly with the \"Repair Party\" consumable.");
        }
        else if (name.equalsIgnoreCase("PCH001_DoubleKill"))
        {
            ut.setName("Hit Fast!");
            ut.setDescription("This talent activates after receiving the Double Strike achievement with William Halsey. The ship under Halsey's command gets -10% to base detection radius, which can accumulate if a player receives the achievement multiple times during a battle.");
        }
        else if (name.equalsIgnoreCase("PCH005_Support"))
        {
            ut.setName("Hit Hard!");
            ut.setDescription("The Hit Hard! talent activates after receiving the Confederate achievement with William Halsey. The ship under Halsey's command gets following bonuses:\n" +
                    "-20% to the main battery reload time.\n" +
                    "-20% to plane service time.\n" +
                    "-10% to torpedo reload time.");
        }
        ut.setTemp(modifier);
        ut.setIdentifier(name);

        this.modifier.put(name, ut);
    }
}
