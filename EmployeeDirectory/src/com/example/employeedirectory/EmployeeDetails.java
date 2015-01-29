package com.example.employeedirectory;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeDetails extends Activity {
	ArrayList<String> images;
	String profile_image;
	ImageView profile;
	int single_image;
	EmployeeDB empdb;
	TextView Id;
	TextView Name;
	TextView Profession;
	Button Edit;
	Button Delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_detail);
		initialze();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		initialze();
	}

	public void initialze() {
		empdb = new EmployeeDB(this);

		profile = (ImageView) findViewById(R.id.prfile_image_value);
		Id = (TextView) findViewById(R.id.details_emp_id_value);
		Name = (TextView) findViewById(R.id.details_emp_name_value);
		Profession = (TextView) findViewById(R.id.details_emp_profession_experience);
		Edit = (Button) findViewById(R.id.EmpEditBtn);
		Delete = (Button) findViewById(R.id.EmpDeleteBtn);

		LinearLayout container = (LinearLayout) findViewById(R.id.imagecontainer);

		String employee_id = (getIntent().getExtras().getString("Id"));

		Employee emp = empdb.getEmployee(employee_id);

		images = empdb.getImages(employee_id, "1");

		container.removeAllViews();

		if (images.size() > 0) {
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
			profile_image = images.get(0);
			ImageView im = new ImageView(this);
			im.setLayoutParams(layoutParams);
			im.setPadding(10, 10, 10, 10);
			im.setImageURI(Uri.parse(profile_image));
			profile.setImageURI(Uri.parse(profile_image));
			im.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(EmployeeDetails.this,
							EmployeeImage.class);
					intent.putExtra("Image", profile_image);
					startActivity(intent);
				}
			});
			container.addView(im);
		}
		images = empdb.getImages(employee_id, "0");
		single_image = 0;
		for (String image : images) {
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
			ImageView im = new ImageView(this);
			im.setId(single_image);
			single_image = single_image + 1;
			im.setLayoutParams(layoutParams);
			im.setPadding(10, 10, 10, 10);
			im.setImageURI(Uri.parse(image));
			im.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(EmployeeDetails.this,
							EmployeeImage.class);
					intent.putExtra("Image", images.get(v.getId()));
					startActivity(intent);
				}
			});
			container.addView(im);
		}
		Id.setText(getIntent().getExtras().getString("Id"));
		Name.setText(emp.getName());
		Profession.setText(emp.getProfession() + EmployeesList.concat
				+ emp.getExperience() + " Years");
	}

	public void Clicked(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.EmpEditBtn:
			intent = new Intent(EmployeeDetails.this, EmployeeEdit.class);
			intent.putExtras(getIntent().getExtras());
			startActivity(intent);
			// finish();
			break;
		case R.id.EmpDeleteBtn:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Delete " + Name.getText().toString() + "'s data")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									empdb.DeleteEmp(getIntent().getExtras()
											.getString("Id"));

									Toast.makeText(
											EmployeeDetails.this,
											Name.getText().toString()
													+ "'s data deleted successfully",
											Toast.LENGTH_SHORT).show();
									finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							})
					.setMessage(
							"Do you want to delete all "
									+ Name.getText().toString() + "'s data?");

			AlertDialog dialog = alert.create();
			dialog.show();
			break;
		default:
			break;
		}
	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
	 * (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	 * startActivity(new Intent(EmployeeDetails.this, EmployeesList.class));
	 * finish(); return true; }
	 * 
	 * return super.onKeyDown(keyCode, event); }
	 */

}