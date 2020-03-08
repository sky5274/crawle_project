package com.sky.pub.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * redis基础接口实现封装，添加redis方法的日志信息 
 * @author 王帆
 * @param <V>
 * 2017年10月10日上午8:58:20
 */
public abstract class BaseRedisServiceImpl<V> {
	/**
	 *  提供redis存储对的class
	 * @return
	 * @author  王帆
	 * 2017年10月10日上午9:16:33
	 */
	protected abstract Class<V> getExtendClass();
	
	public String getPrefixKey() {
		return this.getExtendClass().getSimpleName();
	}
	public String getPrefixKey(String key) {
		if(StringUtils.isEmpty(key)) {
			return getPrefixKey();
		}
		return key=getPrefixKey()+"_"+key;
	}
	private Logger log=Logger.getLogger(BaseRedisServiceImpl.class);

	@Resource(name="redisTemplate")  
	private RedisTemplate<String,V> redisTemplate;  
	private boolean flag=false;
	
	private  RedisTemplate<String,V> getRedisTemplate(){
		if(!flag) {
			flag=true;
			StringRedisSerializer redisSerializer = new StringRedisSerializer();
			redisTemplate.setKeySerializer(redisSerializer);
			redisTemplate.setHashKeySerializer(redisSerializer);
		}
		return redisTemplate;
	}
	
	/**
	 *  获取 RedisSerializer
	 * @return
	 * @author  王帆
	 */
	protected RedisSerializer<String> getRedisSerializer() {  
		return getRedisTemplate().getStringSerializer();  
	}  

