package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.util.Password;
import android.test.AndroidTestCase;

public class PasswordTest extends AndroidTestCase {
	public void testPasswordHash() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		Password pass = new Password("password");
		String passwordHash = Password.hashPassword("password", "salt");
		assertEquals(passwordHash, "7a37b85c8918eac19a9089c0fa5a2ab4dce3f90528dcdeec108b23ddf3607b99");
	}

}
