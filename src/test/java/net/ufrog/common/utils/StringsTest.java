package net.ufrog.common.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ultrafrog
 * @version 1.0, 2013-3-19
 * @since 1.0
 */
public class StringsTest {

	/**
	 * 
	 */
	@Test
	public void testEmpty() {
		Assert.assertTrue(Strings.empty(""));
		Assert.assertTrue(Strings.empty(null));
		Assert.assertFalse(Strings.empty(" "));
		Assert.assertFalse(Strings.empty("teststring"));
		Assert.assertEquals("default", Strings.empty("", "default"));
		Assert.assertEquals("teststring", Strings.empty("teststring", "default"));
	}
}