package edu.upenn.cis350;

import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.models.Checkin;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommentFormActivity extends SyncableActivity {

	String STUDENT_NAME = "Descartes, Rene";
	CheckinDataSource checkinData;
	long studentID;
	String studentName;
	long activityID;
	private long userId;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commentform);

		Bundle extras = getIntent().getExtras();
		studentID = extras.getLong("STUDENT_ID");
		studentName = extras.getString("STUDENT_NAME");
		Log.d("CommentFormActivity",studentName);
		activityID = extras.getLong("ACTIVITY_ID");
		userId = extras.getLong("USER_ID");
		Log.d("CommentFormActivity","activity id: "+activityID);
	}

	protected void onStart()
	{
		super.onStart();
		TextView name = (TextView) findViewById(R.id.student_name);
		name.setText(studentName);
		loadData();
	}

	public void onPause(){
		super.onPause();
		closeData();
	}

	public void onSubmitComment(View view)
	{
		String text = ((EditText)findViewById(R.id.commentbox)).getText().toString();

		Context context = getApplicationContext();

		saveComment(text);
		Toast.makeText(getApplicationContext(),"Comment saved.", Toast.LENGTH_SHORT).show();
	}


	public void onCancelClick(View v){
		//back to last activity
		setResult(RESULT_CANCELED);
		finish();
	}

	public void loadData()
	{
		checkinData = new CheckinDataSource(this);
		checkinData.open();
	}

	public void closeData()
	{
		checkinData.close();
	}

	public void saveComment(String comment)
	{
		long time = System.currentTimeMillis();
		Checkin checkin = checkinData.getOrCreate(time, activityID, studentID);
		Log.d("CommentFormActivity","checkinData is "+checkinData);
		checkin.setComment(comment);
		checkin.setLastChangeTime(time);
		checkinData.save(checkin);
	}
}
