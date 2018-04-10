package WoWSSSC.model.WoWSAPI.warships;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
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
    private String next_module_class = "";
    private String prev_module_class = "";

    private boolean isShiftUp;
    private boolean isShiftLeft;
    private boolean isShiftRight;

    public boolean isIs_default()
    {
        return is_default;
    }

    public void setNext_modules(List<Long> next_modules_list)
    {
        next_modules = next_modules_list;

        if (next_modules != null)
        {
            for (long i : next_modules)
            {
                next_module_class = next_module_class + "next_module_" + String.valueOf(i) + " ";
            }
            next_module_class = next_module_class.trim();
        }
    }

    public void setPrev_module_class(String prev_module)
    {
        prev_module_class = prev_module;
        prev_module_class = prev_module_class.trim();
    }
}
