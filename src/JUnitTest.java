import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import jodd.util.StringTemplateParser;
import jodd.util.StringTemplateParser.MacroResolver;

public class JUnitTest {
	String template;
	Map<String, String> map;
	StringTemplateParser stp;
	MacroResolver mr;

	@Before
	public void setUp() throws Exception {
		stp = new StringTemplateParser();
		map = new HashMap<String, String>();
		map.put("foo", "Jodd");
		mr = StringTemplateParser.createMapMacroResolver(map);
	}

	@Test(expected = NullPointerException.class)
	public void testTemplateNull() {
		stp.parse(null, mr);
	}

	@Test
	public void testTemplateEmpty() {
		String result = stp.parse("", mr);
		assertEquals(result, "");
	}

	@Test
	public void testTemplateWithoutMacro() {
		String result = stp.parse("Hello", mr);
		assertEquals(result, "Hello");
	}

	@Test
	public void testTemplateWithOneMacro() {
		String result = stp.parse("Hello ${foo}", mr);
		assertEquals(result, "Hello Jodd");
	}

	@Test
	public void testTemplateWithIncorrectSyntax() {
		String result = stp.parse("Hello ${foo}, %{foo}", mr);
		assertEquals(result, "Hello Jodd, %{foo}");
	}
	
	/*
	@Test
	public void testMapOfObject(){
		Map<Object,Object> mapObject = new HashMap<Object,Object>();
		mapObject.put("foo", "Jodd");
		mr = StringTemplateParser.createMapMacroResolver(mapObject);
		String result = stp.parse("Hello ${foo}", mr);
		assertEquals(result, "Hello Jodd");
	}*/

	@Test
	public void testTemplateKeyNotInMap(){
	    template = "Hello ${4}";
	    
	    StringTemplateParser stp = new StringTemplateParser();
	    MacroResolver mr = stp.createMapMacroResolver(map);
	    String result = stp.parse(template, mr);
	    assertEquals(result, "Hello ");
	}
	
	@Test
	public void testTemplateCorrect(){
		template = "Hello ${foo} ${test}, it's always sunny on ${dayName}";
	    map.put("test", "James");
	    map.put("dayName", "Monday");
	    
	    StringTemplateParser stp = new StringTemplateParser();
	    MacroResolver mr = stp.createMapMacroResolver(map);
	    String result = stp.parse(template, mr);
	    assertEquals(result, "Hello Jodd James, it's always sunny on Monday");
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testNullObject(){
	    template = "Hello ${foo}";
	    StringTemplateParser stp = new StringTemplateParser();
	    MacroResolver mr = stp.createMapMacroResolver(null);
	    String result = stp.parse(template, mr);
	    System.out.println(result);
	}
	
	@Test
	public void testWithEmptyMap(){
	    template = "Hello ${foo}";
	    Map<String, String> map = new HashMap<String, String>();
	    StringTemplateParser stp = new StringTemplateParser();
	    MacroResolver mr = stp.createMapMacroResolver(map);
	    String result = stp.parse(template, mr);
	    assertEquals(result, "Hello ");
	}
	

}
