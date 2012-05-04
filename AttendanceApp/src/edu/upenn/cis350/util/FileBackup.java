package edu.upenn.cis350.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import android.os.Environment;

public class FileBackup {
	public static String DB_PATH="/data/data/edu.upenn.cis350/databases/attendance.db";
	public static String DB_BACKUP_PATH="/data/data/edu.upenn.cis350/databases/attendance.db.save";

	public static void copyFile(String source, String dest) throws IOException
	{
		File fileA = new File(source);
		File fileB = new File(dest);
		FileUtils.copyFile(fileA, fileB);
	}
	
	public static void backupDB() throws IOException
	{
		copyFile(DB_PATH,DB_BACKUP_PATH);
	}
	
	public static void restoreDB() throws IOException
	{
		copyFile(DB_BACKUP_PATH,DB_PATH);
	}
	
}
