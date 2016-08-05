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

    private List<String> AllShipNameList = new ArrayList<>();

    private List<String> FranceAirCarrierList;
    private List<String> FranceBattleshipList;
    private List<String> FranceCruiserList;
    private List<String> FranceDestroyerList;
    private List<String> FrancePremiumList;

    private List<String> GermanyAirCarrierList;
    private List<String> GermanyBattleshipList;
    private List<String> GermanyCruiserList;
    private List<String> GermanyDestroyerList;
    private List<String> GermanyPremiumList;

    private List<String> JapanAirCarrierList;
    private List<String> JapanBattleshipList;
    private List<String> JapanCruiserList;
    private List<String> JapanDestroyerList;
    private List<String> JapanPremiumList;

    private List<String> PanAsiaPremiumList;

    private List<String> PolandPremiumList;

    private List<String> UKAirCarrierList;
    private List<String> UKBattleshipList;
    private List<String> UKCruiserList;
    private List<String> UKDestroyerList;
    private List<String> UKPremiumList;

    private List<String> USAAirCarrierList;
    private List<String> USABattleshipList;
    private List<String> USACruiserList;
    private List<String> USADestroyerList;
    private List<String> USAPremiumList;

    private List<String> USSRAirCarrierList;
    private List<String> USSRBattleshipList;
    private List<String> USSRCruiserList;
    private List<String> USSRDestroyerList;
    private List<String> USSRPremiumList;

    public ShipNameList() throws IOException, ParseException
    {
        apiNameLists = new API_NameLists();

        nationList = apiNameLists.getNationNameList();
        shipTypeList = apiNameLists.getShipTypeNameList();
        shipTypeList.add("Premium");

        FranceAirCarrierList = apiNameLists.getFrance_AirCarrierNameList();
        FranceBattleshipList = apiNameLists.getFrance_BattleshipNameList();
        FranceCruiserList = apiNameLists.getFrance_CruiserNameList();
        FranceDestroyerList = apiNameLists.getFrance_DestroyerNameList();
        FrancePremiumList = apiNameLists.getFrance_PremiumNameList();

        GermanyAirCarrierList = apiNameLists.getGermany_AirCarrierNameList();
        GermanyBattleshipList = apiNameLists.getGermany_BattleshipNameList();
        GermanyCruiserList = apiNameLists.getGermany_CruiserNameList();
        GermanyDestroyerList = apiNameLists.getGermany_DestroyerNameList();
        GermanyPremiumList = apiNameLists.getGermany_PremiumNameList();

        JapanAirCarrierList = apiNameLists.getJapan_AirCarrierNameList();
        JapanBattleshipList = apiNameLists.getJapan_BattleshipNameList();
        JapanCruiserList = apiNameLists.getJapan_CruiserNameList();
        JapanDestroyerList = apiNameLists.getJapan_DestroyerNameList();
        JapanPremiumList = apiNameLists.getJapan_PremiumNameList();

        PanAsiaPremiumList = apiNameLists.getPan_Asia_PremiumNameList();

        PolandPremiumList = apiNameLists.getPoland_PremiumNameList();

        UKAirCarrierList = apiNameLists.getUK_AirCarrierNameList();
        UKBattleshipList = apiNameLists.getUK_BattleshipNameList();
        UKCruiserList = apiNameLists.getUK_CruiserNameList();
        UKDestroyerList = apiNameLists.getUK_DestroyerNameList();
        UKPremiumList = apiNameLists.getUK_PremiumNameList();

        USAAirCarrierList = apiNameLists.getUSA_AirCarrierNameList();
        USABattleshipList = apiNameLists.getUSA_BattleshipNameList();
        USACruiserList = apiNameLists.getUSA_CruiserNameList();
        USADestroyerList = apiNameLists.getUSA_DestroyerNameList();
        USAPremiumList = apiNameLists.getUSA_PremiumNameList();

        USSRAirCarrierList = apiNameLists.getUSSR_AirCarrierNameList();
        USSRBattleshipList = apiNameLists.getUSSR_BattleshipNameList();
        USSRCruiserList = apiNameLists.getUSSR_CruiserNameList();
        USSRDestroyerList = apiNameLists.getUSSR_DestroyerNameList();
        USSRPremiumList = apiNameLists.getUSSR_PremiumNameList();
        
        AllShipNameList.addAll(FranceAirCarrierList);
        AllShipNameList.addAll(FranceBattleshipList);
        AllShipNameList.addAll(FranceCruiserList);
        AllShipNameList.addAll(FranceDestroyerList);
        AllShipNameList.addAll(FrancePremiumList);

        AllShipNameList.addAll(GermanyAirCarrierList);
        AllShipNameList.addAll(GermanyBattleshipList);
        AllShipNameList.addAll(GermanyCruiserList);
        AllShipNameList.addAll(GermanyDestroyerList);
        AllShipNameList.addAll(GermanyPremiumList);

        AllShipNameList.addAll(JapanAirCarrierList);
        AllShipNameList.addAll(JapanBattleshipList);
        AllShipNameList.addAll(JapanCruiserList);
        AllShipNameList.addAll(JapanDestroyerList);
        AllShipNameList.addAll(JapanPremiumList);

        AllShipNameList.addAll(PanAsiaPremiumList);

        AllShipNameList.addAll(PolandPremiumList);

        AllShipNameList.addAll(UKAirCarrierList);
        AllShipNameList.addAll(UKBattleshipList);
        AllShipNameList.addAll(UKCruiserList);
        AllShipNameList.addAll(UKDestroyerList);
        AllShipNameList.addAll(UKPremiumList);

        AllShipNameList.addAll(USAAirCarrierList);
        AllShipNameList.addAll(USABattleshipList);
        AllShipNameList.addAll(USACruiserList);
        AllShipNameList.addAll(USADestroyerList);
        AllShipNameList.addAll(USAPremiumList);

        AllShipNameList.addAll(USSRAirCarrierList);
        AllShipNameList.addAll(USSRBattleshipList);
        AllShipNameList.addAll(USSRCruiserList);
        AllShipNameList.addAll(USSRDestroyerList);
        AllShipNameList.addAll(USSRPremiumList);
    }
}
