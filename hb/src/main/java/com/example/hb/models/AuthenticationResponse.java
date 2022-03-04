package com.example.hb.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
//@Data
@Getter
@Setter
public class AuthenticationResponse {
    private String token;
    private String token_refresh;
    private int result;

    public AuthenticationResponse() {
    	
    }
    
    public AuthenticationResponse(int result, String jwt, String jwt_refresh) {
    	this.setResult(result);
        this.setToken(jwt);
        this.setToken_refresh(jwt_refresh);
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getToken_refresh() {
		return token_refresh;
	}

	public void setToken_refresh(String token_refresh) {
		this.token_refresh = token_refresh;
	}
}