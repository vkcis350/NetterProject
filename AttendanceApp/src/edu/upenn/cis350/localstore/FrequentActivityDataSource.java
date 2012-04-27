package edu.upenn.cis350.localstore;

import java.util.ArrayList;

import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.FrequentActivity;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FrequentActivityDataSource extends DataSource {

	public FrequentActivityDataSource(Context context) {
		super(context);
	}


	public FrequentActivity create(long userId, long activityId )
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_ACTIVITY_ID, activityId );
		values.put(MySQLiteHelper.COL_USER_ID, userId );	
		long insertId = database.insert(MySQLiteHelper.TABLE_FREQUENT_ACTIVITIES, null,
				values);
		return (FrequentActivity) get(insertId);
	}

	@Override
	protected String[] getTables() {
		return new String[]{MySQLiteHelper.TABLE_FREQUENT_ACTIVITIES};
	}

	@Override
	protected String getIDColumn() {
		return MySQLiteHelper.COL_FREQUENT_ACTIVITY_ID;
	}

	@Override
	protected Model cursorToModel(Cursor cursor) {
		FrequentActivity freqAct = new FrequentActivity();
		freqAct.setId(cursor.getLong( MySQLiteHelper.FREQUENT_ACTIVITY_ID_INDEX) );
		freqAct.setActivityId(cursor.getLong(MySQLiteHelper.FREQUENT_ACTIVITY_ACTIVITY_ID_INDEX));
		freqAct.setUserId(cursor.getLong(MySQLiteHelper.FREQUENT_ACTIVITY_USER_ID_INDEX));
		// TODO Auto-generated method stub
		return freqAct;
	}

	public FrequentActivity get(long userId,long activityId)
	{
		Cursor c=database.query(MySQLiteHelper.TABLE_FREQUENT_ACTIVITIES, 
				null, 
				MySQLiteHelper.COL_USER_ID+"= ? and "+MySQLiteHelper.COL_ACTIVITY_ID+"=?",
				new String[]{userId+"",activityId+""}, null, null, null);
		return (FrequentActivity)getFirstModel(c);
	}

}
