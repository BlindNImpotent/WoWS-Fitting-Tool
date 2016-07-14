import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * @author Aesis (BlindNImpotent)
 * Model class.
 */
public class Model 
{
	private Calculate calculator = new Calculate();

	public Calc calculate(
			String aShip, String turret, String hull, String engine, String radar, String torpedo,
			String mod1, String mod2, String mod3, String mod4, String mod5, String mod6,
			String consume1, String consume2, String consume3, String consume4,
			boolean BFT, boolean BoS,
			boolean EM, boolean TAE, 
			boolean TA, boolean HA, boolean SI, 
			boolean DE, boolean AFT, boolean survivability, boolean MFCAA,
			boolean conceal, boolean JoAT,
			String camouflage,
			boolean JY2, boolean IY, boolean IX, boolean NE7, boolean SM, boolean VL, boolean MY6, boolean NF, boolean ZH, boolean ESCL, boolean IB3,
			boolean ManualAA,
			boolean PremAcc
			) throws FileNotFoundException, IOException, ParseException
	{
		return calculator.calculate(
				aShip, turret, hull, engine,radar, torpedo,
				mod1, mod2, mod3, mod4, mod5, mod6,
				consume1, consume2, consume3, consume4,
				BFT, BoS,
				EM, TAE,
				TA, HA, SI, 
				DE, AFT, survivability, MFCAA,
				conceal, JoAT,
				camouflage,
				JY2, IY, IX, NE7, SM, VL, MY6, NF, ZH, ESCL, IB3,
				ManualAA,
				PremAcc
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
	
	public Calc upgrades(String aShip, String turret, String hull, String engine, String radar, String torpedo,
						String consume1, String consume2, String consume3, String consume4
						) 
						throws FileNotFoundException, IOException, ParseException
	{
		return calculator.upgrades(aShip, turret, hull, engine, radar, torpedo, consume1, consume2, consume3, consume4);
	}
}
