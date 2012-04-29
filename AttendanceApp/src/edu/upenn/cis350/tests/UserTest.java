package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.UserDataSource;
import edu.upenn.cis350.models.User;
import edu.upenn.cis350.util.Password;

public class UserTest extends AbstractDataTest {
	private UserDataSource userData;

	public void setUp() throws Exception
	{
		super.setUp();
		userData = new UserDataSource(context);
		userData.open();
	}
	
	public void testPasswordHash() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		Password pass = new Password("password");
		String passwordHash = Password.hashPassword("password", "salt");
		assertEquals(passwordHash, "7a37b85c8918eac19a9089c0fa5a2ab4dce3f90528dcdeec108b23ddf3607b99");
	}
	
	public void testPasswordCheck() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		User user = userData.create("admin","1");
		assertTrue( user.checkPassword("1") );//"1" is the right password
	}
	
	public void testGetUser() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		User user = userData.create("napoleon", "leipzig");
		User user2 = userData.get("napoleon");
		assertEquals(user2.getUsername(),"napoleon");
	}
	
	public void testLogin() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		User user = userData.create("napoleon", "leipzig");
		user = userData.get("napoleon");
		user.checkPassword("leipzig");
	}
	
	public void tearDown()
	{
		userData.close();
	}

}
