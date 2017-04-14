import java.util.HashMap;
import java.util.Map;

import jodd.util.StringTemplateParser;
import jodd.util.StringTemplateParser.MacroResolver;

public class Test {
	public static void main(String[] args) {
	    String template = "Hello ${foo}. Today is ${dayName}.";
	    // prepare data
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("foo", "Jodd");
	    map.put("dayName", "Sunday");
	    //test
	    // parse
	    StringTemplateParser stp = new StringTemplateParser();
	    MacroResolver mr = stp.createMapMacroResolver(map);
	    String result = stp.parse(template, mr);

	    System.out.println(result);
	}
}
