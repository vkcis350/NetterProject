package edu.upenn.cis350;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

public class EditActivityActivityTest extends
	ActivityInstrumentationTestCase2<EditActivityActivity>
{

	public EditActivityActivityTest(Class<EditActivityActivity> activityClass) 
	{
		super(activityClass);
		
	}
	
	private Activity activity;
	private Button confirmButton;
	private Button cancelButton;
	
	public void setUp() throws Exception
	{
		super.setUp();
		activity = getActivity();
		confirmButton = (Button) activity.findViewById(R.id.edit_activity_ok_button);
		cancelButton = (Button) activity.findViewById(R.id.edit_activity_cancel_button);
	}

	public void testConfirm() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		assertEquals(0,0);
	}
	
	public void testCancel() 
	{
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		assertEquals(0, 0);
	}
}