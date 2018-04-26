package com.clonegod.unittest.junit;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class IgnoringTest {
	
	@Ignore("Test is ignored as a demonstration")
	@Test
	public void testSame() {
	    assertThat(1, is(1));
	}
	
}
