package edu.upenn.cis350;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends SyncableActivity
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
		i.putExtras(getIntent().getExtras());
		Bundle b = i.getExtras();
		Log.d("MainMenuActivity", ""+b.getLong("USER_ID") );
		startActivity(i);
	}

	public void onViewAllStudentsClick(View v)
	{
		Intent i = new Intent(this,FullStudentListActivity.class);
		i.putExtra("ACTIVITY_NAME", "ALL ENROLLED STUDENTS");
		//ID 0 is the default "all activities"
		i.putExtra("ACTIVITY_ID", "0");
		startActivity(i);
	}
}
