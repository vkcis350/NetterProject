package edu.upenn.cis350;
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
import edu.upenn.cis350.models.Student;

public class StudentDataActivity extends Activity{
	Student curStudent;
	StudentDataSource studentData; //database access object
	//Need a student id from the last activity
	//Take that id and populate the student profile.
	
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
	
	public void populateFields(){
		TextView nameField = (TextView) findViewById(R.id.student_name_field);
		nameField.setText(curStudent.toString());
		EditText gradeField = (EditText) findViewById(R.id.student_grade_field);
		gradeField.setText(curStudent.getGrade()+"");
		EditText dTeacherField = (EditText) findViewById(R.id.day_teacher_field);
		dTeacherField.setText("Day Teacher Name");
		EditText phoneContField = (EditText) findViewById(R.id.phone_contact_field);
		phoneContField.setText(curStudent.getPhone());
		EditText addressContField = (EditText) findViewById(R.id.address_contact_field);
		addressContField.setText(curStudent.getAddress());
	}
	
	public void onPause()
	{
		super.onPause();
		studentData.close();
	}
	
	public void openData()
	{
		studentData = new StudentDataSource(this);
		studentData.open();
	}
	
	public void closeData()
	{
		studentData.close();
	}
}