package com.awezumTree.buddyolympics.domain;

public class FriendFactory {

	public static Friend createFriend(String username) {
		Friend friend = new Friend();
		friend.setUsername(username);
		return friend;
	}
}
