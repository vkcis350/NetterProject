package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainMenuActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
	}
	
	public void onViewAllActivitiesClick(View v)
	{
		Intent i = new Intent(this,ActivityListActivity.class);
		startActivity(i);
	}
	
	public void onViewAllStudentsClick(View v)
	{
		Intent i = new Intent(this,FullStudentListActivity.class);
		i.putExtra("ACTIVITY_NAME", "ENROLLED STUDENTS");
		i.putExtra("ACTIVITY_ID", "-1");
		startActivity(i);
	}
}
