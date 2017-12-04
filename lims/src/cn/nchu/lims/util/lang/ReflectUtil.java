package cn.nchu.lims.util.lang;

import java.lang.reflect.Field;

public class ReflectUtil {
	
	/**
	 * 通过反射根据属性明获取属性值
	 * @param fieldName String 
	 * @param object object
	 * @return String
	 */
	public static Object getValue(String fieldName, Object object) {  
	      try {  
	          Field field = object.getClass().getDeclaredField(fieldName);  
	          //设置对象的访问权限，保证对private的属性的访问  
	          field.setAccessible(true);  
	          return field.get(object);  
	      } catch (Exception e) {
	          return null;  
	      }   
	  }

}
