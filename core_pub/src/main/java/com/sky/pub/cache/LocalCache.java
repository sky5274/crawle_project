package com.sky.pub.cache;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 本地缓存工具（不建议存储量超过50万）
 * 
 * @author Aaron·Li
 * @date 2016年11月29日 下午6:28:52
 */
public class LocalCache<T> implements Serializable {
	private volatile ConcurrentHashMap<String,CacheValue<T>> cache = new ConcurrentHashMap<>();
	private Log log = LogFactory.getLog(LocalCache.class);
	private volatile List<String> removeList = new ArrayList<String>();
	private static final long serialVersionUID = -3273454956563078833L;
	private boolean isRun = false;// 用于防止定时任务叠加执行
	private Timer timer;
	private Timer activetimer;
	private int minActiveTimes=5;
	private byte[] lock = new byte[0];
	/**
	 * 定时器清理间隔（默认60秒钟执行一次）
	 */
	private Long DEFAULT_TASK_DELAY = 60000L;

	/**
	 * 存储有效期,默认存储30分钟
	 */
	private  Long DEFAULT_TIMEOUT = 60000 * 30L;
	/**存储活动-是否活动*/
	private  Long DEFAULT_ACTIVE_DELAY = 60000 * 60L;

	private LocalCache() {
	}

	public void setDefaultTimeout(Long timeout) {
		if (timeout > 0) {
			DEFAULT_TIMEOUT = timeout;
		}
	}
	public void setDefaultClearDelay(Long delay) {
		if (delay >= 500) {
			DEFAULT_TASK_DELAY = delay;
		}
	}
	public void setDefaultClearActiveDelay(Long delay) {
		if (delay >= 500) {
			DEFAULT_ACTIVE_DELAY = delay;
		}
	}
	public Long getDefaultTimeout() {
		return DEFAULT_TIMEOUT;
	}
	public Long getDefaultClearDelay() {
		return DEFAULT_TASK_DELAY;
	}
	public Long DefaultClearActiveDelay() {
		return DEFAULT_ACTIVE_DELAY;
	}

	private void clearCache() {
		if (!isRun) {
			synchronized (lock) {
				try {
					if (removeList.isEmpty() == false) {
						removeList.clear();
					}
					isRun = true;
					long start = System.currentTimeMillis();
					if (!cache.isEmpty()) {
						Iterator<Entry<String, CacheValue<T>>> i = cache.entrySet().iterator();
						while (i.hasNext()) {
							Entry<String, CacheValue<T>> e = i.next();
							if (e.getValue() == null) {
								removeList.add(e.getKey());
							} else {
								if (e.getValue().isTimeout()) {
									removeList.add(e.getKey());
								}
							}
						}
						//根据removeList 删除缓存
						clearCacheByRemove();
					}
					int clearSize = removeList.size();
					removeList.clear();
					log.debug("本次清理：" + clearSize + ",当前剩余：" + cache.size() + ",清理用时：" + (System.currentTimeMillis() - start) + "毫秒");
				} finally {
					isRun = false;
				}
			}

		}
	}
	private void clearActiveCache() {
		if (!isRun) {
			synchronized (lock) {
				try {
					if (removeList.isEmpty() == false) {
						removeList.clear();
					}
					isRun = true;
					long start = System.currentTimeMillis();
					if (!cache.isEmpty()) {
						Iterator<Entry<String, CacheValue<T>>> i = cache.entrySet().iterator();
						while (i.hasNext()) {
							Entry<String, CacheValue<T>> e = i.next();
							if (e.getValue() == null) {
								removeList.add(e.getKey());
							} else {
								if (!e.getValue().isLazy()) {
									removeList.add(e.getKey());
								}else {
									CacheValue<T> v = e.getValue();
									v.setActivetimes(0L);
									cache.put(e.getKey(), v);
								}
							}
						}
						//根据removeList 删除缓存
						clearCacheByRemove();
					}
					int clearSize = removeList.size();
					removeList.clear();
					log.debug("本次清理：" + clearSize + ",当前剩余：" + cache.size() + ",清理用时：" + (System.currentTimeMillis() - start) + "毫秒");
				} finally {
					isRun = false;
				}
			}
			
		}
	}

