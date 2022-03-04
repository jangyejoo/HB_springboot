package com.example.hb.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
	private String jwt;
	private String jwt_refresh;
	private String result;
	
	public TokenResponse() {
    	
    }
    
    public TokenResponse(String jwt, String jwt_refresh, String result) {
        this.setJwt(jwt);
        this.setJwt_refresh(jwt_refresh);
        this.setResult(result);
    }

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt_refresh() {
		return jwt_refresh;
	}

	public void setJwt_refresh(String jwt_refresh) {
		this.jwt_refresh = jwt_refresh;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
