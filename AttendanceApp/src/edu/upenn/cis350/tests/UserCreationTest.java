package edu.upenn.cis350.tests;

import java.io.IOException;

import com.jayway.android.robotium.solo.Solo;

import edu.upenn.cis350.AttendanceAppActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.R.id;
import edu.upenn.cis350.UserCreationActivity;
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

public class UserCreationTest extends
	ActivityInstrumentationTestCase2<UserCreationActivity>
{
	final static String USERNAME = "new_person";//Don't make a user with this username
	final static String PASSWORD = "xiaowashere";
	private static final String USER_CREATION_CODE = "thas7";
	

	private Activity activity;
	private Solo solo;
	
	public UserCreationTest(String name) throws IOException 
	{
		super("edu.upenn.cis350",UserCreationActivity.class);
		setName(name);
		
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		activity = getActivity();
		FileBackup.backupDB();
		UserDataSource userData = new UserDataSource(activity.getApplicationContext());
		userData.upgrade();
		
		userData.open();
		
		userData.close();
	}
	 
	 public void testDisplayForms() throws Exception 
	 {
		 assertTrue(solo.searchText("Username"));
		 assertTrue(solo.searchText("Password"));
		 assertTrue(solo.searchText("Confirm Password"));
		 assertTrue(solo.searchText("User Creation Code"));
	 }

	 public void testDisplayButtons() throws Exception
	 {
		 assertTrue(solo.searchButton("Create"));
		 assertTrue(solo.searchButton("Quit"));
	 }
	 
	 public void testCreateUser()
	 {
		 EditText userText = (EditText) solo.getView(R.id.user);
		 EditText passwordText = (EditText) solo.getView(R.id.password);
		 EditText confirmPasswordText = (EditText) solo.getView(R.id.confirm);
		 EditText creationCodeText = (EditText) solo.getView(R.id.creation_code);
		 solo.enterText(userText, USERNAME);
		 solo.enterText(passwordText,PASSWORD);
		 solo.enterText(confirmPasswordText,PASSWORD);
		 solo.enterText(creationCodeText, USER_CREATION_CODE);
		 solo.clickOnButton("Create");
		 assertTrue(solo.searchText("User created."));
		 
		 UserDataSource userData = new UserDataSource(activity.getApplicationContext());
		 userData.open();
		 assertNotNull(userData.get(USERNAME));
		 userData.close();
	 }
	 
	 
	 public void tearDown() throws Exception
	 {
		 super.tearDown();
		 FileBackup.restoreDB();
	 }
	 
}
