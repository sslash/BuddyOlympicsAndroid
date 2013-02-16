package com.awezumTree.buddyolympics.domain;

import java.util.Set;

public class User extends Runner{	
	private String email;	
	private String password;
	private Set<Friend> friendlist;

	public Set<Friend> getFriendlist() {
		return friendlist;
	}

	public void setFriendlist(Set<Friend> friends) {
		this.friendlist = friends;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User ["+ super.toString()+ ", email=" + email
				+ ", password=" + password + ", friends=" + friendlist.toString() + "]";
	}
	
}
