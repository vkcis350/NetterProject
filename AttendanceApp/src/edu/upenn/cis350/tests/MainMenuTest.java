package edu.upenn.cis350.tests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.MainMenuActivity;
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

public class MainMenuTest extends
		ActivityInstrumentationTestCase2<MainMenuActivity> {

	public MainMenuTest(String name) throws Exception {
		super("edu.upenn.cis350", MainMenuActivity.class);
		setName(name);
		Intent i = new Intent();
		i.putExtra("USER_NAME", "Test Smith");
		i.putExtra("USER_ID", 100072);
		setActivityIntent(i);
	}

	private Activity activity;
	private Solo solo;

	public void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("USER_NAME", "Test Smith");
		i.putExtra("USER_ID", 100072);
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
		assertTrue(solo.searchButton("View Activities".trim()));
		assertTrue(solo.searchButton("View Students"));
	}

	public void testViewActivities() throws Exception {
		solo.clickOnButton("View Activities");
		assertTrue(solo.searchButton("Add"));
		solo.goBack();
	}

	public void testViewStudents() throws Exception {
		solo.clickOnButton("View Students");
		assertTrue(solo.searchButton("Sort Students"));
		solo.goBack();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		FileBackup.restoreDB();
	}
}
