package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
public class AdminValidator {

	/**
	 * ����admin��ȫ�ֶ�У�飬
	 * @param admin : Admin ��У�����Ϣ
	 * @return message : String ����������Ϣ
	 */
	public static String validator(Admin admin) {
		String message = "";
		if(StringUtil.isNullOrEmpty(admin.getUserName())) {
			message += "�˺Ŵ���:�˺Ų���Ϊ��;";
		}
		if(StringUtil.isNullOrEmpty(admin.getNickName())) {
			message += "�û���������:�û���������Ϊ��;";
		}
		if(!StringUtil.inLength(6, admin.getPassword(), 20)) {
			message += "�������:������6-20����ĸ���������;";
		}	
		if(admin.getInstituteId() == 0) {
			message += "�о�����Ŵ���:�����ڴ��о���;";
		}
		if(!IntegerUtil.isMobilePhone(admin.getPhone())) {
			message += "�ֻ��Ŵ���:�ֻ��Ÿ�ʽ������;";
		}
		if(!StringUtil.isEmail(admin.getEmail())) {
			message += "�����������:���������ʽ����;";
		}
		if(admin.getState() != 0 && admin.getState() != 1) {
			message += "״̬����:�û�״̬�쳣;";
		}
		
		return message;
	}
	
	/**
	 * ����updateʱ��Ϣ��У��
	 * @param admin : Admin ��У�����Ϣ
	 * @return message : String ����������Ϣ
	 */
	public static String upateValidator(Admin admin) {
		String message = "";
		if(!IntegerUtil.isMobilePhoneOrNull(admin.getPhone())) {
			message += "�ֻ��Ŵ���:�ֻ��Ÿ�ʽ������;";
		}
		if(!StringUtil.isEmailOrNull(admin.getEmail())) {
			message += "�ֻ��Ŵ���:�ֻ��Ÿ�ʽ������;";
		}
		if(admin.getState() !=0 && admin.getState() != 1) {
			message += "״̬����:�û�״̬�루0 �� 1��;";
		}
		return message;
	}
}
