package edu.upenn.cis350;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.TextView;
import edu.upenn.cis350.R;
import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Student;

public class StudentDataActivity extends SyncableActivity {
	Student curStudent;
	Checkin lastAction;
	StudentDataSource studentData; // database access object
	// Need a student id from the last activity
	// Take that id and populate the student profile.
	CheckinDataSource checkinData; // database access object
	SchoolActivityDataSource activityData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentdata);

		Bundle extras = getIntent().getExtras();
		long studentID = extras.getLong("STUDENT_ID");
		// long studentID=Long.parseLong(preStudentID);
		Log.d("StudentDataActivity", "Profile of Student with ID number"
				+ studentID + "loaded");
		openData();

		curStudent = (Student) studentData.get(studentID);

		lastAction = checkinData.getMostRecentForStudent(studentID);

		if (curStudent == null) {
			Log.d("StudentDataActivity", "Student object is null");
		}
		populateFields();
	}

	/**
	 * This method populates an activity which contains the profile of a student
	 * with information from the locally stored database
	 */
	public void populateFields() {
		TextView nameField = (TextView) findViewById(R.id.student_name_field);
		nameField.setText(curStudent.getLastName() + ", "
				+ curStudent.getFirstName());

		TextView lastActionText = (TextView) findViewById(R.id.last_action_field);
		lastActionText.setText(lastAction());

		TextView gradeField = (TextView) findViewById(R.id.student_grade_field);
		gradeField.setText(curStudent.getGrade() + "");

		TextView phoneContField = (TextView) findViewById(R.id.phone_contact_field);
		phoneContField.setText(curStudent.getPhone());

		TextView contactField = (TextView) findViewById(R.id.contact_relation_field);
		contactField.setText(curStudent.getContactRelation());

		TextView contactRelField = (TextView) findViewById(R.id.contact_field);
		contactRelField.setText(curStudent.getContact());

		TextView addressContField = (TextView) findViewById(R.id.address_contact_field);
		addressContField.setText(curStudent.getAddress());

	}

	private String lastAction() {
		String lastActionString = "None";

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		if (lastAction != null && !lastAction.defaultState()) {
			String activityName = activityData.get(lastAction.getActivityID())
					.toString();
			if (lastAction.checkedIn())
				lastActionString = "Checked in to " + activityName + " "
						+ dateFormat.format(lastAction.getInTime());
			else if (lastAction.checkedOut())
				lastActionString = "Checked out of " + activityName + " "
						+ dateFormat.format(lastAction.getOutTime());
		}
		return lastActionString;

	}

	public void onPause() {
		super.onPause();
		closeData();
	}

	public void openData() {
		studentData = new StudentDataSource(this);
		studentData.open();
		checkinData = new CheckinDataSource(this);
		checkinData.open();
		activityData = new SchoolActivityDataSource(this);
		activityData.open();
	}

	public void closeData() {
		studentData.close();
		checkinData.close();
		activityData.close();
	}
}