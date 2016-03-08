package com.pc.domain;

/**
 * 
 * @author Switch
 * 功能：用户信息类
 * id---------id
 * name-------用户名
 * password---密码
 * email------电子邮件
 * grade------等级
 */
public class User {
	private int id;
	private String name;
	
	
	public User() {
	}
	public User(int id,String name, String password, String email, int grade) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.grade = grade;
	}
	public User(String name, String password, String email, int grade) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.grade = grade;
	}
	
	private String password;
	private String email;
	private int grade;
	// 设置器和获取器
	public int getId() {
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
}
