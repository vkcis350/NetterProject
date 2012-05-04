package edu.upenn.cis350.tests;

import java.io.IOException;

import com.jayway.android.robotium.solo.Solo;

import edu.upenn.cis350.MainMenuActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.R.id;
import edu.upenn.cis350.SyncableActivity;
import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.localstore.TemporaryDbInsert;
import edu.upenn.cis350.localstore.UserDataSource;
import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;
import edu.upenn.cis350.models.User;
import edu.upenn.cis350.util.FileBackup;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SyncableActivityTest extends
ActivityInstrumentationTestCase2<MainMenuActivity>
{
	final static String USERNAME = "user";//Don't make a user with this username
	final static String PASSWORD = "1";
	final static String WRONG_PASSWORD = "wrong";

	private MainMenuActivity activity;
	private Solo solo;

	SchoolActivityDataSource actData; 
	StudentDataSource studData;
	CheckinDataSource checkinData;

	private Student s1,s2;
	private SchoolActivity a1,a2;
	private Checkin c1,c2;


	public SyncableActivityTest(String name) throws IOException 
	{
		super("edu.upenn.cis350",MainMenuActivity.class);
		setName(name);

		//	Intent i = new Intent();
		//	i.putExtra("ACTIVITY_NAME", "Test Activity");
		//	i.putExtra("ACTIVITY_ID", 100072);
		//	setActivityIntent(i);

	}

	public void setUp() throws Exception
	{
		super.setUp();
		//	Intent i = new Intent();
		//	i.putExtra("ACTIVITY_NAME", "Test Activity");
		//	i.putExtra("ACTIVITY_ID", 100072);
		//	setActivityIntent(i);
		solo = new Solo(getInstrumentation(), getActivity());
		activity = getActivity();
		FileBackup.backupDB();
		studData = new StudentDataSource(activity.getApplicationContext());
		studData.upgrade();

		actData = new SchoolActivityDataSource(activity.getApplicationContext());
		actData.upgrade();


		checkinData = new CheckinDataSource(activity.getApplicationContext());
		checkinData.upgrade();




		//	TemporaryDbInsert.insert(getActivity());

	}

	public void testgetJSONFromDataSourceStudent() throws Exception 
	{
		studData.open();
		Student s1 = studData.create(9136389, "Pomeroy", "Shanita", "215-123-1234", 
				"?", "Neighbor", 0, 0,
				2011, 3, "123 Some Street, Philadelphia");
		Student test=(Student) studData.get(9136389);
		assertEquals(test.getLastName(), "Pomeroy");
		String s = activity.getJSONFromDataSource(studData);
		assertEquals("[{\"address\":\"123 Some Street, Philadelphia\",\"contact\":\"?\",\"contactRelation\":\"Neighbor\",\"firstName\":\"Shanita\",\"phone\":\"215-123-1234\",\"lastName\":\"Pomeroy\",\"siteID\":0,\"schoolID\":0,\"schoolYear\":2011,\"grade\":3,\"id\":9136389}]",s);
		studData.close();

	}

	public void testgetJSONFromDataSourceActivity(){

		actData.open();

		a1 = actData.create("Android Programming",0);
		a2 = actData.create("Zambian Cultural Festival",0);

		actData.close();

		String s = activity.getJSONFromDataSource(actData);
		assertEquals("[{\"name\":\"Android Programming\",\"siteId\":0,\"id\":1},{\"name\":\"Zambian Cultural Festival\",\"siteId\":0,\"id\":2}]",s);

	}

	public void testgetJSONFromDataSourceCheckin(){
		studData.open();
		Student s1 = studData.create(9136389, "Pomeroy", "Shanita", "215-123-1234", 
				"?", "Neighbor", 0, 0,
				2011, 3, "123 Some Street, Philadelphia");
		studData.close();

		checkinData.open();

		c1 = checkinData.create(0,1,s1.getId());
		c1.setComment("testComment");

		checkinData.close(); 

		String s = activity.getJSONFromDataSource(checkinData);
		assertEquals("[{\"comment\":\"\",\"activityID\":1,\"inTime\":0,\"lastChangeTime\":0,\"outTime\":0,\"siteId\":0,\"studentID\":9136389,\"userId\":0,\"id\":1}]",s);
	}


	public void testgetJSONFromEmpty(){
		String s = activity.getJSONFromDataSource(checkinData);
		assertEquals("[]",s);
	}

	public void testSynchronize(){
		studData.open();
		Student s1 = studData.create(9136389, "Pomeroy", "Shanita", "215-123-1234", 
				"?", "Neighbor", 0, 0,
				2011, 3, "123 Some Street, Philadelphia");
		studData.close();

		checkinData.open();

		c1 = checkinData.create(0,1,s1.getId());
		c1.setComment("testComment");

		checkinData.close(); 


		actData.open();

		a1 = actData.create("Android Programming",0);
		a2 = actData.create("Zambian Cultural Festival",0);

		actData.close();
		
		String studJson=activity.getJSONFromDataSource(studData);
		String actJson=activity.getJSONFromDataSource(actData);
		String checkinJson=activity.getJSONFromDataSource(checkinData);
		
		activity.synchronize(true);
		
		assertEquals(studJson,activity.getJSONFromDataSource(studData));
		assertEquals(actJson,activity.getJSONFromDataSource(actData));
		assertEquals(checkinJson,activity.getJSONFromDataSource(checkinData));
	}


	public void tearDown() throws Exception
	{
		super.tearDown();
		FileBackup.restoreDB();
	}

}
