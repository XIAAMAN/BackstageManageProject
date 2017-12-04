package cn.nchu.lims.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.util.Constant;

/** 
 * �ж��û�Ȩ�޵�Spring MVC��������
 */
public class AuthorizedInterceptor implements HandlerInterceptor {

	// ���岻��Ҫ���ص�����
	private String[] ignorUrls = Constant.IGNORE_URI;
	
	 /** 
     * �÷�����ҪpreHandle�����ķ���ֵΪtrueʱ�Ż�ִ�С�
     * �÷������������������֮��ִ�У���Ҫ����������������Դ��
     */  
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		
	}

	 /** 
     * ���������preHandle��������ֵΪtrue��ʱ��Ż�ִ�С�
     * ִ��ʱ�����ڴ��������д���֮ ��Ҳ������Controller�ķ�������֮��ִ�С�
     */  
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mv) throws Exception {
		
	}

	 /** 
     * preHandle�����ǽ��д����������õģ��÷�������Controller����֮ǰ���е��ã�
     * ��preHandle�ķ���ֵΪfalse��ʱ����������ͽ����ˡ� 
     * ���preHandle�ķ���ֵΪtrue��������ִ��postHandle��afterCompletion��
     */  
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		System.out.println("interceptor begin");
		boolean flag = false;  // Ĭ���û�û�е�¼
		String servletPath = request.getServletPath();  // ��������ServletPath
        for (String url : ignorUrls) {  // �ж������Ƿ���Ҫ����
        	if (servletPath.contains(url)) {
            	flag = true;
                break;
            }
        }
        if (!flag){  // ��������
        	System.out.println("interceptor !flag");
        	Admin admin =  // 1.��ȡsession�е��û� 
        		(Admin) request.getSession().getAttribute(Constant.USER_SESSION);
        	if(admin == null){  // 2.�ж��û��Ƿ��Ѿ���¼
        		request.setAttribute("message", "���ȵ�¼�ٷ�����վ!");  // ����û�û�е�¼����ת����¼ҳ��
        		request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
        		return flag;
        	}else{
        		 flag = true;
        	}
        }
        
        return flag;
	}

}
