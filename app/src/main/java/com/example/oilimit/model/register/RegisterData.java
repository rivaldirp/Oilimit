package com.example.oilimit.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName(":handphone")
	private String handphone;

	@SerializedName(":nama")
	private String nama;

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
}