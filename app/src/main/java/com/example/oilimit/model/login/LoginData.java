package com.example.oilimit.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("handphone")
	private String handphone;

	@SerializedName("nama")
	private String nama;

	@SerializedName("passwordd")
	private String passwordd;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("liter")
	private String liter;

	public void setHandphone(String handphone){
		this.handphone = handphone;
	}

	public String getHandphone(){
		return handphone;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setPasswordd(String passwordd){
		this.passwordd = passwordd;
	}

	public String getPasswordd(){
		return passwordd;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setLiter(String liter){
		this.liter = liter;
	}

	public String getLiter(){
		return liter;
	}
}