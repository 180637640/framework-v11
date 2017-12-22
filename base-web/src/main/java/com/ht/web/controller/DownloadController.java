package com.ht.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 下载相关服务
 * @author swt
 */
@RestController
@RequestMapping("download")
public class DownloadController extends BaseController {
	
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param sessionID		登录成功后分配的Key
	 * @param filePath		文件物理路径
	 * @param fileRealName	文件真实名字
	 * @return
	 */
	@RequestMapping("file")
	public ModelAndView file(HttpServletRequest request, HttpServletResponse response, String sessionID, String filePath, String fileRealName) {
		BufferedInputStream bis		= null;
		BufferedOutputStream bos	= null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			
			// 如果文件不存在
			if(StringUtils.isBlank(filePath) || !(new File(filePath)).exists()) {
				PrintWriter out = response.getWriter();
				out.println("文件【" + filePath + "】不存在");
				out.flush();
				out.close();
				return null;
			}
			
			// 获取文件名
			File file = new File(filePath);
			if(StringUtils.isBlank(fileRealName)) {
				fileRealName = file.getName();
			}
			long fileLength = file.length();
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileRealName.getBytes("gb2312"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			
			bis = new BufferedInputStream(new FileInputStream(filePath));
			bos = new BufferedOutputStream(response.getOutputStream()); 
			
			byte[] buff = new byte[1024 * 100];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch(Exception e) {
			getLog().error(e.getMessage());
		} finally {
			try {
				if(null != bis) {
					bis.close();
				}
				if(null != bos) {
					bos.close();
				}
			} catch (IOException e) {
				getLog().error(e.getMessage());
			}
		}
		return null;
	}
}