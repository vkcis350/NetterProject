package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityListActivity extends Activity{
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activities);
	}
	
	public void onButtonClick(View v){
		Intent i = new Intent(this, StudentSelectionActivity.class);
		startActivity(i);
	}
}
