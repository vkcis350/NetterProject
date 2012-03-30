package edu.upenn.cis350;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import edu.upenn.cis350.R;

public class StudentDataActivity extends Activity{
	//Need a student id from the last activity
	
	//Take that id and populate the student profile.
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentdata);
		//getStudent(student_id);
		//populateView(student); //check out studentdata.xml
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
}