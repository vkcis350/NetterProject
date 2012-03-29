package edu.upenn.cis350.tests;

import edu.upenn.cis350.ActivityListActivity;
import edu.upenn.cis350.R;
import edu.upenn.cis350.R.id;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

public class ActivityListTest extends
	ActivityInstrumentationTestCase2<ActivityListActivity>
{

	public ActivityListTest(Class<ActivityListActivity> activityClass) 
	{
		super(activityClass);
		
	}
	
	private Activity activity;
	private ListView listView;
	private Button togglebutton;
	
	public void setUp() throws Exception
	{
		super.setUp();
		activity = getActivity();
		listView = (ListView) activity.findViewById(R.id.activity_list);
		togglebutton = (Button) activity.findViewById(R.id.toggle_activities);
	}

	public void testToggleFull() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				togglebutton.performClick();
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		int size = listView.getCount();
		
		assertTrue(size > 3);
	}
	
	public void tesDetoggleFull() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				togglebutton.performClick();
				togglebutton.performClick();
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		int size = listView.getCount();
		
		assertTrue(size == 3);
	}
	
	public void testToggleFullAgain() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				togglebutton.performClick();
				togglebutton.performClick();
				togglebutton.performClick();
				togglebutton.performClick();
				togglebutton.performClick();
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		int size = listView.getCount();
		
		assertTrue(size > 3);
	}
	
	public void testRemoveFromFrequent()
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				togglebutton.performClick();
				togglebutton.performClick();
				togglebutton.performClick();
				togglebutton.performClick();
				togglebutton.performClick();
			}
		});
	}
}
