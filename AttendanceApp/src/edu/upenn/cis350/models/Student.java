package edu.upenn.cis350.models;
//testing new branch
public class Student extends Model implements Comparable {
	private String firstname;
	private String lastname;
	private String address;
	private String phone;
	private int grade;
	private String contact;
	private String contactrelation;
	private long schoolid;
	private long siteid;
	private long schoolyear;
	


	public String getFirstName() {
		return firstname;
	}

	public String getLastName() {
		return lastname;
	}


	public void setFirstName(String name) {
		this.firstname = name;
		
	}
	
	public void setLastName(String name) {
		this.lastname = name;
		
	}
	
	public void setAddress(String address){
		this.address=address;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setGrade(int grade){
		this.grade=grade;
	}
	
	public int getGrade(){
		return grade;
	}
	
	public String toString()
	{
		return getGrade()+"  "+lastname+", "+firstname;
	}


	public int compareTo(Object arg0) {
		return this.toString().compareTo( arg0.toString() );
	}
/*	
	public void setID(long id)
	{
		this.id=id;
	}
	
	public long getID()
	{
		return id;
	}
*/	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	
	public String getContact()
	{
		return contact;
	}
	
	public void setContactRelation(String contactRelation)
	{
		this.contactrelation = contactRelation;
	}
	
	public String getContactRelation()
	{
		return contactrelation;
	}
	
	public void setSchoolID(long schoolID)
	{
		this.schoolid = schoolID;
	}
	
	public long getSchoolID()
	{
		return schoolid;
	}
	
	public void setSiteID(long siteID)
	{
		this.siteid = siteID;
	}
	
	public long getSiteID()
	{
		return siteid;
	}
	
	public void setSchoolYear(long schoolYear)
	{
		this.schoolyear = schoolYear;
	}
	
	public long getSchoolYear()
	{
		return schoolyear;
	}
	


}