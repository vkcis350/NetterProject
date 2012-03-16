package edu.upenn.cis350.localstore;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;

import edu.upenn.cis350.models.Student;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SchoolActivityDataSource extends DataSource {
	final static String[] TABLES = { MySQLiteHelper.TABLE_ACTIVITIES };
	final static String ID_COLUMN = MySQLiteHelper.COL_ACTIVITY_ID;
	
	public SchoolActivityDataSource(Context context)
	{
		super(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	protected Model cursorToModel(Cursor cursor) {
		SchoolActivity act = new SchoolActivity();
		act.setID(cursor.getLong(0));
		act.setName(cursor.getString(1));
		return act;
	}

	@Override
	protected String[] getTables() {
		return TABLES;
	}

	@Override
	protected String getIDColumn() {
		return ID_COLUMN;
	}

	@Override
	public void create(Model model) {
		SchoolActivity activity = (SchoolActivity)model;
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_ACTIVITY_NAME, activity.getName() );
		long insertId = database.insert(MySQLiteHelper.TABLE_ACTIVITIES, null,
				values);
		activity.setID(insertId);
	}
}
