package edu.upenn.cis350;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.AdapterView;

public class StudentSelectionActivity extends Activity {

	/**
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
	 **/

	//which list view is selected
	int currentList;
	static final int ALL_STUDENTS = 0;
	static final int CHECKED_IN_STUDENTS = 1;
	static final int CHECKED_OUT_STUDENTS = 2;
	static final int ABSENT_STUDENTS = 3;

	//Request codes
	static final int CHECK_IN_REQUEST = 0;
	static final int CHECK_OUT_REQUEST = 1;
	static final int LEAVE_COMMENT_REQUEST = 2;
	static final int EDIT_DATA_REQUEST = 3;


	StudentDataSource studentData; //database access object
	ArrayList<Student> students;
	ArrayList<Student> inStudents;
	ArrayList<Student> outStudents;
	ArrayList<Student> absentStudents;

	//Activity that called this student list
	String currentActivity;
	long currentActivityID;
	private SchoolActivityDataSource actData;
	private CheckinDataSource checkinData;

	static final int CURRENT_SESSION_ID=0; //TEMPRORARY, later will figure out how to deal with times/sessions of activities


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentselection);

		Bundle extras = getIntent().getExtras();
		currentActivity = extras.getString("ACTIVITY_NAME");
		currentActivityID = extras.getLong("ACTIVITY_ID");

		openData();

		ListView lv = (ListView) findViewById(R.id.student_list);
		lv.setTextFilterEnabled(true);
		lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
		currentList = ALL_STUDENTS;
		reloadList();

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
					case R.id.leave_student_comment:
						onLeaveComment();
						return true;
					case R.id.update_student:
						onViewStudentData();
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

	public void onViewStudentData()
	{
		
		ListView lv = (ListView) findViewById(R.id.student_list);
		if(lv.getCheckedItemCount() == 1)
		{
			long studentID;
			//studentID =(Long) ((Student) lv.getItemAtPosition(lv.getCheckedItemPosition())).getID();
			Intent i = new Intent(this,StudentDataActivity.class);
			i.putExtra("STUDENT_NAME", "DEFAULT NAME");
			i.putExtra("STUDENT_ID", "DEFAULT ID");
			startActivityForResult(i,EDIT_DATA_REQUEST);
		}
		else if(lv.getCheckedItemCount() > 1)
		{
			Toast.makeText(getApplicationContext(), "Please select just one student.",
					Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(getApplicationContext(), "Please select a student first.",
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
				checkInOutStudents(true);

			} });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				Toast.makeText(getApplicationContext(), "Students were not checked in.",
						Toast.LENGTH_SHORT).show();
			} });   
	}

	public void checkInOutStudents(boolean in)
	{
		String inOrOut = "out";
		if (in)
			inOrOut = "in";
		Calendar cal = Calendar.getInstance();
		ListView lv = (ListView) findViewById(R.id.student_list);
		SparseBooleanArray checked = lv.getCheckedItemPositions();
		long time = cal.getTimeInMillis();
		int countSuccessful = 0;
		for (int i=0; i<lv.getCount(); i++)
		{
			Log.d("value,i ", checked.get(i)+", "+i );
			if(checked.get(i))
			{
				Student student = (Student) lv.getItemAtPosition( i );
				Log.d("selected student",i+" "+lv.getCount()+"" );
				Checkin checkin = checkinData.getOrCreate(CURRENT_SESSION_ID,currentActivityID,student.getID());
				if (checkin.getInTime()<0 && in )
				{
					checkin.setInTime ( time );
					checkinData.save(checkin);
					countSuccessful++;
				}
				else if ( checkin.getOutTime()<0 && checkin.getInTime()>=0 && !in )
				{
					checkin.setOutTime(time);
					checkinData.save(checkin);
					countSuccessful++;
				}
				//checkinData.get(sessionID, activityID, studentID)
				//checkinData.save(checkin);
			}
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Toast.makeText(getApplicationContext(), "Checked "+ inOrOut + " " + countSuccessful+ " student(s) at "+dateFormat.format(time),
				Toast.LENGTH_LONG).show();
		reloadList();
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
				checkInOutStudents(false);
			} });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				Toast.makeText(getApplicationContext(), "Students were not checked out.",
						Toast.LENGTH_SHORT).show();
			} });  
	}

	//opens a dialog to leave comments on all selected students
	public void onLeaveComment()
	{
		ListView lv = (ListView) findViewById(R.id.student_list);
		if(lv.getCheckedItemCount() == 1)
		{
			long studentID;
			//studentID =(Long) ((Student) lv.getItemAtPosition(lv.getCheckedItemPosition())).getID();
			Intent i = new Intent(this,StudentCommentActivity.class);
			i.putExtra("STUDENT_NAME", "DEFAULT NAME");
			i.putExtra("STUDENT_ID", "DEFAULT ID");
			startActivityForResult(i,LEAVE_COMMENT_REQUEST);
		}
		else if(lv.getCheckedItemCount() > 1)
		{
			Toast.makeText(getApplicationContext(), "Please select just one student.",
					Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(getApplicationContext(), "Please select a student first.",
					Toast.LENGTH_SHORT).show();
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

	//sets the list view to show only absent students
	public void viewAbsentStudents()
	{
		currentList = ABSENT_STUDENTS;
		reloadList();
	}

	//sets the list view to show all by grade
	public void viewStudentsByGrade()
	{
		Toast.makeText(getApplicationContext(), "Not Yet Implemented",
				Toast.LENGTH_SHORT).show();
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
				case R.id.list_absent_students:
					viewAbsentStudents();
					return true;
				case R.id.list_all_students_by_grade:
					viewStudentsByGrade();
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

	public void onPause()
	{
		super.onPause();
		studentData.close();
		actData.close();
		checkinData.close();
	}

	//reloads the list view for this activity
	public void reloadList()
	{
		loadData();
		ListView lv = (ListView) findViewById(R.id.student_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, false);
		ArrayList<Student> studentList = students;
		if(currentList == CHECKED_IN_STUDENTS)
		{
			studentList = inStudents;
		}
		else if(currentList == CHECKED_OUT_STUDENTS)
		{
			studentList = outStudents;
		}
		
		else if(currentList == ABSENT_STUDENTS)
		{
			studentList = absentStudents;
		}
		else if(currentList == ALL_STUDENTS)
		{
			studentList = students;
		}
		//Note: List of students gotten from DB should already be sorted alphabetically

		lv.setAdapter(new ArrayAdapter<Student>(this,
				android.R.layout.simple_list_item_multiple_choice, studentList));


	}


	public void openData()
	{
		studentData = new StudentDataSource(this);
		studentData.open();
		actData = new SchoolActivityDataSource(this);
		actData.open();
		checkinData = new CheckinDataSource(this);
		checkinData.open();
	}

	public void loadData()
	{
		Log.d("StudentSelectionActivity","current activity id "+currentActivityID);
		SchoolActivity currentActivity = (SchoolActivity) actData.get(currentActivityID);

		students = (ArrayList<Student>) studentData.getStudentsByActivity(currentActivity);
		inStudents = new ArrayList<Student>();
		outStudents = new ArrayList<Student>();
		absentStudents = new ArrayList<Student>();

		for (Student student : students )
		{
			Checkin studentCheckin = checkinData.get(CURRENT_SESSION_ID,currentActivityID,student.getID() );//checkinData.get( CURRENT_SESSION_ID, currentActivityID, studentList.get(i).getID() );
			if (studentCheckin== null)
				absentStudents.add(student);
			else if ( studentCheckin.getInTime()>=0 && studentCheckin.getOutTime()<0  )
				inStudents.add(student);
			else if ( studentCheckin.getInTime()>=0 && studentCheckin.getOutTime()>=0)
				outStudents.add(student);
			else if ( studentCheckin.getInTime()<0 && studentCheckin.getOutTime()<0 )
				absentStudents.add(student);
			else
				throw new IllegalStateException("Illegal student check-in, check-out times.");
		}

	}

	public void closeData()
	{
		studentData.close();
		actData.close();
		checkinData.close();
	}

	public void onActivityResult(int requestCode, int resultCode,
			Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == LEAVE_COMMENT_REQUEST)
		{
			Toast.makeText(getApplicationContext(), "Welcome back from leaving a comment",
					Toast.LENGTH_SHORT).show();
		}
		else if(requestCode == EDIT_DATA_REQUEST)
		{
			if(resultCode == RESULT_OK) //if okayed edit
				Toast.makeText(getApplicationContext(), "Student Data Saved (not really, not implemented yet)",
						Toast.LENGTH_SHORT).show();
			else //if canceled edit
				Toast.makeText(getApplicationContext(), "You did not edit student data.",
						Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(getApplicationContext(), "I don't know how you got this to show up",
					Toast.LENGTH_SHORT).show();
	}
}
