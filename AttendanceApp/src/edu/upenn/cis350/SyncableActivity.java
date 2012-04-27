package edu.upenn.cis350;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.DataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.Student;
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
		mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

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
		mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

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
				synchronize();
			}
		}).start();

		//setProp(net.dns1, 8.8.8.8);


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
	
	
	public String getJSONFromDataSource(DataSource d){
		d.open();
		String s=d.exportJson();
		d.close();
		return s;
	}

	/**
	 * Since multiple teachers will be using this application to log attendance data,the purpose of this method is to send locally collected data to an external server.
	 * this method makes a JSON (essentially a string representing the database) of each local database stored within the application and sends it to the web server for permanent storage
	 * StudentDataSource, CheckinDataSource, and SchoolActivityDataSource are all objects which allow the method to access databases on local storage.
	 * **/
	public void synchronize(){
		StudentDataSource studentData=new StudentDataSource(this);
		String studString = getJSONFromDataSource(studentData);
		Log.d("SyncableActivity","Json of students:"+studString);
		CheckinDataSource checkinData= new CheckinDataSource(this);
		String checkinString = getJSONFromDataSource(checkinData);
		Log.d("SyncableActivity","Json of checkins:"+checkinString);
		SchoolActivityDataSource actData= new SchoolActivityDataSource(this);
		String actString = getJSONFromDataSource(actData);
		Log.d("SyncableActivity","Json of activities:"+actString); 

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postMethod = new HttpPost(hostName);
			postMethod.setHeader( "Content-Type", "application/json" );
			StringBuilder allStrings=new StringBuilder();
			allStrings.append(studString).append('\n').append(actString).append('\n').append(checkinString);
			postMethod.setEntity(new StringEntity(allStrings.toString()));
			
			
			HttpResponse response = httpClient.execute(postMethod);
			Log.d("SyncableActivity","response: "+response.getStatusLine().toString());
			String respString = EntityUtils.toString(response.getEntity());

			Log.d("SyncableActivity","response body: "+respString);
			
			
			//NOTE TO JOSE: REPLACE allStrings IN THE FOLLOWING LINE WITH respString ONCE RESPONSE IS WORKING CORRECTLY
			String[] responseJSONs=allStrings.toString().split("\\n");
			
			String newStud=responseJSONs[0];
			Log.d("SyncableActivity","newstud: "+newStud);
			String newAct=responseJSONs[1];
			String newCheck=responseJSONs[2];
			
			studentData.open();
			studentData.deleteAll();
			studentData.importFromjson(newStud);
			studentData.close();
			
			actData.open();
			actData.deleteAll();
			actData.importFromjson(newAct);
			actData.close();
			
			
			//Comment/Uncomment following lines depending on how we plan to handle checkins
			checkinData.open();
			checkinData.deleteAll();
			checkinData.importFromjson(newCheck);
			checkinData.close();
			
			

		} catch (IOException e) {
			Log.d("SyncableActivity","IO connection failed for "+ hostName);
		}
	}

}
