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

public class MainMenuActivity extends Activity
{

	String hostName="192.168.1.1";
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
		i.putExtra("ACTIVITY_NAME", "ALL ENROLLED STUDENTS");
		//ID 0 is the default "all activities"
		i.putExtra("ACTIVITY_ID", "0");
		startActivity(i);
	}

	public void onSyncClick(View v)
	{
		//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		StudentDataSource studentData=new StudentDataSource(this);
		studentData.open();
		String studString =studentData.exportJson();
		studentData.close();
		Log.d("MainMenuActivity","Json of students:"+studString);
		CheckinDataSource checkinData= new CheckinDataSource(this);
		checkinData.open();
		String checkinString = checkinData.exportJson();
		checkinData.close();
		Log.d("MainMenuActivity","Json of checkins:"+checkinString);
		SchoolActivityDataSource actData= new SchoolActivityDataSource(this);
		actData.open();
		String actString = actData.exportJson();
		actData.close();

		Log.d("MainMenuActivity","Json of activities:"+actString);
		//TODO: send JSON to server here, make sure to catch httperror and output error message if transmission fails  
		//make socket for data transfer to server

		Socket jsonSocket = null;
		PrintWriter out = null;

		//BufferedReader in = null;

		try {
			jsonSocket = new Socket(hostName, 1234);
			out = new PrintWriter(jsonSocket.getOutputStream(), true);
			out.println(studString);
			out.println(checkinString);
			out.println(actString);
			out.close();
			jsonSocket.close();

			//in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			Log.d("MainMenuActivity","Don't know about host "+ hostName);
			Toast.makeText(getApplicationContext(), "Sync Failed: Host not found",
					Toast.LENGTH_SHORT).show();
			// System.exit(1);
		} catch (IOException e) {
			Log.d("MainMenuActivity","IO connection failed for "+ hostName);
			Toast.makeText(getApplicationContext(), "Sync Failed: IO Exception",
					Toast.LENGTH_SHORT).show();
			// System.exit(1);
		}


		//TODO: wait till server sends signal to update current databases
		//TODO: populate databases with jsons sent by server
		//TODO:clear all local databases according to pre-determined convention
		//the following lines clear the local databases, additional fxnality needs to be added to populate database w/ current
		/*
		studentData.open();
		studentData.deleteAll();
		studentData.importFromjson(string from server)
		studentData.close();
		//repeat w/ checkin and act
		
		*/

	}

}
