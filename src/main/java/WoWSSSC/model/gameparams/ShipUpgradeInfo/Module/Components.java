package WoWSSSC.model.gameparams.ShipUpgradeInfo.Module;

import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class Components
{
    private List<String> airArmament;
    private List<String> airDefense;
    private List<String> artillery;
    private List<String> atba;
    private List<String> directors;
    private List<String> diveBomber;
    private List<String> engine;
    private List<String> fighter;
    private List<String> finders;
    private List<String> fireControl;
    private List<String> flightControl;
    private List<String> hull;
    private List<String> radars;
    private List<String> torpedoes;
    private List<String> torpedoBomber;
}
