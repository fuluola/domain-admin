/**
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: bubugao yunhou</p>
 */
package com.fuluola.model;

import java.util.Date;

/**
 * @author fuluola
 *
 */
public class DomainObject {

	private String domainName;
	private String registrantOrganization;
	private String registrantName;
	private String registrantPhone;
	private String registrantEmail;
	private String nsServer;
	private String dnsServer;
	private String creationDate;
	private String expirationDate;
	
	private String ip;
	private String ipAddress;
	private String title;
	private String keywords;
	private String description;
	private String googlePR;
	private Date createTime;
	
	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	/**
	 * @return the registrantOrganization
	 */
	public String getRegistrantOrganization() {
		return registrantOrganization;
	}
	/**
	 * @param registrantOrganization the registrantOrganization to set
	 */
	public void setRegistrantOrganization(String registrantOrganization) {
		this.registrantOrganization = registrantOrganization;
	}
	/**
	 * @return the registrantName
	 */
	public String getRegistrantName() {
		return registrantName;
	}
	/**
	 * @param registrantName the registrantName to set
	 */
	public void setRegistrantName(String registrantName) {
		this.registrantName = registrantName;
	}
	/**
	 * @return the registrantPhone
	 */
	public String getRegistrantPhone() {
		return registrantPhone;
	}
	/**
	 * @param registrantPhone the registrantPhone to set
	 */
	public void setRegistrantPhone(String registrantPhone) {
		this.registrantPhone = registrantPhone;
	}
	/**
	 * @return the registrantEmail
	 */
	public String getRegistrantEmail() {
		return registrantEmail;
	}
	/**
	 * @param registrantEmail the registrantEmail to set
	 */
	public void setRegistrantEmail(String registrantEmail) {
		this.registrantEmail = registrantEmail;
	}
	/**
	 * @return the nsServer
	 */
	public String getNsServer() {
		return nsServer;
	}
	/**
	 * @param nsServer the nsServer to set
	 */
	public void setNsServer(String nsServer) {
		this.nsServer = nsServer;
	}
	/**
	 * @return the dnsServer
	 */
	public String getDnsServer() {
		return dnsServer;
	}
	/**
	 * @param dnsServer the dnsServer to set
	 */
	public void setDnsServer(String dnsServer) {
		this.dnsServer = dnsServer;
	}
	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the googlePR
	 */
	public String getGooglePR() {
		return googlePR;
	}
	/**
	 * @param googlePR the googlePR to set
	 */
	public void setGooglePR(String googlePR) {
		this.googlePR = googlePR;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String toString(){
		return this.domainName+","+this.registrantName+","+this.registrantEmail+","+this.registrantPhone;
	}
}
