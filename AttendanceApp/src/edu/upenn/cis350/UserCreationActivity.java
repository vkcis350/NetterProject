package edu.upenn.cis350;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import edu.upenn.cis350.localstore.TemporaryDbInsert;
import edu.upenn.cis350.localstore.UserDataSource;
import edu.upenn.cis350.models.User;
import edu.upenn.cis350.util.Password;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//Test
public class UserCreationActivity extends Activity {
	private UserDataSource userData;
	private static final String USER_CREATION_CODE_HASH = "e60347b7e399c90bce2e8d193d675e6f2c40981d79a737f1de2cf48709f8e341"; 
	private static final String USER_CREATION_CODE_SALT = "cis350";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usercreation);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		userData = new UserDataSource(this);
		userData.open();
	}

	public void onCreateUserClick(View v) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		EditText user = (EditText) findViewById(R.id.user);
		EditText pass = (EditText) findViewById(R.id.password);
		EditText confirmPass = (EditText) findViewById(R.id.confirm);
		EditText creationCode = (EditText) findViewById(R.id.creation_code);


		String userName=user.getText().toString();
		String passString = pass.getText().toString();
		String confirmPassString = confirmPass.getText().toString();
		String creationCodeString = creationCode.getText().toString();

		if( validate(userName, passString, confirmPassString, creationCodeString) ) {
			userData.create(userName,passString);
			Toast.makeText(getApplicationContext(), "User created.", Toast.LENGTH_SHORT).show();
		}

			
	}
	
	private boolean validate(String username, String password, String confirmPassword, String creationCode) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		if (usernameExists(username)) {
			Toast.makeText(getApplicationContext(), "Username already taken.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!password.equals(confirmPassword)) {
			Toast.makeText(getApplicationContext(), "Password confirmation does not match password.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!passwordLengthCheck(password)) {
			Toast.makeText(getApplicationContext(), "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!validateCreationCode(creationCode)) {
			Toast.makeText(getApplicationContext(), "Incorrect User Creation Code.", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
		
			
	}
	
	/**
	 * makes sure password is long enough
	 */
	public boolean passwordLengthCheck(String password)
	{
		return password.length()>=6;
	}
	
	private boolean validateCreationCode(String creationCode) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		String hash = Password.hashPassword(creationCode, USER_CREATION_CODE_SALT);
		return hash.equals(USER_CREATION_CODE_HASH);
	}

	public void onQuitClick(View v){
		finish();
	}
	
	public boolean usernameExists(String username) {
		return userData.get(username)!=null;
	}

	
	public void onStop()
	{
		super.onStop();
		userData.close();
	}
}