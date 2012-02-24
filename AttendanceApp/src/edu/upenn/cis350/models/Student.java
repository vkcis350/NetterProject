package edu.upenn.cis350.models;

public class Student {
	private String name;
	private long id;
	
	public Student(String name, int id) {
		this.name=name;
		this.id=id;
	}
	

	public Student() {
		// TODO Auto-generated constructor stub
	}



	public long getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
		
	}

	public void setName(String name) {
		this.name = name;
		
	}
	
	public String toString()
	{
		return name;
	}
	
	
	
	

}