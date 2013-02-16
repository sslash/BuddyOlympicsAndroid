package com.awezumTree.buddyolympics.domain;

import java.io.Serializable;

public abstract class Runner implements Serializable {
	public final static String USERNAME = "username";
	
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "username: '" + username+ "'";
	}
}
