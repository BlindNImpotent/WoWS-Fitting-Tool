import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class GameParamsParserTest 
{
	private GameParamsParser GameParamsParser;
	private String ship_id_str = "PASB018";
	
	@Before
	public void setUp() throws FileNotFoundException, IOException, ParseException
	{
		GameParamsParser = new GameParamsParser(ship_id_str);
	}	
	
	@Test
	public void test1() 
	{
		assertNotNull(GameParamsParser);
	}
	
}
