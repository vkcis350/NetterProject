package edu.upenn.cis350.models;

public class SchoolActivity extends Model implements Comparable {
	//private long id;
	private String name;
	

	public SchoolActivity(String name) {
		this.setName(name);
	}

	public SchoolActivity() {
		// TODO Auto-generated constructor stub
	}
/*
	public long getID() {
		return id;
	}
*/	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
/*	
	public void setID(long id) {
		this.id = id;
	}
*/
	public String toString()
	{
		return name;
	}

	public int compareTo(Object arg0) {//we're comparing on names, probably want to compare on ids
		// TODO Auto-generated method stub
		return this.toString().compareTo(arg0.toString());
	}
	
}
