package WoWSSSC.model;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016. 12. 6..
 */
@Data
public class ShipComponents
{
    private LinkedHashMap<String, LinkedHashMap> airArmament;
    private LinkedHashMap<String, LinkedHashMap> airDefense;
    private LinkedHashMap<String, LinkedHashMap> artillery;
    private LinkedHashMap<String, LinkedHashMap> atba;
    private LinkedHashMap<String, LinkedHashMap> directors;
    private LinkedHashMap<String, LinkedHashMap> diveBomber;
    private LinkedHashMap<String, LinkedHashMap> engine;
    private LinkedHashMap<String, LinkedHashMap> fighter;
    private LinkedHashMap<String, LinkedHashMap> finders;
    private LinkedHashMap<String, LinkedHashMap> fireControl;
    private LinkedHashMap<String, LinkedHashMap> flightControl;
    private LinkedHashMap<String, LinkedHashMap> hull;
    private LinkedHashMap<String, LinkedHashMap> radars;
    private LinkedHashMap<String, LinkedHashMap> torpedoes;
    private LinkedHashMap<String, LinkedHashMap> torpedoBomber;
}
