package edu.upenn.cis350;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class FullStudentListActivity extends StudentSelectionActivity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		//Similar to Student Selection Activity
		//But with restricted options
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onFilterStudentsClick(View v){
		PopupMenu popup = new PopupMenu(this, v);
		popup.getMenuInflater().inflate(R.menu.sortallstudents, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) 
				{
				case R.id.list_all_students:
					viewAllStudents();
					return true;
				case R.id.list_all_students_by_grade:
					viewStudentsByGrade();
					return true;
				default:
					Toast.makeText(getApplicationContext(), "Not Yet Implemented",
							Toast.LENGTH_SHORT).show();
					return true;
				}
			}
		});

		popup.show();
	}
	
	@Override
	public void onContinueClick(View v)
	{
		ListView lv = (ListView) findViewById(R.id.student_list); 
		if(lv.getCheckedItemCount() > 0)
		{
			PopupMenu popup = new PopupMenu(this, v);
			popup.getMenuInflater().inflate(R.menu.allstudentselectionmenu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
			{
				public boolean onMenuItemClick(MenuItem item) 
				{
					switch (item.getItemId()) 
					{
					case R.id.leave_student_comment:
						onLeaveComment();
						return true;
					case R.id.update_student:
						onViewStudentData();
						return true;
					default:
						Toast.makeText(getApplicationContext(), "Not Yet Implemented",
								Toast.LENGTH_SHORT).show();
						return true;
					}
				}
			});
			popup.show();
		}
		else
			Toast.makeText(getApplicationContext(), "Please select some students first.",
					Toast.LENGTH_SHORT).show();

	}
}
