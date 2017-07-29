package com.fuluola.domain;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.fuluola.model.AddressMessage;
import com.fuluola.utils.ObjectMapperFactory;
import com.fuluola.utils.WebUtil;

/** 
 * @description 
 * @author  fuzhuan fu.luola@qq.com
 * @date 2017年6月26日 
 */
@Service
public class NsLookup {

	private final static String IP_ADDRESS_URL = "http://ip.taobao.com/service/getIpInfo.php";
	public static void main(String[] args) {
		 NsLookup ns = new NsLookup();
		 String name = "dianping.com";
		 String ip = ns.lookUpIP(name);
		 System.out.println(ip);
		 System.out.println(ns.getAddressCityByIp("ip="+ip));
	}
	
	public  String lookUpIP(String host){
		InetAddress addresses;
		try {
			addresses = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		return addresses.getHostAddress();
	}
	
	public String getAddressCityByIp(String ip) {
		String json;
		AddressMessage address;
		try {
			json = WebUtil.get(IP_ADDRESS_URL,ip);
			address = ObjectMapperFactory.JSON.readValue(json, AddressMessage.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return address.getData().getCity();
	}
}
