package com.example.employeedirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EmployeeMain extends Activity implements OnClickListener {

	Button create_emp;
	Button list_emp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_main);
		initialize_components();
		create_emp.setOnClickListener(this);
		list_emp.setOnClickListener(this);
	}

	private void initialize_components() {
		create_emp = (Button) findViewById(R.id.empCreateBtn);
		list_emp = (Button) findViewById(R.id.empListBtn);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.empCreateBtn:
			startActivity(new Intent(EmployeeMain.this, EmployeeCreate.class));
			break;
		case R.id.empListBtn:
			Toast.makeText(this, "Loading data please wait...",
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(EmployeeMain.this, EmployeesList.class));
			break;
		default:
			Toast.makeText(this,
					getResources().getText(v.getId()) + " not implemented",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}
}