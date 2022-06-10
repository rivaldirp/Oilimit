package com.example.oilimit.model.cari;

import com.google.gson.annotations.SerializedName;

public class Cari{

	@SerializedName("data")
	private CariData cariData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(CariData cariData){
		this.cariData = cariData;
	}

	public CariData getData(){
		return cariData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}