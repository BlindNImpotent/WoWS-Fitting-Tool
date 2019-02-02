package WoWSFT.model.gameparams.modernization;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.LinkedHashMap;


@Data
@WoWSFT
public class Upgrades
{
    private LinkedHashMap<String, Modernization> slot0 = new LinkedHashMap<>();
    private LinkedHashMap<String, Modernization> slot1 = new LinkedHashMap<>();
    private LinkedHashMap<String, Modernization> slot2 = new LinkedHashMap<>();
    private LinkedHashMap<String, Modernization> slot3 = new LinkedHashMap<>();
    private LinkedHashMap<String, Modernization> slot4 = new LinkedHashMap<>();
    private LinkedHashMap<String, Modernization> slot5 = new LinkedHashMap<>();
}
