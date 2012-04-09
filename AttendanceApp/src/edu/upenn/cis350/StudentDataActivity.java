package edu.upenn.cis350;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import edu.upenn.cis350.R;
import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Student;

public class StudentDataActivity extends SyncableActivity{
	Student curStudent;
	Checkin lastCheckinOut;
	StudentDataSource studentData; //database access object
	//Need a student id from the last activity
	//Take that id and populate the student profile.
	CheckinDataSource checkinData; //database access object
	SchoolActivityDataSource activityData;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentdata);

		Bundle extras = getIntent().getExtras();
		long studentID= extras.getLong("STUDENT_ID");
		//long studentID=Long.parseLong(preStudentID);
		Log.d("StudentDataActivity","Profile of Student with ID number"+studentID+"loaded");
		openData();

		curStudent=(Student) studentData.get(studentID);
		
		
		
		lastCheckinOut = checkinData.getMostRecentForStudent(studentID);

		
		if(curStudent==null){
			Log.d("StudentDataActivity","Student object is null");
		}
		populateFields();
	}
	
	public void onSaveDataClick(View v){
		//updatedStudentData = new Student(); fill with data from the form
		//student = getStudent(student_id);
		//Compare the two, if they are different then update the dbmodel with the info 
		setResult(RESULT_OK);
		finish();
	}
	
	public void onCancelDataClick(View v){
		//back to last activity
		setResult(RESULT_CANCELED);
		finish();
	}
	
	/**
	 * This method populates an activity which contains the profile of a student with information from the locally stored database
	 */
	public void populateFields(){
		TextView nameField = (TextView) findViewById(R.id.student_name_field);
		nameField.setText(curStudent.getLastName()+", "+curStudent.getFirstName());
		
		TextView lastAction = (TextView) findViewById(R.id.last_action_field);
		String lastActionString = "None";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if (lastCheckinOut!=null && !lastCheckinOut.defaultState() ) {
			String activityName = activityData.get( lastCheckinOut.getActivityID() ).toString();
			if ( lastCheckinOut.getInTime() > lastCheckinOut.getOutTime() )
				lastActionString = "Checked in to "+activityName+" "+dateFormat.format(lastCheckinOut.getInTime());
			else if ( lastCheckinOut.getInTime() <= lastCheckinOut.getOutTime() )
				lastActionString = "Checked out of "+activityName+" "+dateFormat.format(lastCheckinOut.getOutTime());
		}
		
		lastAction.setText(lastActionString);
		
		EditText gradeField = (EditText) findViewById(R.id.student_grade_field);
		gradeField.setText(curStudent.getGrade()+"");
		
		EditText phoneContField = (EditText) findViewById(R.id.phone_contact_field);
		phoneContField.setText(curStudent.getPhone());
		//EditText contactField = (EditText) findViewById(R.id.contact_field);
		//contactField.setText(curStudent.getContact());
		EditText contactRelField = (EditText) findViewById(R.id.contact_relation_field);
		contactRelField.setText(curStudent.getContactRelation());
		EditText addressContField = (EditText) findViewById(R.id.address_contact_field);
		addressContField.setText(curStudent.getAddress());
		
	}
	
	public void onPause()
	{
		super.onPause();
		closeData();
	}
	
	public void openData()
	{
		studentData = new StudentDataSource(this);
		studentData.open();
		checkinData = new CheckinDataSource(this);
		checkinData.open();
		activityData = new SchoolActivityDataSource(this);
		activityData.open();
	}
	
	public void closeData()
	{
		studentData.close();
		checkinData.close();
		activityData.close();
	}
}