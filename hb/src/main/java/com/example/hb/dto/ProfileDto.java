package com.example.hb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfileDto {
	private String pId;
	private String pNickname;
	private String pGym;
	private String pAge;
	private String pHeight;
	private String pWeight;
	private int pSex;
	private String pRoutine;
	private String pDetail;
	private String pImg;
	private int pOpen;
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpNickname() {
		return pNickname;
	}
	public void setpNickname(String pNickname) {
		this.pNickname = pNickname;
	}
	public String getpGym() {
		return pGym;
	}
	public void setpGym(String pGym) {
		this.pGym = pGym;
	}
	public String getpAge() {
		return pAge;
	}
	public void setpAge(String pAge) {
		this.pAge = pAge;
	}
	public String getpHeight() {
		return pHeight;
	}
	public void setpHeight(String pHeight) {
		this.pHeight = pHeight;
	}
	public String getpWeight() {
		return pWeight;
	}
	public void setpWeight(String pWeight) {
		this.pWeight = pWeight;
	}
	public int getpSex() {
		return pSex;
	}
	public void setpSex(int pSex) {
		this.pSex = pSex;
	}
	public String getpRoutine() {
		return pRoutine;
	}
	public void setpRoutine(String pRoutine) {
		this.pRoutine = pRoutine;
	}
	public String getpDetail() {
		return pDetail;
	}
	public void setpDetail(String pDetail) {
		this.pDetail = pDetail;
	}
	public String getpImg() {
		return pImg;
	}
	public void setpImg(String pImg) {
		this.pImg = pImg;
	}
	public int getpOpen() {
		return pOpen;
	}
	public void setpOpen(int pOpen) {
		this.pOpen = pOpen;
	}
}
