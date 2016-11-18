package WoWSSSC.model.warships;

import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarshipModulesTreeNew
{
    private LinkedHashMap<String, WarshipModulesTree> artillery = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> dive_bomber = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> engine = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> fighter = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> fire_control = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> flight_control = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> hull = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> torpedoes = new LinkedHashMap<>();
    private LinkedHashMap<String, WarshipModulesTree> torpedo_bomber = new LinkedHashMap<>();

    @JsonIgnore
    private Sorter sorter = new Sorter();

    public void sort()
    {
        artillery = sorter.sortShipModules(artillery);
        dive_bomber = sorter.sortShipModules(dive_bomber);
        engine = sorter.sortShipModules(engine);
        fighter = sorter.sortShipModules(fighter);
        fire_control = sorter.sortShipModules(fire_control);
        flight_control = sorter.sortShipModules(flight_control);
        hull = sorter.sortShipModules(hull);
        torpedoes = sorter.sortShipModules(torpedoes);
        torpedo_bomber = sorter.sortShipModules(torpedo_bomber);
    }
}
