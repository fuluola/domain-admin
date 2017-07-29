package com.fuluola.model;

import java.util.Date;

/** 
 * @description 
 * @author  fuzhuan fu.luola@qq.com
 * @date 2017年7月12日 
 */
public class PreDomainInfo {

	private String domain;
	private Integer status;
	private int errorCount;
	private String errorMsg;
	private Date createTime;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
