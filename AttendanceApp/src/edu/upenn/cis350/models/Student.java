package edu.upenn.cis350.models;

public class Student extends Model implements Comparable {
	private String firstName;
	private String lastName;
	private long id;
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
		return firstName;
	}


	public void setFirstName(String name) {
		this.firstName = name;
		
	}
	
	public void setLastName(String name) {
		this.lastName = name;
		
	}
	
	public String toString()
	{
		return lastName+", "+firstName;
	}


	public int compareTo(Object arg0) {
		return this.toString().compareTo( arg0.toString() );
	}
	
	public void setID(long id)
	{
		this.id=id;
	}
	
	public long getID()
	{
		return id;
	}
	
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
	
	public void setSchoolID(long id)
	{
		this.schoolID = id;
	}
	
	public long getSchoolID()
	{
		return schoolID;
	}
	
	public void setSiteID(long id)
	{
		this.siteID = id;
	}
	
	public long getSiteID()
	{
		return siteID;
	}
	
	public void setSchoolYear(long l)
	{
		this.schoolYear = l;
	}
	
	public long getSchoolYear()
	{
		return schoolYear;
	}
	
	

}