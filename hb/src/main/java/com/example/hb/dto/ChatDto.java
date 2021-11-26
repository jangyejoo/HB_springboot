package com.example.hb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChatDto {
	private String crId;
	private String cId;
	private String pNickname;
	private String pImg;
	
	public String getpImg() {
		return pImg;
	}
	public void setpImg(String pImg) {
		this.pImg = pImg;
	}
	public String getpNickname() {
		return pNickname;
	}
	public void setpNickname(String pNickname) {
		this.pNickname = pNickname;
	}
	public String getCrId() {
		return crId;
	}
	public void setCrId(String crId) {
		this.crId = crId;
	}
	public String getcId() {
		return cId;
	}
	public void setpId(String cId) {
		this.cId = cId;
	}
}
