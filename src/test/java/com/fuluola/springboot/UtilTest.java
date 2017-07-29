/**
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: bubugao yunhou</p>
 */
package com.fuluola.springboot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author fuluola
 *
 */
public class UtilTest {

    private static String getTLD(String domain) {  
        final int index;  
        return (domain == null || (index = domain.lastIndexOf('.') + 1) < 1) ? domain  
                : (index < (domain.length())) ? domain.substring(index) : "";  
    }  
    
    public static void main(String[] args) throws IOException {


    	//D:\\software\\phantomjs-2.1.1-windows\\examples\\netsniff2.js 
		Runtime rt = Runtime.getRuntime();
		String url = "http://pr.chinaz.com//www.yunhou.com";
		Process p = rt.exec("phantomjs.exe D:\\software\\phantomjs-2.1.1-windows\\examples\\netsniff2.js "+url);
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"gbk"));
		String line=null;
		String result = "";
		while((line=br.readLine())!=null){
			result += line;
		}
		String pr=result.substring(result.lastIndexOf(".")-1, result.lastIndexOf("."));
    	System.out.println(pr);
	}
}
