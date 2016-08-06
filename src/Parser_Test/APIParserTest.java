package Parser_Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import Parser.API_Parser;

public class APIParserTest 
{
	private API_Parser APIParser;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException, ParseException
	{
		APIParser = new API_Parser();
		APIParser.setShipJSON("Iowa");
	}	
	
	@Test
	public void test1() 
	{
		assertNotNull(APIParser.getShipJSON());
	}

	
}
