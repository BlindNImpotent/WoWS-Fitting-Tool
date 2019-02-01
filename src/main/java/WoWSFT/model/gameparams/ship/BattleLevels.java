package WoWSFT.model.gameparams.ship;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class BattleLevels
{
    @JsonProperty("COOPERATIVE")
    private List<Integer> COOPERATIVE;
    @JsonProperty("PVP")
    private List<Integer> PVP;
}
