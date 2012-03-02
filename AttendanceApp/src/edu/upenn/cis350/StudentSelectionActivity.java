package edu.upenn.cis350;

import java.util.Arrays;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.view.*;
import android.widget.AdapterView;

public class StudentSelectionActivity extends Activity {

	//For UI TESTING. REMOVE WHEN DATABASE EXISTS.
	 static final String[] NAMES = new String[] {
		    "Jordan", "Xiao", "Jose", "Vayu", "Sun Yat-Sen", "Admiral Nelson", "Mr. Eclipse", "Salvador Dali"
		  };
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.studentselection);

	   ListView lv = (ListView) findViewById(R.id.student_list);
	   lv.setTextFilterEnabled(true);
	   lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
	   String[] nameArray = NAMES;
	   Arrays.sort(nameArray);
	   lv.setAdapter(new ArrayAdapter<String>(this,
	                   android.R.layout.simple_list_item_multiple_choice, nameArray));

	  	   
	 }
	 
	 //selects all students
	public void onSelectAllClick(View v){
		ListView lv = (ListView) findViewById(R.id.student_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, true);
	}
	
	//deselects all students
	public void onDeselectAllClick(View v){
		ListView lv = (ListView) findViewById(R.id.student_list);
		for(int x = 0; x < lv.getCount(); x++)
			lv.setItemChecked(x, false);
	}
	
	//Brings up options menu
	public void onContinueClick(View v){
		 PopupMenu popup = new PopupMenu(this, v);
	     popup.getMenuInflater().inflate(R.menu.studentselectionmenu, popup.getMenu());
	     popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
	    	 public boolean onMenuItemClick(MenuItem item) {
	    		 Toast.makeText(getApplicationContext(), "Not Yet Implemented",
	    				 Toast.LENGTH_SHORT).show();
	             	return true;
	            }
	        });

	        popup.show();

	}

}
