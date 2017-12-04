package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
public class AdminValidator {

	/**
	 * 用于admin的全字段校验，
	 * @param admin : Admin 带校验的信息
	 * @return message : String 包含错误信息
	 */
	public static String validator(Admin admin) {
		String message = "";
		if(StringUtil.isNullOrEmpty(admin.getUserName())) {
			message += "账号错误:账号不可为空;";
		}
		if(StringUtil.isNullOrEmpty(admin.getNickName())) {
			message += "用户姓名错误:用户姓名不可为空;";
		}
		if(!StringUtil.inLength(6, admin.getPassword(), 20)) {
			message += "密码错误:密码由6-20个字母或数字组成;";
		}	
		if(admin.getInstituteId() == 0) {
			message += "研究所编号错误:不存在此研究所;";
		}
		if(!IntegerUtil.isMobilePhone(admin.getPhone())) {
			message += "手机号错误:手机号格式不不符;";
		}
		if(!StringUtil.isEmail(admin.getEmail())) {
			message += "电子邮箱错误:电子邮箱格式不符;";
		}
		if(admin.getState() != 0 && admin.getState() != 1) {
			message += "状态错误:用户状态异常;";
		}
		
		return message;
	}
	
	/**
	 * 用于update时信息的校验
	 * @param admin : Admin 带校验的信息
	 * @return message : String 包含错误信息
	 */
	public static String upateValidator(Admin admin) {
		String message = "";
		if(!IntegerUtil.isMobilePhoneOrNull(admin.getPhone())) {
			message += "手机号错误:手机号格式不不符;";
		}
		if(!StringUtil.isEmailOrNull(admin.getEmail())) {
			message += "手机号错误:手机号格式不不符;";
		}
		if(admin.getState() !=0 && admin.getState() != 1) {
			message += "状态错误:用户状态码（0 或 1）;";
		}
		return message;
	}
}
