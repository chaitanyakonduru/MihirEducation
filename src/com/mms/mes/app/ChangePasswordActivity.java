package com.mms.mes.app;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.network.SoapServiceManager;

public class ChangePasswordActivity extends Activity implements OnClickListener {

	public static final String TAG = "ChangePassword Activity";
	private EditText email;
	private EditText oldPwd;
	private EditText newPwd;
	private EditText confPwd;
	private ProgressDialog progressdialog;
	private Student studentInfo;
	private ImageView adview;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_change_password);
		MihirApp app = (MihirApp) getApplication();
		studentInfo = app.getCurStudent();
		initViews();

	}

	private void initViews() {
		setschooldetails(studentInfo);
		email = (EditText) findViewById(R.id.changepwd_edittext_email);
		oldPwd = (EditText) findViewById(R.id.change_pwd_edittext_oldpwd);
		newPwd = (EditText) findViewById(R.id.change_pwd_edittext_newpwd);
		confPwd = (EditText) findViewById(R.id.change_pwd_edittext_confpwd);
		confPwd.setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					changePassword();
					return true;
				}
				return false;
			}
		});

		((TextView) findViewById(R.id.action_bar_tv_student_name))
				.setText(studentInfo.getmStudentName());
		((TextView) findViewById(R.id.action_bar_tv_class_name))
				.setText("Class : " + studentInfo.getmClassName());
		((TextView) findViewById(R.id.action_bar_tv_section_name))
				.setText("Section : " + studentInfo.getmSectionName());
		((Button) findViewById(R.id.change_pwd_btn_change))
				.setOnClickListener(ChangePasswordActivity.this);

		adview = (ImageView) findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 16).getAds();
	}

	private void setschooldetails(Student info) {
		// TODO Auto-generated method stub

		((TextView) findViewById(R.id.action_tv_school_name)).setText(info
				.getmSchoolShortName());

		((ImageView) findViewById(R.id.school_logo))
				.setImageDrawable(HomeActivity.drawable);
		((TextView) findViewById(R.id.mihir_productname)).setText(AuthResponse
				.getmProductName());
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.change_pwd_btn_change:
			changePassword();
			break;
		default:
			break;
		}
	}

	private void changePassword() {

		if (TextUtils.isEmpty(email.getText().toString())) {
			email.setError("Please give your Email Id");
			return;
		}

		else if (TextUtils.isEmpty(oldPwd.getText().toString())) {
			oldPwd.setError("Please give Old Password");
			return;
		}

		else if (TextUtils.isEmpty(newPwd.getText().toString())) {
			newPwd.setError("Please give New Password");
			return;
		}

		else if (TextUtils.isEmpty(confPwd.getText().toString())) {
			confPwd.setError("Please give conform Password");
			return;
		}

		else if (!(newPwd.getText().toString().trim().equalsIgnoreCase(confPwd
				.getText().toString().trim()))) {
			LoginActivity.showDialog(ChangePasswordActivity.this,
					"New and Confirm passwords mismatch", false);
		} else if (newPwd.getText().toString().trim().equalsIgnoreCase(
				oldPwd.getText().toString().trim())) {
			LoginActivity.showDialog(ChangePasswordActivity.this,
					"New Password should not be OldPassword", false);
		}

		else {
			new changePasswordAsynchTask().execute(email.getText().toString()
					.trim(), oldPwd.getText().toString().trim(), newPwd
					.getText().toString().trim());
		}
	}

	class changePasswordAsynchTask extends
			AsyncTask<String, Boolean, SoapObject> {
		protected SoapObject doInBackground(String... params) {

			return SoapServiceManager.sendchangePasswordRequest(params[0],
					params[1], params[2]);
		}

		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);

			Log.v(TAG, "POST EXECUTE");
			progressdialog.dismiss();
			if (result != null) {
				if (result.hasProperty("CPMSG")) {
					final String responseMessage = result
							.getPropertyAsString("CPMSG");
					if (responseMessage.equalsIgnoreCase("Invalid Details")) {

						LoginActivity.showDialog(ChangePasswordActivity.this,
								responseMessage, false);

					} else {
						LoginActivity.showDialog(ChangePasswordActivity.this,
								responseMessage, true);
					}
				}
			} else {
				Toast.makeText(ChangePasswordActivity.this,
						" Unable to process your request ", Toast.LENGTH_SHORT)
						.show();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			progressdialog = new ProgressDialog(ChangePasswordActivity.this);
			progressdialog.setMessage("Please wait....");
			progressdialog.show();

		}

	}

}
