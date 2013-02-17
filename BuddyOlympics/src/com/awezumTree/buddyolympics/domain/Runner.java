package com.awezumTree.buddyolympics.domain;

import java.io.Serializable;
import java.util.List;

public class Runner implements Serializable {
	
	private String username;
	private String runner;	
	private String id;
	
	private List <Run> newRuns;
	
	public Runner() {}
	
	public List<Run> getNewRuns() {
		return newRuns;
	}
	
	public void setNewRuns(List<Run> newRuns) {
		this.newRuns = newRuns;
	}



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
		return "Runner [username=" + username + ", runner=" + runner + ", id="
				+ id + "]";
	}

	public String getRunner() {
		return runner;
	}

	public void setRunner(String runner) {
		this.runner = runner;
	}
}
