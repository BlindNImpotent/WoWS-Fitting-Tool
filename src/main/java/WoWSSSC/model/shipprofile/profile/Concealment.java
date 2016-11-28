package WoWSSSC.model.shipprofile.profile;

import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Concealment
{
    private float detect_distance_by_plane;
    private float detect_distance_by_ship;
    private long total;

    private float visibilityCoefGK;
}
