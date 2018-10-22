package WoWSSSC.model.gameparams.ShipComponents.Torpedoes;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Aesis on 2017-05-15.
 */
@Data
public class Torpedoes
{
    private LinkedHashMap<String, Launcher> launchers = new LinkedHashMap<>();
    private List<Launcher> launchersList = new ArrayList<>();
    private boolean useOneShot;

    private Torpedo torpedo;
    private List<String> groups;
    private List<String> loaders;
    private int numTorpsInSalvo;
    private float oneShotWaitTime;
    private boolean useGroups;
    private float rotationDeg;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setLaunchers(String name, Object value)
    {
        if (mapper.convertValue(value, LinkedHashMap.class).get("HitLocationTorpedo") != null)
        {
            Launcher launcher = mapper.convertValue(value, Launcher.class);
            launchers.put(name, launcher);
            launchersList.add(launcher);
            rotationDeg = launcher.getRotationSpeed().get(0);
        }
    }
}
