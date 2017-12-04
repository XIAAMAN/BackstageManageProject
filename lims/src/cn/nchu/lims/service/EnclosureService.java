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
	 * ����������MultipartFile�����ļ���HttpServletRequest����
	 * �����Enclosure����������Ϣ��װ��Enclosure������
	 * @param file : MultipartFile
	 * @param request : HttpServletRequest
	 * @return enclosure : Enclosure
	 * @throws Exception 
	 */
	public Enclosure multipleSave(
			MultipartFile multipartFile, HttpServletRequest request, String path) 
					throws Exception {
		
		Enclosure enclosure = new Enclosure();
		enclosure.setOldName(multipartFile.getOriginalFilename());  // ����ԭʼ�ļ���
		enclosure.setFileName(   // ����ʱ�����ʽ������
				DateUtil.timeStamp() + "." + EnclosureUtil.getType(multipartFile.getOriginalFilename())); 
		enclosure.setSize(multipartFile.getSize() / 1024);
		
		path = EnclosureUtil.chooseEnclosurePath(path);  // ����·������ѡ��Ҫ�����·��
		try {
			File file = new File(request.getServletContext().getRealPath(path) + enclosure.getFileName());
			// �ж�����ļ�������Ƿ����
			if (!file.exists()) {  // ���������ָ�����ļ���
				file.mkdirs();  // �������ļ���
			} 
			multipartFile.transferTo(file);  // ���ļ�ת���������ļ�
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} 
		return enclosure;
	}
	
	/**
	 * ��̬�����ݿ��еİ�Enclosure�󶨵������Ķ�����
	 * @param fileIds : List<Integer> ��������������б�
 	 * @param patent : Patent ��������
	 * @param database : Ŀ�����ݿ�ı���
	 * @param forginKeyName : �����ʶ
	 */
	public void multipleBindForginKey(
			List<Integer> fileIds, int forginKey, String database, String forginKeyName) {
		
		if(fileIds.size() != 0) {  // �ж�fileIds�Ƿ�Ϊ�գ��ǿո��¸������
			Enclosure enclosure = new Enclosure();
			
			// ѭ������NoticeEnclosure�����Ϊ���β����News��ID
			for(Integer id : fileIds) {  
				
				enclosure.setId(id);
				// ��ѯ��ȡPatentEnclosure
				enclosure = enclosureDao.getDyna(new EnclosureREUDModel(enclosure, database));  
				
				// �ж��Ƿ��Ѿ��������û����������
				if (enclosure != null && IntegerUtil.isNullOrZero(enclosure.getForginKey())) { 
					
					enclosure.setForginKey(forginKey);  // �������
					enclosureDao.updateDyna(new EnclosureREUDModel(
							enclosure, database, forginKeyName));  // �������ݿ�NoticeEnclosure��Ϣ
				}
			}
		}
	}
	
	
	/************************************************** single **************************************************/
	
	/**
	 * ����������MultipartFile�����ļ���HttpServletRequest����
	 *   �����ͷ��ͼƬ�����浽 ����·����
	 * @param multipartFile : MultipartFile
	 * @param request : HttpServletRequest
	 * @param path : String ����ʱ�����ʽ���ļ���
	 */
	public String singleSave(
			MultipartFile multipartFile, HttpServletRequest request, String path) {
		
		String type = EnclosureUtil.getType(multipartFile.getOriginalFilename());
		String name = DateUtil.timeStamp() + "." + type;		
		
		try { 
			File file = new File(  // uploads/����ļ���
					request.getServletContext().getRealPath(path) + name);
		
			if (!file.exists()) {  // �ж�����ļ�������Ƿ����
				file.mkdirs();  // ����uploads/����ļ���
			} 
	
			multipartFile.transferTo(file);  // ���������ļ�
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return name;
	}
	
}
