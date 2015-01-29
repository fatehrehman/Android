//package com.example.employeedirectory;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class EmployeeEdit_backup extends Activity implements OnClickListener {
//	ArrayList<String> images;
//	String profile_image;
//	TextView Id;
//	EditText Name;
//	EditText Profession;
//	EditText Experience;
//	EmployeeDB empdb;
//	Button Save;
//	Button Cancel;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_employee_edit);
//		initialze();
//		Save.setOnClickListener(this);
//		Cancel.setOnClickListener(this);
//
//	}
//	
//
//	public void initialze() {
//		empdb = new EmployeeDB(this);
//
//		Id = (TextView) findViewById(R.id.details_emp_id_value);
//		Name = (EditText) findViewById(R.id.details_emp_name_value);
//		Profession = (EditText) findViewById(R.id.details_emp_profession_value);
//		Experience = (EditText) findViewById(R.id.details_emp_experience_value);
//		Save = (Button) findViewById(R.id.EmpSaveBtn);
//		Cancel = (Button) findViewById(R.id.EmpCancelBtn);
//
//		Name.setText(getIntent().getExtras().getString("Name"));
//		Profession.setText(getIntent().getExtras().getString("Profession"));
//		Experience.setText(getIntent().getExtras().getString("Experience"));
//
//		Employee emp = empdb.getEmployee(Integer.parseInt(getIntent()
//				.getExtras().getString("Id")));
//		
////		profile_image = empdb.getImages(
////				getIntent().getExtras().getString("Id"), "1").get(0);
//		//new EmployeeDetails().setImages(profile_image, R.id.prfile_image_value);
//		//new EmployeeDetails().setImages(profile_image, R.id.employee_image_one);
//
////		images = empdb.getImages(getIntent().getExtras().getString("Id"), "0");
//		//new EmployeeDetails().setImages(images.get(0), R.id.employee_image_two);
//		//new EmployeeDetails().setImages(images.get(1), R.id.employee_image_three);
//		//new EmployeeDetails().setImages(images.get(2), R.id.employee_image_four);
//
//		Id.setText(getIntent().getExtras().getString("Id"));
//		Name.setText(emp.getName());
//		Profession.setText(emp.getProfession());
//		Experience.setText(emp.getExperience());
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.EmpSaveBtn:
//			String val_id = Id.getText().toString();
//			String val_name = Name.getText().toString().trim();
//			String val_profession = Profession.getText().toString().trim();
//			String val_experience = Experience.getText().toString().trim();
//			if (val_name.isEmpty() || val_profession.isEmpty()
//					|| val_experience.isEmpty()) {
//				Toast.makeText(this, "Please enter all fields to save",
//						Toast.LENGTH_SHORT).show();
//			} else {
//				EmployeeDB emp = new EmployeeDB(this);
//				emp.UpdateEmp(val_id, val_name, val_profession, val_experience);
//				finish();
//			}
//			break;
//		case R.id.EmpCancelBtn:
//			finish();
//			break;
//		default:
//			break;
//		}
//	}
//
//}