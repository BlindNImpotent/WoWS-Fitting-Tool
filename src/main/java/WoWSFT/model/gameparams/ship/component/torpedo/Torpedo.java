package WoWSFT.model.gameparams.ship.component.torpedo;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.*;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Torpedo
{
    private List<Launcher> launchers = new ArrayList<>();
    private LinkedHashMap<Integer, List<Object>> launcherTypes = new LinkedHashMap<>();

    private int numTorpsInSalvo;
    private double oneShotWaitTime;
    private boolean useGroups;
    private boolean useOneShot;

    private TorpedoAmmo ammo;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setGuns(String name, Object value)
    {
        if (value instanceof HashMap) {
            HashMap<String, Object> tempObject = mapper.convertValue(value, new TypeReference<HashMap<String, Object>>(){});

            if (tempObject.containsKey("HitLocationTorpedo")) {
                Launcher launcher = mapper.convertValue(value, Launcher.class);
                launchers.add(launcher);

                if (launcherTypes.containsKey(launcher.getNumBarrels())) {
                    launcherTypes.get(launcher.getNumBarrels()).set(0, (int) launcherTypes.get(launcher.getNumBarrels()).get(0) + 1);
                } else {
                    List<Object> tObject = new ArrayList<>();
                    tObject.add(1);
                    tObject.add(launcher.getName());
                    launcherTypes.put(launcher.getNumBarrels(), tObject);
                }
            }
        }
    }
}
