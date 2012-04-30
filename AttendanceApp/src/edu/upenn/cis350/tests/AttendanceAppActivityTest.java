package edu.upenn.cis350.tests;

import java.io.IOException;

import com.jayway.android.robotium.solo.Solo;

import edu.upenn.cis350.AttendanceAppActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.R.id;
import edu.upenn.cis350.localstore.TemporaryDbInsert;
import edu.upenn.cis350.localstore.UserDataSource;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.User;
import edu.upenn.cis350.util.FileBackup;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AttendanceAppActivityTest extends
	ActivityInstrumentationTestCase2<AttendanceAppActivity>
{
	final static String USERNAME = "user";//Don't make a user with this username
	final static String PASSWORD = "1";
	final static String WRONG_PASSWORD = "wrong";

	private Activity activity;
	private Solo solo;
	
	public AttendanceAppActivityTest(String name) throws IOException 
	{
		super("edu.upenn.cis350",AttendanceAppActivity.class);
		setName(name);
		
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		activity = getActivity();
		FileBackup.backupDB();
		
		UserDataSource userData = new UserDataSource(activity.getApplicationContext());
		userData.open();
		userData.create("temporary", "nothere");
		userData.upgrade();
		TemporaryDbInsert.insert(getActivity());
		
		userData.close();
	}
	 
	 public void testDisplayLogin() throws Exception 
	 {
		 assertTrue(solo.searchText("Username"));
		 assertTrue(solo.searchText("Password"));
		 assertTrue(solo.searchButton("Login"));
	 }

	 public void testLoginFail() throws Exception
	 {
		 EditText userText = (EditText) solo.getView(R.id.user);
		 EditText passwordText = (EditText) solo.getView(R.id.password);
		 solo.enterText(userText, USERNAME);
		 solo.enterText(passwordText, WRONG_PASSWORD);
		 solo.clickOnButton("Login");
		 assertTrue(solo.searchText("Login Failed"));
	 }
	 
	 public void testLoginSuccess() throws Exception
	 {
		 EditText userText = (EditText) solo.getView(R.id.user);
		 EditText passwordText = (EditText) solo.getView(R.id.password);
		 solo.enterText(userText, USERNAME);
		 solo.enterText(passwordText, PASSWORD);
		 solo.clickOnButton("Login");
		 assertTrue(solo.searchButton("View Activities"));
	 }
	 
	 public void tearDown() throws Exception
	 {
		 super.tearDown();
		 FileBackup.restoreDB();
	 }
	 
}
