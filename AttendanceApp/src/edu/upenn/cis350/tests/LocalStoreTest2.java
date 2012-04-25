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

	public LocalStoreTest2(Class<DummyActivity> activityClass) 
	{
		super(activityClass);
		
	}
	
	private Activity activity;
	private ListView listView;
	private Button sbutton;
	private Button dbutton;
	ArrayList<SchoolActivity> activities;
	
	public void setUp() throws Exception
	{
		SchoolActivityDataSource actData = new SchoolActivityDataSource(getActivity());
		activities = actData.getAll();
		
	}
	
	public void testSchoolActivities()
	{
		
	}

	public void testSelectAll() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				sbutton.performClick();
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		int size = listView.getCount();
		
		assertEquals(size, listView.getCheckedItemCount());
	}
	
	public void testDeselectAll() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				dbutton.performClick();
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		assertEquals(0, listView.getCheckedItemCount());
	}
	
	public void testMultipleSelects() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				dbutton.performClick();
				sbutton.performClick();
				dbutton.performClick();
				sbutton.performClick();
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		assertEquals(0, listView.getCheckedItemCount());
	}
	
	public void testReloadList() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				sbutton.performClick();
				((StudentSelectionActivity) activity).reloadList();
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		assertEquals(0, listView.getCheckedItemCount());
	}
}
