import java.util.ArrayList;
import java.util.List;

public class Lists 
{
	private	List<String> USACVs = new ArrayList<String>();
	private List<String> USADestroyers = new ArrayList<String>();
	
	
	public List<String> getNationList()
	{
		List<String> nation = new ArrayList<String>();
		nation.add("Germany");
		nation.add("Japan");
		nation.add("Russia");
		nation.add("USA");
		nation.add("Poland");
		nation.add("United Kingdom");
		nation.add("Pan Asia");
		
		return nation;
	}
	
	public List<String> getShipTypeList()
	{
		List<String> shipType = new ArrayList<String>();
		
		shipType.add("CV");
		shipType.add("Battleship");
		shipType.add("Cruiser");
		shipType.add("Destroyer");
		shipType.add("Premium");
		
		return shipType;
	}

	public List<String> getUSABattleships()
	{
		List<String> USABattleships = new ArrayList<String>();
		
		USABattleships.add("South Carolina");
		USABattleships.add("Wyoming");
		USABattleships.add("New York");
		USABattleships.add("New Mexico");
		USABattleships.add("Colorado");
		USABattleships.add("North Carolina");
		USABattleships.add("Iowa");
		USABattleships.add("Montana");		
		
		return USABattleships;
	}
	
	public void setUSACVsList()
	{		
		USACVs.add("Langley");
		USACVs.add("Bogue");
		USACVs.add("Independence");
		USACVs.add("Ranger");
		USACVs.add("Lexington");
		USACVs.add("Essex");
		USACVs.add("Midway");
	}
	public List<String> getUSACVs() 
	{
		return USACVs;
	}

	
	public void setUSADestroyersList()
	{
		USADestroyers.add("Sampson");
		USADestroyers.add("Wickes");
		USADestroyers.add("Clemson");
		USADestroyers.add("Nicholas");
		USADestroyers.add("Farragut");
		USADestroyers.add("Mahan");
		USADestroyers.add("Benson");
		USADestroyers.add("Fletcher");
		USADestroyers.add("Gearing");

	}
	public List<String> getUSADestroyers() 
	{		
		return USADestroyers;
	}

	public List<String> getUSACruisers() 
	{
		List<String> USACruisers = new ArrayList<String>();
		
		USACruisers.add("Erie");
		USACruisers.add("St_Louis");
		USACruisers.add("Phoenix");
		USACruisers.add("Omaha");
		USACruisers.add("Cleveland");
		USACruisers.add("Pensacola");
		USACruisers.add("New Orleans");
		USACruisers.add("Baltimore");
		USACruisers.add("Des Moines");
		
		return USACruisers;
	}

	public List<String> getUSAPremium()
	{
		List<String> USAPremium = new ArrayList<String>(); 
		
		USAPremium.add("Texas");
		USAPremium.add("Atlanta");
		
		return USAPremium;
	}












}
