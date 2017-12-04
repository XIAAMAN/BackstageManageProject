package cn.nchu.lims.util.lang;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import cn.nchu.lims.util.Constant;

public class EnclosureUtil {

	/**
	 * 根究文件的物理路径名删除文件
	 * @param name : String 文件名
	 * @param request : HttpServletRequest 用于获取工程WEN-INF所在路径
	 * @param path : Sting 文件所在WEN-INF下的具体文件夹
	 */
	public static void clearFileOnDisk(
			String name, HttpServletRequest request, String path) {
		
		File file = new File(  // 根据路径删除文件
				request
				.getServletContext()
				.getRealPath(path + name)); 
		
		if(file.isFile() && file.exists()) {  // 判断文件存不存在、是不是文件
	        file.delete();  // 是则删除
	    } 
	}
	
	/**
	 * 返回文件类型
	 * @param fileName
	 * @return
	 */
	public static String getType(String fileName) {
		String[] strs = fileName.split("\\.");
		return strs[strs.length-1];
	}

	/**
	 * 根据模块代号得到Enclosure的物理的路径信息
	 * @param module : String
	 * @return String
	 * @throws Exception
	 */
	public static String chooseEnclosurePath(String module) {
		String path = null;
		switch(module) {
			case "project":
				path = Constant.UPLOAD_PROJECTENCLOSURE_URL;
				break;
			case "news":
				path = Constant.UPLOAD_NEWSENCLOSURE_URL;
				break;
			case "notice":
				path = Constant.UPLOAD_NOTICEENCLOSURE_URL;
				break;
			case "patent":
				path = Constant.UPLOAD_PATENTENCLOSURE_URL;
				break;
			case "paper":
				path = Constant.UPLOAD_PAPERENCLOSURE_URL;
				break;
			case "gsmedal":
				path = Constant.UPLOAD_GSMEDALENCLOSURE_URL;
				break;
			case "teacher":
				path = Constant.UPLOAD_TEACHERENCLOSURE_URL;
				break;
			case "mdstudent":
				path = Constant.UPLOAD_MDSTUDENTENCLOSURE_URL;
				break;
			case "medal":
				path = Constant.UPLOAD_MEDALENCLOSURE_URL;
				break;
			case "life":
				path = Constant.UPLOAD_LIFESHOWENCLOSURE_URL;
				break;
		}
		return path;
	}
	
	/**
	 * 根据模块名选择Enclosure的表名
	 * @param module : String
	 * @return String
	 * @throws Exception
	 */
	public static String chooseEnclosureTable(String module) {
		String table = null;
		switch(module) {
			case "project":
				table = Constant.PROJECTENCLOSURE_TABLE;
				break;
			case "news":
				table = Constant.NEWSENCLOSURE_TABLE;
				break;
			case "notice":
				table = Constant.NOTICEENCLOSURE_TABLE;
				break;
			case "patent":
				table = Constant.PATENTENCLOSURE_TABLE;
				break;
			case "paper":
				table = Constant.PAPERENCLOSURE_TABLE;
				break;
			case "gsmedal":
				table = Constant.GSMEDALENCLOSURE_TABLE;
				break;
		}
		return table;
	}
	
}
