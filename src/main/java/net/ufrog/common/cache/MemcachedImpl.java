package net.ufrog.common.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import net.ufrog.common.Logger;
import net.ufrog.common.Property;
import net.ufrog.common.utils.Strings;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * Memcached 实现
 *
 * @author ultrafrog
 * @version 1.0, 2013-4-15
 * @since 1.0
 */
public class MemcachedImpl implements CacheImpl {

	protected static MemcachedImpl singleton;
	
	protected MemCachedClient client;
	protected String[] servers;
	protected Integer[] weights;
	
	/**
	 * 构造函数
	 */
	protected MemcachedImpl() {
		// 初始化
		client = new MemCachedClient();
		servers = getServers();
		weights = getWeights();
		SockIOPool pool = SockIOPool.getInstance();
		
		// 设置属性
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setMaxIdle(Property.getInteger("cache.max.idle", 1800000));
		pool.setMaxConn(Property.getInteger("cache.max.conn", 250));
		pool.setMinConn(Property.getInteger("cache.min.conn", 5));
		pool.setInitConn(Property.getInteger("cache.init.conn", 5));
		pool.setMaintSleep(Property.getInteger("cache.maint.sleep", 30000));
		pool.setMaxBusyTime(Property.getInteger("cache.max.busy.time", 60000));
		pool.initialize();
		Logger.info("initialize memcached servers: %s", Arrays.asList(servers));
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#add(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void add(String key, Object value, int expiration) {
		safeAdd(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#safeAdd(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeAdd(String key, Object value, int expiration) {
		return client.add(key, value, new Date(expiration * 1000));
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#set(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void set(String key, Object value, int expiration) {
		safeSet(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#safeSet(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeSet(String key, Object value, int expiration) {
		return client.set(key, value, new Date(expiration * 1000));
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#replace(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void replace(String key, Object value, int expiration) {
		safeReplace(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#safeReplace(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeReplace(String key, Object value, int expiration) {
		return client.replace(key, value, new Date(expiration * 1000));
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return client.get(key);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#get(java.lang.String[])
	 */
	@Override
	public Map<String, Object> get(String... keys) {
		return client.getMulti(keys);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#incr(java.lang.String, int)
	 */
	@Override
	public long incr(String key, int by) {
		return client.incr(key, by);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#decr(java.lang.String, int)
	 */
	@Override
	public long decr(String key, int by) {
		return client.decr(key, by);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#delete(java.lang.String)
	 */
	@Override
	public void delete(String key) {
		safeDelete(key);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#safeDelete(java.lang.String)
	 */
	@Override
	public boolean safeDelete(String key) {
		return client.delete(key);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#clear()
	 */
	@Override
	public void clear() {
		client.flushAll();
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.cache.CacheImpl#stop()
	 */
	@Override
	public void stop() {
		Logger.warn("could not stop client!");
	}
	
	/**
	 * 读取服务器
	 * 
	 * @return
	 */
	protected String[] getServers() {
		ArrayList<String> servers = new ArrayList<String>();
		if (!Strings.empty(Property.getValue("cache.server"))) {
			servers.add(Property.getValue("cache.server"));
		} else if (!Strings.empty(Property.getValue("cache.1.server"))) {
			int i = 1;
			while (!Strings.empty(Property.getValue("cache." + i + ".server"))) {
				servers.add(Property.getValue("cache." + i + ".server"));
				i++;
			}
		}
		return servers.toArray(new String[] {});
	}
	
	/**
	 * 读取权重
	 * 
	 * @return
	 */
	protected Integer[] getWeights() {
		ArrayList<Integer> weights = new ArrayList<Integer>();
		if (!Strings.empty(Property.getValue("cache.weight"))) {
			weights.add(Property.getInteger("cache.weight", 1));
		} else if (!Strings.empty(Property.getValue("cache.1.weight"))) {
			int i = 1;
			while (!Strings.empty(Property.getValue("cache." + i + ".weight"))) {
				weights.add(Property.getInteger("cache." + i + ".weight", 1));
				i++;
			}
		}
		return weights.toArray(new Integer[] {});
	}
	
	/**
	 * 读取实例
	 * 
	 * @return
	 */
	public static MemcachedImpl getInstance() {
		if (singleton == null) {
			singleton = new MemcachedImpl();
		}
		return singleton;
	}
}
