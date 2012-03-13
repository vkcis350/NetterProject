package edu.upenn.cis350;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

public class StudentSelectionTest extends
	ActivityInstrumentationTestCase2<StudentSelectionActivity>
{

	public StudentSelectionTest(Class<StudentSelectionActivity> activityClass) 
	{
		super(activityClass);
		
	}
	
	private Activity activity;
	private ListView listView;
	private Button sbutton;
	private Button dbutton;
	
	public void setUp() throws Exception
	{
		super.setUp();
		activity = getActivity();
		listView = (ListView) activity.findViewById(R.id.student_list);
		sbutton = (Button) activity.findViewById(R.id.select_all_button);
		dbutton = (Button) activity.findViewById(R.id.deselect_all_button);
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
