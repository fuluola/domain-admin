package com.fuluola.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author fuluola
 *
 */
@Repository
public class ContactRepository {

	private JdbcTemplate jdbc;
	
	public ContactRepository(JdbcTemplate jdbc){
		this.jdbc = jdbc;
	}
	
	public List<Contact> findAll(){
		List<Contact> contacts = new ArrayList<Contact>();
		Contact c1= new Contact();
		c1.setFirstName("傅");
		c1.setLastName("罗拉");
		c1.setPhoneNumber("15308484001");
		Contact c2 = new Contact();
		c2.setFirstName("e-");
		c2.setLastName("mail");
		c2.setPhoneNumber("fu.luola@qq.com");
		contacts.add(c1);
		contacts.add(c2);
		return contacts;
	}
}
