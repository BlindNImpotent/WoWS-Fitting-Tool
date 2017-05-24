package WoWSSSC.utils;

import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.shipprofile.profile.anti_aircraft.Anti_Aircraft_Slot;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.WoWSAPI.upgrade.profile.Anti_Aircraft;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.model.WoWSAPI.upgrade.Upgrade;
import WoWSSSC.model.gameparams.Consumables.Consumable;

import java.util.*;

/**
 * Created by Qualson-Lee on 2016-11-16.
 */
public class Sorter
{
    public LinkedHashMap<String, Warship> sortShips(LinkedHashMap<String, Warship> unsorted)
    {
        LinkedHashMap<String, Warship> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, Warship>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Warship>>() {
            @Override
            public int compare(Map.Entry<String, Warship> o1, Map.Entry<String, Warship> o2) {
                int tierDiff = 0;

                tierDiff = (int) (o1.getValue().getTier() - o2.getValue().getTier());

                if (tierDiff == 0)
                {
                    if (o1.getValue().getDefaultType().compareTo(o2.getValue().getDefaultType()) != 0)
                    {
                        return o1.getValue().getDefaultType().compareTo(o2.getValue().getDefaultType());
                    }
                    return (o1.getValue().getName().compareTo(o2.getValue().getName()));
                }
                return tierDiff;
            }
        });

