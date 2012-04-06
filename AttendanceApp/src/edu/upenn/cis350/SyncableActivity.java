package edu.upenn.cis350;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class SyncableActivity extends Activity{

	
	String hostName="http://nettercenter350.appspot.com";
	int port = 1234;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.optionsmenu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.sync_option:
	            confirmSync();
	            return true;
	        case R.id.logout_option:
	            confirmLogout();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void confirmSync()
	{
		AlertDialog mDialog = new AlertDialog.Builder(this)
		.setTitle("Sync Data")
		.setMessage("Sync data now?")
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
				Toast.makeText(getApplicationContext(), "Syncing...",
						Toast.LENGTH_SHORT).show();
				sync();
			} });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				Toast.makeText(getApplicationContext(), "Data was not synced.",
						Toast.LENGTH_SHORT).show();
			} });  
	}
	
	public void confirmLogout()
	{
		AlertDialog mDialog = new AlertDialog.Builder(this)
		.setTitle("Logout")
		.setMessage("Are you sure you want to logout?")
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
				confirmSync();
				Toast.makeText(getApplicationContext(), "Logout not yet implemented",
						Toast.LENGTH_SHORT).show();
			} });
		mDialog.setButton2("No", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				Toast.makeText(getApplicationContext(), "You were not logged out.",
						Toast.LENGTH_SHORT).show();
			} });  
	}
	
	public void sync()
	{
		//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				
				//BufferedReader in = null;
				new Thread(new Runnable() {
			        public void run() {
			        	doHttp();
			        }
			    }).start();
				


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
	
	public void doHttp(){
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

		
		try {
			//URL u = new URL(hostName+1234);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postMethod = new HttpPost(hostName+":"+port);
			postMethod.setHeader( "Content-Type", "application/json" );
		    ResponseHandler <String> resonseHandler = new BasicResponseHandler();
			//jsonSocket = new Socket(hostName, 1234);
			//out = new PrintWriter(jsonSocket.getOutputStream(), true);
			postMethod.setEntity(new ByteArrayEntity(studString.toString().getBytes("UTF8")));
			String response = httpClient.execute(postMethod,resonseHandler);
			Log.e("response :", response);
			postMethod.setHeader( "Content-Type", "application/json" );
			postMethod.setEntity(new ByteArrayEntity(checkinString.toString().getBytes("UTF8")));
			response = httpClient.execute(postMethod,resonseHandler);
			Log.e("response :", response);
			postMethod.setHeader( "Content-Type", "application/json" );
			postMethod.setEntity(new ByteArrayEntity(actString.toString().getBytes("UTF8")));
			response = httpClient.execute(postMethod,resonseHandler);
			Log.e("response :", response);
			
			//out.println(studString);
			//out.println(checkinString);
			//out.println(actString);
			//out.close();
			//jsonSocket.close();

			//in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			Log.d("MainMenuActivity","Don't know about host "+ hostName);
			//Toast.makeText(getApplicationContext(), "Sync Failed: Host not found",
			//		Toast.LENGTH_SHORT).show();
			// System.exit(1);
		} catch (IOException e) {
			Log.d("MainMenuActivity","IO connection failed for "+ hostName);
			//Toast.makeText(getApplicationContext(), "Sync Failed: IO Exception",
			//		Toast.LENGTH_SHORT).show();
			// System.exit(1);
		}
	}
}
