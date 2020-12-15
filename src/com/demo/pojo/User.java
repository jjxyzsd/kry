package com.demo.pojo;

import java.util.Date;
/**
 * (user)
 */ 
public class User{

	private int id;//id
	private String phone;//手机号码，登录注册账号
	private String password;//登录密码
	private String name;//姓名
	private String sex;//性别

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	public String getPhone(){
		return phone;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setSex(String sex){
		this.sex=sex;
	}
	public String getSex(){
		return sex;
	}
	public String toString() {
		return "user{" + 
			"id=" + id + 
			", phone=" + phone + 
			", password=" + password + 
			", name=" + name + 
			", sex=" + sex + 
			"}";
	}
}

