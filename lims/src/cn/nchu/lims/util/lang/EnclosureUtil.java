package cn.nchu.lims.util.lang;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import cn.nchu.lims.util.Constant;

public class EnclosureUtil {

	/**
	 * �����ļ�������·����ɾ���ļ�
	 * @param name : String �ļ���
	 * @param request : HttpServletRequest ���ڻ�ȡ����WEN-INF����·��
	 * @param path : Sting �ļ�����WEN-INF�µľ����ļ���
	 */
	public static void clearFileOnDisk(
			String name, HttpServletRequest request, String path) {
		
		File file = new File(  // ����·��ɾ���ļ�
				request
				.getServletContext()
				.getRealPath(path + name)); 
		
		if(file.isFile() && file.exists()) {  // �ж��ļ��治���ڡ��ǲ����ļ�
	        file.delete();  // ����ɾ��
	    } 
	}
	
	/**
	 * �����ļ�����
	 * @param fileName
	 * @return
	 */
	public static String getType(String fileName) {
		String[] strs = fileName.split("\\.");
		return strs[strs.length-1];
	}

	/**
	 * ����ģ����ŵõ�Enclosure�������·����Ϣ
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
	 * ����ģ����ѡ��Enclosure�ı���
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
