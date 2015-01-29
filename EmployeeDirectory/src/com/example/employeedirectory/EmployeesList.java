package com.example.employeedirectory;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EmployeesList extends Activity {

	static String concat = ", ";
	EmployeeDB emp;
	ListView lv;
	MyAdapter MyAdapter;
	ArrayList<Employee> employees;
	Button Loadmore;
	static int last_id_retrieved;
	int completed = 0;
	static int limit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employees_list);
		initialze(1);
		Log.d("Start", "onCreate");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		initialze(completed);
		Log.d("Start", "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		initialze(completed);
		Log.d("Start", "onResume");
	}

	public void initialze(int completed) {
		emp = new EmployeeDB(this);
		last_id_retrieved = 0;
		limit = 5;

		employees = emp.getAllEmployees(last_id_retrieved, limit);

		lv = (ListView) findViewById(R.id.lists);
		Button btn = (Button) lv.findViewById(100);
		if ((btn == null) && (completed == 1) && (employees.size() == limit)) {
			Loadmore = new Button(this);
			Loadmore.setId(100);
			Loadmore.setText("Load More Employees");
			lv.addFooterView(Loadmore);
			Loadmore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new loadMoreListView().execute();
				}
			});
		}

		MyAdapter = new MyAdapter(this, android.R.layout.simple_list_item_1,
				R.layout.emp_list_item, employees);

		lv.setAdapter(MyAdapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> Adapter, View v,
					int position, long id) {
				TextView Id = (TextView) v.findViewById(R.id.empid);
				Intent intent = new Intent(EmployeesList.this,
						EmployeeDetails.class);
				intent.putExtra("Id", Id.getText().toString());
				startActivity(intent);
				// finish();
			}

		});

		
	}

	private class MyAdapter extends ArrayAdapter<Employee> {
		public ArrayList<Employee> empDetails;
		public int itemview;

		public MyAdapter(Context context, int resource, int textViewResourceId,
				ArrayList<Employee> strings) {
			super(context, resource, textViewResourceId, strings);
			this.empDetails = strings;
			this.itemview = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View row = inflator.inflate(itemview, parent, false);

			ImageView im = (ImageView) row.findViewById(R.id.empprofileimg);

			TextView Id = (TextView) row.findViewById(R.id.empid);
			TextView Name = (TextView) row.findViewById(R.id.empname);
			TextView Details = (TextView) row.findViewById(R.id.empdetails);
			TextView ProfileImage = (TextView) row
					.findViewById(R.id.empprofileimgvalue);

			String profile_image = emp.getProfileImage(empDetails.get(position)
					.getId(), "1");

			Id.setText(empDetails.get(position).getId());
			Name.setText(empDetails.get(position).getName());
			Details.setText(empDetails.get(position).getProfession() + concat
					+ empDetails.get(position).getExperience() + " Years");

			if (!(profile_image.isEmpty())) {
				ProfileImage.setText(profile_image);
				im.setImageURI(Uri.parse(profile_image));
			}

			return row;
		}
	}

	private class loadMoreListView extends AsyncTask<Void, Void, Void> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(EmployeesList.this);
			pDialog.setMessage("Loading Employee Data, Please wait...");
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(Void... args) {
			runOnUiThread(new Runnable() {
				public void run() {
					int currentPosition = lv.getFirstVisiblePosition();

					ArrayList<Employee> more_employees = emp.getAllEmployees(
							last_id_retrieved, limit);

					employees.addAll(more_employees);

					MyAdapter = new MyAdapter(EmployeesList.this,
							android.R.layout.simple_list_item_1,
							R.layout.emp_list_item, employees);

					lv.setAdapter(MyAdapter);
					lv.setSelectionFromTop(currentPosition + limit, 0);

					if (more_employees.size() < limit) {
						lv.removeFooterView(Loadmore);
						completed = 1;
					}
				}
			});
			return (null);
		}

		protected void onPostExecute(Void unused) {
			pDialog.dismiss();
		}
	}

}
