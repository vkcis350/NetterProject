package edu.upenn.cis350.models;

public class Checkin extends Model {

	private long studentID;
	private long sessionID;
	private long activityID;
	private String comment;
	private boolean present;
	private long inTime;
	private long outTime;
	
	public Checkin()
	{
		comment = "None";
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
	
	public void setComment(String comment)
	{
		this.comment = comment;
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
	
	public String getComment(){
		return comment;
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
	



}
