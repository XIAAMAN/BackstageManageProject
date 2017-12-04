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
	 * 自动注入EnclosureDao用于操作Enclosur相关数据库的方法
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
	 * 上传文件为外键的文件
	 *
	 *********************************** enclosures upload ***********************************/
	
	/**
	 * 根据JSP页面的MultipartHttpServletRequest，获取到用于保存的File相关信息
	 * 把File写入磁盘并把信息封装到Enclosure存入数据库
	 * 先设置要访问的数据库
	 * 返回单个文件
	 * @param request : MultipartHttpServletRequest
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/upload",  method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam e_upload(MultipartHttpServletRequest request){
		
		//List<Object> list = new ArrayList<Object>();  // 用于返回附信息的列表
		Iterator<String> iterator = request.getFileNames();  // 获取request中所有的文件名
		Enclosure enclosure = null;
		
		while (iterator.hasNext()) {  //遍历所有上传文件
	        MultipartFile multipartFile = request.getFile(iterator.next());  // 根据文件名获取文件对象
	        try {
	        	enclosure = enclosureService.multipleSave(    // 用自定义FileUtil来将信息存入Enclosure
	        			multipartFile, request, multipartFile.getName());
	         	
	        	// 根据传递的数据库标记标记生成静态变量属性名
	        	String table = multipartFile.getName().toUpperCase()+Constant.SUFFIX_ENCLOSURE_TABLE;
	        	// 通过反射根据属性名得到属性值（数据库名静态变量）
	        	table = (String) ReflectUtil.getValue(table, new Constant());
	        	
	         	// 动态插入数据库数据
	        	enclosureDao.saveDyna(new EnclosureREUDModel(enclosure,table));

	        } catch (Exception e) {
	            e.printStackTrace();
	            return new AjaxJsonReturnParam(1, "文件上传错误");
	        }
	    }
	    return new AjaxJsonReturnParam(0, "文件上传成功", enclosure);
	}
	 
	/**
	 * 根据JSP页面的HttpServletRequest，得到WEN-INF的路径
	 * 把File写入磁盘并把信息封装到Enclosure,根据信息从该数据库删除数据 并根据路径和fileName路径清理磁盘上的物理文件实体
	 * @param edmm : EnclosureDatabaseManageModel eamm 封装的有Enclosure信息和所在Database表的信息
	 * @param request : HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public AjaxJsonReturnParam e_delete(@RequestBody EnclosureREUDModel edmm, HttpServletRequest request) {
		
		// 根据传递的数据库标记标记生成静态附件表变量属性名
    	String table = edmm.getTable().toUpperCase() + Constant.SUFFIX_ENCLOSURE_TABLE;
    	// 通过反射根据属性名得到属性值（数据库名静态变量）
    	table = (String) ReflectUtil.getValue(table, new Constant());
    	
    	String path = Constant.PREFIX_URL + edmm.getTable().toUpperCase() + Constant.SUFFIX_URL;
    	path = (String) ReflectUtil.getValue(path, new Constant());
		
		// 调用enclosureDao.e_get从数据库中获取enclosure信息
		Enclosure e = enclosureDao.getDyna(new EnclosureREUDModel(edmm.getEnclosure(), table));
		
		if (e != null) {
			// 清理文件实体
			EnclosureUtil.clearFileOnDisk(e.getFileName(), request, path);
			// 删除数据库记录 
			enclosureDao.deleteDyna(new EnclosureREUDModel(edmm.getEnclosure(), table));
			
			return new AjaxJsonReturnParam(0);
		}
		
		return new AjaxJsonReturnParam(1, "文件不存在");
	}
	 
	 /**
	  * 上传文件路径字段属于表的单个文件
	  *
	  *********************************** enclosure single upload ***********************************/
	
	 /**
		 * 将头像，扫描文件等保存到物理路径下，并返回文件名
		 * @param request : MultipartHttpServletRequest
		 * @return
		 */
		@RequestMapping(value="/singleSave", method=RequestMethod.POST)
		@ResponseBody
		public AjaxJsonReturnParam singleUpload(MultipartHttpServletRequest request) {
			String name = null;  // 返回的已经储存到物理路径下的文件名
			Iterator<String> iterator = request.getFileNames();  
			
			//遍历所有上传文件
			while (iterator.hasNext()) {  
				// 根据文件名获取文件对象
		        MultipartFile multipartFile = request.getFile(iterator.next()); 
		        
		        String path = Constant.PREFIX_URL + multipartFile.getName().toUpperCase() + Constant.SUFFIX_URL; 
		        path = (String) ReflectUtil.getValue(path, new Constant());
		        
		        try {
		        	// 将头像路径保存到物理路径下
					name = enclosureService.singleSave(multipartFile, request, path);  
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return new AjaxJsonReturnParam(1, "头像上传错误");
				} 
		    }
			if (name != null) {
				return new AjaxJsonReturnParam(0, null, name); 
			}
			return new AjaxJsonReturnParam(1, "图片上传错误");
		}
		
	 
	 /**
	 * 根据头像文件名，删除物理路径下的文件
	 * @param typeAndName : Map<String, String> 
	 *  	存的参数有table和name，表示数据库名和文件名
	 * @param request : HttpServletRequest 得到当前工程的路径
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/singleDelete")
	@ResponseBody
	public AjaxJsonReturnParam singleDelete(
			@RequestBody Map<String, String> params, HttpServletRequest request) throws Exception {
		
//		String path = Constant.PREFIX_URL + params.get("module").toUpperCase() + Constant.SUFFIX_URL; 
//      path = (String) ReflectUtil.getValue(path, new Constant());
       
        // 选择路径
		String path = EnclosureUtil.chooseEnclosurePath(params.get("module"));
		// 因为未知原因，造成键值对应的字符串形式出现，所以split("\\=")方法处理，得到文件名
		String name = params.get("name");
		
		// 将数据库中的photo字段置空
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
     			medalDao.updatePhotoSetNull(name);  // 将附件存入数据库
     			break;
     		default :
     			return new AjaxJsonReturnParam(1, "参数不符合，请正常操作！");
		}
		// 清除路径下的物理文件
		EnclosureUtil.clearFileOnDisk(name, request, path);
		return new AjaxJsonReturnParam(0);
	}
		 
	/**
	 * 显示表内文件
	 * 
	 * @param table : String 判断路径
	 * @param name : String 文件名
	 * @param session : HttpSession
	 * @return ResponseEntity<byte[]>
	 * @throws EnclosureNotFoundException 
	 * @throws IOException
	 */
	
	@RequestMapping(value = "/download/{fileModule}/{filename:.+}")
	public ResponseEntity<byte[]> single(@PathVariable("fileModule") String fileModule, 
			@PathVariable("filename") String fileName, HttpSession session) 
					throws EnclosureNotFoundException {
		
		// 根据前台的标记选择所属表明和文路径
		String path = EnclosureUtil.chooseEnclosurePath(fileModule);

		// 组装文件下载路径
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


