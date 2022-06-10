package com.example.oilimit.model.cari;

import com.google.gson.annotations.SerializedName;

public class CariData {

	@SerializedName("namaa")
	private String namaa;

	@SerializedName("literr")
	private String literr;

	public void setNamaa(String namaa){
		this.namaa = namaa;
	}

	public String getNamaa(){
		return namaa;
	}

	public void setLiterr(String literr){
		this.literr = literr;
	}

	public String getLiterr(){
		return literr;
	}
}