package com.example.employeedirectory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeEdit extends Activity implements OnClickListener {
	private static final int GET_PROFILE_IMAGE = 10001;
	private static final int GET_GALLERY_IMAGE = 10002;
	ArrayList<String> MyImages;
	ArrayList<String> RemoveImages;
	String profileImage;
	ImageView profile_image;
	ImageView profile_image_btn;
	ImageView empimg;
	TextView Id;
	EditText Name;
	EditText Profession;
	EditText Experience;
	EmployeeDB empdb;
	Button Save;
	Button Cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_edit);
		initialze();
		Save.setOnClickListener(this);
		Cancel.setOnClickListener(this);
		empimg.setOnClickListener(this);
		profile_image_btn.setOnClickListener(this);
	}

	public void initialze() {
		empdb = new EmployeeDB(this);

		Id = (TextView) findViewById(R.id.details_emp_id_value);
		Name = (EditText) findViewById(R.id.details_emp_name_value);
		Profession = (EditText) findViewById(R.id.details_emp_profession_value);
		Experience = (EditText) findViewById(R.id.details_emp_experience_value);
		Save = (Button) findViewById(R.id.EmpSaveBtn);
		Cancel = (Button) findViewById(R.id.EmpCancelBtn);
		empimg = (ImageView) findViewById(R.id.empImg);
		profile_image = (ImageView) findViewById(R.id.empProfImg);
		profile_image_btn = (ImageView) findViewById(R.id.empProfImgbtn);

		String employee_id = getIntent().getExtras().getString("Id");
		Employee emp = empdb.getEmployee(employee_id);
		profileImage = emp.getProfileImage(EmployeeEdit.this);
		MyImages = emp.getImages(EmployeeEdit.this);
		RemoveImages = new ArrayList<String>();

		// Setting All Data
		Id.setText(emp.getId());
		Name.setText(emp.getName());
		Profession.setText(emp.getProfession());
		Experience.setText(emp.getExperience());
		profile_image.setImageURI(Uri.parse(profileImage));

		for (String image : MyImages) {

			LinearLayout container = (LinearLayout) findViewById(R.id.container);

			LayoutInflater Inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final View addView = Inflater.inflate(R.layout.create_form_row,
					null);

			ImageView img = (ImageView) addView.findViewById(R.id.textout);
			Button buttonRemove = (Button) addView.findViewById(R.id.remove);
			TextView filename = (TextView) addView.findViewById(R.id.filename);
			img.setImageURI(Uri.parse(image));
			filename.setText(image);

			buttonRemove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView tv = (TextView) addView
							.findViewById(R.id.filename);
					((LinearLayout) addView.getParent()).removeView(addView);
					RemoveImages.add(tv.getText().toString());
					MyImages.remove(MyImages.indexOf(tv.getText().toString()));
				}
			});
			container.addView(addView);

			for (String myimage : MyImages) {
				Log.d("MyImage", myimage);
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.EmpSaveBtn:
			String val_id = Id.getText().toString();
			String val_name = Name.getText().toString().trim();
			String val_profession = Profession.getText().toString().trim();
			String val_experience = Experience.getText().toString().trim();
			if (val_name.isEmpty() || val_profession.isEmpty()
					|| val_experience.isEmpty()) {
				Toast.makeText(this, "Please enter all fields to save",
						Toast.LENGTH_SHORT).show();
			} else {
				empdb.UpdateEmp(val_id, val_name, val_profession,
						val_experience);
				empdb.DeleteImages(val_id);
				
				String isProfile = "1";
				if (!(profileImage.isEmpty())) {
					empdb.saveImage(profileImage, val_id, isProfile);
				}

				for (String emp_img_name : RemoveImages) {
					File file = new File(emp_img_name);
					if (file.exists())
						file.delete();
				}

				isProfile = "0";
				for (String emp_img_name : MyImages) {
					empdb.saveImage(emp_img_name, val_id, isProfile);
				}
				empdb.close();
				finish();
			}
			break;
		case R.id.EmpCancelBtn:
			empdb.close();
			finish();
			break;
		case R.id.empImg:
			intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					GET_GALLERY_IMAGE);
			break;
		case R.id.empProfImgbtn:
			intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					GET_PROFILE_IMAGE);
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK) {
			if (null == data)
				return;
			setImages(data, GET_GALLERY_IMAGE);
		}
		if (requestCode == GET_PROFILE_IMAGE && resultCode == RESULT_OK) {
			if (null == data)
				return;
			setImages(data, GET_PROFILE_IMAGE);
		}
	}

	private void setImages(Intent data, int Type) {
		String selectedImagePath;
		Uri selectedImageUri = data.getData();

		selectedImagePath = ImageFilePath.getPath(getApplicationContext(),
				selectedImageUri);

		Bitmap bmp = BitmapFactory.decodeFile(selectedImagePath);

		String fname = String.valueOf(System.currentTimeMillis())
				+ '-'
				+ Secure.getString(this.getContentResolver(), Secure.ANDROID_ID)
				+ ".jpg";

		try {
			FileOutputStream out;// = new FileOutputStream(file);
			out = openFileOutput(fname, Context.MODE_PRIVATE);
			int min_size = 200;
			int height = bmp.getHeight() / 10;
			int width = bmp.getWidth() / 10;
			if (height < min_size) {
				height = min_size;
			}
			if (width < min_size) {
				width = min_size;
			}
			// bmp = Bitmap.createBitmap(bmp, 0, 0, width, height);
			bmp = Bitmap.createScaledBitmap(bmp, width, height, false);
			bmp.compress(Bitmap.CompressFormat.JPEG, 80, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("Image File Path", "" + selectedImageUri);
		String filepath = this.getApplicationContext().getFilesDir() + "/"
				+ fname;

		switch (Type) {
		case GET_GALLERY_IMAGE:
			MyImages.add(filepath);

			LinearLayout container = (LinearLayout) findViewById(R.id.container);

			LayoutInflater Inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final View addView = Inflater.inflate(R.layout.create_form_row,
					null);

			ImageView img = (ImageView) addView.findViewById(R.id.textout);
			Button buttonRemove = (Button) addView.findViewById(R.id.remove);
			TextView filename = (TextView) addView.findViewById(R.id.filename);
			img.setImageBitmap(bmp);
			filename.setText(filepath);

			buttonRemove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView tv = (TextView) addView
							.findViewById(R.id.filename);
					((LinearLayout) addView.getParent()).removeView(addView);
					MyImages.remove(MyImages.indexOf(tv.getText().toString()));
				}
			});
			container.addView(addView);
			for (String myimage : MyImages) {
				Log.d("MyImage", myimage);
			}
			break;
		case GET_PROFILE_IMAGE:
			RemoveImages.add(profileImage);
			profileImage = filepath;
			profile_image.setImageBitmap(bmp);
			break;
		default:
			break;
		}
	}
}
