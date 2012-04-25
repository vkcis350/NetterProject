package edu.upenn.cis350.tests;

import java.util.ArrayList;

import edu.upenn.cis350.DummyActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.StudentSelectionActivity;
import edu.upenn.cis350.R.id;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.models.SchoolActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

public class LocalStoreTest2 extends
	ActivityInstrumentationTestCase2<DummyActivity>
	{
	

	
	public LocalStoreTest2(String name) 
	{
		super("edu.upenn.cis350", DummyActivity.class);
		setName(name);
	}
	
	private Activity activity;
	private ListView listView;
	private Button sbutton;
	private Button dbutton;
	ArrayList<SchoolActivity> activities;
	SchoolActivityDataSource actData;
	
	public void setUp() throws Exception
	{
		super.setUp();
		actData = new SchoolActivityDataSource(getActivity());
		//actData.open();
	}
	
	public void testSchoolActivities()
	{
		//SchoolActivity act1 = actData.create("Software Testing");
		//SchoolActivity act1Copy = (SchoolActivity) actData.get(act1.getId()); //get the activity from the DB
		//assertEquals(act1.getName(),act1Copy.getName());
	}
	
	public void testSomething()
	{
		assertTrue(true);
	}
}
