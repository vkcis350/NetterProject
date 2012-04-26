package edu.upenn.cis350.localstore;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.User;
import edu.upenn.cis350.util.Password;

public class UserDataSource extends DataSource {
	public UserDataSource(Context context) {
		super(context);
	}

	public User create(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{		
		Password pass = new Password(password);
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_USERNAME, username );
		values.put(MySQLiteHelper.COL_PASSWORD_HASH, pass.hashPassword());
		values.put(MySQLiteHelper.COL_SALT, pass.getSalt());
		long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
				values);
		return (User)get(insertId);
	}

	@Override
	protected String[] getTables() {
		return new String[]{MySQLiteHelper.TABLE_USERS};
	}

	@Override
	protected String getIDColumn() {
		// TODO Auto-generated method stub
		return MySQLiteHelper.COL_USER_ID;
	}

	@Override
	protected Model cursorToModel(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(MySQLiteHelper.USER_USER_ID_INDEX));
		user.setUsername(cursor.getString(MySQLiteHelper.USER_USERNAME_INDEX));
		user.setPasswordHash(cursor.getString(MySQLiteHelper.USER_PASSWORD_HASH_INDEX));
		user.setSalt(cursor.getString(MySQLiteHelper.USER_SALT_INDEX));
		return user;
	}
	
	public User get(String username)
	{
		Cursor c=database.query(MySQLiteHelper.TABLE_USERS, 
				null, 
				MySQLiteHelper.COL_USERNAME+"=?",
				new String[]{username}, null, null, null);
		return (User)getFirstModel(c);
	}

	@Override
	@Deprecated
	public void create(Model model) {
		// TODO Auto-generated method stub
		
	}

}
