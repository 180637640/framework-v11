package com.ht.web.controller;

import com.ht.bean.base.SerializeObject;
import com.ht.bean.type.ResultType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 错误提示相关页面
 * @author swt
 */
@RestController
@RequestMapping("error")
public class ErrorController extends BaseController {

	@RequestMapping("404")
    public ModelAndView error404(HttpServletRequest request, ModelMap model, String message) {
		if(StringUtils.isNotBlank(message)) {
			try {
				model.put("message", new SerializeObject(ResultType.ERROR, URLDecoder.decode(message,"UTF-8")));
			} catch (UnsupportedEncodingException e) {
				this.getLog().error(e.getMessage());
			}
		}
		return new ModelAndView("error/404");
	}

	@RequestMapping("503")
	public ModelAndView error503(HttpServletRequest request, ModelMap model, String message) {
		if(StringUtils.isNotBlank(message)) {
			try {
				model.put("message", new SerializeObject(ResultType.ERROR, URLDecoder.decode(message,"UTF-8")));
			} catch (UnsupportedEncodingException e) {
				this.getLog().error(e.getMessage());
			}
		}
		return new ModelAndView("error/503");
	}

	@RequestMapping("500")
	public ModelAndView error500(HttpServletRequest request, ModelMap model, String message) {
		if(StringUtils.isNotBlank(message)) {
			try {
				model.put("message", new SerializeObject(ResultType.ERROR, URLDecoder.decode(message,"UTF-8")));
			} catch (UnsupportedEncodingException e) {
				this.getLog().error(e.getMessage());
			}
		}
		return new ModelAndView("error/500");
	}
}