package edu.upenn.cis350.models;

public class Checkin extends Model {

	private long studentid;
	private long activityid;
	private long intime;
	private long outtime;
	private long userid;
	private long lastchangetime;
	private String comment;
	private long siteid;
	
	public Checkin()
	{
	}
	
	public long getUserID(){
		return userid;
	}
	
	public void setUserID(long userId){
		this.userid=userId;
	}
	
	public void setStudentID(long studentID)
	{
		this.studentid = studentID;
	}
	
	public void setActivityID(long activityID)
	{
		this.activityid = activityID;
	}

	public long getActivityID() {
		// TODO Auto-generated method stub
		return activityid;
	}

	public long getStudentID() {
		// TODO Auto-generated method stub
		return studentid;
	}
	
	public void setInTime(long time) {
		this.intime = time;
	}

	public void setOutTime(long time) {
		this.outtime = time;
	}

	public long getInTime() {
		return intime;
	}
	
	public long getOutTime() {
		return outtime;
	}
	
	public long getLastChangeTime() {
		return lastchangetime;
	}
	
	public void setLastChangeTime(long time) {
		lastchangetime = time;
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

	public boolean markAbsent(long time) {
		if (!absent()) {
			intime=-1;
			outtime=-1;
			setLastChangeTime(time);
			return true;
		}
		return false;
	}
	
	public boolean checkIn(long time) {
		if ( neverCheckedIn() ) {
			setInTime(time);
			setLastChangeTime(time);
			return true;
		}
		return false;
	}
	
	public boolean checkOut(long time) {
		if ( checkedIn() ) {
			setOutTime(time);
			setLastChangeTime(time);
			return true;
		}
		return false;
 	}
	
	public void setSiteId(long siteId)
	{
		this.siteid = siteId;
	}
	
	public long getSiteId()
	{
		return siteid;
	}


}
