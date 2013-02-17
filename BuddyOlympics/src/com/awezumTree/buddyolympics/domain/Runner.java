package com.awezumTree.buddyolympics.domain;

import java.io.Serializable;

public class Runner implements Serializable {
	
	private String username;
	private String runner;
	
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

	public String getRunner() {
		return runner;
	}

	public void setRunner(String runner) {
		this.runner = runner;
	}
}
