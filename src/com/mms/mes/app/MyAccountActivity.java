package com.mms.mes.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.AuthResponse.Student;

public class MyAccountActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {
	private static final String TAG = "MyAccountActivity";
	private Spinner spinner;
	private Spinner number_spinner;
	private Student studentInfo;
	private String[] days_hours = { "Days", "Hours", "Never" };
	private ImageView adview;
	private MihirApp app;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myaccount1);
		app = (MihirApp) getApplication();
		studentInfo = app.getCurStudent();
		initViews();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, days_hours);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

	}

	private void initViews() {
		setschooldetails(studentInfo);
		((TextView) findViewById(R.id.action_bar_tv_student_name))
				.setText(studentInfo.getmStudentName());
		((TextView) findViewById(R.id.action_bar_tv_class_name))
				.setText("Class : " + studentInfo.getmClassName());
		((TextView) findViewById(R.id.action_bar_tv_section_name))
				.setText("Section : " + studentInfo.getmSectionName());
		spinner = (Spinner) findViewById(R.id.myaccount_spinner_logouttime);
		number_spinner = (Spinner) findViewById(R.id.number_spinner);
		((Button) findViewById(R.id.myaccount_btn_signout))
				.setOnClickListener(MyAccountActivity.this);
		((Button) findViewById(R.id.myaccount_btn_changepwd))
				.setOnClickListener(MyAccountActivity.this);

		spinner.setOnItemSelectedListener(this);
		number_spinner.setOnItemSelectedListener(this);

		((TextView) findViewById(R.id.myaccount_tv_mmsid)).setText(AuthResponse
				.getmAccName());
		adview = (ImageView) findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 15).getAds();

	}

	private void setschooldetails(Student info) {

		((TextView) findViewById(R.id.action_tv_school_name)).setText(info
				.getmSchoolShortName());

		((ImageView) findViewById(R.id.school_logo))
				.setImageDrawable(HomeActivity.drawable);
		((TextView) findViewById(R.id.mihir_productname)).setText(AuthResponse
				.getmProductName());
	}

	public void removePrefs() {
		LoginActivity.mMyPrefs = this.getSharedPreferences("userPrefs",
				MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = LoginActivity.mMyPrefs.edit();
		prefsEditor.putString(LoginActivity.USERDATA, null);
		prefsEditor.putLong(LoginActivity.LOGOUTTIME, 0L);
		prefsEditor.commit();

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myaccount_btn_signout:
			removePrefs();
			Context context=v.getContext();
			app.setIsloggedin(false);
//			app.setCurStudent(null);
			
//			Intent intent = new Intent(MyAccountActivity.this,
//					LoginActivity.class);
//			
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			context.startActivity(intent);
			finish();
			break;

		case R.id.myaccount_btn_changepwd:
			startActivity(new Intent(MyAccountActivity.this,
					ChangePasswordActivity.class));

			break;
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long id) {
		Log.v(TAG, arg0.getItemAtPosition(arg2).toString());

		Log.v(TAG, "Spinner" + id);
		if (arg0.getItemAtPosition(arg2).toString().equals("Days")) {
			number_spinner.setVisibility(View.VISIBLE);
			String days[] = new String[10];
			for (int i = 0; i < 10; i++) {
				days[i] = String.valueOf(i + 1);
			}

			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
					MyAccountActivity.this,
					android.R.layout.simple_spinner_item, days);
			adapter1
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			number_spinner.setAdapter(adapter1);
			Log.v(TAG, "Days Selected");

		}

		else if (arg0.getItemAtPosition(arg2).toString().equals("Hours")) {
			number_spinner.setVisibility(View.VISIBLE);
			String days[] = new String[23];
			for (int i = 0; i < 23; i++) {
				days[i] = String.valueOf(i + 1);
			}

			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
					MyAccountActivity.this,
					android.R.layout.simple_spinner_item, days);
			adapter1
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			number_spinner.setAdapter(adapter1);
			Log.v(TAG, "Hours is selected");

		} else if (arg0.getItemAtPosition(arg2).toString().equals("Never")) {
			number_spinner.setVisibility(View.INVISIBLE);
			Log.v(TAG, "Never Selected");
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(TAG, "On Pause");
		Log.v(TAG, spinner.getSelectedItem().toString());
		Log.v(TAG, number_spinner.getSelectedItem().toString());
		Long logoutTime = 0L;
		if (spinner.getSelectedItem().toString().equalsIgnoreCase("Days")) {
			logoutTime = System.currentTimeMillis()
					+ Long.valueOf(number_spinner.getSelectedItem().toString())
					* 24 * 60 * 60 * 1000;
		} else if (spinner.getSelectedItem().toString().equalsIgnoreCase(
				"Hours")) {
			logoutTime = System.currentTimeMillis()
					+ Long.valueOf(number_spinner.getSelectedItem().toString())
					* 60 * 60 * 1000;
		} else if (spinner.getSelectedItem().toString().equalsIgnoreCase(
				"Never")) {
			logoutTime = 1L;
		}

		LoginActivity.mMyPrefs = this.getSharedPreferences("userPrefs",
				MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = LoginActivity.mMyPrefs.edit();
		prefsEditor.putLong(LoginActivity.LOGOUTTIME, logoutTime);
		prefsEditor.commit();
		// Toast.makeText(MyAccountActivity.this, "Settings has been changed",
		// Toast.LENGTH_SHORT).show();

	}

}
