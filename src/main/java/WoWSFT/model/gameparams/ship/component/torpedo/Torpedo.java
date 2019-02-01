package WoWSFT.model.gameparams.ship.component.torpedo;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Torpedo
{
    private List<Launcher> launchers = new ArrayList<>();

    private float numTorpsInSalvo;
    private float oneShotWaitTime;
    private boolean useGroups;
    private boolean useOneShot;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setGuns(String name, Object value)
    {
        if (value instanceof HashMap) {
            HashMap<String, Object> tempObject = mapper.convertValue(value, new TypeReference<HashMap<String, Object>>(){});

            if (tempObject.containsKey("HitLocationTorpedo")) {
                launchers.add(mapper.convertValue(value, Launcher.class));
            }
        }
    }
}
