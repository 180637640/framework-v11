package com.ht.bean.type;

/**
 * 返回值类型
 * @author swt
 */
public class ResultType {
	/** 正常 */
	public static final int NORMAL 		= 200;
	/** 错误 */
	public static final int ERROR 		= 500;
	/** 异常 */
	public static final int EXCEPTION	= 502;
	/** 未登录 */
	public static final int UNLOGIN		= 503;
	/** 未授权 */
	public static final int UNAUTH		= 401;
	/** 非法请求 */
	public static final int UNLAWFUL	= 403;
	/** 未定义 */
	public static final int UNDEFINED	= -1;
}
