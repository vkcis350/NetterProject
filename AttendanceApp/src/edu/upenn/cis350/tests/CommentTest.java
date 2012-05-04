package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.StudentDataActivity;
import edu.upenn.cis350.AttendanceAppActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.StudentSelectionActivity;
import edu.upenn.cis350.R.id;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.localstore.TemporaryDbInsert;
import edu.upenn.cis350.localstore.UserDataSource;
import edu.upenn.cis350.models.User;
import edu.upenn.cis350.util.FileBackup;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;

public class CommentTest extends
		ActivityInstrumentationTestCase2<StudentSelectionActivity> {

	public CommentTest(String name) throws Exception {
		super("edu.upenn.cis350", StudentSelectionActivity.class);
		setName(name);
		Intent i = new Intent();
		i.putExtra("ACTIVITY_NAME", "Test Activity");
		i.putExtra("ACTIVITY_ID", "1");
		setActivityIntent(i);

	}

	private Activity activity;
	private Solo solo;

	public void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("ACTIVITY_NAME", "Test Activity");
		i.putExtra("ACTIVITY_ID", "1");
		setActivityIntent(i);
		solo = new Solo(getInstrumentation(), getActivity());
		activity = getActivity();

		StudentDataSource datasource = new StudentDataSource(
				activity.getApplicationContext());

		FileBackup.backupDB();
		datasource.upgrade();
		TemporaryDbInsert.insert(activity.getApplicationContext());

		datasource.close();

	}

	public void testEverythingPresent() throws Exception {
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("Leave Comment");
		assertTrue(solo.searchText("Sun"));
		assertTrue(solo.searchButton("Add New"));
		assertTrue(solo.searchText("Back"));
		solo.clickOnButton("Add New");
		assertTrue(solo.searchText("Sun"));
		assertTrue(solo.searchButton("Submit"));
		assertTrue(solo.searchButton("Cancel"));
		solo.goBack();
		solo.goBack();
	}

	public void testBack() throws Exception {
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("Leave Comment");
		solo.clickOnButton("Add New");
		solo.clickOnButton("Cancel");
		solo.clickOnButton("Back");
		assertTrue(solo.searchButton("Sort Students"));
	}
	
	public void testAdd() throws Exception {
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("Leave Comment");
		solo.clickOnButton("Add New");
		EditText text = solo.getEditText("");
		solo.typeText(text, "Test");
		//solo.
		//solo.typeText(text, "Test");
		solo.clickOnButton("Submit");
		solo.goBack();
		assertTrue(solo.searchText("Test"));
		solo.goBack();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		FileBackup.restoreDB();
	}
}
