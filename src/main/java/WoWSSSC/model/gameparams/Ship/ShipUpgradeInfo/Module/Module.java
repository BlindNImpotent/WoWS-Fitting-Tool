package WoWSSSC.model.gameparams.Ship.ShipUpgradeInfo.Module;

import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class Module
{
    private int bombersAmount;
    private Components components;
    private List<String> disabledAbilities;
    private int diversAmount;
    private List<Long> distance;
    private int fightersAmount;
    private List<String> nextShips;
    private float prepareTimeFactor;
    private String prev;
    private List<Float> rotationSpeed;
    private int scoutsAmount;
    private float shotDelay;
    private String ucType;
}
