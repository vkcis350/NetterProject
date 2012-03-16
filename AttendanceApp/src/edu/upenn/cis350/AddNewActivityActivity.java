package edu.upenn.cis350;

import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewActivityActivity extends Activity {
	SchoolActivityDataSource dbsrc;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_activity);
	}
	
	public void onCreateActivity(View v)
	{
		EditText activity_name = (EditText)findViewById(R.id.newActivityName);
    	String new_activity_name = activity_name.getText().toString();
    	
    	Model act = new SchoolActivity(new_activity_name);
    	dbsrc = new SchoolActivityDataSource(this);
    	dbsrc.open();
    	dbsrc.create(act);
    	dbsrc.close();
    	
    	Intent i = new Intent(this,ActivityListActivity.class);
		startActivity(i);
	}


}
