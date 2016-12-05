package WoWSSSC.model.WoWSAPI.info;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Qualson-Lee on 2016-11-24.
 */
@Data
public class Encyclopedia
{
    private long ships_updated_at;
    private HashMap<String, String> ship_types;
    private HashMap<String, String> languages;
    private HashMap<String, String> ship_modifications;
    private HashMap<String, String> ship_modules;
    private HashMap<String, HashMap> ship_type_images;
    private HashMap<String, String> ship_nations;
    private String game_version;
}
