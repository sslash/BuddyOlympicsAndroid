package com.awezumTree.buddyolympics.schemas;

/*
username : {type : String, unique : true, required : true},
password : {type : String, required : true},
fullname : String,
email : {type : String, unique : true, required : true},
birthday : Date,
gender : String,
country : String,
city : String,
datejoined : Date,
rank : Number,
interests : String,
ambition : String,
description : String,
profilepic : String,
friends : [ObjectId],
achievements : [ObjectId],
runs : [ObjectId]
*/
public class RunnersSchema {
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	public final static String FULLNAME = "fullname";
	public final static String EMAIL = "email";
	public final static String BIRTHDAY = "birthday";
	public final static String GENDER = "String";
	public final static String COUNTRY = "country";
	public final static String CITY = "city";
	public final static String DATEJOINED = "datejoined";
	public final static String RANK = "number";
	public final static String INTERESTS = "interests";
	public final static String AMBITION = "ambition";
	public final static String DESCRIPTION = "description";
	public final static String PROFILEPIC = "profilepic";
	public final static String FRIENDS = "friends";
	public final static String ACHIEVEMENTS = "achievements";
	public final static String RUNS = "runs";
	public final static String NEW_RUNS = "newruns";
	
}
