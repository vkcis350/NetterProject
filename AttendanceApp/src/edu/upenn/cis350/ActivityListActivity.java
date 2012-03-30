package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.TemporaryDbInsert;
import edu.upenn.cis350.models.SchoolActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityListActivity extends Activity{

	/**
	//For UI TESTING. REMOVE WHEN DATABASE EXISTS.\
	static String[] SOME_CLASSES = new String[] {
		"Android Programming", "Homework", "Something Athletic"};
	static String[] CLASSES = new String[] {
		"Android Programming", "Homework", "Something Athletic", "\"Fun Activity\"", "Overthrowing the Qing", "Defeating Napoleon", "Visiting the Eclipse Family", "I made this one up.", "Snacktime?"
	};
	**/
	Boolean fullView = false;

	//Request codes
	static final int VIEW_STUDENT_REQUEST = 0;
	static final int EDIT_ACTIVITY_REQUEST = 1;
	
	SchoolActivityDataSource actData; //database access
	private ArrayList<SchoolActivity> schoolActivities;
	private ArrayList<SchoolActivity> someSchoolActivities;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities);
		
		loadData();

		//the list itself
		ListView lv = (ListView) findViewById(R.id.activity_list);
		lv.setTextFilterEnabled(true);
		lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
		ArrayList<SchoolActivity> classList = someSchoolActivities;
		
		lv.setAdapter(new ArrayAdapter<SchoolActivity>(this,
				android.R.layout.simple_list_item_single_choice, classList));

		Button selectButton = (Button) findViewById(R.id.select_activity_button);
	}

	public void onAddActivityClick(View v){
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0 || lv.getCheckedItemCount() == 0)
		{
			PopupMenu popup = new PopupMenu(this, v);
			popup.getMenuInflater().inflate(R.menu.activityaddmenu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
			{
				public boolean onMenuItemClick(MenuItem item) 
				{
					switch (item.getItemId()) 
					{
					case R.id.add_to_frequently:
						onAddFrequently();
						return true;
					case R.id.add_new_activity:
						onAddNew();
						return true;
					default:
						Toast.makeText(getApplicationContext(), "Not Yet Implemented",
								Toast.LENGTH_SHORT).show();
						return true;
					}
				}
			});

			popup.show();

		}
		else
			Toast.makeText(getApplicationContext(), "Select Activity First",
					Toast.LENGTH_SHORT).show();
	}

	public void onRemoveActivityClick(View v){
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0)
		{
			PopupMenu popup = new PopupMenu(this, v);
			popup.getMenuInflater().inflate(R.menu.activityremovemenu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
			{
				public boolean onMenuItemClick(MenuItem item) 
				{
					switch (item.getItemId()) 
					{
					case R.id.remove_frequently:
						onRemoveFrequently();
						return true;
					case R.id.remove_completely:
						onRemoveCompletely();
						return true;
					default:
						Toast.makeText(getApplicationContext(), "Not Yet Implemented",
								Toast.LENGTH_SHORT).show();
						return true;
					}
				}
			});

			popup.show();

		}
		else
			Toast.makeText(getApplicationContext(), "Select Activity First",
					Toast.LENGTH_SHORT).show();
	}
	
	private void onRemoveCompletely() {
		SchoolActivityDataSource dbsrc = new SchoolActivityDataSource(this);
		dbsrc.open();
		
		ListView lv = (ListView) findViewById(R.id.activity_list);
		SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));
		
		dbsrc.delete(activity);
		someSchoolActivities.remove(activity);
		schoolActivities.remove(activity);
		
		dbsrc.close();
		
		reloadList();
	}

	//brings up an options menu
	public void onSelectActivityClick(View v)
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));

		Intent i = new Intent(this,StudentSelectionActivity.class);
		i.putExtra("ACTIVITY_NAME", activity.toString());
		i.putExtra("ACTIVITY_ID", activity.getID());
		
		startActivityForResult(i,VIEW_STUDENT_REQUEST);
	}
	
	//removes activity from list of frequently accessed activities
	//just hardcoded for now
	public void removeFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		SchoolActivity schoolActivity = (SchoolActivity) lv.getItemAtPosition(lv.getCheckedItemPosition());
		
		//in case you selected something not on the frequent list
		if(!someSchoolActivities.contains(schoolActivity))
		{
			Toast.makeText(getApplicationContext(), schoolActivity + " is not in the Frequent List.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		someSchoolActivities.remove(schoolActivity);
		
		//TO DO: Remove activity from persistent frequent list
		
		reloadList();
		Toast.makeText(getApplicationContext(), "Removed " + schoolActivity + " from Frequent List.",
				Toast.LENGTH_SHORT).show();
	}
	
	//asks for confirmation
	public void onRemoveFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));
		
		AlertDialog mDialog = new AlertDialog.Builder(this)
		.setTitle("Remove from List")
		.setMessage("Are you sure you want to remove " + activity + " from the frequent activity list?")
		.setPositiveButton("Yes", null)
		.setNegativeButton("No", null)
		.show();

		WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
		layoutParams.dimAmount = 0.9f;
		mDialog.getWindow().setAttributes(layoutParams);
		//mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		//what happens when you press the buttons
		mDialog.setButton("Yes", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		    	  removeFrequently();
		    } });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		    	  Toast.makeText(getApplicationContext(), "Activity was not removed.",
							Toast.LENGTH_SHORT).show();
		    } });  		
	}
	
	//adds activity to list of frequently accessed activities
	//just hardcoded for now
	public void addFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));
		
		//in case you selected something not on the frequent list
		if(someSchoolActivities.contains(activity))
		{
			Toast.makeText(getApplicationContext(), activity + " is already in the Frequent List.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		int addLoc = someSchoolActivities.indexOf(activity);
		someSchoolActivities.add(activity);
	
		reloadList();
		Toast.makeText(getApplicationContext(), "Added " + activity + " to Frequent List.",
				Toast.LENGTH_SHORT).show();
		
	}
	
	//asks for confirmation
	public void onAddFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));
		
		AlertDialog mDialog = new AlertDialog.Builder(this)
		.setTitle("Add to List")
		.setMessage("Are you sure you want to add " + activity + " to the frequent activity list?")
		.setPositiveButton("Yes", null)
		.setNegativeButton("No", null)
		.show();

		WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
		layoutParams.dimAmount = 0.9f;
		mDialog.getWindow().setAttributes(layoutParams);
		//mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		//what happens when you press the buttons
		mDialog.setButton("Yes", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		    	  addFrequently();
		    } });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		    	  Toast.makeText(getApplicationContext(), "Activity was not added.",
							Toast.LENGTH_SHORT).show();
		    } });  		
	}
	
	public void onAddNew()
	{
		Intent i = new Intent(this,AddNewActivityActivity.class);
		startActivity(i);
	}

	//what happens when you return from other activities (nothing yet)
	public void onActivityResult(int requestCode, int resultCode,
			Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == VIEW_STUDENT_REQUEST)
		{
			Toast.makeText(getApplicationContext(), "Welcome back from viewing the student list",
					Toast.LENGTH_SHORT).show();
		}
		else if(requestCode == EDIT_ACTIVITY_REQUEST)
		{
			if(resultCode == RESULT_OK) //if okayed edit
				Toast.makeText(getApplicationContext(), "Welcome back from editing an activity",
						Toast.LENGTH_SHORT).show();
			else //if canceled edit
				Toast.makeText(getApplicationContext(), "You did not edit that activity",
						Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(getApplicationContext(), "I don't know how you got this to show up",
					Toast.LENGTH_SHORT).show();
		//data.
	}

	//switches between full list of activities and partial list
	//currently the activities are just hardcoded
	public void onToggleActivityClick(View v)
	{
		fullView = !fullView;
		reloadList();
	}
	
	public void onPause()
	{
		super.onPause();
		actData.close();
	}
	
	public void loadData()
	{
		
		actData = new SchoolActivityDataSource(this);
		actData.open();
		schoolActivities = (ArrayList<SchoolActivity>) actData.getAll();
		someSchoolActivities = new ArrayList<SchoolActivity>(); //TO CHANGE
		for (int i=0; i<schoolActivities.size(); i++)
		{
			someSchoolActivities.add(schoolActivities.get(i));
		}

	}
	
	public void reloadList()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, false);
		Button toggleButton = (Button) findViewById(R.id.toggle_activities);
		TextView listtype = (TextView) findViewById(R.id.activity_list_type);
		ArrayList<SchoolActivity> classArrayList = schoolActivities;
		if(fullView)
		{
			toggleButton.setText(R.string.toggleActivityPart);
			listtype.setText(R.string.activitylistFull);
		}
		else
		{
			toggleButton.setText(R.string.toggleActivityFull);
			listtype.setText(R.string.activitylistPart);
			classArrayList = someSchoolActivities;//TO CHANGE
		}
		lv.setAdapter(new ArrayAdapter<SchoolActivity>(this,
				android.R.layout.simple_list_item_single_choice, classArrayList));
	}
}
