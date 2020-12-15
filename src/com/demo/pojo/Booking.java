package com.demo.pojo;

/**
 * (booking)
 */ 
public class Booking{

	private int id;//id
	private int roomId;//房间id
	private int money;//收入金额
	private String name;//姓名
	private String sex;//性别
	private String phone;//手机号码
	private String bookingState;//订单状态有四种：预订，未完成，取消，完成

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setRoomId(int roomId){
		this.roomId=roomId;
	}
	public int getRoomId(){
		return roomId;
	}
	public void setMoney(int money){
		this.money=money;
	}
	public int getMoney(){
		return money;
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
	public void setPhone(String phone){
		this.phone=phone;
	}
	public String getPhone(){
		return phone;
	}
	public void setBookingState(String bookingState){
		this.bookingState=bookingState;
	}
	public String getBookingState(){
		return bookingState;
	}
	public String toString() {
		return "booking{" + 
			"id=" + id + 
			", roomId=" + roomId + 
			", money=" + money + 
			", name=" + name + 
			", sex=" + sex + 
			", phone=" + phone + 
			", bookingState=" + bookingState + 
			"}";
	}
}

