package WoWSFT.model.gameparams.ship.abilities;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
public class AbilitySlot
{
    private List<List<String>> abils;
    @JsonInclude
    private int slot;
}
