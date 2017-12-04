package cn.nchu.lims.util.lang;

import java.lang.reflect.Field;

public class ReflectUtil {
	
	/**
	 * ͨ�����������������ȡ����ֵ
	 * @param fieldName String 
	 * @param object object
	 * @return String
	 */
	public static Object getValue(String fieldName, Object object) {  
	      try {  
	          Field field = object.getClass().getDeclaredField(fieldName);  
	          //���ö���ķ���Ȩ�ޣ���֤��private�����Եķ���  
	          field.setAccessible(true);  
	          return field.get(object);  
	      } catch (Exception e) {
	          return null;  
	      }   
	  }

}
