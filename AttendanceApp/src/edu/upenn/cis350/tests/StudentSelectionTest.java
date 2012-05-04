package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.ActivityListActivity;
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

public class StudentSelectionTest extends
		ActivityInstrumentationTestCase2<StudentSelectionActivity> {

	public StudentSelectionTest(String name) throws Exception {
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

	public void testCantContinue() throws Exception {
		assertFalse(solo.searchText("DEFAULT ACTIVITY"));
		assertTrue(solo.searchButton("Sort Students".trim()));
		assertTrue(solo.searchButton("Select All"));
		assertTrue(solo.searchButton("Continue"));
	}

	public void testEverythingPresent() throws Exception {
		assertFalse(solo.searchText("DEFAULT ACTIVITY"));
		assertTrue(solo.searchButton("Sort Students".trim()));
		assertTrue(solo.searchButton("Select All"));
		assertTrue(solo.searchButton("Continue"));
	}

	public void testSelectAll() throws Exception {
		solo.clickOnButton("Select All");
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Please select just one student."));
	}

	public void testDeselectAll() throws Exception {
		solo.clickOnButton("Select All");
		solo.clickOnButton("Deselect All");
		solo.clickOnButton("Continue");
		assertTrue(solo.searchText("Please select some students first."));
	}

	public void testViewStudentInfo() throws Exception {
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("View Student Info");
		assertTrue(solo.searchText("Last Action:"));
		solo.goBack();
		solo.clickOnText("12".trim());
		solo.clickOnButton("Continue");
		solo.clickOnText("Leave Comment");
		assertTrue(solo.searchText("Add New"));
	}

	public void tearDown() throws Exception {
		super.tearDown();
		FileBackup.restoreDB();
	}
}
