package edu.upenn.cis350;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.upenn.cis350.R;
import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Student;

public class StudentCommentActivity extends SyncableActivity{

	/**
	 * //FOR UI TESTING
	String[] COMMENT_ARRAY = {"Behaved today.", 
			"Fought well at Waterloo", 
			"Lost Wuchang because of him today.", 
			"Couldn't find Zambia on a map. Unbelievable."};
	**/
	
	String studentName;
	long studentID;
	ArrayList<String> comments;
	StudentDataSource studentData; 
	CheckinDataSource checkinData;
	Student student;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leavecomment);
		
		Bundle extras = getIntent().getExtras();
		studentID = extras.getLong("STUDENT_ID");
		
		ListView lv = (ListView) findViewById(R.id.comment_list);
		lv.setTextFilterEnabled(true);
		lv.setChoiceMode(lv.CHOICE_MODE_NONE);
		comments = new ArrayList<String>();
		
		

	}
	
	@Override
	protected void onStart(){
		super.onStart();
		openData();
		reloadList();
		TextView nameField = (TextView) findViewById(R.id.student_name);
		nameField.setText("Comments for "+student.getLastName()+", "+student.getFirstName());
	}
	
	public void onPause(){
		super.onPause();
		closeData();
	}
	
	public void onCommentBackClick(View view)
	{
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void onAddCommentClick(View view)
	{
		Toast.makeText(getApplicationContext(), "Okay. I would let you leave a comment, but it hasn't been implemented yet",
				Toast.LENGTH_SHORT).show();
	}
	
	public void reloadList()
	{
		loadData();
		ListView lv = (ListView) findViewById(R.id.comment_list);
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, comments));
	}
	
	public void openData()
	{
		studentData = new StudentDataSource(this);
		studentData.open();
		checkinData = new CheckinDataSource(this);
		checkinData.open();
	}
	
	public void loadData()
	{
		student = (Student) studentData.get(studentID);
		ArrayList<Checkin> checkins = checkinData.getByStudent(student.getId());
		
		for (Checkin checkin : checkins)
		{
			String comment = checkin.getComment();
			if (!comment.equals(""))
				comments.add(comment);
		}
	}

	private String comment() {
		// TODO Auto-generated method stub
		return null;
	}

	public void closeData()
	{
		studentData.close();
		checkinData.close();
	}


}