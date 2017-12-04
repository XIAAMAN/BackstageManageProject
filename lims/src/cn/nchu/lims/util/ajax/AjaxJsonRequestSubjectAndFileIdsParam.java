package cn.nchu.lims.util.ajax;

import java.util.List;

public class AjaxJsonRequestSubjectAndFileIdsParam<T> {

	private T param;
	private List<Integer> fileIds;
	
	public AjaxJsonRequestSubjectAndFileIdsParam() {
		super();
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	public List<Integer> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<Integer> fileIds) {
		this.fileIds = fileIds;
	}
	
}
