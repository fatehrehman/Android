package com.example.employeedirectory;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeList extends Activity {

	public static ArrayList<String> empList = new ArrayList<String>();
	GridView employeeListHeader;
	GridView employeeListGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_list);
		EmployeeDB empdb = new EmployeeDB(this);
		empdb.getAllEmployees(0,100);

		String[] header = { getResources().getString(R.string.EmpID),
				getResources().getString(R.string.EmpName),
				getResources().getString(R.string.EmpProfession),
				getResources().getString(R.string.EmpExperience),
				getResources().getString(R.string.EmpImage) };

		employeeListHeader = (GridView) findViewById(R.id.employeeListHeader);
		employeeListGrid = (GridView) findViewById(R.id.employeeListGrid);

		ArrayAdapter<String> empListHeader = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, header);

		ArrayAdapter<String> empListAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, empList);

		employeeListHeader.setAdapter(empListHeader);
		employeeListGrid.setAdapter(empListAdapter);

		employeeListGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
