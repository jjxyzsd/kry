package com.demo.pojo;

import java.util.Date;
/**
 * (retire)
 */ 
public class Retire{

	private int id;//id
	private int roomId;//房间id
	private int money;//房间设备维护金额
	private String remark;//备注（房间设备损坏和维护情况）

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
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public String toString() {
		return "retire{" + 
			"id=" + id + 
			", roomId=" + roomId + 
			", money=" + money + 
			", remark=" + remark + 
			"}";
	}
}

