package WoWSFT.model.gameparams.commander;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
public class Ships
{
    private List<String> nation;
    private List<String> peculiarity;
    private List<String> ships;
    private List<String> groups;
}
