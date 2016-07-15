package Parser_Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import Parser.JSON_Parser;

public class JSONParserTest 
{
	private JSON_Parser JSONParser;	
	private String aShipName;
	
	@Before
	public void setup() throws FileNotFoundException, IOException, ParseException
	{
		aShipName = "Iowa";
		JSONParser = new JSON_Parser(aShipName);
		JSONParser.setTurretStats("406 mm/50 Mk7");
		JSONParser.setHullStats("Iowa (C)");
		JSONParser.setEngineStats("Propulsion: 212,000 hp");
		JSONParser.setRadarStats("Mk9 mod. 2");

	}

	@Test
	public void testNotNull()
	{
		assertNotNull(JSONParser);
	}

}
