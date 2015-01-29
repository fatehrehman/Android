package com.example.employeedirectory;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class EmployeeImage extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_image);
		ImageView emp_img_viewer = (ImageView) findViewById(R.id.emp_img_viewer);
		emp_img_viewer.setImageURI(Uri.parse(getIntent().getExtras().getString("Image")));
	}
}
