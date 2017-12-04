package cn.nchu.lims.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import cn.nchu.lims.dao.EnclosureDao;
import cn.nchu.lims.dao.MDStudentDao;
import cn.nchu.lims.dao.MedalDao;
import cn.nchu.lims.dao.NewsDao;
import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.exception.EnclosureNotFoundException;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.EnclosureREUDModel;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.ReflectUtil;

@Controller
@RequestMapping(value="/enclosure")
public class EnclosureController {
	
	/**
	 * �Զ�ע��EnclosureDao���ڲ���Enclosur������ݿ�ķ���
	 */
	@Autowired
	EnclosureDao enclosureDao;
	
	@Autowired
	MDStudentDao mdStudentDao;
	
	@Autowired
	TeacherDao teacherDao;
	
	@Autowired
	NewsDao newsDao;
	
	@Autowired
	MedalDao medalDao;
	
	@Autowired
	EnclosureService enclosureService;
	
	/**
	 * �ϴ��ļ�Ϊ������ļ�
	 *
	 *********************************** enclosures upload ***********************************/
	
	/**
	 * ����JSPҳ���MultipartHttpServletRequest����ȡ�����ڱ����File�����Ϣ
	 * ��Fileд����̲�����Ϣ��װ��Enclosure�������ݿ�
	 * ������Ҫ���ʵ����ݿ�
	 * ���ص����ļ�
	 * @param request : MultipartHttpServletRequest
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/upload",  method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam e_upload(MultipartHttpServletRequest request){
		
		//List<Object> list = new ArrayList<Object>();  // ���ڷ��ظ���Ϣ���б�
		Iterator<String> iterator = request.getFileNames();  // ��ȡrequest�����е��ļ���
		Enclosure enclosure = null;
		
		while (iterator.hasNext()) {  //���������ϴ��ļ�
	        MultipartFile multipartFile = request.getFile(iterator.next());  // �����ļ�����ȡ�ļ�����
	        try {
	        	enclosure = enclosureService.multipleSave(    // ���Զ���FileUtil������Ϣ����Enclosure
	        			multipartFile, request, multipartFile.getName());
	         	
	        	// ���ݴ��ݵ����ݿ��Ǳ�����ɾ�̬����������
	        	String table = multipartFile.getName().toUpperCase()+Constant.SUFFIX_ENCLOSURE_TABLE;
	        	// ͨ����������������õ�����ֵ�����ݿ�����̬������
	        	table = (String) ReflectUtil.getValue(table, new Constant());
	        	
	         	// ��̬�������ݿ�����
	        	enclosureDao.saveDyna(new EnclosureREUDModel(enclosure,table));

	        } catch (Exception e) {
	            e.printStackTrace();
	            return new AjaxJsonReturnParam(1, "�ļ��ϴ�����");
	        }
	    }
	    return new AjaxJsonReturnParam(0, "�ļ��ϴ��ɹ�", enclosure);
	}
	 
	/**
	 * ����JSPҳ���HttpServletRequest���õ�WEN-INF��·��
	 * ��Fileд����̲�����Ϣ��װ��Enclosure,������Ϣ�Ӹ����ݿ�ɾ������ ������·����fileName·����������ϵ������ļ�ʵ��
	 * @param edmm : EnclosureDatabaseManageModel eamm ��װ����Enclosure��Ϣ������Database�����Ϣ
	 * @param request : HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public AjaxJsonReturnParam e_delete(@RequestBody EnclosureREUDModel edmm, HttpServletRequest request) {
		
		// ���ݴ��ݵ����ݿ��Ǳ�����ɾ�̬���������������
    	String table = edmm.getTable().toUpperCase() + Constant.SUFFIX_ENCLOSURE_TABLE;
    	// ͨ����������������õ�����ֵ�����ݿ�����̬������
    	table = (String) ReflectUtil.getValue(table, new Constant());
    	
    	String path = Constant.PREFIX_URL + edmm.getTable().toUpperCase() + Constant.SUFFIX_URL;
    	path = (String) ReflectUtil.getValue(path, new Constant());
		
		// ����enclosureDao.e_get�����ݿ��л�ȡenclosure��Ϣ
		Enclosure e = enclosureDao.getDyna(new EnclosureREUDModel(edmm.getEnclosure(), table));
		
		if (e != null) {
			// �����ļ�ʵ��
			EnclosureUtil.clearFileOnDisk(e.getFileName(), request, path);
			// ɾ�����ݿ��¼ 
			enclosureDao.deleteDyna(new EnclosureREUDModel(edmm.getEnclosure(), table));
			
			return new AjaxJsonReturnParam(0);
		}
		
		return new AjaxJsonReturnParam(1, "�ļ�������");
	}
	 
	 /**
	  * �ϴ��ļ�·���ֶ����ڱ�ĵ����ļ�
	  *
	  *********************************** enclosure single upload ***********************************/
	
