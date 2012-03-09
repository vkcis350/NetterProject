package edu.upenn.cis350;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivityActivity extends Activity
{
	
	//Activity that is being edited
	String currentActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editactivity);

		Bundle extras = getIntent().getExtras();
		currentActivity = extras.getString("ACTIVITY_NAME");

		Toast.makeText(getApplicationContext(), currentActivity,
				Toast.LENGTH_SHORT).show();

		TextView title = (TextView) findViewById(R.id.edit_activity_name);
		title.setText(currentActivity);
	}
	
	//what happens when the confirm button is clicked
	public void onConfirmActivityEdit(View v)
	{
		AlertDialog mDialog = new AlertDialog.Builder(this)
		.setTitle("Confirm Edit")
		.setMessage("Are you sure you want to save your changes for " + currentActivity + " ?")
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
		    	  setResult(RESULT_OK);
		    	  finish();
		    } });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		    	  Toast.makeText(getApplicationContext(), "Edit not saved",
							Toast.LENGTH_SHORT).show();
		    } });  	
	}
	
	//what happens when the cancel button is clicked
	public void onCancelActivityEdit(View v)
	{
		AlertDialog mDialog = new AlertDialog.Builder(this)
		.setTitle("Cancel Edit")
		.setMessage("Are you sure you want to quit editing " + currentActivity + " ?")
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
		    	  setResult(RESULT_CANCELED);
		    	  finish();
		    } });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		    	  Toast.makeText(getApplicationContext(), "Edit not canceled",
							Toast.LENGTH_SHORT).show();
		    } });  		
	}
	
	
}
