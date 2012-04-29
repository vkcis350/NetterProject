package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.ActivityListActivity;
import edu.upenn.cis350.AttendanceAppActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.StudentSelectionActivity;
import edu.upenn.cis350.R.id;
import edu.upenn.cis350.localstore.UserDataSource;
import edu.upenn.cis350.models.User;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;

public class StudentSelectionTest extends
		ActivityInstrumentationTestCase2<AttendanceAppActivity> {

	final static String USERNAME = "tester1234somethingsomething";// Don't make
																	// a user
																	// with this
																	// username
	final static String PASSWORD = "letmein";

	public StudentSelectionTest(String name) throws Exception {
		super("edu.upenn.cis350", AttendanceAppActivity.class);
	}

	private Activity activity;
	private Solo solo;

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		activity = getActivity();

		UserDataSource userData = new UserDataSource(
				activity.getApplicationContext());
		userData.open();
		User user = userData.get(USERNAME);
		if (user != null)
			userData.delete(user);
		userData.create(USERNAME, PASSWORD);
		userData.close();

		// Login
		EditText userText = (EditText) solo.getView(R.id.user);
		EditText passwordText = (EditText) solo.getView(R.id.password);
		solo.enterText(userText, USERNAME);
		solo.enterText(passwordText, PASSWORD);
		solo.clickOnButton("Login");
		solo.clickOnButton("View Activities");
		solo.clickOnButton("View All Activities");

		// now in Activity List
		solo.clickOnText("Android Programming");
		solo.clickOnButton("Continue");
	}
	
	public void testCantContinue() throws Exception {
		assertFalse(solo.searchText("DEFAULT ACTIVITY"));
		assertTrue(solo.searchButton("Sort Students".trim()));
		assertTrue(solo.searchButton("Select All"));
		assertTrue(solo.searchButton("Continue"));
		
		solo.clickOnButton("Continue");
		assertTrue(solo.searchText("Please select some students first."));
		
		solo.clickOnButton("Select All");
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Please select just one student."));
		
		solo.clickOnButton("Deselect All");
		solo.clickOnButton("Continue");
		assertTrue(solo.searchText("Please select some students first."));
		
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Last Action:"));
		
		solo.goBack();
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Add New"));
		
	}
	
	/*public void testEverythingPresent() throws Exception {
		assertFalse(solo.searchText("DEFAULT ACTIVITY"));
		assertTrue(solo.searchButton("Sort Students".trim()));
		assertTrue(solo.searchButton("Select All"));
		assertTrue(solo.searchButton("Continue"));
	}
	
	public void testSelectAll() throws Exception
	{
		solo.clickOnButton("Select All");
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Please select just one student."));
	}
	
	public void testDeselectAll() throws Exception
	{
		solo.clickOnButton("Select All");
		solo.clickOnButton("Deselect All");
		solo.clickOnButton("Continue");
		assertTrue(solo.searchText("Please select some students first."));
	}
	
	public void testViewStudentInfo() throws Exception
	{
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnButton("View Student Info");
		assertTrue(solo.searchText("Last Action:"));
	}
	
	public void testViewStudentComments() throws Exception
	{
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Add New:"));
	}*/

	public void tearDown() throws Exception {
		super.tearDown();
	}
}
