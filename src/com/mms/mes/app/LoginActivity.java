package com.mms.mes.app;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.network.Comm;
import com.mms.mes.network.SoapServiceManager;

public class LoginActivity extends Activity implements OnClickListener {

	private ProgressDialog progressDialog;
	private EditText pwdText;
	private EditText unameText;
	private ImageView imageView;
	private String userData;

	public static SharedPreferences mMyPrefs;
	public static List<Student> mStudentInfos;
	public String mUserName;
	private String mPassword;
	private String mDeviceId;
	public static final String USERDATA = "userdata";
	public static final String LOGOUTTIME = "logouttime";
	public static final String USERNAME = "user";
	private static final String TAG = "LoginActivity";
	private MihirApp app;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		Log.v(TAG, "OnCreate ");
		initViews();

		mStudentInfos = new ArrayList<Student>();
		GettingAds ads = new GettingAds(imageView, LoginActivity.this, 01);
		ads.getAds();

	}

	private void initViews() {
		imageView = (ImageView) findViewById(R.id.mms_ad_image);
		unameText = (EditText) findViewById(R.id.login_edittext_username);
		pwdText = (EditText) findViewById(R.id.login_edittext_password);
		pwdText.setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					checkLoginCredentials();
					return true;
				}
				return false;
			}
		});

		findViewById(R.id.login_btn_login).setOnClickListener(this);
		findViewById(R.id.login_btn_forgotpwd).setOnClickListener(this);
		findViewById(R.id.login_btn_register).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.login_btn_login:
			checkLoginCredentials();
			break;

		case R.id.login_btn_forgotpwd:
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			intent.putExtra("from", "ForgotPwd");
			startActivity(intent);
			break;

		case R.id.login_btn_register:
			intent = new Intent(LoginActivity.this, RegisterActivity.class);
			intent.putExtra("from", "Register");
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void checkLoginCredentials() {
		mUserName = unameText.getText().toString().trim();
		mPassword = pwdText.getText().toString().trim();

		if (mUserName.equalsIgnoreCase("") || mPassword.equalsIgnoreCase("")) {
			showDialog(LoginActivity.this, "Both Fields are manadatory", false);
		}

		else {
			TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			mDeviceId = manager.getDeviceId();

			new AuthenticateRequestTask().execute(mUserName, mPassword,
					mDeviceId);
		}
	}

	public void storePreferences(String userInfo, long logoutTime) {

		mMyPrefs = this.getSharedPreferences("userPrefs", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = mMyPrefs.edit();
		prefsEditor.putString(USERDATA, userInfo);
		prefsEditor.putLong(LOGOUTTIME, logoutTime);
		prefsEditor.putString(USERNAME, mUserName);
		prefsEditor.commit();

	}

	class AuthenticateRequestTask extends
			AsyncTask<String, Boolean, SoapObject> {

		@Override
		protected SoapObject doInBackground(String... params) {

			return SoapServiceManager.sendAuthenticateRequest(params[0],
					params[1], params[2]);

		}

		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);

			hideProgressDialog();
			Log.v(TAG, "POST EXECUTE");

			if (result != null) {
				Log.v(TAG, result.toString());
				AuthResponse.builder = new StringBuilder("");

				AuthResponse authResponse = null;
				try {
					authResponse = Comm.parseAuthentication(result);
				} catch (Exception e) {
				}

				if (!(authResponse.mAuthenticateMsg
						.equalsIgnoreCase("Please provide valid account details.Contact School Administration for further assistance"))) {
					long logoutTime = System.currentTimeMillis()
							+ (10 * 24 * 60 * 60 * 1000);

					storePreferences(AuthResponse.builder.toString(),
							logoutTime);
				}

				loginStatus(authResponse);
			} else {

				displayToast("Unable to process your request");
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog();

		}

		private void showProgressDialog() {
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setMessage("Signing in...");
			progressDialog.show();
		}
	}

	public void displayToast(String msg) {

		Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	public void hideProgressDialog() {
		progressDialog.dismiss();
	}

	public void loginStatus(AuthResponse authResponse) {

		Log.v(TAG, "In Login Activity:::" + authResponse.toString());
		String loginType = authResponse.getmLogonType();
		MihirApp app = (MihirApp) getApplication();
		if (loginType.equals("Parent")) {
			app.setIsloggedin(true);
			mStudentInfos = authResponse.getStudents();
			int size = mStudentInfos.size();
			if (size == 1) {
				app.setCurStudent(null);
				Student studentInfo = authResponse.getStudents().get(0);
				Log.v(TAG, "Student Name is:" + studentInfo.getmStudentName()
						+ "\n" + studentInfo.getmSchoolLogoUrl() + "\n"
						+ studentInfo.getmSchoolName());
				app.setCurStudent(studentInfo);
				Intent intent = new Intent(LoginActivity.this,
						HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				// finish();

			} else {

				Intent intent = new Intent(LoginActivity.this,
						ParentActivity.class);
				startActivity(intent);
				// finish();
			}
		}

		else if (loginType.equalsIgnoreCase("Student")) {
			app.setIsloggedin(true);
			AuthResponse.Student mStudentInfo = authResponse.getStudents().get(
					0);
			app.setCurStudent(mStudentInfo);
			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
			startActivity(intent);
			finish();
		}

		else if (authResponse.getmAuthenticateMsg().equalsIgnoreCase(
				"NO_CHILDS")) {
			showDialog(LoginActivity.this,
					"No Student(s) associated with this User", false);
		} else {
			showDialog(LoginActivity.this, authResponse.mAuthenticateMsg, false);
		}
	}

	public static void showDialog(final Context context, String msg,
			final Boolean toBeFinish) {
		AlertDialog.Builder builder = new Builder(context);

		TextView msgText = new TextView(context);
		msgText.setText(msg);
		msgText.setTextSize(20);
		msgText.setGravity(0x11);
		msgText.setTextColor(Color.WHITE);
		builder.setView(msgText);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				if (toBeFinish) {
					Activity activity = (Activity) context;
					activity.finish();
				}
			}
		});
		builder.create().show();
	}

	public void enableSignIn(String registrationId) {
		findViewById(R.id.login_btn_login).setEnabled(true);
		Log.v(TAG, registrationId);
	}

	protected void onStart() {
		super.onStart();
		Log.v(TAG, "On Start");
	}

	protected void onStop() {
		super.onStop();

		Log.v(TAG, "On Stop");
	}

	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "On Destroy");
	}

	protected void onRestart() {
		super.onRestart();
		app = (MihirApp) getApplication();
		if (app.isIsloggedin()) {
			finish();
		}
	}
}
