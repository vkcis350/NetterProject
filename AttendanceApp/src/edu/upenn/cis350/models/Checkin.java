package edu.upenn.cis350.models;

public class Checkin extends Model {

	private long studentID;
	private long sessionID;
	private long activityID;
	private long inTime;
	private long outTime;
	private long userId;
	private long lastChangeTime;
	
	public Checkin()
	{
	}
	
	public long getUserID(){
		return userId;
	}
	
	public void setUserID(long userId){
		this.userId=userId;
	}
	
	public void setStudentID(long studentID)
	{
		this.studentID = studentID;
	}
	
	public void setSessionID(long sessionID)
	{
		this.sessionID = sessionID;
	}
	
	public void setActivityID(long activityID)
	{
		this.activityID = activityID;
	}
	

	public long getSessionID() {
		// TODO Auto-generated method stub
		return sessionID;
	}

	public long getActivityID() {
		// TODO Auto-generated method stub
		return activityID;
	}

	public long getStudentID() {
		// TODO Auto-generated method stub
		return studentID;
	}
	
	public void setInTime(long time) {
		this.inTime = time;
	}

	public void setOutTime(long time) {
		this.outTime = time;
	}

	public long getInTime() {
		return inTime;
	}
	
	public long getOutTime() {
		return outTime;
	}
	
	public long getLastChangeTime() {
		return lastChangeTime;
	}
	
	public void setLastChangeTime(long time) {
		lastChangeTime = time;
	}


}
