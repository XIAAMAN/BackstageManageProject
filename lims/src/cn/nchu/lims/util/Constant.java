package cn.nchu.lims.util;

public class Constant {

	/**
	 * ���ݿ�����̬���������淶
	 */
	public static final String SUFFIX_TABLE = "_TABLE";
	public static final String SUFFIX_ENCLOSURE_TABLE = "ENCLOSURE_TABLE"; 
	/**
	 * �ļ�·����̬���������淶
	 */
	public static final String PREFIX_URL = "UPLOAD_"; 
	public static final String SUFFIX_URL = "ENCLOSURE_URL";
	
	// ���ݿ��������
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
	
	// �ļ�·������
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
		

	// ��ʦְ�Ƴ���
	public static final String[] TEACHER_TITLE = {"����", "������", "��ʦ", "����"};
	// ��ʦ�ʸ���
	public static final String[] TEACHER_DIRECTOR = {"����", "˶��", "��"};
	// ���⼶����
	public static final String[] PROJECT_LEVEL = {"���Ҽ�", "ʡ����", "������", "У��", "����"};
	// ר�����ͳ���
	public static final String[] PATENT_TYPE = {"����ר��", "���ר��", "ʵ������", "����Ȩ"};
	// ר��״̬����
	public static final String[] PATENT_STATUS = {"������", "����Ȩ"};
	// �������ͳ���
	public static final String[] PAPER_TYPE = {"JA", "CA", "JG"};
	// �����������ͳ���
	public static final String[] PAPER_INDEX_TYPE = {"SCI", "EI", "CSCI", "ISTP", "CPCI"};
	// ѧ�����ͳ���
	public static final String[] MDSTUDENT_TYPE = {"˶ʿ", "��ʿ"};
	// �񽱵ȼ�
	public static final String[] MEDAL_GRADE = {"�صȽ�", "һ�Ƚ�", "���Ƚ�", "���Ƚ�", "��������"};
	// ������
	public static final String[] MEDAL_TYPE = {"��ѧ�ɹ���", "���гɹ���", "��������"};
	// ���ݿ��ҳ-ÿҳ��¼��
	public static final int PAGE_DEFAULT_SIZE = 10;
	// ���岻��Ҫ���ص�����
	public static final String[] IGNORE_URI = {"/loginForm", "/login","/404.html"};
	// ��¼����
	public static final String LOGIN = "loginForm";
	// �û���session����
	public static final String USER_SESSION = "user_session";
	// �˵���session����
	public static final String MENU_SESSION = "menu_session";
	
}
