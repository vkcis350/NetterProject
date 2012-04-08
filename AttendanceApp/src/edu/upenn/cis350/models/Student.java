package edu.upenn.cis350.models;
//testing new branch
public class Student extends Model implements Comparable {
	private String firstName;
	private String lastName;
	private String address;
	//private long id;
	private String phone;
	private int grade;
	private String contact;
	private String contactRelation;
	private long schoolID;
	private long siteID;
	private long schoolYear;
	
	public Student(String name) {
		this.firstName = name;
		this.id = -1;//id is -1 if not written to database yet
	}
	

	public Student() {
		// TODO Auto-generated constructor stub
	}


	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}


	public void setFirstName(String name) {
		this.firstName = name;
		
	}
	
	public void setLastName(String name) {
		this.lastName = name;
		
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
		return getGrade()+"  "+lastName+", "+firstName;
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
		this.contactRelation = contactRelation;
	}
	
	public String getContactRelation()
	{
		return contactRelation;
	}
	
	public void setSchoolID(long schoolID)
	{
		this.schoolID = schoolID;
	}
	
	public long getSchoolID()
	{
		return schoolID;
	}
	
	public void setSiteID(long siteID)
	{
		this.siteID = siteID;
	}
	
	public long getSiteID()
	{
		return siteID;
	}
	
	public void setSchoolYear(long schoolYear)
	{
		this.schoolYear = schoolYear;
	}
	
	public long getSchoolYear()
	{
		return schoolYear;
	}
	


}