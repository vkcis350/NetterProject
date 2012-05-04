package edu.upenn.cis350;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	private static final int COMMENT_FORM_REQUEST = 0;
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
	private long activityID;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leavecomment);

		Bundle extras = getIntent().getExtras();
		studentID = extras.getLong("STUDENT_ID");
		activityID = Long.parseLong(extras.getString("ACTIVITY_ID"));
		Log.d("StudentCommentActivity", "___" + activityID);
		ListView lv = (ListView) findViewById(R.id.comment_list);
		lv.setTextFilterEnabled(true);
		lv.setChoiceMode(lv.CHOICE_MODE_NONE);
	}

	@Override
	protected void onStart(){
		super.onStart();
		comments = new ArrayList<String>();
		openData();
		reloadList();
		TextView nameField = (TextView) findViewById(R.id.student_name);
		studentName = student.getLastName()+", "+student.getFirstName();
		nameField.setText("Comments for "+studentName);
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
		if (activityID==0)
		{
			Toast.makeText(getApplicationContext(), "Sorry, you can only add comments from a particular activity.",
					Toast.LENGTH_LONG).show();
			return;
		}
		Intent i = new Intent(this,CommentFormActivity.class);
		i.putExtra("STUDENT_ID", new Long(studentID));
		Log.d("StudentCommentActivity","Student name"+" "+studentName);
		i.putExtra("STUDENT_NAME", studentName);
		Log.d("StudentCommentActivity","Student name"+" "+studentName);
		i.putExtra("ACTIVITY_ID", new Long(activityID));
		i.putExtras(getIntent().getExtras());
		startActivityForResult(i,COMMENT_FORM_REQUEST);
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
			if (comment!=null && !comment.equals(""))
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