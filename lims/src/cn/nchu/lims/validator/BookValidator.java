package cn.nchu.lims.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Book;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

@Component
public class BookValidator {

	@Autowired
	private TeacherDao teacherDao;
	
	public String validator(Book book) {
		String message = "";
		if(StringUtil.isNullOrEmpty(book.getName())) {
			message += "教材名称错误:教材名称不可为空;";
		}
		if(teacherDao.get(book.getTeacher()) == null) {  // 检查teacherId 是否存在
			message += "教师编号错误:获奖教师不存在;";
		}
		if(StringUtil.isNullOrEmpty(book.getPublisher())) {
			message += "出版社错误:出版社不可为空;";
		}
		if(book.getPublishTime() == null) {
			message += "出版时间错误:出版时间不可为空;";
		}

		if(!("0".equals(book.getSponsor()) || "1".equals(book.getSponsor()))) {
			message += "第一单位错误:参数不可为空（为0或1）;";
		}
		return message;
	}
	
	/**
	 * 更新Book时的参数校验
	 * @param book ： Book
	 * @return message : String
	 */
	public String updateValidator(Book book) {
		String message = "";

		if(!IntegerUtil.isNullOrZero(book.getTeacher().getId())) {
			if(teacherDao.get(book.getTeacher()) == null) {
				message += "负责人编号错误:负责人编号不存在;";
			}
		}
		
		if(!StringUtil.isNullOrEmpty(book.getSponsor()) && (
				!("0".equals(book.getSponsor()) || "1".equals(book.getSponsor())))) {
			message += "第一单位错误:参数不可为空（为0或1）;";
		}
		
		return message;
	}

}
