package WoWSSSC.model.warships;

import lombok.Data;

import java.util.HashSet;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
public class ShipModules
{
    private HashSet<Long> artillery;
    private HashSet<Long> dive_bomber;
    private HashSet<Long> engine;
    private HashSet<Long> fighter;
    private HashSet<Long> fire_control;
    private HashSet<Long> flight_control;
    private HashSet<Long> hull;
    private HashSet<Long> torpedoes;
    private HashSet<Long> torpedo_bomber;
}
