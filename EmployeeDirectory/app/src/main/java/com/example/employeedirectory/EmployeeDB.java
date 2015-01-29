package com.example.employeedirectory;

import java.io.File;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class EmployeeDB extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "emp.db";
	private static final int DATABASE_VERSION = 1;

	// Table name
	public static final String EMPLOYEE_TABLE = "employees";
	public static final String IMAGES_TABLE = "images";

	// Columns
	public static final String Name = "name";
	public static final String Profession = "profession";
	public static final String Experience = "experience";

	public static final String Imgae_Name = "name";
	public static final String Imgae_Profile = "is_profile";
	public static final String Imgae_Employee = "employee_id";

	public EmployeeDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + EMPLOYEE_TABLE + "( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + Name
				+ " varchar(200) , " + Profession + " varchar(200), "
				+ Experience + " varchar(200) " + ");";

		Log.d("EmployeeDirectory", "onCreate: " + sql);
		db.execSQL(sql);

		String sqls = "create table " + IMAGES_TABLE + "( " + BaseColumns._ID
				+ " integer primary key autoincrement," + Imgae_Name
				+ " varchar(200), " + Imgae_Profile + " varchar(1),"
				+ Imgae_Employee + " integer ," + " FOREIGN KEY ("
				+ Imgae_Employee + ") REFERENCES " + EMPLOYEE_TABLE + " ("
				+ BaseColumns._ID + "));";

		Log.d("EmployeeDirectory", "onCreate: " + sqls);
		db.execSQL(sqls);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)
			return;

		String sql = null;
		if (oldVersion == 1)
			sql = "alter table " + EMPLOYEE_TABLE + " add note text;";

		if (oldVersion == 2)
			sql = "";

		Log.d("EmployeeDirectory", "onUpgrade	: " + sql);
		if (sql != null)
			db.execSQL(sql);

	}

	public ArrayList<Employee> getAllEmployees(int employee_id, int limit) {
		String selectQuery = "SELECT  * FROM " + EMPLOYEE_TABLE + " where "
				+ BaseColumns._ID + ">" + employee_id + " Limit " + limit;

		ArrayList<Employee> allEmp = new ArrayList<Employee>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Employee emp = new Employee(Integer.parseInt(cursor
						.getString(0)), cursor.getString(1),
						cursor.getString(2), cursor.getString(3));
				allEmp.add(emp);
				EmployeesList.last_id_retrieved = Integer.parseInt(cursor
						.getString(0));
			} while (cursor.moveToNext());
		}
		db.close();
		return allEmp;
	}

	public Employee getEmployee(String employee_id) {
		Employee emp = null;
		String selectQuery = "SELECT  * FROM " + EMPLOYEE_TABLE + " where "
				+ BaseColumns._ID + "=" + employee_id;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				emp = new Employee(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3));
			} while (cursor.moveToNext());
		}
		db.close();
		return emp;
	}

	public String getProfileImage(String Employee, String isProfile) {
		String images = "";
		String selectQuery = "SELECT  * FROM " + IMAGES_TABLE + " where "
				+ Imgae_Employee + "=" + Employee + " AND " + Imgae_Profile
				+ "=" + isProfile;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			images = cursor.getString(1);
		}
		db.close();
		return images;
	}

	public ArrayList<String> getImages(String Employee, String isProfile) {
		ArrayList<String> images = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + IMAGES_TABLE + " where "
				+ Imgae_Employee + "=" + Employee + " AND " + Imgae_Profile
				+ "=" + isProfile;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Log.d("id : ", cursor.getString(0));
				Log.d("Name : ", cursor.getString(1));
				Log.d("Type : ", cursor.getString(2));
				Log.d("Employee : ", cursor.getString(3));
				images.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return images;
	}

	public void UpdateEmp(String Id, String Name, String Profession,
			String Experience) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(EmployeeDB.Name, Name);
		cv.put(EmployeeDB.Profession, Profession);
		cv.put(EmployeeDB.Experience, Experience);
		db.update(EMPLOYEE_TABLE, cv, BaseColumns._ID + "=?",
				new String[] { String.valueOf(Id) });
		db.close();
	}

	public long createEmployee(String Name, String Profession, String Experience) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues employee_data = new ContentValues();
		employee_data.put(EmployeeDB.Name, Name);
		employee_data.put(EmployeeDB.Profession, Profession);
		employee_data.put(EmployeeDB.Experience, Experience);

		long employee_id = db.insert(EmployeeDB.EMPLOYEE_TABLE, null,
				employee_data);

		db.close();
		return employee_id;

	}

	public long saveImage(String image, String employeeId, String isProfile) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues image_data = new ContentValues();
		image_data.put(EmployeeDB.Imgae_Name, image);
		image_data.put(EmployeeDB.Imgae_Employee, employeeId);
		image_data.put(EmployeeDB.Imgae_Profile, isProfile);

		long image_id = db.insert(EmployeeDB.IMAGES_TABLE, null, image_data);

		db.close();
		return image_id;
	}

	public void DeleteEmp(String Id) {
		ArrayList<String> RemoveImages = new ArrayList<String>();
		RemoveImages = getImages(Id, "0");
		for (String emp_img_name : RemoveImages) {
			File file = new File(emp_img_name);
			if (file.exists())
				file.delete();
		}
		RemoveImages = getImages(Id, "1");
		for (String emp_img_name : RemoveImages) {
			File file = new File(emp_img_name);
			if (file.exists())
				file.delete();
		}
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(IMAGES_TABLE, Imgae_Employee + "=?",
				new String[] { String.valueOf(Id) });
		db.delete(EMPLOYEE_TABLE, BaseColumns._ID + "=?",
				new String[] { String.valueOf(Id) });
		db.close();
	}

	public void DeleteImages(String Id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(IMAGES_TABLE, Imgae_Employee + "=?",
				new String[] { String.valueOf(Id) });
		db.close();
	}
}
