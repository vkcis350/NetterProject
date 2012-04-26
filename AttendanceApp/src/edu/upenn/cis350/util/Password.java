package edu.upenn.cis350.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;

public class Password {
	private String salt;
	private String password;
	public Password(String password)
	{
		this.salt = generateSalt();
		this.password = password;
	}
	public static String hashString(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		byte[] strBytes = str.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		final byte[] resultBytes = md.digest(strBytes);
		BigInteger bigInt = new BigInteger(1,resultBytes);
		String hash = bigInt.toString(16);
		return hash;
	}
	
	public static String hashPassword(String password, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return hashString(password+salt);
	}
	
	public static String generateSalt()
	{
		return RandomStringUtils.random(10);
	}
	
	
	public String hashPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		return hashPassword(this.password,this.salt);
	}
	
	public String getSalt()
	{
		return salt;
	}
	

}
