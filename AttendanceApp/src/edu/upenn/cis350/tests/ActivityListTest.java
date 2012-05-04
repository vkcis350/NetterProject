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

public class ActivityListTest extends
		ActivityInstrumentationTestCase2<ActivityListActivity> {

	public ActivityListTest(String name) throws Exception {
		super("edu.upenn.cis350", ActivityListActivity.class);
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

	public void testContinue() throws Exception {
		solo.clickOnButton("View All Activities");
		solo.clickOnText("Android Programming".trim());
		solo.clickOnButton("Continue");
		assertTrue(solo.searchButton("Sort Students"));
		solo.goBack();
	}
	
	public void testCantContinue() throws Exception {
		solo.clickOnButton("Continue");
		assertTrue(solo.searchText("Select Activity First"));
		solo.clickOnButton("Continue");
		assertTrue(solo.searchText("Select Activity First"));
	}

	public void testEverythingPresent() throws Exception {
		assertTrue(solo.searchButton("Add".trim()));
		assertTrue(solo.searchButton("Remove"));
		assertTrue(solo.searchButton("Continue"));
		solo.clickOnButton("Add");
		
		
	}

	public void testAddRemove() throws Exception {
		solo.clickOnButton("View All Activities");
		solo.clickOnText("Android Programming".trim());
		solo.clickOnButton("Add");
		solo.clickOnText("Add to Frequent");
		solo.clickOnText("Yes");
		solo.clickOnButton("View Only Frequent Activities");
		assertTrue(solo.searchText("Android Programming"));
		solo.clickOnText("Android Programming".trim());
		solo.clickOnText("Remove");
		solo.clickOnText("Remove from Frequent List");
		solo.clickOnText("Yes");
		solo.clickOnText("Remove");
		assert(solo.searchText("Android Programming"));
	}

	public void tearDown() throws Exception {
		super.tearDown();
		FileBackup.restoreDB();
	}
}
