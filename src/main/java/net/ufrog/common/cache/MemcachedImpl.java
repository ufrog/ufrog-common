package net.ufrog.common.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Strings;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * Memcached 实现
 *
 * @author ultrafrog
 * @version 1.0, 2013-04-15
 * @since 1.0
 */
public class MemcachedImpl implements Cache {

	private static final String CONF_MAX_IDLE		= App.config("cache.max.idle", "1800000");
	private static final String CONF_MAX_CONN		= App.config("cache.max.conn", "250");
	private static final String CONF_MIN_CONN		= App.config("cache.min.conn", "5");
	private static final String CONF_INIT_CONN		= App.config("cache.init.conn", "5");
	private static final String CONF_MAINT_SLEEP	= App.config("cache.maint.sleep", "30000");
	private static final String CONF_MAX_BUSY_TIME	= App.config("cache.max.busy.time", "60000");
	
	private static final String CONF_SERVER			= "app.server";
	private static final String CONF_SERVER_PREFIX	= "app.server.";
	private static final String CONF_WEIGHT			= "app.weight";
	private static final String CONF_WEIGHT_PERFIX	= "app.weight.";
	
	private static MemcachedImpl singleton;
	
	private MemCachedClient client;
	private String[] servers;
	private Integer[] weights;
	
	/** 构造函数 */
	protected MemcachedImpl() {
		client = new MemCachedClient();
		servers = getServers();
		weights = getWeights();
		SockIOPool pool = SockIOPool.getInstance();
		
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setMaxIdle(Integer.valueOf(CONF_MAX_IDLE));
		pool.setMaxConn(Integer.valueOf(CONF_MAX_CONN));
		pool.setMinConn(Integer.valueOf(CONF_MIN_CONN));
		pool.setInitConn(Integer.valueOf(CONF_INIT_CONN));
		pool.setMaintSleep(Integer.valueOf(CONF_MAINT_SLEEP));
		pool.setMaxBusyTime(Integer.valueOf(CONF_MAX_BUSY_TIME));
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
		if (!Strings.empty(App.config(CONF_SERVER))) {
			servers.add(App.config(CONF_SERVER));
		} else {
			for (int i = 1; !Strings.empty(App.config(CONF_SERVER_PREFIX + i)); i++) {
				servers.add(App.config(CONF_SERVER_PREFIX + i));
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
		if (!Strings.empty(App.config(CONF_WEIGHT))) {
			weights.add(Integer.valueOf(App.config(CONF_WEIGHT)));
		} else {
			for (int i = 1; !Strings.empty(App.config(CONF_WEIGHT_PERFIX + i)); i++) {
				weights.add(Integer.valueOf(App.config(CONF_WEIGHT_PERFIX + i)));
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
		if (singleton == null) singleton = new MemcachedImpl();
		return singleton;
	}
}
