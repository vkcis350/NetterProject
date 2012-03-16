package edu.upenn.cis350.localstore;

import java.util.ArrayList;
import java.util.List;


import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class DataSource<T extends Model>{
	final static int PRIMARY_TABLE_INDEX = 0;
	protected SQLiteDatabase database;
	protected MySQLiteHelper dbHelper;
	
	protected abstract String[] getTables();
	protected abstract String getIDColumn();
	protected abstract T cursorToModel(Cursor c);
	
	public abstract void create(T model);
	
	public DataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public void delete(T model) {
		long id = ((Model) model).getID();
		System.out.println("Comment deleted with id: " + id);
		database.delete(getTables()[PRIMARY_TABLE_INDEX], getIDColumn()
				+ " = " + id, null);
	}

	public T get(long id)
	{
		Cursor c=database.query(getTables()[PRIMARY_TABLE_INDEX], 
				null, 
				getIDColumn()+"=?",
				new String[]{id+""}, null, null, null);
		c.moveToFirst();
		return cursorToModel(c);
	}
	

	
	public List<T> getAll()
	{
		List<T> models = new ArrayList<T>();
		Cursor cursor = database.query(getTables()[PRIMARY_TABLE_INDEX],
				null, null, null, null, null, 
				null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			T model = cursorToModel(cursor);
			models.add(model);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return models;
	}
	
	public void deleteAll()
	{
		String[] tables = getTables();
		for (int i=0; i<tables.length; i++)
		{
			database.delete(tables[i], null, null);
		}
	}

}