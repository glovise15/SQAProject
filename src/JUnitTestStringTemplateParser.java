import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import jodd.util.StringTemplateParser;
import jodd.util.StringTemplateParser.MacroResolver;

public class JUnitTestStringTemplateParser {

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
	 * @Test public void testMapOfObject(){ Map<Object,Object> mapObject = new
	 * HashMap<Object,Object>(); mapObject.put("foo", "Jodd"); mr =
	 * StringTemplateParser.createMapMacroResolver(mapObject); String result =
	 * stp.parse("Hello ${foo}", mr); assertEquals(result, "Hello Jodd"); }
	 */

	@Test
	public void testTemplateKeyNotInMap() {
		template = "Hello ${4}";

		StringTemplateParser stp = new StringTemplateParser();
		MacroResolver mr = stp.createMapMacroResolver(map);
		String result = stp.parse(template, mr);
		assertEquals(result, "Hello ");
	}

	@Test
	public void testTemplateCorrect() {
		template = "Hello ${foo} ${test}, it's always sunny on ${dayName}";
		map.put("test", "James");
		map.put("dayName", "Monday");

		StringTemplateParser stp = new StringTemplateParser();
		MacroResolver mr = stp.createMapMacroResolver(map);
		String result = stp.parse(template, mr);
		assertEquals(result, "Hello Jodd James, it's always sunny on Monday");
	}

	@Test(expected = NullPointerException.class)
	public void testNullObject() {
		template = "Hello ${foo}";
		StringTemplateParser stp = new StringTemplateParser();
		MacroResolver mr = stp.createMapMacroResolver(null);
		stp.parse(template, mr);
	}

	@Test
	public void testWithEmptyMap() {
		template = "Hello ${foo}";
		Map<String, String> map = new HashMap<String, String>();
		StringTemplateParser stp = new StringTemplateParser();
		MacroResolver mr = stp.createMapMacroResolver(map);
		String result = stp.parse(template, mr);
		assertEquals(result, "Hello ");
	}
	
	
	//Additionnal tests coverage
	@Test
	public void testMacroPrefixNull(){
		stp.setMacroPrefix(null);
		String result = stp.parse("Hello ${foo}", mr);
		assertEquals(result, "Hello Jodd");
	}
	
	@Test
	public void testNewMacro(){
		//System.out.println(stp.getMacroStart() + " " + stp.getMacroPrefix() + " " + stp.getMacroEnd());
		stp.setMacroStart("%[");
		stp.setMacroPrefix("%");
		stp.setMacroEnd("]");
		String result = stp.parse("Hello %[foo]", mr);
		assertEquals(result, "Hello Jodd");
	}
	
	@Test
	public void testResolvesEscapeFalse(){
		stp.setResolveEscapes(false);
		String result = stp.parse("Hello \\${foo}", mr);
		assertEquals(result, "Hello \\${foo}");
	}
	
	@Test
	public void testEscape(){
		String result = stp.parse("Hello \\${foo}", mr);
		assertEquals(result,"Hello ${foo}");
	}
	
	@Test
	public void testNotStrictFormat(){
		String result = stp.parse("Hello $foo", mr);
		assertEquals(result,"Hello Jodd");
	}
		
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMacroEnd(){
		String result = stp.parse("Hello ${foo)", mr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNoMacroEnd(){
		String result = stp.parse("Hello ${foo", mr);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalKey(){
		String result = stp.parse("Hello ${${foo}", mr);
		assertEquals(result,"Hello Jodd");
	}
	
	@Test
	public void testInvalidMacroStart(){
		String result = stp.parse("Hello $(foo}", mr);
		assertEquals(result,"Hello $(foo}");
	}
	
	@Test
	public void testMacroIncludedInMacro(){
		String result = stp.parse("Hello ${foo${${foo}}}", mr);
		assertEquals(result,"Hello Jodd");
	}
	
	@Test
	public void testMacroIncludedInMacroWithSpace(){
		String result = stp.parse("Hello ${foo ${${foo}}}", mr);
		assertEquals(result,"Hello ");
	}
	
	@Test
	public void testMissingKey(){
		stp.setMissingKeyReplacement("test");
		String result = stp.parse("Hello ${notKeyInMap}", mr);
		assertEquals(result,"Hello test");
	}
	
	@Test
	public void testReplaceMissingKeyFalse(){
		stp.setReplaceMissingKey(false);
		String result = stp.parse("Hello ${notKeyInMap}", mr);
		assertEquals(result,"Hello ${notKeyInMap}");
	}
	
	@Test
	public void testParseValue(){
		stp.setParseValues(true);
		String result = stp.parse("Hello ${notKeyInMap}", mr);
		assertEquals(result,"Hello ");
	}
}
