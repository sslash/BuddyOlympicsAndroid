package com.awezumTree.buddyolympics.schemas;

/*
 starttime : Date,
type : {
    time : Number,
    distance : Number,
    avgspeed : Number,
    topspeed : Number
},
participants : [{
    runner : ObjectId,
    time : Number,
    distance : Number,
    owner : Boolean,
    finished : Boolean,
    coordinates : [{
        longitude : Number,
        latitude : Number,
        timestamp : Number
    }]
}],
finished : Boolean,
winner : ObjectId
 */
public class RunSchema {
	public final static String STARTTIME = "starttime";
	public final static String TYPE = "type";
	public final static String PARTICIPANTS = "participants";
	public final static String FINISHED = "finished";
	public final static String WINNER = "winner";
}
