package WoWSSSC.model.gameparams.ShipComponents;

import WoWSSSC.model.gameparams.Consumables.Consumable;
import WoWSSSC.model.gameparams.ShipComponents.AA.AirDefense;
import WoWSSSC.model.gameparams.ShipComponents.AA.AntiAir;
import WoWSSSC.model.gameparams.ShipComponents.ATBA.ATBA;
import WoWSSSC.model.gameparams.ShipComponents.Artillery.Artillery;
import WoWSSSC.model.gameparams.ShipComponents.DiveBomber.DiveBomber;
import WoWSSSC.model.gameparams.ShipComponents.Engine.Engine;
import WoWSSSC.model.gameparams.ShipComponents.FireControl.FireControl;
import WoWSSSC.model.gameparams.ShipComponents.Hull.Hull;
import WoWSSSC.model.gameparams.ShipComponents.TorpedoBomber.TorpedoBomber;
import WoWSSSC.model.gameparams.ShipComponents.Torpedoes.Torpedoes;
import WoWSSSC.model.gameparams.test.Values.ShipAbilities.ShipAbilities;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Aesis on 2016. 12. 6..
 */
@Data
public class ShipComponents
{
    private AirArmament airArmament;
    private AirDefense airDefense;
    private Artillery artillery;
    private ATBA atba;
    private LinkedHashMap<String, LinkedHashMap> directors;
    private DiveBomber diveBomber;
    private Engine engine;
    private LinkedHashMap<String, LinkedHashMap> fighter;
    private LinkedHashMap<String, LinkedHashMap> finders;
    private FireControl fireControl;
    private LinkedHashMap<String, LinkedHashMap> flightControl;
    private Hull hull;
    private Torpedoes torpedoes;
    private TorpedoBomber torpedoBomber;

    private ShipAbilities shipAbilities;
    private HashMap<String, Consumable> abilities = new HashMap<>();

    private List<AntiAir> auraFarList = new ArrayList<>();
    private List<AntiAir> auraMediumList = new ArrayList<>();
    private List<AntiAir> auraNearList = new ArrayList<>();
}
