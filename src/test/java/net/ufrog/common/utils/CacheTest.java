package net.ufrog.common.utils;

import net.ufrog.common.cache.Caches;
import net.ufrog.common.cache.EhCacheImpl;
import net.ufrog.common.cache.MemcachedImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ultrafrog
 * @version 1.0, 2013-3-19
 * @since 1.0
 */
public class CacheTest {

	/**
	 * @throws InterruptedException 
	 */
	@Test
	public void testCache() throws InterruptedException {
		Caches.set("hello", "world", "1s");
		Assert.assertNotNull(Caches.get("hello"));
		Assert.assertNull(Caches.get("world"));
		Assert.assertEquals("world", Caches.get("hello"));
		
		Thread.sleep(2000);
		Assert.assertNull(Caches.get("hello"));
	}
	
	/**
	 * @throws InterruptedException 
	 */
	@Test
	public void testEhCache() throws InterruptedException {
		EhCacheImpl cache = EhCacheImpl.getInstance();
		cache.set("hello", "china", 1);
		Assert.assertEquals("china", Caches.get("hello"));
		
		Thread.sleep(2000);
		Assert.assertNull(cache.get("hello"));
	}
	
	/**
	 * @throws InterruptedException 
	 */
	//@Test
	public void testMemcachedCache() throws InterruptedException {
		MemcachedImpl cache = MemcachedImpl.getInstance();
		cache.set("hello", "world", 1);
		Assert.assertEquals("world", cache.get("hello"));
		
		Thread.sleep(2000);
		Assert.assertNull(cache.get("hello"));
	}
}
