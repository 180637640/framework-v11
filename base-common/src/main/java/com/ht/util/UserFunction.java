package com.ht.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 常用函数
 * @author swt
 */
public class UserFunction {

	/** 日志输出器 */
	private static Log log = LogFactory.getLog(UserFunction.class);
	
	/**
     * 获取默认编码
     * @return	默认编码
     */
    public static Charset getDefaultCharset() {
        return Charset.forName("UTF-8");
    }

	/**
	 * 数字金额转大写
	 * @param v
	 * @return
	 */
	public static String digitUppercase(double v){
		final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
		final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
		final double MAX_VALUE = 9999999999999.99D;

		if (v < 0 || v > MAX_VALUE){
			return "参数非法!";
		}
		long l = Math.round(v * 100);
		if (l == 0){
			return "零元整";
		}
		String strValue = l + "";
		// i用来控制数
		int i = 0;
		// j用来控制单位
		int j = UNIT.length() - strValue.length();
		String rs = "";
		boolean isZero = false;
		for (; i < strValue.length(); i++, j++) {
			char ch = strValue.charAt(i);
			if (ch == '0') {
				isZero = true;
				if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
					rs = rs + UNIT.charAt(j);
					isZero = false;
				}
			} else {
				if (isZero) {
					rs = rs + "零";
					isZero = false;
				}
				rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
			}
		}
		if (!rs.endsWith("分")) {
			rs = rs + "整";
		}
		rs = rs.replaceAll("亿万", "亿");
		return rs;
	}

	/**
     * 获取文件扩展名
     * @param filename      文件名
     * @param defExt        缺省扩展名
     * @return				扩展名
     */
    public static String getExtension(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i >-1) && (i < (filename.length() - 1))) {
                return filename.substring(i + 1);
            }
        }
        return defExt;
    }
    
	
	/**
	 * 获取系统唯一编号,采用UUID编码规则
	 * @return	String
	 */
	public static String getRandomUUID(){
		//return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		// 采用Base58编码
		return UUIDUtils.base58Uuid();
	}
	
	/**
	 * 对字符串处理，遇字符串未NULL自动赋空字串,同时对字串进行trim操作
	 * @param val	传入字符串
	 * @return		处理后的字符串
	 */
	public static String toString(String val) {
		if (StringUtils.isBlank(val)){
			return "";
		}
		return val.trim();
	}
	
	/**
	 * 对字符串处理，遇字符串未NULL自动赋空字串,同时对字串进行trim操作
	 * @param value	传入double
	 * @return		处理后的字符串
	 */
	public static String toString(double value) {
		return Double.toString(value);
	}

	/**
	 * 数字字串转成整型（int），遇NULL或空字串自动赋0，出错返回0
	 * @param val	传入的字符串
	 * @return		处理后的Int
	 */
	public static int toInt(String val) {
		return NumberUtils.toInt(val);
	}

	/**
	 * 字串转成Double，遇NULL或空字串自动赋0，出错返回0
	 * @param str	传入的字符串
	 * @return		传出的double
	 */
	public static double toDouble(String str) {
		return NumberUtils.toDouble(str);
	}

	/**
	 * 整型转为字串
	 * @param val	传入的int
	 * @return		传出的字符串
	 */
	public static String toString(int val) {
		return Integer.toString(val);
	}

	/**
	 * 将字符串转换成大数字BigInteger, 出错返回-1 
	 * @param str
	 * @return BigInteger
	 */
	public static BigInteger toBigInteger(String str) {
		try {
			return NumberUtils.createBigInteger(str);
		} catch (Exception e) {
			log.error("将字符串转换成大数字BigInteger时发生异常:"+e.getMessage());
			return BigInteger.ZERO;
		}
	}

	/**
	 * 字符串转换为整型，出错返回-1 
	 * @param str	传入的字符串 String
	 * @return		传出的Integer
	 */
	public static Integer toInteger(String str) {
		try {
			return NumberUtils.toInt(str);
		} catch (NumberFormatException e) {
			log.error("字符串转换为整型，发生异常:"+e.getMessage());
			return -1;
		}
	}

	/**
	 * 字符串转换为浮点数，出错返回-1 
	 * @param str	传入的字符串 String
	 * @return		传出的float
	 */
	public static float toFloat(String str) {
		try {
			return NumberUtils.toFloat(str);
		} catch (NumberFormatException e) {
			log.error("字符串转换为浮点数，发生异常:"+e.getMessage());
			return -1;
		}
	}

	/**
	 * 浮点数转为字符串
	 * @param f		传入的float
	 * @return		传出的String
	 */
	public static String toString(float f) {
		return Float.toString(f);
	}

	/**
	 * 获取系统当前日期
	 * @return		日期
	 */
	public static LocalDateTime getDateTime(){
		return LocalDateTime.now(ZoneId.of("UTC+08:00"));
	}
	
	/**
	 * 把字符串时间,转换成日期
	 * @param strDate	日期字符串.格式为:2008-04-24
	 * @return			日期
	 */
	public static LocalDateTime getDate(String strDate){
		return getDateTime(strDate);
	}
	
	/**
	 * 把字符串时间,转换成时间
	 * @param val		日期字符串.格式为:yyyy-MM-dd HH:mm:ss
	 * @return			日期
	 */
	public static LocalDateTime getDateTime(String val){
		LocalDateTime localDateTime = null;
		if(StringUtils.isBlank(val)) return null;
		try {
			val = val.replace("/", "-");
			if("yyyy-MM-dd".length() == val.length() && val.contains("-")) {
				val += " 01:01:01";
			} else  if("yyyy-MM-dd HH:mm".length() == val.length()) {
				val += ":00";
			}
			localDateTime = LocalDateTime.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			log.error("把字符串时间,转换成时间发生异常:"+e.getMessage());
		}
		return localDateTime;
	}

	/**
	 * 获取当天是星期几
	 * @return			星期几(0：星期天 1：星期1 .......)
	 */
	public static int getTodayWeek(){
		LocalDateTime localDateTime = getDateTime();
		int week = localDateTime.getDayOfWeek().getValue();
		if(week == 7) {
			week = 0;
		}
		return week;
	}
	
	/**
	 * 格式化指定日期 默认格式 yyyy-MM-dd HH:mm:ss
	 * @param dateTime		传入的日期
	 */
	public static String getFormatDateTime(LocalDateTime dateTime) {
		return getFormatDateTime(null, dateTime);
	}
	
	/**
	 * 格式化指定日期
	 * @param pattern	传入日期格式"yyyy-MM-dd HH:mm:ss"
	 * @param dateTime	传入的日期
	 */
	public static String getFormatDateTime(String pattern, LocalDateTime dateTime) {
		if(dateTime == null) return "";
		
		if(StringUtils.isBlank(pattern)){
			//默认显示的时间格式
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return dateTime.format(DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * 获取出生日期
	 * @param strIDCard	身份证(支持15/18位)
	 * @return			LocalDate
	 */
	public static LocalDate getBirthday(String strIDCard){
		strIDCard = UserFunction.toString(strIDCard);
		if(strIDCard == null || strIDCard.equals("")) return null;
		int intYear = 0;
		int intMonth = 0;
		int intDay = 0;
		
		if(strIDCard.length() == 18){
			//412726198409162870   	18
			intYear 	= UserFunction.toInt(strIDCard.substring(6, 10));
			intMonth 	= UserFunction.toInt(strIDCard.substring(10, 12));
			intDay 		= UserFunction.toInt(strIDCard.substring(12, 14));
		}else if(strIDCard .length() == 15){
			//4127268409162870   	15
			intYear 	= UserFunction.toInt(strIDCard.substring(6, 8));
			intMonth 	= UserFunction.toInt(strIDCard.substring(8, 10));
			intDay 		= UserFunction.toInt(strIDCard.substring(10, 12));
			if(intYear<100) intYear+=1900;
		}else{
			return null;
		}

		return LocalDate.of(intYear, intMonth, intDay);
	}
	
	/**
	 * 获取性别
	 * @param strIDCard	身份证
	 * @return	1:男 2:女 3:保密
	 */
	public static int getGender(String strIDCard){
		int rInt = 3;
		strIDCard = UserFunction.toString(strIDCard);
		if(strIDCard == null || strIDCard.equals("")) return 3;
		String str = "";
		if(strIDCard.length() == 18){
			str = strIDCard.substring(16, 17);
		}else if(strIDCard .length() == 15){
			str = strIDCard.substring(14, 15);
		}else{
			return 3;
		}
		
		if(str.equalsIgnoreCase("X")){
			rInt = 3;
		}else{
			int intStr = UserFunction.toInt(str);
			if((intStr % 2) == 0){
				rInt = 2;
			}else{
				rInt = 1;
			}
		}
		return rInt;
	}

	/**
	 * 获取URL内容
	 * @param uri		网址
	 * @param timeout	超时
	 * @return			网页内容
	 */
	public static String getURLContentByHttpURLConnection(String uri, int timeout) {
		String content = "";
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(timeout);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("content-type", "text/html");
			conn.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			content = br.readLine();
			br.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return content;
	}


	/**
	 * 发送信息(UDP),并接收一个返回包
	 * @param msg		数据
	 * @param ip		IP
	 * @param port		端口
	 * @param timeout	超时 单位毫秒
	 * @return			返回值
	 */
	public static String sendByUDP(String msg, String ip, int port, int timeout) {
		String recVal;
		try{
			if(timeout <= 0) timeout = 1000;
			log.debug("发送【"+ip+":"+port+"】："+msg);
			
			DatagramSocket client = new DatagramSocket();
			byte[] send = msg.getBytes(getDefaultCharset());
			DatagramPacket pack=new DatagramPacket(send, send.length, InetAddress.getByName(ip), port);

			byte[] recs = new byte[102400];
			DatagramPacket recPacket = new DatagramPacket(recs, recs.length);
			client.setSoTimeout(timeout);
			client.send(pack);
			client.receive(recPacket);
			recVal = new String(recs, 0, recPacket.getLength());
			client.close();
			log.debug("接收【"+ip+":"+port+"】："+recVal);
		}catch(Exception e){
			log.error(e.getMessage());
			return "操作失败！"+e.getMessage();
		}
		return recVal;
    }
	
	/**
	 * 发送信息(TCP),并接收一个返回包
	 * @param msg		数据
	 * @param ip		IP
	 * @param port		端口
	 * @param timeout	超时 单位毫秒
	 * @return			返回信息
	 */
	public static String sendByTCP(String msg, String ip, int port, int timeout) {
		String recVal = "";
		try{
			if(timeout <= 0) timeout = 1000;
			log.debug("TCP【"+ip+":"+port+"】："+msg);
			
			Socket client = new Socket(ip, port);
			client.setSoTimeout(timeout);
			
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			
			os.write(msg.getBytes(getDefaultCharset()));
			os.flush();
			
			byte[] recs = new byte[1024000];
			
			int len = is.read(recs);
			recVal = new String(recs, 0, len, getDefaultCharset());
			
			os.close();
			is.close();
			client.close();
			log.debug("接收【"+ip+":"+port+"】："+recVal);
		}catch(Exception e){
			log.error(e.getMessage());
			return "操作失败！"+e.getMessage();
		}
		return recVal;
    }
	
	/**
	 * 发送信息(TCP), 不接收返回包
	 * @param msg		数据
	 * @param ip		IP
	 * @param port		端口
	 * @param timeout	超时 单位毫秒
	 * @return			null 发送成功，否则返回异常信息
	 */
	public static String sendByTCPNoReceive(String msg, String ip, int port, int timeout) {
		try{
			if(timeout <= 0) timeout = 1000;
			log.debug("发送【"+ip+":"+port+"】："+msg);
			
			Socket client = new Socket(ip, port);
			client.setSoTimeout(timeout);
			
			OutputStream os = client.getOutputStream();
			
			os.write(msg.getBytes(getDefaultCharset()));
			os.flush();
			
			os.close();
			client.close();
		}catch(Exception e){
			log.error(e.getMessage());
			return e.getMessage();
		}
		return null;
    }
	
	/**
	 * 拷贝文件
	 * @param sourceFile	源文件
	 * @param targetFile	目标文件
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
        	inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
        	// 新建文件输出流并对它进行缓冲
        	outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

        	// 缓冲数组
        	byte[] b = new byte[1024 * 1024];
        	int len;
        	while ((len = inBuff.read(b)) != -1) {
        		outBuff.write(b, 0, len);
        	}
        	
        	// 刷新此缓冲的输出流
        	outBuff.flush();
        } catch(Exception e){
			log.error(e.getMessage());
		} finally {
        	// 关闭流
        	if (inBuff != null)
        		inBuff.close();
        	if (outBuff != null)
        		outBuff.close();
        }
	}
	
	/**
	 * 获取文件内容
	 * @param filePath	文件路径
	 * @return
	 */
	public static String readFile(String filePath){
		StringBuffer stringBuffer = new StringBuffer(); 
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            String _val = null;
            while ((_val = reader.readLine()) != null) {
            	stringBuffer.append(_val);
            }
            reader.close();
        } catch (IOException e) {
        	log.error(e.getMessage());
        }
        return stringBuffer.toString();
	}
	
	/**
	 * 保存文件内容
	 * @param filePath	文件路径
	 * @param content	文件内容
	 */
	public static void saveFile(String filePath, String content){
		try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
        	log.error(e.getMessage());
        }
	}
	
	/**
	 * 拷贝文件
	 * @param oldPath	源路径
	 * @param newPath	目标路径
	 */
	public static void copyFile(String oldPath, String newPath){
		try {
			 int byteread = 0;
			 File oldfile = new File(oldPath);
			 if (oldfile.exists()) { 
				 //文件存在时                
				 InputStream inStream = new FileInputStream(oldPath); 
				 //读入原文件                
				 FileOutputStream fs = new FileOutputStream(newPath);                
				 byte[] buffer = new byte[1024 * 100];                
				 while ( (byteread = inStream.read(buffer)) != -1) {                    
					 fs.write(buffer, 0, byteread);               
				 }
				 fs.close();
				 inStream.close();            
			 }
        } catch (IOException e) {
        	log.error(e.getMessage());
        }
	}
}
