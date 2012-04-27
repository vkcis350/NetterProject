package edu.upenn.cis350;

import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewActivityActivity extends SyncableActivity {
	SchoolActivityDataSource dbsrc;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_activity);
	}

	public void createActivity()
	{
		EditText activity_name = (EditText)findViewById(R.id.newActivityName);
		String new_activity_name = activity_name.getText().toString();

		Model act = new SchoolActivity(new_activity_name);
		dbsrc = new SchoolActivityDataSource(this);
		dbsrc.create(new_activity_name);
		dbsrc.close();

		//back to last activity
		setResult(RESULT_OK);
		finish();
	}

	public void onCreateActivityClick(View v)
	{
		EditText activity_name = (EditText)findViewById(R.id.newActivityName);
		String new_activity_name = activity_name.getText().toString();

		AlertDialog mDialog = new AlertDialog.Builder(this)
		.setTitle("Confirm New Activity")
		.setMessage("Are you sure you want to create " + new_activity_name + "?")
		.setPositiveButton("Yes", null)
		.setNegativeButton("No", null)
		.show();

		WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
		layoutParams.dimAmount = 0.9f;
		mDialog.getWindow().setAttributes(layoutParams);
		mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		//what happens when you press the buttons
		mDialog.setButton("Yes", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {
				createActivity();
			} });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				Toast.makeText(getApplicationContext(), "Activity was not created.",
						Toast.LENGTH_SHORT).show();
			} });  
	}

	public void onNotCreateClick(View v)
	{
		//back to last activity
		setResult(RESULT_CANCELED);
		finish();
	}


}
