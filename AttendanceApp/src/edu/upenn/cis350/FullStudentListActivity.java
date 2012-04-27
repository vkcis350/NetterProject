package edu.upenn.cis350;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class FullStudentListActivity extends StudentSelectionActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Similar to Student Selection Activity
		// But with restricted options
		super.onCreate(savedInstanceState);
		ListView lv = (ListView) findViewById(R.id.student_list);
		lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
		Button selectAll = (Button) findViewById(R.id.select_all_button);
		Button deselectAll = (Button) findViewById(R.id.deselect_all_button);
		selectAll.setVisibility(View.GONE);
		deselectAll.setVisibility(View.GONE);
		Button cont = (Button) findViewById(R.id.continue_button);
		cont.setText("View Student's Info");

	}

	@Override
	public void onFilterStudentsClick(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		popup.getMenuInflater()
				.inflate(R.menu.sortallstudents, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.list_all_students:
					viewAllStudents();
					return true;
				case R.id.list_all_students_by_grade:
					viewStudentsByGrade();
					return true;
				default:
					Toast.makeText(getApplicationContext(),
							"Not Yet Implemented", Toast.LENGTH_SHORT).show();
					return true;
				}
			}
		});

		popup.show();
	}

	@Override
	public void onContinueClick(View v) {
		ListView lv = (ListView) findViewById(R.id.student_list);
		if (lv.getCheckedItemCount() > 0) {
			onViewStudentData();
		} else
			Toast.makeText(getApplicationContext(),
					"Please select some students first.", Toast.LENGTH_SHORT)
					.show();

	}
}
