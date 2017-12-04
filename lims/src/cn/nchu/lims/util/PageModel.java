package cn.nchu.lims.util;

/**
 *  ��ҳʵ�� 
 */
public class PageModel  {
	private int recordCount;  // ��ҳ���������� 
	private int pageIndex;	// ��ǰҳ��
	private int pageSize = Constant.PAGE_DEFAULT_SIZE;	 // ÿҳ�ֶ��������� 
	private int totalSize; 	//��ҳ�� 

	public int getRecordCount() {
		this.recordCount = this.recordCount <= 0 ? 0:this.recordCount;
		return recordCount;
	}
	
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	public int getPageIndex() {
		if(this.pageIndex <= 0) {
			return 1;
		} else if(this.pageIndex >0  && this.pageIndex < this.getTotalSize()) {
			return this.pageIndex;
		} else {
			return this.getTotalSize() <= 0 ? 1 : this.getTotalSize();
		}
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getPageSize() {
		this.pageSize = this.pageSize <= Constant.PAGE_DEFAULT_SIZE 
				? Constant.PAGE_DEFAULT_SIZE:this.pageSize;
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalSize() {
		if(this.getRecordCount() <=0){
			totalSize = 0 ;
		}else{
			totalSize = (this.getRecordCount() -1)/this.getPageSize() + 1;
		}
		return totalSize;
	}
	
	/**
	 * SQL ��ҳlimit��ѯ��� ��һ����������ʼ��
	 * @return int
	 */
	public int getFirstLimitParam(){
		return (this.getPageIndex()-1)*this.getPageSize() ;
	}
	
	@Override
	public String toString() {
		return ("PageIndex " + this.getPageIndex() + " PageSize " + this.getPageSize() 
				+ " FirstLimitParam " + this.getFirstLimitParam() + " TotalSize " + this.getTotalSize());
	}
	
}