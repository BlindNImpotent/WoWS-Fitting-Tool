package WoWSFT.model.gameparams.ship.component;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.ship.component.airarmament.AirArmament;
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.component.atba.ATBA;
import WoWSFT.model.gameparams.ship.component.engine.Engine;
import WoWSFT.model.gameparams.ship.component.firecontrol.FireControl;
import WoWSFT.model.gameparams.ship.component.flightcontrol.FlightControl;
import WoWSFT.model.gameparams.ship.component.hull.Hull;
import WoWSFT.model.gameparams.ship.component.planes.Plane;
import WoWSFT.model.gameparams.ship.component.torpedo.Torpedo;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@WoWSFT
public class ShipComponent
{
    private LinkedHashMap<String, FlightControl> flightControl = new LinkedHashMap<>();
    private LinkedHashMap<String, AirArmament> airArmament = new LinkedHashMap<>();
    private LinkedHashMap<String, AirDefense> airDefense = new LinkedHashMap<>();
    private LinkedHashMap<String, Artillery> artillery = new LinkedHashMap<>();
    private LinkedHashMap<String, ATBA> atba = new LinkedHashMap<>();
    private LinkedHashMap<String, Hull> hull = new LinkedHashMap<>();
    private LinkedHashMap<String, FireControl> suo = new LinkedHashMap<>();
    private LinkedHashMap<String, Torpedo> torpedoes = new LinkedHashMap<>();
    private LinkedHashMap<String, Plane> fighter = new LinkedHashMap<>();
    private LinkedHashMap<String, Plane> torpedoBomber = new LinkedHashMap<>();
    private LinkedHashMap<String, Plane> diveBomber = new LinkedHashMap<>();
    private LinkedHashMap<String, Engine> engine = new LinkedHashMap<>();
}
