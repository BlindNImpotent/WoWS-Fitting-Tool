package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@WoWSFT
public class ShipComponents
{
    private List<String> airArmament = new ArrayList<>();
    private List<String> airDefense = new ArrayList<>();
    private List<String> artillery = new ArrayList<>();
    private List<String> atba = new ArrayList<>();
    private List<String> directors = new ArrayList<>();
    private List<String> engine = new ArrayList<>();
    private List<String> finders = new ArrayList<>();
    private List<String> fireControl = new ArrayList<>();
    private List<String> hull = new ArrayList<>();
    private List<String> radars = new ArrayList<>();
    private List<String> torpedoes = new ArrayList<>();

    private List<String> flightControl = new ArrayList<>();
    private List<String> diveBomber = new ArrayList<>();
    private List<String> fighter = new ArrayList<>();
    private List<String> torpedoBomber = new ArrayList<>();
}
