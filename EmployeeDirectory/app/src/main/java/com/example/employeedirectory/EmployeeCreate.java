package com.example.employeedirectory;

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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class EmployeeCreate extends Activity implements OnClickListener {

	private static final int GET_PROFILE_IMAGE = 10001;
	private static final int GET_GALLERY_IMAGE = 10002;
	EmployeeDB emp;
	EditText name;
	EditText profession;
	EditText experience;
	Button create_emp;
	ImageView empimg;
	int viewid = 1000;
	ArrayList<String> MyImages;
	ArrayList<String> RemoveImages;
	String profileImage;
	ImageView profile_image;
	ImageView profile_image_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyImages = new ArrayList<String>();
		RemoveImages = new ArrayList<String>();
		profileImage = "";
		super.onCreate(savedInstanceState);
		emp = new EmployeeDB(this);
		setContentView(R.layout.activity_employee_create);
		initialize_components();
		create_emp.setOnClickListener(this);
		empimg.setOnClickListener(this);
		profile_image_btn.setOnClickListener(this);
	}

	private void initialize_components() {
		name = (EditText) findViewById(R.id.editTextName);
		profession = (EditText) findViewById(R.id.editTextProfession);
		experience = (EditText) findViewById(R.id.editTextExperience);
		create_emp = (Button) findViewById(R.id.empCreateBtn);
		empimg = (ImageView) findViewById(R.id.empImg);
		profile_image = (ImageView) findViewById(R.id.empProfImg);
		profile_image_btn = (ImageView) findViewById(R.id.empProfImgbtn);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.empCreateBtn:
			String val_name = name.getText().toString().trim();
			String val_profession = profession.getText().toString().trim();
			String val_experience = experience.getText().toString().trim();

			if (val_name.isEmpty() || val_profession.isEmpty()
					|| val_experience.isEmpty()) {
				Toast.makeText(this, "Please enter all fields to save",
						Toast.LENGTH_SHORT).show();
			} else {
				long employee_id = emp.createEmployee(val_name, val_profession,
						val_experience);

				if (employee_id > 0) {
					
					String isProfile = "1";
					
					if (!(profileImage.isEmpty())) {
						emp.saveImage(profileImage,
								String.valueOf(employee_id), isProfile);
					}

					for (String emp_img_name : RemoveImages) {
						File file = new File(emp_img_name);
						if (file.exists())
							file.delete();
					}

					isProfile = "0";
					for (String emp_img_name : MyImages) {
						emp.saveImage(emp_img_name,
								String.valueOf(employee_id), isProfile);
					}
					Log.d("Insert", String.valueOf(employee_id));
					Toast.makeText(this,
							val_name + "'s data saved successfully",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(EmployeeCreate.this,
							EmployeesList.class));
					finish();
				} else {
					Toast.makeText(this, "Data Saving Error",
							Toast.LENGTH_SHORT).show();
				}
			}
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
					RemoveImages.add(tv.getText().toString());
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

	@Override
	protected void onStop() {
		super.onStop();
		emp.close();
	}

}
