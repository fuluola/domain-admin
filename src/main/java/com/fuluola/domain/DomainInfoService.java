package com.fuluola.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuluola.model.DomainObject;
import com.fuluola.model.HtmlHead;
import com.fuluola.model.QueryDomainRespMessage;
import com.fuluola.utils.WebUtil;

/** 
 * @description 
 * @author  fuzhuan fu.luola@qq.com
 * @date 2017年7月4日 
 */
@Service
public class DomainInfoService {

	private static Logger logger = LoggerFactory.getLogger(DomainInfoService.class);
	
	@Autowired
	private NsLookup nslookUp;
	
	@Autowired
	private WhoisService whois;
	
	public QueryDomainRespMessage domainInfoQuery(String domain) {
		logger.info("开始采集whois信息,域名："+domain);
		QueryDomainRespMessage respMessage = whois.query(domain);
		if(respMessage==null)
			return null;
		if(respMessage.getCode()==null || respMessage.getDomainObject()==null){
			return null;
		}
		DomainObject domainInfo = respMessage.getDomainObject();
		domainInfo.setDomainName(domain);
		logger.info("开始采集ip地址信息,域名："+domain);
		String ip = nslookUp.lookUpIP(domain);
		if(ip!=null){
			String city = nslookUp.getAddressCityByIp("ip="+ip);
			domainInfo.setIpAddress(city);
		}
		logger.info("开始采集网页信息,域名："+domain);
		HtmlHead hh = WebUtil.getHtmlHead(domain);
		if(hh!=null){
			domainInfo.setTitle(hh.getTitle());
			domainInfo.setKeywords(hh.getKeywords());
			domainInfo.setDescription(hh.getDescription());
		}
		domainInfo.setIp(ip);
		return respMessage;
		
	}
	
}
