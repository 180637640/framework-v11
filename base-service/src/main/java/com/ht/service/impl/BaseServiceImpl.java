package com.ht.service.impl;

import com.ht.util.ParameterMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据操作基类实现类
 * @author swt
 */
public class BaseServiceImpl {

	private static Log log;

	/**
	 * @return the log
	 */
	protected final Log getLog() {
		if (null == log) {
			log = LogFactory.getLog(this.getClass());
		}
		return log;
	}

	/**
	 * 获取参数集合
	 * @return	ParameterMap
	 */
	protected ParameterMap getParams() {
		return new ParameterMap();
	}

	/**
	 * 获取参数集合
	 * @param keyword           关键字
	 * @param order			    排序字段
	 * @param sort			    排序方式 desc或asc
	 * @return					ParameterMap
	 */
	protected ParameterMap getParams(String keyword, String order, String sort) {
		return getParams()
				.put("keyword", keyword)
				.put("order", order)
				.put("sort", sort);
	}
}
