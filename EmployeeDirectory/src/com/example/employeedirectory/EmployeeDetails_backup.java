//package com.example.employeedirectory;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class EmployeeDetails_backup extends Activity {
//	ArrayList<String> images;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_employee_detail);
//
//		TextView Id = (TextView) findViewById(R.id.details_emp_id_value);
//		TextView Name = (TextView) findViewById(R.id.details_emp_name_value);
//		TextView Profession = (TextView) findViewById(R.id.details_emp_profession_experience);
//		Button Edit = (Button) findViewById(R.id.EmpEditBtn);
//		Button Delete = (Button) findViewById(R.id.EmpDeleteBtn);
//
//		Id.setText(getIntent().getExtras().getString("Id"));
//		Name.setText(getIntent().getExtras().getString("Name"));
//		Profession.setText(getIntent().getExtras().getString(
//				"ProfessionExperience"));
//
//		setImages(getIntent().getExtras().getString("Image"),
//				R.id.prfile_image_value);
//
//		EmployeeDB empdb = new EmployeeDB(this);
//		images = empdb.getImages(getIntent().getExtras().getString("Id"), "0");
//
//		setImages(getIntent().getExtras().getString("Image"),
//				R.id.employee_image_one);
//
//		Log.d("Images", images.get(0) + " /n " + images.get(1) + " /n "
//				+ images.get(2) + getIntent().getExtras().getString("Image"));
//
//		setImages(images.get(0), R.id.employee_image_two);
//		setImages(images.get(1), R.id.employee_image_three);
//		setImages(images.get(2), R.id.employee_image_four);
//
//		// Edit.setOnClickListener(this);
//		// Delete.setOnClickListener(this);
//	}
//
//	public void Clicked(View v) {
//		// TODO Auto-generated method stub
//		Intent intent = new Intent();
//		switch (v.getId()) {
//		case R.id.EmpEditBtn:
//			intent = new Intent(EmployeeDetails_backup.this, EmployeeEdit.class);
//			intent.putExtras(getIntent().getExtras());
//			startActivity(intent);
//			finish();
//			break;
//		case R.id.EmpDeleteBtn:
//			AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//			alert.setTitle(
//					"Delete" + getIntent().getExtras().getString("Name")
//							+ "'s data")
//					.setPositiveButton("Yes",
//							new DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									EmployeeDB db = new EmployeeDB(
//											EmployeeDetails_backup.this);
//									db.DeleteEmp(getIntent().getExtras()
//											.getString("Id"));
//									startActivity(new Intent(
//											EmployeeDetails_backup.this,
//											EmployeesList.class));
//									finish();
//								}
//							})
//					.setNegativeButton("No",
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									dialog.cancel();
//								}
//							})
//					.setMessage(
//							"Do you want to delete all "
//									+ getIntent().getExtras().getString("Name")
//									+ "'s data?");
//
//			AlertDialog dialog = alert.create();
//			dialog.show();
//			break;
//		case R.id.employee_image_one:
//			// Toast.makeText(this, "One", Toast.LENGTH_LONG).show();
//			intent = new Intent(EmployeeDetails_backup.this, EmployeeImage.class);
//			intent.putExtra("Image", getIntent().getExtras().getString("Image"));
//			startActivity(intent);
//			break;
//		case R.id.employee_image_two:
//			// Toast.makeText(this, "Two", Toast.LENGTH_LONG).show();
//			intent = new Intent(EmployeeDetails_backup.this, EmployeeImage.class);
//			intent.putExtra("Image", images.get(0));
//			startActivity(intent);
//			break;
//		case R.id.employee_image_three:
//			// Toast.makeText(this, "Three", Toast.LENGTH_LONG).show();
//			intent = new Intent(EmployeeDetails_backup.this, EmployeeImage.class);
//			intent.putExtra("Image", images.get(1));
//			startActivity(intent);
//			break;
//		case R.id.employee_image_four:
//			// Toast.makeText(this, "Four", Toast.LENGTH_LONG).show();
//			intent = new Intent(EmployeeDetails_backup.this, EmployeeImage.class);
//			intent.putExtra("Image", images.get(2));
//			startActivity(intent);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	/*
//	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
//	 * (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//	 * startActivity(new Intent(EmployeeDetails.this, EmployeesList.class));
//	 * finish(); return true; }
//	 * 
//	 * return super.onKeyDown(keyCode, event); }
//	 */
//
//	private void setImages(String image_name, int image) {
//		ImageView im = (ImageView) findViewById(image);
//		if (!image_name.equals(getResources().getString(R.string.EmpImage))) {
//			im.setVisibility(1);
//			im.setImageURI(Uri.parse(image_name));
//		}
//	}
//
//}