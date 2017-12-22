package com.ht.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseController {

	protected static Log log;

	/**
	 * @return the log
	 */
	public final Log getLog() {
		if(null == log) {
			log = LogFactory.getLog(this.getClass());
		}
		return log;
	}

	/**
	 * DEBUG级别日志
	 * @param message
	 */
	public final void debug(Object message) {
		getLog().debug(message);
	}

	/**
	 * INFO级别日志
	 * @param message
	 */
	public final void info(Object message) {
		getLog().info(message);
	}

	/**
	 * WARN级别日志
	 * @param message
	 */
	public final void warn(Object message) {
		getLog().warn(message);
	}

	/**
	 * ERROR级别日志
	 * @param message
	 */
	public final void error(Object message) {
		getLog().error(message);
	}

}
