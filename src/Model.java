import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * 
 * @author Aesis (BlindNImpotent)
 *
 */
public class Model 
{
	private Calculator calculator = new Calculator();
	
	/**
	 * Calculates ship stats with parameters.
	 * @param aShip
	 * @param mod1
	 * @param mod2
	 * @param mod3
	 * @param mod4
	 * @param mod5
	 * @param mod6
	 * @param conceal
	 * @param survivability
	 * @param AFT
	 * @param EM
	 * @param BFT
	 * @param TAE
	 * @param TA
	 * @param BoS
	 * @param concealCamo
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Calc calculate(
			String aShip, String mod1, String mod2, String mod3, String mod4, String mod5, String mod6, 
			boolean conceal, boolean survivability, boolean AFT, boolean EM, boolean BFT, boolean TAE, boolean TA, boolean BoS,
			boolean concealCamo) 
					throws FileNotFoundException, IOException, ParseException
	{
		return calculator.calculate(
				aShip, mod1, mod2, mod3, mod4, mod5, mod6, 
				conceal, survivability, AFT, EM, BFT, TAE, TA, BoS,
				concealCamo);
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

}
