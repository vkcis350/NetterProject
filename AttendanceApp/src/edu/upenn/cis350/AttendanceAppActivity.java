package edu.upenn.cis350;

import edu.upenn.cis350.localstore.TemporaryDbInsert;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//Test
public class AttendanceAppActivity extends Activity {
	/** Called when the activity is first created. */
	String userName=""; //access this from other activities to get info on user's credentials

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//load data from sqlite
		TemporaryDbInsert.insert(this);
	}

	public void onLoginClick(View v){
		EditText user = (EditText) findViewById(R.id.user);
		EditText pass = (EditText) findViewById(R.id.password);

		userName=user.getText().toString();
		String pw = pass.getText().toString();
		pass.setText("");

		if(validate(userName,pw)){
			Intent i = new Intent(this,MainMenuActivity.class);
			startActivity(i);
		}else{
			Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

		}
	}

	public void onQuitClick(View v){
		finish();
	}

	private boolean validate(String user, String pass){

		return userName.equals("admin");//will be method which validates user's name and pw, currently returns true iff username input is "admin"
	}
}