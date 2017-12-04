package cn.nchu.lims.util;

public class Constant {

	/**
	 * 数据库名静态常量命名规范
	 */
	public static final String SUFFIX_TABLE = "_TABLE";
	public static final String SUFFIX_ENCLOSURE_TABLE = "ENCLOSURE_TABLE"; 
	/**
	 * 文件路径静态常量命名规范
	 */
	public static final String PREFIX_URL = "UPLOAD_"; 
	public static final String SUFFIX_URL = "ENCLOSURE_URL";
	
	// 数据库表名常量
	public static final String TEACHER_TABLE = "T_Teacher";
	public static final String PROJECT_TABLE = "T_Project";
	public static final String PROJECTENCLOSURE_TABLE = "T_ProjectEnclosure";
	public static final String ADMIN_TABLE = "T_Admin";
	public static final String MENU_TABLE = "T_Menu";
	public static final String NEWS_TABLE = "F_News";
	public static final String NEWSENCLOSURE_TABLE = "F_NewsEnclosure";
	public static final String NOTICE_TABLE = "F_Notice";
	public static final String NOTICEENCLOSURE_TABLE = "F_NoticeEnclosure";
	public static final String PATENT_TABLE = "T_Patent";
	public static final String PATENTENCLOSURE_TABLE= "T_PatentEnclosure";
	public static final String PAPER_TABLE = "T_Paper";
	public static final String PAPERENCLOSURE_TABLE= "T_PaperEnclosure";
	public static final String MDSTUDENT_TABLE = "T_MDStudent";
	public static final String MEDAL_TABLE = "F_Medal";
	public static final String GSMEDAL_TABLE = "F_GSMedal";
	public static final String GSMEDALENCLOSURE_TABLE = "F_GSMedalEnclosure";
	public static final String BOOK_TABLE = "F_Book";
	public static final String INSTUTE_TABLE = "T_Institute";
	public static final String LIFESHOW_TABLE = "F_LifeShow";
	
	// 文件路径常量
	public static final String UPLOAD_PROJECTENCLOSURE_URL = "/WEB-INF/uploads/project/";
	public static final String UPLOAD_NEWSENCLOSURE_URL = "/WEB-INF/uploads/news/";
	public static final String UPLOAD_NOTICEENCLOSURE_URL = "/WEB-INF/uploads/notice/";
	public static final String UPLOAD_PATENTENCLOSURE_URL = "/WEB-INF/uploads/patent/";
	public static final String UPLOAD_PAPERENCLOSURE_URL = "/WEB-INF/uploads/paper/";
	public static final String UPLOAD_GSMEDALENCLOSURE_URL = "/WEB-INF/uploads/gsmedal/";
	public static final String UPLOAD_TEACHERENCLOSURE_URL = "/WEB-INF/uploads/teacher/";
	public static final String UPLOAD_MDSTUDENTENCLOSURE_URL = "/WEB-INF/uploads/mdstudent/";
	public static final String UPLOAD_MEDALENCLOSURE_URL = "/WEB-INF/uploads/medal/";
	public static final String UPLOAD_LIFESHOWENCLOSURE_URL = "/WEB-INF/uploads/life/";
		

	// 教师职称常量
	public static final String[] TEACHER_TITLE = {"教授", "副教授", "讲师", "助教"};
	// 导师资格常量
	public static final String[] TEACHER_DIRECTOR = {"博导", "硕导", "无"};
	// 课题级别常量
	public static final String[] PROJECT_LEVEL = {"国家级", "省部级", "市厅级", "校级", "横向"};
	// 专利类型常量
	public static final String[] PATENT_TYPE = {"发明专利", "外观专利", "实用新型", "著作权"};
	// 专利状态常量
	public static final String[] PATENT_STATUS = {"已受理", "已授权"};
	// 论文类型常量
	public static final String[] PAPER_TYPE = {"JA", "CA", "JG"};
	// 论文索引类型常量
	public static final String[] PAPER_INDEX_TYPE = {"SCI", "EI", "CSCI", "ISTP", "CPCI"};
	// 学生类型常量
	public static final String[] MDSTUDENT_TYPE = {"硕士", "博士"};
	// 获奖等级
	public static final String[] MEDAL_GRADE = {"特等奖", "一等奖", "二等奖", "三等奖", "其他奖项"};
	// 获奖类型
	public static final String[] MEDAL_TYPE = {"教学成果奖", "科研成果奖", "其他奖项"};
	// 数据库分页-每页记录数
	public static final int PAGE_DEFAULT_SIZE = 10;
	// 定义不需要拦截的请求
	public static final String[] IGNORE_URI = {"/loginForm", "/login","/404.html"};
	// 登录界面
	public static final String LOGIN = "loginForm";
	// 用户的session对象
	public static final String USER_SESSION = "user_session";
	// 菜单的session对象
	public static final String MENU_SESSION = "menu_session";
	
}
