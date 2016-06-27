import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.json.simple.parser.ParseException;

public class Model 
{
	private Calculator calculator = new Calculator();
	
	public Calc calculate(
			String aShip, String mod1, String mod2, String mod3, String mod4, String mod5, String mod6, 
			boolean conceal, boolean survivability, boolean AFT, boolean EM, boolean BFT, boolean TAE, boolean TA,
			boolean concealCamo) 
					throws FileNotFoundException, IOException, ParseException
	{
		return calculator.calculate(
				aShip, mod1, mod2, mod3, mod4, mod5, mod6, 
				conceal, survivability, AFT, EM, BFT, TAE, TA, 
				concealCamo);
	}
	public Calc search(String aShip) throws FileNotFoundException, IOException, ParseException
	{
		return calculator.search(aShip);
	}

}
