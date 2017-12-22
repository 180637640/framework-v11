package com.ht.web.controller;

import com.ht.config.WebConfig;
import com.ht.login.bean.LoginBean;
import com.ht.login.bean.type.LoginType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;

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



	/**
	 * 权限判断(是否拥有权限) 管理员（LoginType.ADMINISTRATOR） 拥有所有权限
	 * @param session		Session
	 * @param type			0:单一判断  1:多个动作或判断  2:多个动作与判断  3:单个以开头（比如：以“_XTSZ_”开头）判断
	 * @param actionValue	动作[多个动作用逗号(,)隔开]
	 * @return
	 */
	public boolean isCheckPurview(HttpSession session, int type, String actionValue){
		boolean val = false;
		//if(true) {
		if(StringUtils.isBlank(actionValue)) {
			return true;
		}
		try {
			LoginBean loginBean = (LoginBean)session.getAttribute(WebConfig.USER_LOGIN_INFO);
			if(loginBean.getLoginType() == LoginType.ADMINISTRATOR) {
				return true;
			}
			/*List<AuthorizeBean> authList = loginBean.getAuthorizeBeanList();
    		if(null == authList || authList.isEmpty()) {
    			return false;
    		}

	        if(type == 0){
		        for(AuthorizeBean a: authList){
		            if(a.getActionValue().equals(actionValue)){
		                return true;
		            }
		        }
	        }else if(type == 1){
	        	 String[] aAction = actionValue.split(",");
	        	 if(aAction == null) return false;
	        	 for(String str : aAction){
	        		 if(this.isCheckPurview(session, 0, str)){
	        			 return true;
	        		 }
	        	 }
	        	 return false;
	        }else if(type == 2){
	            String[] aAction = actionValue.split(",");
	            if(aAction == null) return false;
	            for(String str: aAction){
	            	if(!this.isCheckPurview(session, 0, str)){
		            	return false;
		            }
	            }
	            return true;
	        }else if(type == 3){
	        	for(AuthorizeBean a: authList){
		            if(a.getActionValue().startsWith(actionValue)){
		                return true;
		            }
		        }
	        }*/
		}catch(Exception e){
			this.getLog().error(e.getMessage());
			return false;
		}
		return val;
	}
}
