package com.fuluola.model;


/** 
 * @description 
 * @author  fuzhuan fu.luola@qq.com
 * @date 2017年7月4日 
 */
public class HtmlHead {

	private String title;
	private String keywords;
	private String description ;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		return this.title+"\n"+this.keywords+"\n"+this.description;
	}
}
