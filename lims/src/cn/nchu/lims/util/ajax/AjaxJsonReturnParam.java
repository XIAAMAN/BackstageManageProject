package cn.nchu.lims.util.ajax;

public class AjaxJsonReturnParam {
	private int state;
	private String message;
	private Object information;

	public AjaxJsonReturnParam() {
		super();
	}
	
	public AjaxJsonReturnParam(int state) {
		this.state = state;
	}
	
	public AjaxJsonReturnParam(int state, String message) {
		this.state = state;
		this.message = message;
	}
	
	public AjaxJsonReturnParam(int state, Object information) {
		this.state = state;
		this.information = information;
	}
	
	public AjaxJsonReturnParam(int state, String message, Object information) {
		this.state = state;
		this.message = message;
		this.information = information;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getInformation() {
		return information;
	}

	public void setInformation(Object information) {
		this.information = information;
	}
}
