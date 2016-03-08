package com.pc.domain;

/**
 * 
 * @author Switch
 * ���ܣ��û���Ϣ��
 * id---------id
 * name-------�û���
 * password---����
 * email------�����ʼ�
 * grade------�ȼ�
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
	// �������ͻ�ȡ��
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
