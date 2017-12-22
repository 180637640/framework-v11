package com.ht.account.bean;


import com.ht.bean.base.BaseBean;

/**
 * @author swt
 */
public class AccountBean extends BaseBean implements java.io.Serializable {

	private String loginName;	// 登录名
	private String password;	// 密码
	private String name;		// 姓名
	private String mobile;		// 手机
	private Integer status;		// 状态	 1：正常 2：被锁定
	private String description;	// 备注

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
