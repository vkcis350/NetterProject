package edu.upenn.cis350;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
		long studentID = extras.getLong("STUDENT_ID");
		openData();
		populateFields();
		curStudent=(Student) studentData.get(studentID);
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
		EditText nameField = (EditText) findViewById(R.id.student_name_field);
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