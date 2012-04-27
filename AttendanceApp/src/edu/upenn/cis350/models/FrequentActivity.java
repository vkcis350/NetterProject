package edu.upenn.cis350.models;

public class FrequentActivity extends Model {
	long userId;
	long activityId;
	
	public long getUserId(){
		return userId;
	}
	
	public void setUserId(long userId){
		this.userId=userId;
	}
	
	public long getActivityId() {
		return activityId;
	}
	
	public void setActivityId(long activityId)
	{
		this.activityId = activityId;
	}
	

	

}
