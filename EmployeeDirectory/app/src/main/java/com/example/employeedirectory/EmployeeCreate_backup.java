//package com.example.employeedirectory;
//
//import android.app.Activity;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//public class EmployeeCreate_backup extends Activity implements OnClickListener {
//
//	EmployeeDB emp;
//	EditText name;
//	EditText profession;
//	EditText experience;
//	Button create_emp;
//	ImageView empimgone;
//	ImageView empimgtwo;
//	ImageView empimgthree;
//	ImageView empimgfour;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		emp = new EmployeeDB(this);
//		setContentView(R.layout.activity_employee_create);
//		initialize_components();
//		create_emp.setOnClickListener(this);
//		empimgone.setOnClickListener(this);
//		empimgtwo.setOnClickListener(this);
//		empimgthree.setOnClickListener(this);
//		empimgfour.setOnClickListener(this);
//	}
//
//	private void initialize_components() {
//		name = (EditText) findViewById(R.id.editTextName);
//		profession = (EditText) findViewById(R.id.editTextProfession);
//		experience = (EditText) findViewById(R.id.editTextExperience);
//		create_emp = (Button) findViewById(R.id.empCreateBtn);
//		empimgone = (ImageView) findViewById(R.id.empImgOne);
//		empimgtwo = (ImageView) findViewById(R.id.empImgTwo);
//		empimgthree = (ImageView) findViewById(R.id.empImgThree);
//		empimgfour = (ImageView) findViewById(R.id.empImgFour);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.empCreateBtn:
//			String val_name = name.getText().toString().trim();
//			String val_profession = profession.getText().toString().trim();
//			String val_experience = experience.getText().toString().trim();
//
//			if (val_name.isEmpty() || val_profession.isEmpty()
//					|| val_experience.isEmpty()) {
//				Toast.makeText(this, "Please enter all fields to save",
//						Toast.LENGTH_SHORT).show();
//			} else {
//				SQLiteDatabase db = emp.getWritableDatabase();
//				ContentValues values = new ContentValues();
//				values.put(EmployeeDB.Name, val_name);
//				values.put(EmployeeDB.Profession, val_profession);
//				values.put(EmployeeDB.Experience, val_experience);
//				long result = db.insert(EmployeeDB.EMPLOYEE_TABLE, null, values);
//				if (result > 0) {
//					Log.d("Insert", String.valueOf(result));
//					Toast.makeText(this, val_name + "'s data saved successfully",
//							Toast.LENGTH_SHORT).show();
//					name.setText("");
//					profession.setText("");
//					experience.setText("");
//				} else {
//					Toast.makeText(this, "Data Saving Error",
//							Toast.LENGTH_SHORT).show();
//				}
//			}
//			break;
//		case R.id.empImgOne:
//			Intent empImgOne = new Intent(
//					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//			startActivityForResult(empImgOne, 10001);
//			break;
//		case R.id.empImgTwo:
//			Intent empImgTwo = new Intent(
//					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//			startActivityForResult(empImgTwo, 10002);
//			break;
//		case R.id.empImgThree:
//			Intent empImgThree = new Intent(
//					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//			startActivityForResult(empImgThree, 10003);
//			break;
//		case R.id.empImgFour:
//			Intent empImgFour = new Intent(
//					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//			startActivityForResult(empImgFour, 10004);
//			break;
//		default:
//
//			break;
//		}
//	}
//
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == 10001 && resultCode == RESULT_OK) {
//			Bitmap photo = (Bitmap) data.getExtras().get("data");
//			empimgone.setImageBitmap(photo);
//		}
//		if (requestCode == 10002 && resultCode == RESULT_OK) {
//			Bitmap photo = (Bitmap) data.getExtras().get("data");
//			empimgtwo.setImageBitmap(photo);
//		}
//		if (requestCode == 10003 && resultCode == RESULT_OK) {
//			Bitmap photo = (Bitmap) data.getExtras().get("data");
//			empimgthree.setImageBitmap(photo);
//		}
//		if (requestCode == 10004 && resultCode == RESULT_OK) {
//			Bitmap photo = (Bitmap) data.getExtras().get("data");
//			empimgfour.setImageBitmap(photo);
//		}
//	}
//
//	@Override
//	protected void onStop() {
//		super.onStop();
//		emp.close();
//	}
//
//}
