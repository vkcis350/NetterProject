package edu.upenn.cis350.models;

public class Student extends Model implements Comparable {
	private String name;
	private long id;
	
	public Student(String name) {
		this.name = name;
		this.id = -1;//id is -1 if not written to database yet
	}
	

	public Student() {
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
		
	}
	
	public String toString()
	{
		return name;
	}


	public int compareTo(Object arg0) {
		return this.toString().compareTo( arg0.toString() );
	}
	
	
	
	

}