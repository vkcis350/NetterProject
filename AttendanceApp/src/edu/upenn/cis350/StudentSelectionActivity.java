package edu.upenn.cis350;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.view.*;
import android.widget.AdapterView;

public class StudentSelectionActivity extends Activity {

	 static final String[] NAMES = new String[] {
		    "Jordan", "Xiao", "Jose", "Vayu", "Sun Yat-Sen", "Admiral Nelson", "Mr. Eclipse", "Salvador Dali"
		  };
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.studentselection);

	   //setListAdapter(new ArrayAdapter<String>(this, R.layout.studentselectlist, NAMES));

	   
	   //ListView lv = getListView();
	   ListView lv = (ListView) findViewById(R.id.student_list);
	   lv.setTextFilterEnabled(true);
	   lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
	   lv.setAdapter(new ArrayAdapter<String>(this,
	                   android.R.layout.simple_list_item_multiple_choice, NAMES));

/*
	   lv.setOnItemClickListener(new OnItemClickListener() {
	     public void onItemClick(AdapterView<?> parent, View view,
	         int position, long id) {
	       // When clicked, show a toast with the TextView text
	       Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	           Toast.LENGTH_SHORT).show();
	     }
	   });*/
	   
	   
	 }
	 
	public void onSelectAllClick(View v){
		ListView lv = (ListView) findViewById(R.id.student_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, true);
	}
	
	public void onDeselectAllClick(View v){
		ListView lv = (ListView) findViewById(R.id.student_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, false);
	}
	
	public void onContinueClick(View v){
		Toast.makeText(getApplicationContext(), "Can't Do That Yet",
		           Toast.LENGTH_SHORT).show();
	}

}
