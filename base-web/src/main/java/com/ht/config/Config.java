package com.ht.config;

import java.io.Serializable;

/**
 * 系统配置
 * @author swt
 */
public class Config implements Serializable{
	private static final long serialVersionUID 	= 1L;
	private String defaultPassword 				= "123456";									// 默认密码
	private String systemId						= "";										// 系统ID 唯一标识
	private String systemName					= "";										// 系统名称
	private String systemVersion 				= "1.0.0.0";								// 系统版本
	private int pageSize						= 10;										// 每页记录数
	private long cacheTimeout					= 60;										// 缓存超时时间，单位秒

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCacheTimeout() {
		return cacheTimeout;
	}

	public void setCacheTimeout(long cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}
}
