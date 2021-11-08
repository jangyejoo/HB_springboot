package com.example.hb.models;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest{
    private String mId;
    private String mPwd;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String mId, String mPwd) {
        this.setUsername(mId);
        this.setPassword(mPwd);
    }

	public String getUsername() {
		return mId;
	}

	public void setUsername(String mId) {
		this.mId =mId;
	}

	public String getPassword() {
		return mPwd;
	}

	public void setPassword(String mPwd) {
		this.mPwd = mPwd;
	}
}
