package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityListActivity extends Activity{
		
	//For UI TESTING. REMOVE WHEN DATABASE EXISTS.
	 static final String[] CLASSES = new String[] {
		    "Android Programming", "Homework", "Something Athletic", "\"Fun Activity\"", "Overthrowing the Qing", "Defeating Napoleon", "Visiting the Eclpise Family", "I made this one up.", "Snacktime?"
		  };
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activities);

	   ListView lv = (ListView) findViewById(R.id.activity_list);
	   lv.setTextFilterEnabled(true);
	   lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
	   lv.setAdapter(new ArrayAdapter<String>(this,
	                   android.R.layout.simple_list_item_single_choice, CLASSES));
	   
	 }
	 
	public void onAddActivityClick(View v){
		//ListView lv = (ListView) findViewById(R.id.activity_list);
		//for(int x = 0; x < lv.getCount(); x++)
		//	lv.setItemChecked(x, true);
	}
	
	public void onRemoveActivityClick(View v){
		//ListView lv = (ListView) findViewById(R.id.activity_list);
		//for(int x = 0; x < lv.getCount(); x++)
		//	lv.setItemChecked(x, false);
	}
	
	public void onSelectActivityClick(View v){
		Intent i = new Intent(this,StudentSelectionActivity.class);
		startActivity(i);
	}
}
