import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * @author Aesis (BlindNImpotent)
 * Model class.
 */
public class Model 
{
	private Calculator calculator = new Calculator();

	public Calc calculate(
			String aShip, String turret, String hull, String engine, String radar, String torpedo,
			String mod1, String mod2, String mod3, String mod4, String mod5, String mod6,
			boolean BFT, boolean BoS,
			boolean EM, boolean TAE,
			boolean TA,
			boolean DE, boolean AFT, boolean survivability,
			boolean conceal,							  
			boolean concealCamo
			) throws FileNotFoundException, IOException, ParseException
	{
		return calculator.calculate(
				aShip, turret, hull, engine,radar, torpedo,
				mod1, mod2, mod3, mod4, mod5, mod6,
				BFT, BoS,
				EM, TAE,
				TA,
				DE, AFT, survivability,
				conceal,							  
				concealCamo
				);
	}
	
	/**
	 * Searches for ship name.
	 * @param aShip
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Calc search(String aShip) throws FileNotFoundException, IOException, ParseException
	{
		return calculator.search(aShip);
	}
	
	public Calc upgrades(String aShip, String turret, String hull, String engine, String radar, String torpedo) throws FileNotFoundException, IOException, ParseException
	{
		return calculator.upgrades(aShip, turret, hull, engine, radar, torpedo);
	}
}
