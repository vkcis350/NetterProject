package edu.upenn.cis350;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.localstore.TemporaryDbInsert;
import edu.upenn.cis350.localstore.UserDataSource;
import edu.upenn.cis350.models.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//Test
public class AttendanceAppActivity extends Activity {
	/** Called when the activity is first created. */
	String userName=""; //access this from other activities to get info on user's credentials
	private UserDataSource userData;
	User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//load data from sqlite
		TemporaryDbInsert.insert(this);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		userData = new UserDataSource(this);
		userData.open();
	}

	public void onLoginClick(View v) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		EditText usernameText = (EditText) findViewById(R.id.user);
		EditText pass = (EditText) findViewById(R.id.password);

		userName=usernameText.getText().toString();
		String pw = pass.getText().toString();
		pass.setText("");

		if(validate(userName,pw)){
			Intent i = new Intent(this,MainMenuActivity.class);
			i.putExtra("USER_ID", user.getId());
			Log.d("AttendanceAppActivity","user id "+user.getId());
			i.putExtra("USERNAME", user.getUsername());
			startActivity(i);
		}else{
			Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

		}
	}

	public void onQuitClick(View v){
		finish();
	}
	
	public void onCreateUserClick(View v) {
		Intent i = new Intent(this,UserCreationActivity.class);
		startActivity(i);
	}

	private boolean validate(String username, String pass) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		user = userData.get(username);
		
		if (user==null)
			return false;
		Log.d("AttendanceAppActivity","user id "+user.getId());
		return user.checkPassword(pass);
	}
	
	public void onStop()
	{
		super.onStop();
		userData.close();
	}
}