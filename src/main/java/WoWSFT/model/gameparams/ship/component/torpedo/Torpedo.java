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
    private LinkedHashMap<String, List<Integer>> launcherTypes = new LinkedHashMap<>();

    private int numTorpsInSalvo;
    private float oneShotWaitTime;
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

                launcherTypes.putIfAbsent(launcher.getName(), new ArrayList<>(Arrays.asList(0, launcher.getNumBarrels())));
                launcherTypes.get(launcher.getName()).set(0, launcherTypes.get(launcher.getName()).get(0) + 1);
                launcherTypes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
                    launcherTypes.remove(e.getKey());
                    launcherTypes.put(e.getKey(), e.getValue());
                });
            }
        }
    }
}
