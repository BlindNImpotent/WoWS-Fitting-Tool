package WoWSFT.model.gameparams.modernization;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.HashMap;


@Data
@WoWSFT
public class Upgrades
{
    private HashMap<String, Modernization> slot0 = new HashMap<>();
    private HashMap<String, Modernization> slot1 = new HashMap<>();
    private HashMap<String, Modernization> slot2 = new HashMap<>();
    private HashMap<String, Modernization> slot3 = new HashMap<>();
    private HashMap<String, Modernization> slot4 = new HashMap<>();
    private HashMap<String, Modernization> slot5 = new HashMap<>();
}
