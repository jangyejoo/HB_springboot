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
    private int result;

    public AuthenticationResponse() {
    	
    }
    
    public AuthenticationResponse(int result, String jwt) {
    	this.setResult(result);
        this.setToken(jwt);
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
}