	/**
	 * 根据removeList 删除缓存
	 * 
	 * @author 王帆
	 * @date 2019年8月17日 下午9:43:13
	 */
	private synchronized void clearCacheByRemove() {
		for (String key : removeList) {
			cache.remove(key);
		}
	}

	public static <T> LocalCache<T> createLocalCache() {
		LocalCache<T> localCache = new LocalCache<T>();
		Timer ntimer = new Timer();
		ntimer.schedule(new TimerTask() {
			@Override
			public void run() {
				localCache.clearCache();
			}
		}, localCache.DEFAULT_TASK_DELAY,0);
		localCache.setTimer(ntimer);

		Timer actTimer = new Timer();
		actTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				localCache.clearActiveCache();
			}
		}, localCache.DEFAULT_ACTIVE_DELAY,30);
		localCache.setActivetimer(actTimer);
		return localCache;
	}

	public boolean containsKey(String key) {
		return get(key) != null;
	}

	public T get(String key) {
		if (key != null) {
			CacheValue<T> obj = null;
			if (cache.containsKey(key)) {
				obj = cache.get(key);
				//更新缓存活跃度
				cache.put(key,obj.addActive());
			}
			if (obj != null) {
				return obj.getValue();
			}
		}
		return null;
	}

	/**
	 * 默认缓存 30分钟
	 */
	public CacheValue<T> put(String key, T value) {
		return put(key, value, DEFAULT_TIMEOUT);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *      单位默认是秒,传入小于0的值则使用默认值(30分钟)，传入0 则永久有效(应用重启后失效,目前没有实现存盘)
	 * @return
	 */
	public CacheValue<T> put(String key, T value, Long timeout) {
		if (key == null) {
			return null;
		}
		CacheValue<T> cvalue = new CacheValue<T>();
		cvalue.setKey(key);
		if (timeout > 0) {
			cvalue.setTimeout(timeout * 1000L);
		} else if (timeout < 0) {
			cvalue.setTimeout(DEFAULT_TIMEOUT);
		} else {
			cvalue.setTimeout(0L);
		}
		cvalue.setValue(value);
		return cache.put(key, cvalue);
	}

	public void remove(String key) {
		cache.remove(key);
	}
	public long size() {
		return cache.size();
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public Timer getActivetimer() {
		return activetimer;
	}
	public void setActivetimer(Timer activetimer) {
		this.activetimer = activetimer;
	}
	public int getMinActiveTimes() {
		return minActiveTimes;
	}
	public void setMinActiveTimes(int minActiveTimes) {
		this.minActiveTimes = minActiveTimes;
	}

	@SuppressWarnings("hiding")
	public static class CacheValue<T> implements Serializable {
		private static final long serialVersionUID = 800800964682488492L;
		private String key;
		private Long createTime;
		private Long timeout;
		private Long activetimes;
		private T value;

		public CacheValue() {
			activetimes=0L;
			createTime = System.currentTimeMillis() - 100;// 将创建时间提前100毫秒，防止定时回收任务因上一次执行时间过长导致回收滞后
		}

		/**
		 * 判断对象是否已经失效
		 * 
		 * @return 返回 true 失效 false 没有失效
		 */
		public boolean isTimeout() {
			boolean ret = true;
			if (value != null) {
				if (timeout == 0) {
					return false;
				} else {
					ret = System.currentTimeMillis() - createTime >= timeout;
					if (ret) {
						value = null;
					}
				}
			}
			return ret;
		}
		
		/**
		 * 是否使用懒惰 ，true 是；false 否
		 * @return
		 * @author 王帆
		 * @date 2019年8月17日 下午9:56:08
		 */
		public boolean isLazy() {
			boolean ret = false;
			if (value != null) {
				if (timeout == 0) {
					ret = activetimes<10;
				}
			}
			return ret;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Long getCreateTime() {
			return createTime;
		}
		public Long getTimeout() {
			return timeout;
		}
		public void setTimeout(Long timeout) {
			this.timeout = timeout;
		}
		public T getValue() {
			isTimeout();
			return value;
		}
		public void setValue(T value) {
			this.value = value;
		}
		public Long getActivetimes() {
			return activetimes;
		}
		public void setActivetimes(Long activetimes) {
			this.activetimes = activetimes;
		}
		public CacheValue<T> addActive() {
			this.activetimes+=1;
			return this;
		}
	}

}