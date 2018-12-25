package com.crawl.pub.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对集合进行按父节点归类
 * @author 王帆
 *2018年4月2日 下午3:27:00
 */
public class FilterReduce {
	
	/**过滤：
	 * 1：按父节点进行归类
	 * 2：找出所有节点的总的顶级
	 * 3：设置每个节点的子集
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> filter(List<T>list, Filter<T> filter) {
		Map<Object, List<T>> map=new HashMap<>();
		Set<Object> idList=new HashSet<>();
		for(T obj:list) {
			Object pidKey = filter.getPid(obj);
			List<T> mlist = map.get(pidKey);
			if(mlist==null) {
				mlist=new LinkedList();
			}
			idList.add(filter.getId(obj));
			mlist.add(obj);
			map.put(pidKey, mlist);
		}
		Object topKey=idList.toArray()[0];
		for(Object key:map.keySet()) {
			if(!idList.contains(key)) {
				topKey=key;
				break;
			}
		}
		return filterChild(map,topKey,filter);
	}

	/**
	 * 递归设置每个节点的子集
	 *
	 * @author  王帆
	 * @date 2018年4月2日 下午3:29:09
	 * @param map
	 * @param topKey
	 * @param filter
	 * @return
	 */
	private static <T> List<T> filterChild(Map<Object, List<T>> map, Object topKey, Filter<T> filter) {
		List<T> list=map.get(topKey);
		if(list!=null) {
			for(T ch:list) {
				if(ch!=null) {
					filter.setChild(ch,filterChild(map,filter.getId(ch),filter));
				}
			}
		}
		return list;
	}
	
}
