package com.example.hb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MessageDto {
	private String mgSender;
	private String mgDetail;
	private String mgDate;
	private String pNickname;
	private String pImg;
	public String getMgSender() {
		return mgSender;
	}
	public void setMgSender(String mgSender) {
		this.mgSender = mgSender;
	}
	public String getMgDetail() {
		return mgDetail;
	}
	public void setMgDetail(String mgDetail) {
		this.mgDetail = mgDetail;
	}
	public String getpNickname() {
		return pNickname;
	}
	public void setpNickname(String pNickname) {
		this.pNickname = pNickname;
	}
	public String getMgDate() {
		return mgDate;
	}
	public void setMgDate(String mgDate) {
		this.mgDate = mgDate;
	}
	public String getpImg() {
		return pImg;
	}
	public void setpImg(String pImg) {
		this.pImg = pImg;
	}

}
