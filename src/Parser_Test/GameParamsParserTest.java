package Parser_Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import Parser.GameParams_Parser;

public class GameParamsParserTest 
{
	private GameParams_Parser GameParamsParser;
	private String ship_id_str = "PASB018";
	
	@Before
	public void setUp() throws FileNotFoundException, IOException, ParseException
	{
		GameParamsParser = new GameParams_Parser(ship_id_str);
	}	
	
	@Test
	public void test1() 
	{
		assertNotNull(GameParamsParser);
	}
	
}
