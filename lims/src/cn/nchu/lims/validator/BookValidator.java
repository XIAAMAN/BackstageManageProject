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
			message += "�̲����ƴ���:�̲����Ʋ���Ϊ��;";
		}
		if(teacherDao.get(book.getTeacher()) == null) {  // ���teacherId �Ƿ����
			message += "��ʦ��Ŵ���:�񽱽�ʦ������;";
		}
		if(StringUtil.isNullOrEmpty(book.getPublisher())) {
			message += "���������:�����粻��Ϊ��;";
		}
		if(book.getPublishTime() == null) {
			message += "����ʱ�����:����ʱ�䲻��Ϊ��;";
		}

		if(!("0".equals(book.getSponsor()) || "1".equals(book.getSponsor()))) {
			message += "��һ��λ����:��������Ϊ�գ�Ϊ0��1��;";
		}
		return message;
	}
	
	/**
	 * ����Bookʱ�Ĳ���У��
	 * @param book �� Book
	 * @return message : String
	 */
	public String updateValidator(Book book) {
		String message = "";

		if(!IntegerUtil.isNullOrZero(book.getTeacher().getId())) {
			if(teacherDao.get(book.getTeacher()) == null) {
				message += "�����˱�Ŵ���:�����˱�Ų�����;";
			}
		}
		
		if(!StringUtil.isNullOrEmpty(book.getSponsor()) && (
				!("0".equals(book.getSponsor()) || "1".equals(book.getSponsor())))) {
			message += "��һ��λ����:��������Ϊ�գ�Ϊ0��1��;";
		}
		
		return message;
	}

}
