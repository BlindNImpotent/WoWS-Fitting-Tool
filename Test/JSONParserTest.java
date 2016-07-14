import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class JSONParserTest 
{
	private JSONParser JSONParser;	
	private String aShipName;
	
	@Before
	public void setup() throws FileNotFoundException, IOException, ParseException
	{
		aShipName = "Iowa";
		JSONParser = new JSONParser(aShipName);
	}	

	@Test
	public void test() 
	{
		assertNotNull(JSONParser);
	}

}
