package com.demo.pojo;

import java.util.Date;
/**
 * (finance)
 */ 
public class Finance{

	private int id;//
	private int roomId;//房间id
	private String source;//收支来源
	private int money;//收支情况，包括收入和支出
	private String time;//时间

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
	public void setSource(String source){
		this.source=source;
	}
	public String getSource(){
		return source;
	}
	public void setMoney(int money){
		this.money=money;
	}
	public int getMoney(){
		return money;
	}
	public void setTime(String time){
		this.time=time;
	}
	public String getTime(){
		return time;
	}
	public String toString() {
		return "finance{" + 
			"id=" + id + 
			", roomId=" + roomId + 
			", source=" + source + 
			", money=" + money + 
			", time=" + time + 
			"}";
	}
}

