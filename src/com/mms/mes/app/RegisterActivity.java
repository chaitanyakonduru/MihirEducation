package com.mms.mes.app;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mms.mes.R;
import com.mms.mes.model.GettingAds;
import com.mms.mes.network.SoapServiceManager;

public class RegisterActivity extends Activity implements OnClickListener {
	private static final String TAG = "Register Activity";
	private EditText emailText;
	private Button forgotPwdBtn;
	private Button registerBtn;
	private TextView header;
	private String from;
	
	private SoapObject responseObject;
	private Register_forgotAsynchTask asynchTask;
	private ImageView adImage;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_signup);
		initViews();
		from = getIntent().getStringExtra("from");
		
	
		if (from.equalsIgnoreCase("Register")) {

			forgotPwdBtn.setVisibility(View.GONE);
			registerBtn.setVisibility(View.VISIBLE);
			header.setText("REGISTER");
			new GettingAds(adImage, this, 2).getAds();
		
		} else if (from.equalsIgnoreCase("ForgotPwd")) {
			registerBtn.setVisibility(View.GONE);
			forgotPwdBtn.setVisibility(View.VISIBLE);
			header.setText("FORGOT PASSWORD");
			new GettingAds(adImage, this, 3).getAds();
			// new adRequestTask().execute(FORGETPWDSCREENID,SCHOOLID);
		}
		
		registerBtn.setOnClickListener(this);
		forgotPwdBtn.setOnClickListener(this);
	}

	private void initViews() {
		header = (TextView) findViewById(R.id.register_tv_header);
		emailText = (EditText) findViewById(R.id.signup_edittext_email);
		registerBtn = ((Button) findViewById(R.id.signup_btn_register));
		forgotPwdBtn = (Button) findViewById(R.id.forgotpwd_btn_send);
		adImage=(ImageView) findViewById(R.id.mms_ad_image);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_btn_register:
			registerDetails();
			break;
		case R.id.forgotpwd_btn_send:
			forgotPwd();
		default:
			break;
		}
	}

	private void forgotPwd() {
		if (TextUtils.isEmpty(emailText.getText().toString())) {
			emailText.setError("Please give your Mihir Id");
			return;
		}

		else {
			asynchTask = (Register_forgotAsynchTask) new Register_forgotAsynchTask();
			asynchTask.execute(emailText.getText().toString());
		}
	}

	private void registerDetails() {
		if (TextUtils.isEmpty(emailText.getText().toString())) {
			emailText.setError("Please give yout Mihir Id");
			return;
		}

		else {
			new Register_forgotAsynchTask()
					.execute(emailText.getText().toString());
		}
	}

	class Register_forgotAsynchTask extends
			AsyncTask<String, Boolean, SoapObject> {

		private ProgressDialog progressDialog;

		protected SoapObject doInBackground(String... params) {

			if (from.equalsIgnoreCase("Register")) {
				responseObject = SoapServiceManager
						.sendregisterRequest(params[0]);
			} else {
				responseObject = SoapServiceManager
						.sendforgotPasswordRequest(params[0]);
			}
			return responseObject;
		}

		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			String msg = "";
			if (result != null) {
				if (result.hasProperty("RegistrationMSG")) {

					msg = result.getProperty("RegistrationMSG")
							.toString();
					LoginActivity.showDialog(RegisterActivity.this, msg, false);
					/*if (msg.equalsIgnoreCase("NO MMS_ID EXISTS")) 
					{
						LoginActivity.showDialog(RegisterActivity.this, msg,false);
					} 
					else if (msg.equalsIgnoreCase("MAILSENTFAILURE")) 
					{
						LoginActivity.showDialog(RegisterActivity.this, msg,true);
						
					} else if (msg.equalsIgnoreCase("USER ALREADY EXISTS")) {

						LoginActivity.showDialog(RegisterActivity.this, msg,false);
						
						
						
					}
*/				}

				else if (result.hasProperty("FPMSG")) {
					msg = result.getProperty("FPMSG").toString();
					LoginActivity.showDialog(RegisterActivity.this, msg,false);
/*					if (msg.equalsIgnoreCase("EMAIL OR MOBILENUMBER NOT FOUND")) {
						LoginActivity.showDialog(RegisterActivity.this, msg,false);

					} else {
						LoginActivity.showDialog(RegisterActivity.this, msg,true);

					}
*/				}
			}

			else {
				displayMessage("Unable to process your request");
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(RegisterActivity.this);
			progressDialog.setMessage("Please Wait...");
			progressDialog.show();
		}

	}

	private void displayMessage(String msg) {
		Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	
}
