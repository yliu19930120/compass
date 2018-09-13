package com.core.bean;

public class Paging {
	
    private Integer startNum;
    
    private Integer pageSize;
    
    private Integer endNum;
    
    
	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum() {
		this.endNum = startNum+pageSize;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		setEndNum();
	}

	
    
    
}