	/**
	 * 将对象转换成JSON格式存储到redies中
	 * @param obj
	 * @return
	 * @author  王帆
	 */
	protected String getJson(V obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * 转化json成对象
	 * @param con
	 * @return
	 * @author  王帆
	 */
	@SuppressWarnings("unused")
	private V getObj(String con) {
		return JSON.parseObject(con, this.getExtendClass());
	}

	/**
	 * 获取redis-操作字符串 方法
	 * @return
	 * @author  王帆
	 */
	private ValueOperations<String,V> getValueOptions() {
		return this.getRedisTemplate().opsForValue();
	}

	/**
	 * 获取hash的操作方法
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午6:43:23
	 */
	private HashOperations<String, String, String> getHashOptions() {
		return this.getRedisTemplate().opsForHash();
	}
	
	/**
	 * 获取list的操作对象
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午8:36:06
	 */
	private ListOperations<String, V> getListOptions() {
		return this.getRedisTemplate().opsForList();
	}
	
	/**
	 * 获取集合的操作对象,无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午8:38:21
	 */
	private SetOperations<String, V> getCollectionOptions() {
		return this.getRedisTemplate().opsForSet();
	}
	
	/**
	 * 获取经纬度操作对象
	 * @return
	 * @author  王帆
	 * 2017年11月10日下午5:20:09
	 */
	private GeoOperations<String, V> getGeoOption() {
		return this.getRedisTemplate().opsForGeo();
	}
	
	/**
	 * 模糊删除key
	 * @param key
	 * @author  王帆
	 * 2017年11月16日下午2:46:44
	 */
	public void delKeyLike(String key) {
		Set<String> keys = this.getRedisTemplate().keys(key);
		for(String k:keys) {
			this.getRedisTemplate().delete(k);
		}
	}
	
	
	/**
	 *  redis发布信息
	 * @param channel
	 * @param message
	 * @author  王帆
	 * 2017年10月6日上午10:13:17
	 */
	protected void sendStringMessage(String channel,String message) {
		this.getRedisTemplate().convertAndSend(channel, message);
	}
	
	/**
	 * 发布对象为消息
	 * @param channel
	 * @param message
	 * @author  王帆
	 * 2017年10月27日上午9:50:46
	 */
	protected void sendObjMessage(String channel,V message) {
		this.getRedisTemplate().convertAndSend(channel, message);
	}
	
	
	/**
	 * 模糊查询redis中的keys
	 * @param con
	 * @return
	 * @author  王帆
	 * 2017年10月21日上午9:51:59
	 */
	public Set<String> doGetKeys(String con) {
		log.debug("===> redis action: keys key="+con);
		return this.getRedisTemplate().keys(con);
	}
	
	/**
	 * 模糊查询redis中的对象参数keys
	 * @param con
	 * @return
	 * @author  王帆
	 * 2017年10月21日上午9:51:59
	 */
	public Set<String> doGetObjKeys(String key) {
		key=getPrefixKey(key);
		log.debug("===> redis action: keys key="+key);
		return this.getRedisTemplate().keys(key);
	}
	
	/**
	 * 根据key获得value
	 * @param con
	 * @return
	 * @author  王帆
	 * 2017年10月21日上午9:52:35
	 */
	protected V doGet(String con) {
		log.debug("===> redis action: get key="+con);
		return this.getValueOptions().get(con);
	}
	
	/**
	 *  删除键
	 * @param key
	 * @author  王帆
	 * 2017年10月1日下午9:45:02
	 */
	public void deleteKey(String key) {
		log.debug("===> redis action: del key="+key);
		this.getRedisTemplate().delete(key);
	}
	/**
	 * 删除键(有类名)
	 * @param key
	 * @author  王帆
	 * 2017年10月1日下午9:45:02
	 */
	protected void doDeleteKey(String key) {
		key=getPrefixKey(key);
		log.debug("===> redis action: del key="+key);
		this.getRedisTemplate().delete(key);
	}
	
	/**
	 * 添加String，设置key与value
	 * @param key
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午4:46:28
	 */
	protected void doStringSet(String key,V value) {
		key=getPrefixKey(key);
		log.debug("===> redis action: set key="+key+", value="+value);
		this.getValueOptions().set(key, value);
	}
	/**
	 * 添加String，设置key与value,timeout:time/s
	 * @param key
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午4:46:28
	 */
	protected void doStringSet(String key,V value,long time) {
		key=getPrefixKey(key);
		log.debug("===> redis action: set key="+key+", value="+value+", time/s="+time);
		this.getValueOptions().set(key, value,time,TimeUnit.MINUTES);
	}
	/**
	 * 添加String，设置key与value,timeout:itme,timeUnit:uinit
	 * @author  王帆
	 * @date 2018年5月24日 下午2:01:11
	 * @param key
	 * @param value
	 * @param time
	 * @param unit
	 */
	protected void doStringSet(String key,V value,long time,TimeUnit unit) {
		key=getPrefixKey(key);
		log.debug("===> redis action: set key="+key+", value="+value+", time="+time+",timeUint="+unit);
		this.getValueOptions().set(key, value,time,unit);
	}
	/**
	 * 添加String，设置键的值，仅当键不存在时
	 * @param key
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午4:46:28
	 */
	protected void setIfAbsent(String key,V value) {
		key=getPrefixKey(key);
		log.debug("===> redis action: setnx key="+key+", value="+value);
		this.getValueOptions().setIfAbsent(key, value);
	}

	/**
	 * 获取String  根据key
	 * @param key
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午4:49:57
	 */
	protected V doStringGet(String key) {
		key=getPrefixKey(key);
		log.debug("===> redis action: get key="+key);
		return this.getValueOptions().get(key);
	}
	

	/**
	 *  使用键和到期时间来设置值
	 * @param key
	 * @param timeout
	 * @param value
	 * @param unit
	 * @author  王帆
	 * 2017年10月1日下午6:49:53
	 */
	protected void doStringExprie(String key,long timeout,V value,TimeUnit unit) {
		key=getPrefixKey(key);
		log.debug("===> redis action: SETEX key="+key+", "+unit+"="+timeout+", value="+value);
		this.getValueOptions().set(key, value,timeout,unit);
	}

	/**
	 * 设置key的过期时间
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午4:50:22
	 */
	protected Boolean doKeyExpire(String key,long timeout,TimeUnit unit) {
		key=getPrefixKey(key);
		log.debug("===> redis action: EXP set key="+key+","+unit+"="+timeout);
		return this.getRedisTemplate().expire(key, timeout, unit);
	}

	/**
	 *  设置散列字段的字符串值
	 * @param key
	 * @param hashKey
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午7:10:31
	 */
	protected void doHashSet(String key, V value) {
		doHashSet(null, key, value);
	}
	protected void doHashSet(String prefix,String key, V value) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HSET key="+prefix+", field="+key+", value ="+value);
		this.getHashOptions().put(prefix, key, JSON.toJSONString(value));
	}
	/**
	 * 仅当字段不存在时，才设置散列字段的值
	 * @param key
	 * @param hashKey
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午7:10:31
	 */
	protected void doHashSetIfAbsent(String key, V value) {
		doHashSetIfAbsent(null, key, value);
	}
	protected void doHashSetIfAbsent(String prefix,String key, V value) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HSET key="+prefix+", field="+key+", value ="+value);
		this.getHashOptions().putIfAbsent(prefix, key, JSON.toJSONString(value));
	}

	/**
	 * 获取所有给field对应的值
	 * @param key
	 * @author  王帆
	 * 2017年10月1日下午7:40:59
	 * @return 
	 */
	protected V doHashGet(String key) {
		return doHashGet(null, key);
	}
	protected V doHashGet(String prefix,String key) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HGET key="+prefix+", field="+key);
		return JSON.parseObject(this.getHashOptions().get(prefix, key),getExtendClass());
	}
	/**
	 * 获取所有给field对应的值
	 * @param key
	 * @author  王帆
	 * 2017年10月1日下午7:40:59
	 * @return 
	 */
	protected long doHashGetSize() {
		return doHashGetSize(null);
	}
	protected long doHashGetSize(String prefix) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HLEN key="+prefix);
		return this.getHashOptions().size(prefix);
	}

	/**
	 * 根据field 删除一个哈希字段
	 * @param key
	 * @author  王帆
	 * 2017年10月1日下午7:42:54
	 */
	protected void doHashDelete(String key) {
		doHashDelete(null, key);
	}
	protected void doHashDelete(String prefix,String key) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HDEL key="+prefix+", field="+key);
		this.getHashOptions().delete(prefix, key);
	}

	/**
	 * 获取根据key的hashmap
	 * @author  王帆
	 * 2017年10月1日下午8:28:06
	 */
	protected void doHashGetAllMap() {
		doHashGetAllMap(null);
	}
	protected void doHashGetAllMap(String prefix) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HGETALL key="+prefix);
		this.getHashOptions().entries(prefix);
	}

	/**
	 * 获取哈希中的所有值
	 * @author  王帆
	 * 2017年10月1日下午8:32:08
	 * @return 
	 */
	protected List<V> doHashGetValues() {
		return doHashGetValues(null);
	}
	protected List<V> doHashGetValues(String prefix) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HVALS key="+prefix);
		List<String> list = this.getHashOptions().values(prefix);
		List<V> results=new LinkedList<>();
		for(String s:list) {
			results.add(JSON.parseObject(s,getExtendClass()));
		}
		return results;
	}
	
	/**
	 * hash中对应的keys
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午9:46:40
	 */
	protected Set<String> doHashGetKeys() {
		return doHashGetKeys(null);
	}
	protected Set<String> doHashGetKeys(String prefix) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HKEYS key="+prefix);
		return this.getHashOptions().keys(prefix);
	}
	
	/**
	 * HEXISTS key field
	 * @author  王帆
	 * 2017年10月1日下午8:32:08
	 * @return 
	 */
	protected Boolean doHashIsHasKey(String key) {
		return doHashIsHasKey(null,key);
	}
	protected Boolean doHashIsHasKey(String prefix,String key) {
		prefix=getPrefixKey(prefix);
		log.debug("===> redis action: HEXISTS key ="+prefix+"field="+key);
		return this.getHashOptions().hasKey(prefix,key);
	}
	
	/**
	 * 根据key获取list的长度
	 * @param key
	 * @author  王帆
	 * 2017年10月1日下午8:59:22
	 * @return 
	 */
	protected Long doListGetSize(String key) {
		key=getPrefixKey(key);
		log.debug("===> redis action: LLEN key ="+key);
		return this.getListOptions().size(key);
	}
	
	/**
	 * 获取key对应的list的所有value
	 * @param key
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午9:04:22
	 */
	protected List<V> doListGetAll(String key) {
		key=getPrefixKey(key);
		log.debug("===> redis action: LRANGE key ="+key+",start=0 stop=size");
		return this.getListOptions().range(key, 0, this.getListOptions().size(key));
	}
	
	/**
	 * 获取key对应的list的分页查询
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午9:06:41
	 */
	protected List<V> doListGetLimit(String key,long start,Long end){
		key=getPrefixKey(key);
		log.debug("===> redis action: LRANGE key ="+key+",start="+start+", stop="+end);
		return this.getListOptions().range(key, start,end);
	}
	
	/**
	 * 向key对应的list中从头添加value
	 * @param key
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午9:10:23
	 */
	protected void doListAddTop(String key,V value) {
		key=getPrefixKey(key);
		log.debug("===> redis action: LPUSH key ="+key+",value="+value);
		this.getListOptions().leftPush(key, value);
	}
	
	/**
	 * 将value添加到key对应的list的结尾中
	 * @param key
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午9:12:43
	 */
	protected void doListAddEnd(String key,V value) {
		key=getPrefixKey(key);
		log.debug("===> redis action: RPUSH key ="+key+",value="+value);
		this.getListOptions().rightPush(key, value);
	}
	
	/**
	 * <p>count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。<br>
					count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。<br>
					count = 0 : 移除表中所有与 VALUE 相等的值</p>
	 * @param key
	 * @author  王帆
	 * 2017年10月1日下午9:15:54
	 */
	protected void  doListDelet(String key, long i, V value) {
		key=getPrefixKey(key);
		log.debug("===> redis action: LREM key="+key+",count="+i+",value="+value);
		this.getListOptions().remove(key, i, value);
	}
	
	/**
	 *  获取key对应的list，索引index对应的value
	 * @param key
	 * @param index
	 * @author  王帆
	 * 2017年10月1日下午9:22:26
	 * @return 
	 */
	protected V doListGetByIndex(String key, long index) {
		key=getPrefixKey(key);
		log.debug("===> redis action: LINDEX key="+key+",index="+index);
		return this.getListOptions().index(key, index);
	}
	
	/**
	 * 向集合中添加一个元素
	 * @param key
	 * @param value
	 * @author  王帆
	 * 2017年10月1日下午9:28:17
	 */
	@SuppressWarnings("unchecked")
	protected void doSetAdd(String key,V value) {
		key=getPrefixKey(key);
		log.debug("===> redis action: SADD key="+key+",value="+value);
		this.getCollectionOptions().add(key, value);
	}
	
	/**
	 * 获取key对应的集合的size
	 * @param key
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午9:30:40
	 */
	protected Long doSetGetSize(String key) {
		key=getPrefixKey(key);
		log.debug("===> redis action: SCARD key="+key);
		return this.getCollectionOptions().size(key);
	}
	/**
	 * 获取key对应的集合的所有成员
	 * @param key
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午9:30:40
	 */
	protected Set<V> doSetGetAll(String key) {
		key=getPrefixKey(key);
		log.debug("===> redis action: SMEMBERS key="+key);
		return this.getCollectionOptions().members(key);
	}
	
	/**
	 * 从key对应的集合中删除value
	 * @param key
	 * @param value
	 * @return
	 * @author  王帆
	 * 2017年10月1日下午9:35:45
	 */
	protected Long doSetDelete(String key,V value) {
		key=getPrefixKey(key);
		log.debug("===> redis action: SREM  key="+key);
		return this.getCollectionOptions().remove(key, value);
	}
	
	/**
	 * 添加一个经纬度对应的点
	 * @param key
	 * @param point
	 * @param member
	 * @author  王帆
	 * 2017年11月10日下午5:26:57
	 */
	protected void doGeoAddOne(String key,Point point,V member) {
		log.debug("===> redis action: GEOADD key="+key+" longitude="+point.getX()+" latitude="+point.getY()+" member="+member);
		this.getGeoOption().geoAdd(key, point, member);
	}
	
	/**
	 * 插入经纬度对应点集合
	 * @param key
	 * @author  王帆
	 * 2017年11月10日下午5:31:12
	 */
	protected void doGeoAddIterable(String key,Iterable<GeoLocation<V> > locations) {
		log.debug("===> redis action: GEOADD key="+key+" list:"+locations.toString());
		this.getGeoOption().geoAdd(key, locations);
	}
	
	/**
	 * 根据经纬度及距离米  获取对象
	 * @param key
	 * @param center
	 * @param radius
	 * @return
	 * @author  王帆
	 * 2017年11月12日下午2:06:35
	 */
	protected GeoResults<GeoLocation<V>> doGeoGetByPoint(String key,Point center,double radius) {
		log.debug("===> redis action: GEORADIUS key="+key+" longitude="+center.getX()+" latitude="+center.getY()+" radius="+radius);
		Circle within=new Circle(center, radius);
		return this.getGeoOption().geoRadius(key, within);
	}
	
	/**
	 * 根据member  半径查询
	 * @param key
	 * @param member
	 * @param radius
	 * @return
	 * @author  王帆
	 * 2017年11月12日上午10:03:27
	 */
	protected GeoResults<GeoLocation<V>> doGeoGetDis(String key,V member,double radius) {
		log.debug("===> redis action: GEORADIUSBYMEMBER key="+key+" member="+member.toString()+" radius="+radius);
		return this.getGeoOption().geoRadiusByMember(key, member, radius);
	}
	
	/**
	 *  获取对象的hash值
	 * @param key
	 * @param members
	 * @return
	 * @author  王帆
	 * 2017年11月12日下午2:19:46
	 */
	@SuppressWarnings("unchecked")
	protected List<String> doGeoGetHash(String key,V members) {
		log.debug("===> redis action: GEOHASH key="+key+" MEMBER="+members.toString());
		return this.getGeoOption().geoHash(key, members);
	}
	
	/**
	 *  获取对象的经纬度
	 * @param key
	 * @param members
	 * @return
	 * @author  王帆
	 * 2017年11月12日下午2:19:23
	 */
	@SuppressWarnings("unchecked")
	protected List<Point> doGeoGetPos(String key,V members) {
		log.debug("===> redis action: GEOPOS key="+key+" member="+members.toString());
		return this.getGeoOption().geoPos(key, members);
	}
	
	/**
	 * 删除member
	 * @param key
	 * @param members
	 * @author  王帆
	 * 2017年11月12日上午10:27:29
	 */
	@SuppressWarnings("unchecked")
	protected void doGeoRemove(String key,V members) {
		log.debug("===> redis action: GEORemove key="+key+" member="+members.toString());
		this.getGeoOption().geoRemove(key, members);
	}
	
	
}
