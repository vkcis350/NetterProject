package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.test.AndroidTestCase;
import edu.upenn.cis350.models.User;

public class UserTest extends AndroidTestCase {
	//See UserDataTest for more extensive tests which involve the database
	public void testSetUsername()
	{
		final String NAME = "myname";
		User user = new User();
		user.setUsername(NAME);
		assertEquals(NAME,user.getUsername());
	}
	
	public void testSetPasswordHash() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		String HASH = "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb";
		User user = new User();
		user.setPasswordHash(HASH);
		assertEquals(user.getPasswordHash(),HASH);
	}

}
