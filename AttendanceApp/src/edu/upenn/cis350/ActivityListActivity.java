package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.upenn.cis350.localstore.FrequentActivityDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.TemporaryDbInsert;
import edu.upenn.cis350.models.FrequentActivity;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityListActivity extends SyncableActivity{

	Boolean fullView = false;

	//Request codes
	static final int VIEW_STUDENT_REQUEST = 0;
	static final int NEW_ACTIVITY_REQUEST = 1;

	SchoolActivityDataSource actData; //database access
	FrequentActivityDataSource freqActData;
	private ArrayList<SchoolActivity> schoolActivities;
	private ArrayList<SchoolActivity> someSchoolActivities;
	
	long userId;
	String username;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities);

		Bundle extras = getIntent().getExtras();
		Log.d("ActivityListActivity",extras+"");
		userId = extras.getLong("USER_ID");
		username = extras.getString("USER_NAME");
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		actData = new SchoolActivityDataSource(this);
		freqActData = new FrequentActivityDataSource(this);
		openData();
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
	
	@Override
	public void onPause()
	{
		super.onPause();
		closeData();
	}

	public void closeData()
	{
		actData.close();
		freqActData.close();
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
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0)
		{
			SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));

			AlertDialog mDialog = new AlertDialog.Builder(this)
			.setIconAttribute(android.R.attr.alertDialogIcon)
			.setTitle("Permanently Remove Activity")
			.setMessage("Are you sure you want to completely remove " + activity + "? This cannot be undone.")
			.setPositiveButton("Yes", null)
			.setNegativeButton("No", null)
			.show();

			WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
			layoutParams.dimAmount = 0.9f;
			mDialog.getWindow().setAttributes(layoutParams);
			mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

			//what happens when you press the buttons
			mDialog.setButton("Yes", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int which) {  
					removeCompletely();
				} });
			mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int which) {  
					Toast.makeText(getApplicationContext(), "Activity was not removed.",
							Toast.LENGTH_SHORT).show();
				} }); 
		}
		else
			Toast.makeText(getApplicationContext(), "Select Activity First",
					Toast.LENGTH_SHORT).show();
	}

	public void removeCompletely()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));
		actData.delete(activity);
		someSchoolActivities.remove(activity);
		schoolActivities.remove(activity);

		reloadList();
	}
	
	//brings up an options menu
	public void onSelectActivityClick(View v)
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0)
		{
			SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));

			Intent i = new Intent(this,StudentSelectionActivity.class);
			i.putExtra("ACTIVITY_NAME", activity.toString());
			i.putExtra("ACTIVITY_ID", activity.getId());

			startActivityForResult(i,VIEW_STUDENT_REQUEST);
		}
		else
			Toast.makeText(getApplicationContext(), "Select Activity First",
					Toast.LENGTH_SHORT).show();
	}

	//removes activity from list of frequently accessed activities
	public void removeFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);

		if(lv.getCheckedItemCount() > 0)
		{
			SchoolActivity schoolActivity = (SchoolActivity) lv.getItemAtPosition(lv.getCheckedItemPosition());

			//in case you selected something not on the frequent list
			if(!someSchoolActivities.contains(schoolActivity))
			{
				Toast.makeText(getApplicationContext(), schoolActivity + " is not in the Frequent List.",
						Toast.LENGTH_SHORT).show();
				return;
			}

			someSchoolActivities.remove(schoolActivity);

			reloadList();
			Toast.makeText(getApplicationContext(), "Removed " + schoolActivity + " from Frequent List.",
					Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(getApplicationContext(), "Select Activity First",
					Toast.LENGTH_SHORT).show();
	}

	//asks for confirmation
	public void onRemoveFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0)
		{
			SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));

			AlertDialog mDialog = new AlertDialog.Builder(this)
			.setTitle("Remove from List")
			.setIconAttribute(android.R.attr.alertDialogIcon)
			.setMessage("Are you sure you want to remove " + activity + " from the frequent activity list?")
			.setPositiveButton("Yes", null)
			.setNegativeButton("No", null)
			.show();

			WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
			layoutParams.dimAmount = 0.9f;
			mDialog.getWindow().setAttributes(layoutParams);
			mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

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
		else Toast.makeText(getApplicationContext(), "Select Activity First",
				Toast.LENGTH_SHORT).show();
	}

	//adds activity to list of frequently accessed activities
	public void addFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0)
		{
			SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));

			//in case you selected something not on the frequent list
			if(someSchoolActivities.contains(activity))
			{
				Toast.makeText(getApplicationContext(), activity + " is already in the Frequent List.",
						Toast.LENGTH_SHORT).show();
				return;
			}

			freqActData.create(userId, activity.getId());
			int addLoc = someSchoolActivities.indexOf(activity);
			someSchoolActivities.add(activity);

			reloadList();
			Toast.makeText(getApplicationContext(), "Added " + activity + " to Frequent List.",
					Toast.LENGTH_SHORT).show();
		}else Toast.makeText(getApplicationContext(), "Select Activity First",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * This method sets up the UI for adding an activity to a frequently used activities list
	 * Please refer to addFrequently for the subroutine which this method executes which adds the activity to the frequently used activities list
	 */
	public void onAddFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0)
		{

			SchoolActivity activity = (SchoolActivity) (lv.getItemAtPosition(lv.getCheckedItemPosition()));

			AlertDialog mDialog = new AlertDialog.Builder(this)
			.setTitle("Add to List")
			.setIconAttribute(android.R.attr.alertDialogIcon)
			.setMessage("Are you sure you want to add " + activity + " to the frequent activity list?")
			.setPositiveButton("Yes", null)
			.setNegativeButton("No", null)
			.show();

			WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
			layoutParams.dimAmount = 0.9f;
			mDialog.getWindow().setAttributes(layoutParams);
			mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

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
		}else Toast.makeText(getApplicationContext(), "Select Activity First",
				Toast.LENGTH_SHORT).show();
	}

	public AlertDialog onAddNew()
	{
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.dimAmount = 0.9f;
		getWindow().setAttributes(layoutParams);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		//Intent i = new Intent(this,AddNewActivityActivity.class);
		//startActivityForResult(i,NEW_ACTIVITY_REQUEST);
		LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.add_new_activity, null);
        return new AlertDialog.Builder(this)
            .setTitle("Add New Activity")
            .setView(textEntryView)
            .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	EditText activity_name = (EditText)textEntryView.findViewById(R.id.newActivityName);
            		String new_activity_name = activity_name.getText().toString();
                    if(new_activity_name != null && new_activity_name != "")
                    	createActivity(new_activity_name);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                	Toast.makeText(getApplicationContext(), "No New Activity Created.",
            				Toast.LENGTH_SHORT).show();
                }
            })
        	.show();
	}
	
	public void createActivity(String new_activity_name)
	{
		actData.create(new_activity_name);
		
		loadData();
		reloadList();
		Toast.makeText(getApplicationContext(), "New activity added!",
				Toast.LENGTH_SHORT).show();
	}

	//what happens when you return from other activities
	public void onActivityResult(int requestCode, int resultCode,
			Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == VIEW_STUDENT_REQUEST)
		{
			Toast.makeText(getApplicationContext(), "Welcome back from viewing the student list",
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


	public void openData()
	{
		actData.open();
		freqActData.open();
	}
	
	public void loadData()
	{		
		schoolActivities = (ArrayList<SchoolActivity>) actData.getAll();
		someSchoolActivities = actData.getFrequentSchoolActivities(userId);
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
	
	public ArrayList<Long> frequentActivityIdList()
	{
		ArrayList<Long> freqActivityIds = new ArrayList<Long>();
		for (SchoolActivity act : someSchoolActivities)
		{
			freqActivityIds.add(act.getId());
		}
		return freqActivityIds;
	}
}
