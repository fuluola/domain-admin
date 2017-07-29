/**
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: bubugao yunhou</p>
 */
package com.fuluola.springboot;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fuluola.domain.DomainQueryThread;

/**
 * @author fuluola
 *
 */
public class QueryDomainTest {
	
	public static void main(String[] args) throws Exception {
		 BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("E://域名采集项目//22.txt"),"UTF-8"));
		 String line = "";
		 List<String> domains = new ArrayList<String>();
		 while ((line = reader.readLine()) != null) {
			domains.add(line);
		 }
		 reader.close();
		 for(int i=0;i<domains.size();i++){
			
			String domain = domains.get(i).replace("www.", "");
			new Thread(new DomainQueryThread(domain)).start();
			Thread.sleep(300);
		 }
	}
}
