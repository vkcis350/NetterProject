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

public class StudentInfoTest extends
		ActivityInstrumentationTestCase2<StudentSelectionActivity> {

	public StudentInfoTest(String name) throws Exception {
		super("edu.upenn.cis350", StudentSelectionActivity.class);
		setName(name);
		Intent i = new Intent();
		i.putExtra("ACTIVITY_NAME", "Test Activity");
		i.putExtra("ACTIVITY_ID", 100072);
		setActivityIntent(i);

	}

	private Activity activity;
	private Solo solo;

	public void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("ACTIVITY_NAME", "Test Activity");
		i.putExtra("ACTIVITY_ID", 100072);
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
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Last Action:"));
		assertTrue(solo.searchText("Grade Level:"));
		assertTrue(solo.searchText("Contact:"));
		assertTrue(solo.searchText("Contact Relationship:"));
		assertTrue(solo.searchText("Phone Number:"));
		assertTrue(solo.searchText("Address:"));
		solo.goBack();
	}

	public void testData() throws Exception {
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Sun, Yat-sen"));
		assertTrue(solo.searchText("Checked"));
		assertTrue(solo.searchText("12"));
		assertTrue(solo.searchText("Chiang"));
		assertTrue(solo.searchText("eventual"));
		assertTrue(solo.searchText("PETMEDS"));
		assertTrue(solo.searchText("Nanjing"));
		solo.goBack();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		FileBackup.restoreDB();
	}
}
