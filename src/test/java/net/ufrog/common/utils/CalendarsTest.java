package net.ufrog.common.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ultrafrog
 * @version 1.0, 2013-5-3
 * @since 1.0
 */
public class CalendarsTest {

	/**
	 * 
	 */
	@Test
	public void testParseDuration() {
		Assert.assertEquals(1, Calendars.parseDuration("1s"));
		Assert.assertEquals((1 * 60), Calendars.parseDuration("1min"));
		Assert.assertEquals((2 * 60), Calendars.parseDuration("2mn"));
		Assert.assertEquals((2 * 60 * 60), Calendars.parseDuration("2h"));
		Assert.assertEquals((1 * 60 * 60 * 24), Calendars.parseDuration("1d"));
	}
}
