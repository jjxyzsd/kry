package com.demo.pojo;

import java.util.Date;
/**
 * (lodging)
 */ 
public class Lodging{

	private int id;//
	private int roomId;//房间id
	private String time;//时间
	private String state;//房间状态：预订，入住，离开

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
	public void setTime(String time){
		this.time=time;
	}
	public String getTime(){
		return time;
	}
	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return state;
	}
	public String toString() {
		return "lodging{" + 
			"id=" + id + 
			", roomId=" + roomId + 
			", time=" + time + 
			", state=" + state + 
			"}";
	}
}

