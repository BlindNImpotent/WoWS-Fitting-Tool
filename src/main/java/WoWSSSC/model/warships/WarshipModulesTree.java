package WoWSSSC.model.warships;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarshipModulesTree
{
    private String type;
    private String name;
    private String module_id_str;
    private long module_id;
    private long price_xp;
    private long price_credit;
    private boolean is_default;
    private List<Long> next_modules = new ArrayList<>();
    private List<Long> next_ships = new ArrayList<>();
    private List<Long> prev_modules = new ArrayList<>();

    public boolean isIs_default()
    {
        return is_default;
    }
}
