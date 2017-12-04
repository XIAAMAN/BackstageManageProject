package cn.nchu.lims.util.ajax;

import cn.nchu.lims.util.PageModel;

public class AjaxJsonRequestDynaPageParam<T> {

	private T param;
	private PageModel pageModel;
	
	public AjaxJsonRequestDynaPageParam() {
		super();
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
}
