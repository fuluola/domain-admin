package com.fuluola.springboot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.fuluola.domain.DomainInfoService;
import com.fuluola.model.Constants;
import com.fuluola.model.DomainObject;
import com.fuluola.model.PreDomainInfo;
import com.fuluola.model.QueryDomainRespMessage;

/** 
 * @description 
 * @author  fuzhuan fu.luola@qq.com
 * @date 2017年7月12日 
 */
@Repository
public class DomainRepository {
	
	private static Logger logger = LoggerFactory.getLogger(DomainRepository.class);
	/**
	 * 导入域名sql
	 */
	private static String insertSQL = "insert into domain (domain,status,errorCount,createTime) values(?,0,0,now())"; 
	/**
	 * 查询导入域名详情
	 */
	private static String findSQL = "select domain,status,createTime,errorCount from domain where domain=?";
	
	private static String processSQL = "select domain,status,errorCount,createTime from domain where status=0 and errorCount<3 order by createTime asc limit 200";
	private static String updateSUCCESS_SQL = "update domain set status=1,errorMsg=null,updateTime=now() where domain=?";
	private static String updateERROR_SQL = "update domain set errorMsg=?,errorCount=errorCount+1,updateTime=now() where domain=?";
	
	private static String pageQueryDomainInfo_SQL = "select * from domaininfo_collect limit ?,? ";
	private static String pageQueryDomainTotal = "select count(1) from domaininfo_collect";
	
	private static String pageQueryCondition_SQL = 
			"where domainName like '%?%' or registrar like '%?%' or registrantName like '%?%' or registrantPhone like '%?%' or "
			+ "registrantEmail like '%?%' or nsServer like '%?%' or dnsServer like '%?%' or ip like '%?%' or ipAddress like '%?%' "
			+ "or title like '%?%' or keywords like '%?%' or description like '%?%' or remark like '%?%' ";
	
	private static String insertDomainInfoSQL= "INSERT INTO domaininfo_collect (domainName,registrantOrganization,registrantName,"
			+ "registrantPhone,registrantEmail,nsServer,dnsServer,creationDate,expirationDate,ip,ipAddress,"
			+ "title,keywords,description,googlePR,createTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
	
	private static String updateRemarkSQL = "update domaininfo_collect set remark=? where domainName=?";
	
	private static String queryUngetedDomainSQL = "SELECT domain,errorMsg FROM domain WHERE status=0 limit ?,?";
	
	private static String queryUngetedDomainCountSQL = "SELECT count(1) FROM domain WHERE status=0";
	
	private JdbcTemplate jdbc;
	
	public DomainRepository(JdbcTemplate jdbc){
		this.jdbc = jdbc;
	}
	@Autowired
	private DomainInfoService infoService;
	
	public PreDomainInfo findByDomain(String data){
		
		return jdbc.query(findSQL,new Object[]{data}, new DomainResultSetExtractor());
		
	}
	
	public int insertDomain(String data) {
		
		if(findByDomain(data)==null){
			return jdbc.update(insertSQL,data);
		}else{
			return 0;
		}
	}
	
	public List<Map<String,Object>> queryProcessDomain(){

		return jdbc.queryForList(processSQL);
		  
	}
	
	@Transactional
	public void processSingleDomain(String data){
		QueryDomainRespMessage respMsg=infoService.domainInfoQuery(data);
		if(respMsg==null) {
			jdbc.update(updateERROR_SQL, new Object[]{"该域名没有找到注册信息",data});
		}else if(Constants.SUCCESS.equals(respMsg.getCode())){
			DomainObject domainInfo = respMsg.getDomainObject();
			jdbc.update(updateSUCCESS_SQL, new Object[]{data});
			jdbc.update(insertDomainInfoSQL, domainInfo.getDomainName(),domainInfo.getRegistrantOrganization(),domainInfo.getRegistrantName(),
								domainInfo.getRegistrantPhone(),domainInfo.getRegistrantEmail(),domainInfo.getNsServer(),
								domainInfo.getDnsServer(),domainInfo.getCreationDate(),domainInfo.getExpirationDate(),
								domainInfo.getIp(),domainInfo.getIpAddress(),domainInfo.getTitle(),domainInfo.getKeywords(),
								domainInfo.getDescription(),domainInfo.getGooglePR());
		}else{
			jdbc.update(updateERROR_SQL, new Object[]{respMsg.getExceptionMsg(),data});
		}
	}
	
