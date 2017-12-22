package com.ht.bean.base;

import com.ht.bean.type.ResultType;

import java.io.Serializable;

/**
 * 返回值
 * @author swt
 */
public class SerializeObject<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int code = ResultType.NORMAL;
	private String msg = "";
	private T data;
	
	public SerializeObject() {
		
	}

	public SerializeObject(int code) {
		super();
		this.code = code;
		this.data = null;
	}

	public SerializeObject(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = (T) msg;
	}

	public SerializeObject(int code, T data) {
		super();
		this.code = code;
		if(data instanceof String) {
			this.msg = (String) data;
		} else {
			this.data = data;
		}
	}

	public SerializeObject(int code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
