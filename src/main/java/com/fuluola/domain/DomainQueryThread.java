/**
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: bubugao yunhou</p>
 */
package com.fuluola.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.fuluola.model.Constants;
import com.fuluola.model.QueryDomainRespMessage;

/**
 * @author fuluola
 *
 */
public class DomainQueryThread implements Runnable{

	String domain;
	public DomainQueryThread(String domain){
		this.domain = domain;
	}
	@Autowired
	private DomainInfoService infoService;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		WhoisService who = new WhoisService();
		try {
			PrintStream pr=null;
			QueryDomainRespMessage respMsg = who.query(domain);
	
			if(Constants.FAIL.equals(respMsg.getCode())){
				FileWriter output = new FileWriter("E://域名采集项目//fail.txt",true);
				output.append(domain+">>>>>"+respMsg.getExceptionMsg()+"\n");
				output.flush();
				output.close();
			}else if(Constants.SUCCESS.equals(respMsg.getCode())){
				File output = new File("E://域名采集项目//output2//"+domain+".txt");

				pr = new PrintStream(new FileOutputStream(output));
				pr.print(respMsg.getSuccResultStr());
				pr.close();
				FileWriter output2 = new FileWriter("E://域名采集项目//success.txt",true);
				output2.append(respMsg.getDomainObject().toString()+"\n");
				output2.flush();
				output2.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
