package WoWSSSC.model.gameparams.ship;


import WoWSSSC.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Ship
{
    private float apDamageLimitCoeff;
    private boolean canEquipCamouflage;
    private String defaultCrew;
    private String group;
    private long id;
    private String index;
    private boolean paperShip;
    private int level;
    private int maxEquippedFlags;
    private String name;
    private TypeInfo typeinfo;
    private int weight;
}
