package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityListActivity extends Activity{

	//For UI TESTING. REMOVE WHEN DATABASE EXISTS.\
	static String[] SOME_CLASSES = new String[] {
		"Android Programming", "Homework", "Something Athletic"};
	static String[] CLASSES = new String[] {
		"Android Programming", "Homework", "Something Athletic", "\"Fun Activity\"", "Overthrowing the Qing", "Defeating Napoleon", "Visiting the Eclipse Family", "I made this one up.", "Snacktime?"
	};
	Boolean fullView = false;

	//Request codes
	//for View Student
	static final int VIEW_STUDENT_REQUEST = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities);

		//the list itself
		ListView lv = (ListView) findViewById(R.id.activity_list);
		lv.setTextFilterEnabled(true);
		lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
		String[] classArray = SOME_CLASSES;
		Arrays.sort(classArray);
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, classArray));

		Button selectButton = (Button) findViewById(R.id.select_activity_button);
	}

	public void onAddActivityClick(View v){
		Toast.makeText(getApplicationContext(), "Can't Do That Yet. You probably shouldn't be doing that here anyway.",
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
					default:
						Toast.makeText(getApplicationContext(), "Not Yet Implemented",
								Toast.LENGTH_SHORT).show();
						return true;
					}
				}
			});

			popup.show();

			//	Intent i = new Intent(this,StudentSelectionActivity.class);
			//	startActivity(i);
		}
		else
			Toast.makeText(getApplicationContext(), "Select Activity First",
					Toast.LENGTH_SHORT).show();
	}

	//brings up an options menu
	public void onSelectActivityClick(View v){
		ListView lv = (ListView) findViewById(R.id.activity_list);
		if(lv.getCheckedItemCount() > 0)
		{
			PopupMenu popup = new PopupMenu(this, v);
			popup.getMenuInflater().inflate(R.menu.activityselectionmenu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
			{
				public boolean onMenuItemClick(MenuItem item) 
				{
					switch (item.getItemId()) 
					{
					case R.id.view_students:
						onViewStudents();
						return true;
					default:
						Toast.makeText(getApplicationContext(), "Not Yet Implemented",
								Toast.LENGTH_SHORT).show();
						return true;
					}
				}
			});

			popup.show();

			//	Intent i = new Intent(this,StudentSelectionActivity.class);
			//	startActivity(i);
		}
		else
			Toast.makeText(getApplicationContext(), "Select Activity First",
					Toast.LENGTH_SHORT).show();
	}

	//Opens up the student view. Need to implement database interaction
	//for now, just sends activity name
	public void onViewStudents()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		String activityName = (String) (lv.getItemAtPosition(lv.getCheckedItemPosition()));

		Intent i = new Intent(this,StudentSelectionActivity.class);
		i.putExtra("ACTIVITY_NAME", activityName);
		startActivityForResult(i,VIEW_STUDENT_REQUEST);
	}
	
	//removes activity from list of frequently accessed activities
	//just hardcoded for now
	public void onRemoveFrequently()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		String activityName = (String) (lv.getItemAtPosition(lv.getCheckedItemPosition()));
		ArrayList<String> someClasses = new ArrayList(Arrays.asList(SOME_CLASSES));
		
		//in case you selected something not on the frequent list
		if(!someClasses.contains(activityName))
		{
			Toast.makeText(getApplicationContext(), activityName + " is not in the Frequent List.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		int removeLoc = someClasses.indexOf(activityName);
		someClasses.remove(removeLoc);
		SOME_CLASSES = new String[someClasses.size()];
		for(int x = 0; x < someClasses.size(); x++)
			SOME_CLASSES[x] = someClasses.get(x);
		
		reloadList();
		Toast.makeText(getApplicationContext(), "Removed " + activityName + " from Frequent List.",
				Toast.LENGTH_SHORT).show();
		
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
	
	public void reloadList()
	{
		ListView lv = (ListView) findViewById(R.id.activity_list);
		Button toggleButton = (Button) findViewById(R.id.toggle_activities);
		TextView listtype = (TextView) findViewById(R.id.activity_list_type);
		String[] classArray = CLASSES;
		if(fullView)
		{
			toggleButton.setText(R.string.toggleActivityPart);
			listtype.setText(R.string.activitylistFull);
		}
		else
		{
			toggleButton.setText(R.string.toggleActivityFull);
			listtype.setText(R.string.activitylistPart);
			classArray = SOME_CLASSES;
		}
		Arrays.sort(classArray);
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, classArray));
	}
}
