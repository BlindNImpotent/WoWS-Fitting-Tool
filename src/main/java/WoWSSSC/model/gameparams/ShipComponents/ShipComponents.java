package WoWSSSC.model.gameparams.ShipComponents;

import WoWSSSC.model.gameparams.Consumables.Consumable;
import WoWSSSC.model.gameparams.ShipComponents.Artillery.Artillery;
import WoWSSSC.model.gameparams.ShipComponents.DiveBomber.DiveBomber;
import WoWSSSC.model.gameparams.ShipComponents.Engine.Engine;
import WoWSSSC.model.gameparams.ShipComponents.Hull.Hull;
import WoWSSSC.model.gameparams.ShipComponents.TorpedoBomber.TorpedoBomber;
import WoWSSSC.model.gameparams.ShipComponents.Torpedoes.Torpedoes;
import WoWSSSC.model.gameparams.test.Values.ShipAbilities.ShipAbilities;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016. 12. 6..
 */
@Data
public class ShipComponents
{
    private AirArmament airArmament;
    private LinkedHashMap<String, LinkedHashMap> airDefense;
    private Artillery artillery;
    private LinkedHashMap<String, LinkedHashMap> atba;
    private LinkedHashMap<String, LinkedHashMap> directors;
    private DiveBomber diveBomber;
    private Engine engine;
    private LinkedHashMap<String, LinkedHashMap> fighter;
    private LinkedHashMap<String, LinkedHashMap> finders;
    private LinkedHashMap<String, LinkedHashMap> fireControl;
    private LinkedHashMap<String, LinkedHashMap> flightControl;
    private Hull hull;
    private Torpedoes torpedoes;
    private TorpedoBomber torpedoBomber;

    private ShipAbilities shipAbilities;
    private HashMap<String, Consumable> abilities = new HashMap<>();
}
