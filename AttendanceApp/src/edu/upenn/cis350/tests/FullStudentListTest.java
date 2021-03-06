package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.ActivityListActivity;
import edu.upenn.cis350.AttendanceAppActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.FullStudentListActivity;
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

public class FullStudentListTest extends
		ActivityInstrumentationTestCase2<FullStudentListActivity> {

	public FullStudentListTest(String name) throws Exception {
		super("edu.upenn.cis350", FullStudentListActivity.class);
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

	public void testCantContinue() throws Exception {
		solo.clickOnButton("View Student's Info");
		assertTrue(solo.searchText("Please select some students first."));
	}

	public void testEverythingPresent() throws Exception {
		assertFalse(solo.searchText("DEFAULT ACTIVITY"));
		assertTrue(solo.searchButton("View Student's Info"));
		assertTrue(solo.searchButton("Sort Students"));
	}

	public void testViewStudentInfo() throws Exception {
		solo.clickOnText("12".trim());
		solo.clickOnButton("View Student's Info");
		assertTrue(solo.searchText("Last Action:"));
		solo.goBack();
	}
	

	public void tearDown() throws Exception {
		super.tearDown();
		FileBackup.restoreDB();
	}
}
