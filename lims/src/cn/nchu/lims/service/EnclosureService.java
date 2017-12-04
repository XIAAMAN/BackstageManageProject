package cn.nchu.lims.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import cn.nchu.lims.dao.EnclosureDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.util.EnclosureREUDModel;
import cn.nchu.lims.util.lang.DateUtil;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;

@Service
public class EnclosureService {

	
	@Autowired
	private EnclosureDao enclosureDao;
	
	/************************************************** multiple **************************************************/
	
	/**
	 * 根究传来的MultipartFile类型文件和HttpServletRequest请求
	 * 将相关Enclosure（附件）信息封装到Enclosure对象中
	 * @param file : MultipartFile
	 * @param request : HttpServletRequest
	 * @return enclosure : Enclosure
	 * @throws Exception 
	 */
	public Enclosure multipleSave(
			MultipartFile multipartFile, HttpServletRequest request, String path) 
					throws Exception {
		
		Enclosure enclosure = new Enclosure();
		enclosure.setOldName(multipartFile.getOriginalFilename());  // 保存原始文件名
		enclosure.setFileName(   // 保存时间戳形式的名字
				DateUtil.timeStamp() + "." + EnclosureUtil.getType(multipartFile.getOriginalFilename())); 
		enclosure.setSize(multipartFile.getSize() / 1024);
		
		path = EnclosureUtil.chooseEnclosurePath(path);  // 根据路径代号选择要储存的路径
		try {
			File file = new File(request.getServletContext().getRealPath(path) + enclosure.getFileName());
			// 判断类别文件夹类别是否存在
			if (!file.exists()) {  // 如果不存在指定的文件夹
				file.mkdirs();  // 创建新文件夹
			} 
			multipartFile.transferTo(file);  // 将文件转换从物理文件
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} 
		return enclosure;
	}
	
	/**
	 * 动态在数据库中的把Enclosure绑定到所属的对象上
	 * @param fileIds : List<Integer> 附件的主键编号列表
 	 * @param patent : Patent 所属对象
	 * @param database : 目标数据库的表名
	 * @param forginKeyName : 外键标识
	 */
	public void multipleBindForginKey(
			List<Integer> fileIds, int forginKey, String database, String forginKeyName) {
		
		if(fileIds.size() != 0) {  // 判断fileIds是否为空，非空更新附件外键
			Enclosure enclosure = new Enclosure();
			
			// 循环更新NoticeEnclosure的外键为本次插入的News的ID
			for(Integer id : fileIds) {  
				
				enclosure.setId(id);
				// 查询获取PatentEnclosure
				enclosure = enclosureDao.getDyna(new EnclosureREUDModel(enclosure, database));  
				
				// 判断是否已经有外键，没有则插入外键
				if (enclosure != null && IntegerUtil.isNullOrZero(enclosure.getForginKey())) { 
					
					enclosure.setForginKey(forginKey);  // 设置外键
					enclosureDao.updateDyna(new EnclosureREUDModel(
							enclosure, database, forginKeyName));  // 跟新数据库NoticeEnclosure信息
				}
			}
		}
	}
	
	
	/************************************************** single **************************************************/
	
	/**
	 * 根究传来的MultipartFile类型文件和HttpServletRequest请求
	 *   将相关头像图片到保存到 物理路径下
	 * @param multipartFile : MultipartFile
	 * @param request : HttpServletRequest
	 * @param path : String 返回时间戳形式的文件名
	 */
	public String singleSave(
			MultipartFile multipartFile, HttpServletRequest request, String path) {
		
		String type = EnclosureUtil.getType(multipartFile.getOriginalFilename());
		String name = DateUtil.timeStamp() + "." + type;		
		
		try { 
			File file = new File(  // uploads/类别文件夹
					request.getServletContext().getRealPath(path) + name);
		
			if (!file.exists()) {  // 判断类别文件夹类别是否存在
				file.mkdirs();  // 创建uploads/类别文件夹
			} 
	
			multipartFile.transferTo(file);  // 换成物理文件
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return name;
	}
	
}
