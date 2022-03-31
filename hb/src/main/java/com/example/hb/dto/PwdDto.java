package com.example.hb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PwdDto {
	private String mId;
	private String mPwd;
	private String newPwd;
	
	public PwdDto(String mId, String mPwd, String newPwd) {
		this.mId = mId;
		this.mPwd = mPwd;
		this.newPwd = newPwd;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmPwd() {
		return mPwd;
	}
	public void setmPwd(String mPwd) {
		this.mPwd = mPwd;
	}
	public String getnewPwd() {
		return newPwd;
	}
	public void setnewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
}
