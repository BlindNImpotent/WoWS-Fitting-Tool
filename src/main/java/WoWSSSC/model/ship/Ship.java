package WoWSSSC.model.ship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private String nation;
    private String type;
    private String name;
    private String ship_id_str;
    private String description;
    private long tier;
    private long ship_id;
    private long price_gold;
    private long price_credit;
    private long mod_slots;
    private boolean is_premium;
    private HashMap<String, String> images;
    private HashMap<String, List<Long>> modules;
    private HashMap<String, ShipModulesTree> modules_tree;
    private HashMap<String, Long> next_ships;
    private List<Long> upgrades;

    private ShipModule shipModule = new ShipModule();
    
    private static final String Artillery = "Artillery";
    private static final String DiveBomber = "DiveBomber";
    private static final String Engine = "Engine";
    private static final String Fighter = "Fighter";
    private static final String FlightControl = "FlightControl";
    private static final String Hull = "Hull";
    private static final String Suo = "Suo";
    private static final String Torpedoes = "Torpedoes";
    private static final String TorpedoBomber = "TorpedoBomber";
    
    public boolean isIs_premium()
    {
        return is_premium;
    }

    public void setModules_tree(HashMap<String, ShipModulesTree> modules_tree)
    {
        this.modules_tree = modules_tree;

        this.modules_tree.entrySet().forEach(mt ->
        {
            if (mt.getValue().getNext_modules() != null)
            {
                mt.getValue().getNext_modules().forEach(nm -> modules_tree.get(String.valueOf(nm)).getPrev_modules().add(mt.getValue().getModule_id()));
            }

            if (mt.getValue().getType().equals(Artillery))
            {
                shipModule.getArtillery().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(DiveBomber))
            {
                shipModule.getDive_bomber().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(Engine))
            {
                shipModule.getEngine().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(Fighter))
            {
                shipModule.getFighter().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(FlightControl))
            {
                shipModule.getFlight_control().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(Hull))
            {
                shipModule.getHull().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(Suo))
            {
                shipModule.getFire_control().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(Torpedoes))
            {
                shipModule.getTorpedoes().put(mt.getValue().getName(), mt.getValue());
            }
            else if (mt.getValue().getType().equals(TorpedoBomber))
            {
                shipModule.getTorpedo_bomber().put(mt.getValue().getName(), mt.getValue());
            }            
            else
            {
                System.out.println(mt.getValue().getType());
            }
        });

        shipModule.sort();
    }
}
