package com.awezumTree.buddyolympics.domain;

import java.io.Serializable;

public class Runner implements Serializable {
	public final static String USERNAME = "username";
	
	private String username;
	
	private String id;
	
	public Runner() {}
	
	public Runner(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
