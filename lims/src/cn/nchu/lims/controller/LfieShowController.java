package cn.nchu.lims.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.nchu.lims.dao.LifeShowDao;
import cn.nchu.lims.domain.LifeShow;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;

@Controller
@RequestMapping("/life")
public class LfieShowController {
	
	@Autowired
	private LifeShowDao lifeShowDao;
	
	@Autowired
	private EnclosureService enclosureService;
	
	/**
	 * 多图片上传
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public AjaxJsonReturnParam upload(MultipartHttpServletRequest request){
		
		Iterator<String> iterator = request.getFileNames();  // 获取request中所有的文件名
		LifeShow lifeShow = new LifeShow();
		
		while (iterator.hasNext()) {  //遍历所有上传文件
	        MultipartFile multipartFile = request.getFile(iterator.next());  // 根据文件名获取文件对象
	        lifeShow.setType(multipartFile.getName());
	        String fileName = enclosureService.singleSave(
	        		multipartFile, request, Constant.UPLOAD_LIFESHOWENCLOSURE_URL);
	        lifeShow.setFileName(fileName);
	        lifeShowDao.save(lifeShow);
		}
		return new AjaxJsonReturnParam(0, null, lifeShow.getId());
	}
	
	/**
	 * 将上上传的文件保存到数据库（跟新 type 字段）
	 * @param params Map<String, Object>
	 * @return AjaxJsonReturnParam
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/add")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody Map<String, Object> params) {
	
		String type = (String) params.get("type");
		List<Integer> fileIds = (List<Integer>) params.get("fileIds");	
		LifeShow lifeShow = new LifeShow();
		lifeShow.setType(type);

		for (Integer id : fileIds) {
			lifeShow.setId(id);
			lifeShowDao.updateType(lifeShow);
		}
		
		return new AjaxJsonReturnParam(0);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody LifeShow lifeShow, HttpServletRequest request) {

		lifeShow = lifeShowDao.get(lifeShow);
		
		if (lifeShow != null) {
			EnclosureUtil.clearFileOnDisk(lifeShow.getFileName(), request, Constant.UPLOAD_LIFESHOWENCLOSURE_URL);
			lifeShowDao.delete(lifeShow);
			return new AjaxJsonReturnParam(0);
		} else {
			return new AjaxJsonReturnParam(1, "数据错误，请正确操作");
		}
	}
	
	@RequestMapping(value="/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<LifeShow> listDynaPage(
			@RequestBody AjaxJsonRequestDynaPageParam<LifeShow> ajrdpp) {
		
		PageModel pageModel = ajrdpp.getPageModel();
		ajrdpp.setPageModel(null);
		int recordCount = lifeShowDao.listDynaPage(ajrdpp).size();
		ajrdpp.setPageModel(pageModel);
		ajrdpp.getPageModel().setRecordCount(recordCount);  
		List<LifeShow> lifeShows = lifeShowDao.listDynaPage(ajrdpp); 
		return new AjaxJsonReturnDynaPageParam<LifeShow>(recordCount, lifeShows);

	}
}
