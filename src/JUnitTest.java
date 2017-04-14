import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import jodd.util.StringTemplateParser;

public class JUnitTest {
	String template;
	Map map;
	StringTemplateParser stp;

	@Before
	public void setUp() throws Exception {
		stp = new StringTemplateParser();
		map = new HashMap();
	}

	@Test
	public void testTemplateNull() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testTemplateEmpty() {
		fail("Not yet implemented");
	}

}