	 /**
		 * ��ͷ��ɨ���ļ��ȱ��浽����·���£��������ļ���
		 * @param request : MultipartHttpServletRequest
		 * @return
		 */
		@RequestMapping(value="/singleSave", method=RequestMethod.POST)
		@ResponseBody
		public AjaxJsonReturnParam singleUpload(MultipartHttpServletRequest request) {
			String name = null;  // ���ص��Ѿ����浽����·���µ��ļ���
			Iterator<String> iterator = request.getFileNames();  
			
			//���������ϴ��ļ�
			while (iterator.hasNext()) {  
				// �����ļ�����ȡ�ļ�����
		        MultipartFile multipartFile = request.getFile(iterator.next()); 
		        
		        String path = Constant.PREFIX_URL + multipartFile.getName().toUpperCase() + Constant.SUFFIX_URL; 
		        path = (String) ReflectUtil.getValue(path, new Constant());
		        
		        try {
		        	// ��ͷ��·�����浽����·����
					name = enclosureService.singleSave(multipartFile, request, path);  
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return new AjaxJsonReturnParam(1, "ͷ���ϴ�����");
				} 
		    }
			if (name != null) {
				return new AjaxJsonReturnParam(0, null, name); 
			}
			return new AjaxJsonReturnParam(1, "ͼƬ�ϴ�����");
		}
		
	 
	 /**
	 * ����ͷ���ļ�����ɾ������·���µ��ļ�
	 * @param typeAndName : Map<String, String> 
	 *  	��Ĳ�����table��name����ʾ���ݿ������ļ���
	 * @param request : HttpServletRequest �õ���ǰ���̵�·��
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/singleDelete")
	@ResponseBody
	public AjaxJsonReturnParam singleDelete(
			@RequestBody Map<String, String> params, HttpServletRequest request) throws Exception {
		
//		String path = Constant.PREFIX_URL + params.get("module").toUpperCase() + Constant.SUFFIX_URL; 
//      path = (String) ReflectUtil.getValue(path, new Constant());
       
        // ѡ��·��
		String path = EnclosureUtil.chooseEnclosurePath(params.get("module"));
		// ��Ϊδ֪ԭ����ɼ�ֵ��Ӧ���ַ�����ʽ���֣�����split("\\=")���������õ��ļ���
		String name = params.get("name");
		
		// �����ݿ��е�photo�ֶ��ÿ�
		switch(params.get("module")) {
     		case "teacher":
    			teacherDao.updatePhotoSetNull(name);
     			break;
     		case "mdstudent":
     			mdStudentDao.updatePhotoSetNull(name); 
     			break;
     		case "news":
     			newsDao.updatePhotoSetNull(name); 
     			break;
     		case "medal":
     			medalDao.updatePhotoSetNull(name);  // �������������ݿ�
     			break;
     		default :
     			return new AjaxJsonReturnParam(1, "���������ϣ�������������");
		}
		// ���·���µ������ļ�
		EnclosureUtil.clearFileOnDisk(name, request, path);
		return new AjaxJsonReturnParam(0);
	}
		 
	/**
	 * ��ʾ�����ļ�
	 * 
	 * @param table : String �ж�·��
	 * @param name : String �ļ���
	 * @param session : HttpSession
	 * @return ResponseEntity<byte[]>
	 * @throws EnclosureNotFoundException 
	 * @throws IOException
	 */
	
	@RequestMapping(value = "/download/{fileModule}/{filename:.+}")
	public ResponseEntity<byte[]> single(@PathVariable("fileModule") String fileModule, 
			@PathVariable("filename") String fileName, HttpSession session) 
					throws EnclosureNotFoundException {
		
		// ����ǰ̨�ı��ѡ��������������·��
		String path = EnclosureUtil.chooseEnclosurePath(fileModule);

		// ��װ�ļ�����·��
		String url = session.getServletContext().getRealPath(path);
		File file = new File(url + File.separator + fileName);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	
		try {
		
			return new ResponseEntity<byte[]>(
					FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new EnclosureNotFoundException();
		}

    }  
	 
}


