package edu.upenn.cis350.models;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.util.Password;

public class User extends Model {
	private String username;
	private String passwordHash;
	private String salt;
	

	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPasswordHash()
	{
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}
	
	public String getSalt()
	{
		return salt;
	}
	
	public void setSalt(String salt)
	{
		this.salt = salt;
	}

	public boolean checkPassword(String enteredPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		String hash = Password.hashPassword(enteredPassword,this.salt);
		return hash.equals(this.passwordHash);
	}
}
