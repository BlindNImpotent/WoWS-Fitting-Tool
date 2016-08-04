package WoWSSSC.model;

import Parser.API_NameLists;
import lombok.Data;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aesis on 2016-08-05.
 */
@Data
public class ShipNameList
{
    private API_NameLists apiNameLists;

    private List<String> nationList;
    private List<String> shipTypeList;
    private List<String> GermanyBattleshipList;
    private List<String> GermanyCruiserList;
    private List<String> GermanyPremiumList;



    public ShipNameList() throws IOException, ParseException
    {
        apiNameLists = new API_NameLists();

        nationList = apiNameLists.getNationNameList();
        shipTypeList = apiNameLists.getShipTypeNameList();
        shipTypeList.add("Premium");

        GermanyBattleshipList = apiNameLists.getGermany_BattleshipNameList();
        GermanyCruiserList = apiNameLists.getGermany_CruiserNameList();
        GermanyPremiumList = apiNameLists.getGermany_PremiumNameList();
    }


}
