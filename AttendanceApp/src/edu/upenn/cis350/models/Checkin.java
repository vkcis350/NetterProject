package edu.upenn.cis350.models;

public class Checkin extends Model {

	private long studentID;
	private long activityID;
	private long inTime;
	private long outTime;
	private long userId;
	private long lastChangeTime;
	private String comment;
	private long siteId;
	
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
	
	
	public void setActivityID(long activityID)
	{
		this.activityID = activityID;
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
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public boolean absent()
	{
		return ( getInTime()<0 && getOutTime()<0 );
	}
	
	public boolean checkedIn()
	{
		return getInTime()>0 && getOutTime()<=0;
	}
	
	public boolean checkedOut()
	{
		return getInTime()>0 && getOutTime()>getInTime();
	}
	
	/**
	 * 
	 * @return if the student is in the default state (i.e., never checked in or out)
	 */
	public boolean defaultState()
	{
		return getInTime()==0 && getOutTime()==0;
	}

	/**
	 * 
	 * @return if the student has never been checked in on this day
	 */
	public boolean neverCheckedIn() {
		// TODO Auto-generated method stub
		return getInTime()<=0 && getOutTime()<=0;
	}

	public boolean markAbsent() {
		if (!absent()) {
			inTime=-1;
			outTime=-1;
			return true;
		}
		return false;
	}
	
	public boolean checkIn(long time) {
		if ( neverCheckedIn() ) {
			setInTime(time);
			return true;
		}
		return false;
	}
	
	public boolean checkOut(long time) {
		if ( checkedIn() ) {
			setOutTime(time);
			return true;
		}
		return false;
 	}
	
	public void setSiteId(long siteId)
	{
		this.siteId = siteId;
	}
	
	public long getSiteId()
	{
		return siteId;
	}


}
