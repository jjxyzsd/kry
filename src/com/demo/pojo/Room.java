package com.demo.pojo;

import java.util.Date;
/**
 * (room)
 */ 
public class Room{

	private int id;//id
	private int sequence;//房间号
	private String roomType;//房间类型
	private int price;//房间价格
	private String message;//房间信息

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setSequence(int sequence){
		this.sequence=sequence;
	}
	public int getSequence(){
		return sequence;
	}
	public void setRoomType(String roomType){
		this.roomType=roomType;
	}
	public String getRoomType(){
		return roomType;
	}
	public void setPrice(int price){
		this.price=price;
	}
	public int getPrice(){
		return price;
	}
	public void setMessage(String message){
		this.message=message;
	}
	public String getMessage(){
		return message;
	}
	public String toString() {
		return "room{" + 
			"id=" + id + 
			", sequence=" + sequence + 
			", roomType=" + roomType + 
			", price=" + price + 
			", message=" + message + 
			"}";
	}
}