        for (Map.Entry<String, Warship> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, WarshipModulesTree> sortShipModules(LinkedHashMap<String, WarshipModulesTree> unsorted)
    {
        LinkedHashMap<String, WarshipModulesTree> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, WarshipModulesTree>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, WarshipModulesTree>>() {
            @Override
            public int compare(Map.Entry<String, WarshipModulesTree> o1, Map.Entry<String, WarshipModulesTree> o2) {
                if (o1.getValue().isIs_default() && !o2.getValue().isIs_default())
                {
                    return -1;
                }
                else if (!o1.getValue().isIs_default() && o2.getValue().isIs_default())
                {
                    return 1;
                }
                else
                {
                    return o1.getValue().getName().compareTo(o2.getValue().getName());
                }
            }
        });

        for (Map.Entry<String, WarshipModulesTree> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, Consumables> sortUpgrades(LinkedHashMap<String, Consumables> unsorted)
    {
        LinkedHashMap<String, Consumables> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, Consumables>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Consumables>>() {
            @Override
            public int compare(Map.Entry<String, Consumables> o1, Map.Entry<String, Consumables> o2) {
                return o1.getValue().getImage().compareTo(o2.getValue().getImage());
            }
        });

        for (Map.Entry<String, Consumables> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, LinkedHashMap> sortWarshipModulesTreeNew(LinkedHashMap<String, LinkedHashMap> unsorted)
    {
        LinkedHashMap<String, LinkedHashMap> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, LinkedHashMap>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, LinkedHashMap>>() {
            @Override
            public int compare(Map.Entry<String, LinkedHashMap> o1, Map.Entry<String, LinkedHashMap> o2) {
                if (o1.getKey().equals("Hull"))
                {
                    if (o2.getKey().equals("Artillery") || o2.getKey().equals("FlightControl"))
                    {
                        return 1;
                    }
                    return -1;
                }
                else if (o2.getKey().equals("Hull"))
                {
                    if (o1.getKey().equals("Artillery") || o1.getKey().equals("FlightControl"))
                    {
                        return -1;
                    }
                    return 1;
                }
                else if (o1.getKey().equals("Engine"))
                {
                    if (o2.getKey().equals("Suo") || o2.getKey().equals("Torpedoes"))
                    {
                        return -1;
                    }
                    return 1;
                }
                else if (o2.getKey().equals("Engine"))
                {
                    if (o1.getKey().equals("Suo") || o1.getKey().equals("Torpedoes"))
                    {
                        return 1;
                    }
                    return -1;
                }
                else if (o1.getKey().equals("Fighter"))
                {
                    if (o2.getKey().equals("DiveBomber") || o2.getKey().equals("TorpedoBomber"))
                    {
                        return -1;
                    }
                    return 1;
                }
                else if (o2.getKey().equals("Fighter"))
                {
                    if (o1.getKey().equals("DiveBomber") || o1.getKey().equals("TorpedoBomber"))
                    {
                        return 1;
                    }
                    return -1;
                }
                else if (o1.getKey().equals("DiveBomber"))
                {
                    return 1;
                }
                else if (o2.getKey().equals("DiveBomber"))
                {
                    return -1;
                }
                else
                {
                    return o1.getKey().compareTo(o2.getKey());
                }
            }
        });

        for (Map.Entry<String, LinkedHashMap> entry:list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, LinkedHashMap> sortUpgradeType(LinkedHashMap<String, LinkedHashMap> unsorted)
    {
        LinkedHashMap<String, LinkedHashMap> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, LinkedHashMap>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, LinkedHashMap>>() {
            @Override
            public int compare(Map.Entry<String, LinkedHashMap> o1, Map.Entry<String, LinkedHashMap> o2)
            {
                //125, 500, 3000, 250, 1000, 2000
                if (o1.getKey().equals("125000"))
                {
                    return -1;
                }
                else if (o2.getKey().equals("125000"))
                {
                    return 1;
                }
                else if (o1.getKey().equals("500000"))
                {
                    if (o2.getKey().equals("125000"))
                    {
                        return 1;
                    }
                    return -1;
                }
                else if (o2.getKey().equals("500000"))
                {
                    if (o1.getKey().equals("125000"))
                    {
                        return -1;
                    }
                    return 1;
                }
                else if (o1.getKey().equals("3000000"))
                {
                    if (o2.getKey().equals("125000") || o2.getKey().equals("500000"))
                    {
                        return 1;
                    }
                    return -1;
                }
                else if (o2.getKey().equals("3000000"))
                {
                    if (o1.getKey().equals("125000") || o1.getKey().equals("500000"))
                    {
                        return -1;
                    }
                    return 1;
                }
                else
                {
                    return Integer.parseInt(o1.getKey()) - Integer.parseInt(o2.getKey());
                }
            }
        });

        for (Map.Entry<String, LinkedHashMap> entry:list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, CrewSkills> sortCrewSkills(LinkedHashMap<String, CrewSkills> unsorted)
    {
        LinkedHashMap<String, CrewSkills> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, CrewSkills>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, CrewSkills>>() {
            @Override
            public int compare(Map.Entry<String, CrewSkills> o1, Map.Entry<String, CrewSkills> o2)
            {
                return o1.getValue().getType_id() - o2.getValue().getType_id();
            }
        });

        for (Map.Entry<String, CrewSkills> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }

    public LinkedHashMap<String, Anti_Aircraft_Slot> sortAARange(LinkedHashMap<String, Anti_Aircraft_Slot> unsorted)
    {
        LinkedHashMap<String, Anti_Aircraft_Slot> sorted = new LinkedHashMap<>();

        List<Map.Entry<String, Anti_Aircraft_Slot>> list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Anti_Aircraft_Slot>>() {
            @Override
            public int compare(Map.Entry<String, Anti_Aircraft_Slot> o1, Map.Entry<String, Anti_Aircraft_Slot> o2)
            {
                if (o1.getValue().getDistance() == o2.getValue().getDistance())
                {
                    if (o1.getValue().getCaliber() == o2.getValue().getCaliber())
                    {
                        return (int) ((o1.getValue().getAvg_damage() - o2.getValue().getAvg_damage()) * 100);
                    }
                    else
                    {
                        return (int) ((o1.getValue().getCaliber() - o2.getValue().getCaliber()) * 100);
                    }
                }
                else
                {
                    return (int) ((o1.getValue().getDistance() - o2.getValue().getDistance()) * 100);
                }
            }
        });

        for (Map.Entry<String, Anti_Aircraft_Slot> entry : list)
        {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }
}
