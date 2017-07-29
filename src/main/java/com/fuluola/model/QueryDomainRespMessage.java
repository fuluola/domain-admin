/**
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: bubugao yunhou</p>
 */
package com.fuluola.model;

/**
 * @author fuluola
 *
 */
public class QueryDomainRespMessage {

	private String code;//0,success，1 异常
	private String succResultStr;
	private DomainObject domainObject;
	private String exceptionMsg; //异常信息
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the succResultStr
	 */
	public String getSuccResultStr() {
		return succResultStr;
	}
	/**
	 * @param succResultStr the succResultStr to set
	 */
	public void setSuccResultStr(String succResultStr) {
		this.succResultStr = succResultStr;
	}
	
	public DomainObject getDomainObject() {
		return domainObject;
	}
	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}
	/**
	 * @return the exceptionMsg
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	/**
	 * @param exceptionMsg the exceptionMsg to set
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
}
