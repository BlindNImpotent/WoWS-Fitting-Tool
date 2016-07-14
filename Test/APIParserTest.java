import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class APIParserTest 
{
	private APIParser APIParser;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException, ParseException
	{
		APIParser = new APIParser("Iowa");
	}	
	
	@Test
	public void test1() 
	{
		assertNotNull(APIParser.getShipJSON());
	}

	
}
