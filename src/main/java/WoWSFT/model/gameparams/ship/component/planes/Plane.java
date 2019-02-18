package WoWSFT.model.gameparams.ship.component.planes;

import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import WoWSFT.model.gameparams.ship.component.artillery.Shell;
import WoWSFT.model.gameparams.ship.component.torpedo.TorpedoAmmo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plane
{
    @JsonProperty("PlaneAbilities")
    private LinkedHashMap<String, AbilitySlot> planeAbilities;

    private int attackCount;
    private int attackerSize;
    private String bombName;
    private float forsageRegeneration;
    private float fuelTime;
    private HangarSetting hangarSettings;
    private long id;
    private String index;
    private int level;
    private float maxForsageAmount;
    private int maxHealth;
    private float maxVisibilityFactor;
    private float maxVisibilityFactorByPlane;
    private float minVisibilityFactor;
    private float minVisibilityFactorByPlane;
    private String name;
    private int numPlanesInSquadron;
    private float speedMax;
    private float speedMin;
    private float speedMove;
    private float speedMoveWithBomb;
    private TypeInfo typeinfo;
    private Shell rocket;
    private Shell bomb;
    private TorpedoAmmo torpedo;
    private List<Consumable> consumables;
}
