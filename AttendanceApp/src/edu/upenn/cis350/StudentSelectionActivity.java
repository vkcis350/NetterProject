package edu.upenn.cis350;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.view.*;
import android.widget.AdapterView;

public class StudentSelectionActivity extends Activity {

	//For UI TESTING. REMOVE WHEN DATABASE EXISTS.
	static final String[] NAMES = new String[] {
		"Jordan", "Xiao", "Jose", "Vayu", "Sun Yat-Sen", "Admiral Nelson", "Mr. Eclipse", "Salvador Dali"
	};
	static final String[] CHECKED_IN_NAMES = new String[] {
		"Jordan", "Xiao", "Jose", "Vayu"
	};
	static final String[] CHECKED_OUT_NAMES = new String[] {
		"Sun Yat-Sen", "Admiral Nelson", "Mr. Eclipse", "Salvador Dali"
	};
	
	//which list view is selected
	int currentList;
	static final int ALL_STUDENTS = 0;
	static final int CHECKED_IN_STUDENTS = 1;
	static final int CHECKED_OUT_STUDENTS = 2;
	
	//Activity that called this student list
	String currentActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentselection);

		ListView lv = (ListView) findViewById(R.id.student_list);
		lv.setTextFilterEnabled(true);
		lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
		currentList = ALL_STUDENTS;
		reloadList();

		Bundle extras = getIntent().getExtras();
		currentActivity = extras.getString("ACTIVITY_NAME");

		Toast.makeText(getApplicationContext(), currentActivity,
				Toast.LENGTH_SHORT).show();

		TextView title = (TextView) findViewById(R.id.student_view_activity_name);
		title.setText(currentActivity);
	}

	//selects all students
	public void onSelectAllClick(View v){
		ListView lv = (ListView) findViewById(R.id.student_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, true);
	}

	//deselects all students
	public void onDeselectAllClick(View v){
		ListView lv = (ListView) findViewById(R.id.student_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, false);
	}

	//Brings up options menu
	public void onContinueClick(View v)
	{
		ListView lv = (ListView) findViewById(R.id.student_list); 
			if(lv.getCheckedItemCount() > 0)
			{
				PopupMenu popup = new PopupMenu(this, v);
				popup.getMenuInflater().inflate(R.menu.studentselectionmenu, popup.getMenu());
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
				{
					public boolean onMenuItemClick(MenuItem item) 
					{
						switch (item.getItemId()) 
						{
						case R.id.check_in_student:
							onCheckInStudents();
							return true;
						case R.id.check_out_student:
							onCheckOutStudents();
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
				Toast.makeText(getApplicationContext(), "Please select some students first.",
						Toast.LENGTH_SHORT).show();

	}

		//brings up popup asking to confirm. Actual function not yet implemented
		public void onCheckInStudents()
		{
			ListView lv = (ListView) findViewById(R.id.student_list); 
			int count = lv.getCheckedItemCount();
			AlertDialog mDialog = new AlertDialog.Builder(this)
			.setTitle("Check in Students")
			.setMessage("Are you sure you want to check in " + count + " students to " + currentActivity + "?")
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
			    	  Toast.makeText(getApplicationContext(), "Sure, but this isn't implemented yet",
								Toast.LENGTH_SHORT).show();
			    } });
			mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
			      public void onClick(DialogInterface dialog, int which) {  
			    	  Toast.makeText(getApplicationContext(), "Students were not checked in.",
								Toast.LENGTH_SHORT).show();
			    } });   
		}
		
		//brings up popup asking to confirm. Actual function not yet implemented
		public void onCheckOutStudents()
		{
			ListView lv = (ListView) findViewById(R.id.student_list); 
			int count = lv.getCheckedItemCount();
			AlertDialog mDialog = new AlertDialog.Builder(this)
			.setTitle("Check out Students")
			.setMessage("Are you sure you want to check out " + count + " students from " + currentActivity + "?")
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
			    	  Toast.makeText(getApplicationContext(), "Sure, but this isn't implemented yet",
								Toast.LENGTH_SHORT).show();
			    } });
			mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
			      public void onClick(DialogInterface dialog, int which) {  
			    	  Toast.makeText(getApplicationContext(), "Students were not checked out.",
								Toast.LENGTH_SHORT).show();
			    } });  
		}

		//sets the list view to show all students registered for activity
		public void viewAllStudents()
		{
			currentList = ALL_STUDENTS;
			reloadList();
		}
		
		//sets the list view to show only checked in students
		public void viewCheckedInStudents()
		{
			currentList = CHECKED_IN_STUDENTS;
			reloadList();
		}
		
		//sets the list view to show only checked out students
		public void viewCheckedOutStudents()
		{
			currentList = CHECKED_OUT_STUDENTS;
			reloadList();
		}
		
		//filter which students are shown
		//operates on the hard coded list of names for now.
		public void onFilterStudentsClick(View v){
			PopupMenu popup = new PopupMenu(this, v);
			popup.getMenuInflater().inflate(R.menu.filterstudentsmenu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) 
					{
					case R.id.list_all_students:
						viewAllStudents();
						return true;
					case R.id.list_checkedin_students:
						viewCheckedInStudents();
						return true;
					case R.id.list_checkedout_students:
						viewCheckedOutStudents();
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
		
		//reloads the list view for this activity
		public void reloadList()
		{
			ListView lv = (ListView) findViewById(R.id.student_list);
			String[] nameArray = NAMES;
			if(currentList == CHECKED_IN_STUDENTS)
			{
				nameArray = CHECKED_IN_NAMES;
			}
			else if(currentList == CHECKED_OUT_STUDENTS)
			{
				nameArray = CHECKED_OUT_NAMES;
			}
			Arrays.sort(nameArray);
			lv.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_single_choice, nameArray));
		}

	}
