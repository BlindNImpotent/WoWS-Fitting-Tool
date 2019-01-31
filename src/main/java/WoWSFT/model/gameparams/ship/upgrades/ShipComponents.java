package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
public class ShipComponents
{
    private List<String> airArmament;
    private List<String> airDefense;
    private List<String> artillery;
    private List<String> atba;
    private List<String> directors;
    private List<String> engine;
    private List<String> finders;
    private List<String> fireControl;
    private List<String> hull;
    private List<String> radars;
    private List<String> torpedoes;

    private List<String> flightControl;
    private List<String> diveBomber;
    private List<String> fighter;
    private List<String> torpedoBomber;
}
