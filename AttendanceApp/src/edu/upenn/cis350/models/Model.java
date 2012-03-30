package edu.upenn.cis350.models;

public abstract class Model {
	protected long id;
	
	public long getID() {
		return id;
	}
	
	public void setID(long id) {
		this.id = id;
	}
	
	//we probably want to implement compareTo here (on id) and override in later inherited classes if necessary

}
