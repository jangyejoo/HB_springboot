package com.example.hb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IdDto {
	private String mId;
	
	public IdDto(String mId) {
		this.mId = mId;
	}
	public String getmId() {
		return mId;	}
	public void setmId(String mId) {
		this.mId = mId;
	}
}