	public String processBatchDomain(){
		List<Map<String,Object>> list= queryProcessDomain();
		logger.info("本次共处理"+list.size()+"条记录!");
		if(list.size()>=1){
			for(Map<String,Object> map:list){
				String domain = (String) map.get("domain");
				logger.info("开始采集信息，域名："+domain);
				processSingleDomain(domain);
			}
		}
		return "";
	}
	
	public List<Map<String,Object>> pageQueryDomainInfo(Map<String,Object> params){

		int start=(Integer) params.get("start");
		int rows = (Integer) params.get("rows");		
		String param = (String) params.get("param");
		if(!StringUtils.isEmptyOrWhitespace(param)){
			String conditionSQL = pageQueryCondition_SQL.replaceAll("\\?", param);
			return jdbc.queryForList("select * from domaininfo_collect "+conditionSQL+"limit ?,?",start,rows);
		}else{
			return jdbc.queryForList(pageQueryDomainInfo_SQL,start,rows);
		}

	}

	/**
	 * @date 2017年7月17日下午11:02:42
	 * @return
	 * 
	 */
	public Integer pageQueryDomainTotal(String param) {
		if(StringUtils.isEmptyOrWhitespace(param)){
			
			return jdbc.queryForObject(pageQueryDomainTotal, Integer.class);
		}else{
			String conditionSQL = pageQueryCondition_SQL.replaceAll("\\?", param);
			return jdbc.queryForObject("select count(1) from domaininfo_collect "+conditionSQL,Integer.class);
		}
	}
	
	public int updateRemark(String remark,String domain) {
		return jdbc.update(updateRemarkSQL, new Object[]{remark,domain});
	}
	
	public List<Map<String,Object>> findUngetInfoDomains (Map<String,Object> params){
		int start=(Integer) params.get("start");
		int rows = (Integer) params.get("rows");	
		return jdbc.queryForList(queryUngetedDomainSQL,start,rows);
	}
	
	public int findUngetInfoCount() {
		return jdbc.queryForObject(queryUngetedDomainCountSQL, Integer.class);
	}

	/**
	 * @desc 查询导出的采集信息
	 * @date 2017年8月1日 下午3:43:49
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findDownloadDomain(Map<String,Object> params){

		int rows = (Integer) params.get("rows");		
		String param = (String) params.get("param");
		if(!StringUtils.isEmptyOrWhitespace(param)){
			String conditionSQL = pageQueryCondition_SQL.replaceAll("\\?", param);
			return jdbc.queryForList("select * from domaininfo_collect "+conditionSQL+"limit 0,?",rows);
		}else{
			return null;
		}

	}
}

class DomainResultSetExtractor implements ResultSetExtractor<PreDomainInfo> {


	public PreDomainInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		
		PreDomainInfo entity = null;
		while(rs.next()){
			entity = new PreDomainInfo();
			entity.setCreateTime(rs.getDate("createTime"));
			entity.setDomain(rs.getString("domain"));
			entity.setStatus(rs.getInt("status"));
			entity.setErrorCount(rs.getInt("errorCount"));
		}
		return entity;
	}
	
}
class PreDomainRowMapper implements RowMapper<PreDomainInfo> {


	public PreDomainInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		PreDomainInfo entity = new PreDomainInfo();
		entity.setCreateTime(rs.getDate("createTime"));
		entity.setDomain(rs.getString("domain"));
		entity.setStatus(rs.getInt("status"));
		entity.setErrorCount(rs.getInt("errorCount"));
		return entity;
	}
	
}