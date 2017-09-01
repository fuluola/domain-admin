/**
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: bubugao yunhou</p>
 */
package com.fuluola.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.fuluola.model.Constants;
import com.fuluola.model.DomainObject;
import com.fuluola.model.QueryDomainRespMessage;
import com.fuluola.utils.ParseResultDomainInfo;

@Service
public class WhoisService {  

    private static final int DEFAULT_PORT = 43;  
    static String[] urls = new String[]{"=baidu.com","ele.me","csdn.net"};
      //grs-whois.hichina.com whois.paycenter.com.cn whois.markmonitor.com whois.verisign-grs.com
    public QueryDomainRespMessage query(String domain)  {  
    	
    	if(domain.contains("www.")){
    		domain = domain.replace("www.", "").trim();
    	}
        String server = "";  
        String tld = getTLD(domain);  
        if ("com".equals(tld)) {  
        	server = "whois.verisign-grs.com";
        	return queryComWhoisServer(domain, server);  
        } else if ("net".equals(tld)) {  
            server = "whois.networksolutions.com";  
            return queryNetWhoisServer(domain, server);  
        } else if ("org".equals(tld)) {  
            server = "whois.pir.org";  
            return queryOrgWhoisServer(domain, server);  
        } else if ("cn".equals(tld)) {  
            server = "whois.cnnic.cn";  
            return queryCnWhoisServer(domain, server);  
        } else if ("jp".equals(tld)) {  
            server = "whois.jprs.jp";  
        } else if ("tw".equals(tld)) {  
            server = "whois.twnic.tw";  
        }else if("io".equals(tld)){
        	server = "whois.nic.io";
        }else if("me".equals(tld)){
        	server = "whois.nic.me";
        }else if("hk".equals(tld)){
        	server = "whois.hkirc.hk";
        }
        return  null;
    }  
    private QueryDomainRespMessage queryOrgWhoisServer(String domain,
			String server) {
    	 Socket socket = new Socket();  
         SocketAddress  remoteAddr=new InetSocketAddress(server, DEFAULT_PORT);
         PrintWriter out = null;
         String lineSeparator = "\r\n",line="";  
         StringBuilder ret = new StringBuilder();  
         DomainObject obj = new DomainObject();
         QueryDomainRespMessage respMsg = new QueryDomainRespMessage();
     	 obj.setDomainName(domain);
         try {
 			socket.connect(remoteAddr, 15*1000);
 			socket.setSoTimeout(20 * 1000);  
         	out = new PrintWriter(socket.getOutputStream());  
         	out.println(domain);  
         	out.flush(); 
         	BufferedReader in2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
         	while((line=in2.readLine())!=null){
         		ret.append(line + lineSeparator);  
         		ParseResultDomainInfo.parseOrgDomainInfo(obj, line);
         	}
         	respMsg.setCode(Constants.SUCCESS);
         	respMsg.setDomainObject(obj);
         	respMsg.setSuccResultStr(ret.toString());
 		} catch (IOException e) {
 			respMsg.setCode(Constants.FAIL);
 			respMsg.setExceptionMsg(e.getMessage());
 			e.printStackTrace();
 		}finally{
 		   	out.close();
         	try {
 				socket.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		}
         return respMsg;  
	}
	/**
     * 查询.com域名whois信息
     * @date 2017年7月2日上午11:27:09
     * @author fuzhuan
     * @param domain
     * @param server
     * @return
     *
     */
    public QueryDomainRespMessage queryComWhoisServer(String domain, String server) {  
        Socket socket = new Socket();  
        SocketAddress  remoteAddr=new InetSocketAddress(server, DEFAULT_PORT);
        PrintWriter out = null;
        String lineSeparator = "\r\n",line="";  
        String whoisServer=null;
        StringBuilder ret = new StringBuilder();  
        DomainObject obj = new DomainObject();
        QueryDomainRespMessage respMsg = new QueryDomainRespMessage();
        try {
			socket.connect(remoteAddr, 15*1000);
			socket.setSoTimeout(15 * 1000);  
		    out = new PrintWriter(socket.getOutputStream());  
		    out.println(domain);  
		    out.flush();  
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
	        while ((line = in.readLine()) != null) {
	        	if(line.contains("Whois Server:")){
	        		whoisServer=line.substring(line.indexOf(":")+1).trim();
	        		break;
	        	}
	        }  
	        socket.close(); 
		} catch (IOException e) {
			respMsg.setCode(Constants.FAIL);
			respMsg.setExceptionMsg(e.getMessage());
			e.printStackTrace();
		}
        if(whoisServer!=null){
        	
        	obj.setDomainName(domain);
            Socket socket2 = new Socket();  
            SocketAddress  remoteAddr2=new InetSocketAddress(whoisServer, DEFAULT_PORT);
            try {
				socket2.connect(remoteAddr2, 15*1000);
				socket2.setSoTimeout(20 * 1000);  
	        	out = new PrintWriter(socket2.getOutputStream());  
	        	out.println(domain);  
	        	out.flush(); 
	        	BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));  
	        	while((line=in2.readLine())!=null){
	        		ret.append(line + lineSeparator);  
	        		ParseResultDomainInfo.parseComDomainInfo(obj, line);
	        	}
	        	respMsg.setCode(Constants.SUCCESS);
	        	respMsg.setDomainObject(obj);
	        	respMsg.setSuccResultStr(ret.toString());
			} catch (IOException e) {
				respMsg.setCode(Constants.FAIL);
				respMsg.setExceptionMsg(e.getMessage());
				e.printStackTrace();
			}finally{
			   	out.close();
	        	try {
					socket2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
        return respMsg;  
    }
    
    public QueryDomainRespMessage queryCnWhoisServer(String domain, String server) {  
        Socket socket = new Socket();  
        SocketAddress  remoteAddr=new InetSocketAddress(server, DEFAULT_PORT);
        PrintWriter out = null;
        String lineSeparator = "\r\n",line="";  
        StringBuilder ret = new StringBuilder();  
        DomainObject obj = new DomainObject();
        QueryDomainRespMessage respMsg = new QueryDomainRespMessage();
    	obj.setDomainName(domain);
        try {
			socket.connect(remoteAddr, 15*1000);
			socket.setSoTimeout(20 * 1000);  
        	out = new PrintWriter(socket.getOutputStream());  
        	out.println(domain);  
        	out.flush(); 
        	BufferedReader in2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
        	while((line=in2.readLine())!=null){
        		ret.append(line + lineSeparator);  
        		ParseResultDomainInfo.parseCnDomainInfo(obj, line);
        	}
        	respMsg.setCode(Constants.SUCCESS);
        	respMsg.setDomainObject(obj);
        	respMsg.setSuccResultStr(ret.toString());
		} catch (IOException e) {
			respMsg.setCode(Constants.FAIL);
			respMsg.setExceptionMsg(e.getMessage());
			e.printStackTrace();
		}finally{
		   	out.close();
        	try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return respMsg;  
    }  
    public QueryDomainRespMessage queryNetWhoisServer(String domain, String server) {  
        Socket socket = new Socket();  
        SocketAddress  remoteAddr=new InetSocketAddress(server, DEFAULT_PORT);
        PrintWriter out = null;
        String lineSeparator = "\r\n",line="";  
        StringBuilder ret = new StringBuilder();  
        DomainObject obj = new DomainObject();
        QueryDomainRespMessage respMsg = new QueryDomainRespMessage();
    	obj.setDomainName(domain);
        try {
			socket.connect(remoteAddr, 15*1000);
			socket.setSoTimeout(20 * 1000);  
        	out = new PrintWriter(socket.getOutputStream());  
        	out.println(domain);  
        	out.flush(); 
        	BufferedReader in2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
        	while((line=in2.readLine())!=null){
        		ret.append(line + lineSeparator);  
        		ParseResultDomainInfo.parseComDomainInfo(obj, line);
        	}
        	respMsg.setCode(Constants.SUCCESS);
        	respMsg.setDomainObject(obj);
        	respMsg.setSuccResultStr(ret.toString());
		} catch (IOException e) {
			respMsg.setCode(Constants.FAIL);
			respMsg.setExceptionMsg(e.getMessage());
			e.printStackTrace();
		}finally{
		   	out.close();
        	try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return respMsg;  
    }  
    
    public String queryWhoisServer(String domain,String server) throws UnknownHostException, IOException {
    	String lineSeparator = "\r\n";  
    	Socket socket = new Socket(server, DEFAULT_PORT);  
    	PrintWriter out = new PrintWriter(socket.getOutputStream());  
    	out = new PrintWriter(socket.getOutputStream());  
    	out.println(domain);  
    	out.flush(); 
    	String line;
    	StringBuffer ret = new StringBuffer();
    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
    	while((line=in.readLine())!=null){
    		ret.append(line + lineSeparator);  
    	}
    	out.close();
    	socket.close();
    	return ret.toString();
    }
    
    private String getTLD(String domain) {  
        final int index;  
        return (domain == null || (index = domain.lastIndexOf('.') + 1) < 1) ? domain  
                : (index < (domain.length())) ? domain.substring(index) : "";  
    }  
      
    public static void main(String[] args) throws Exception {  
    	WhoisService w = new WhoisService();  
      //  System.out.println(w.query("spring.io")); 
        long start=System.currentTimeMillis();
        //QueryDomainRespMessage
        String msg = w.queryWhoisServer("tpkfoundation.com", "whois.verisign-grs.com");

        System.out.println("用时: "+(System.currentTimeMillis()-start));
        System.out.println(msg);
    }  
      
}  