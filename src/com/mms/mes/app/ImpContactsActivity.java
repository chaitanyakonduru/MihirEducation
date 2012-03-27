package com.mms.mes.app;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.ViewContactsResp;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.model.ViewContactsResp.FeeDetails;
import com.mms.mes.model.ViewContactsResp.SchoolContacts;
import com.mms.mes.model.ViewContactsResp.StudentContacts;
import com.mms.mes.network.Comm;
import com.mms.mes.network.SoapServiceManager;

public class ImpContactsActivity extends Activity implements OnClickListener {
	public static final String TAG = "ImpContactsActivity";
	private Student studentInfo;

	private ProgressDialog progressDialog;
	private ImageView adview;
	private TextView stdEmgContact;
	private TextView stdDoctorContact;
	private TextView schEmgContact;
	private TextView scAdmin1;
	private TextView schbillingno;
	private TextView complContact;
	private TextView ragContactno;
	private SchoolContacts schoolContacts;
	private StudentContacts studentContacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_emergency);
		MihirApp app = (MihirApp) getApplication();
		studentInfo = app.getCurStudent();
		initViews();
		Intent intent=new Intent();
		intent.setAction(Intent.ACTION_SENDTO);
		
		new ViewContactsRequestTask().execute(studentInfo.getmStudentId());
	}

	public class ViewContactsRequestTask extends
			AsyncTask<Integer, Boolean, SoapObject> {

		protected SoapObject doInBackground(Integer... params) {

			return SoapServiceManager
					.sendViewImpContactsRequest(studentInfo.mStudentId);
		}

		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);
			hideProgressDialog();
			Log.v(TAG, "POST EXECUTE");
			if (result != null) {
				processResult(result);
			} else {
				Toast.makeText(ImpContactsActivity.this,
						"Unable to process your request", Toast.LENGTH_SHORT)
						.show();
			}

		}

		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog();
		}

	}

	public void processResult(SoapObject result) {

		ViewContactsResp contactsResp = Comm.parseViewContacts(result);

		FeeDetails details = contactsResp.getmFeeDetails();

		((TextView) findViewById(R.id.contacts_tv_fee_due_date))
				.setText(details.getmFeeDueDate());
		((TextView) findViewById(R.id.contacts_tv_dueamt)).setText(details
				.getmDueAmt());

		studentContacts = contactsResp.getmStudentContacts();

		stdEmgContact = (TextView) findViewById(R.id.contacts_tv_emergency_no);
		stdDoctorContact = (TextView) findViewById(R.id.contacts_tv_doctor_no);

		schEmgContact = (TextView) findViewById(R.id.contacts_tv_emergency1_no);
		scAdmin1 = (TextView) findViewById(R.id.contacts_tv_schooladmin1_no);
		schbillingno = (TextView) findViewById(R.id.contacts_tv_schoolbilling1_no);
		complContact = (TextView) findViewById(R.id.contacts_tv_complaints_contact_no);
		ragContactno = (TextView) findViewById(R.id.contacts_tv_ragging_contact_no);

		stdEmgContact.setText(studentContacts.getmStudentEmergencyContactNo());
		stdEmgContact.setOnClickListener(this);
		stdDoctorContact.setText(studentContacts.getmStudentDoctorno());
		stdDoctorContact.setOnClickListener(this);

		schoolContacts = contactsResp.getmSchoolContacts();

		schEmgContact.setText(schoolContacts.getmSchoolEmergencyContactno());
		schEmgContact.setOnClickListener(this);
		scAdmin1.setText(schoolContacts.getmSchoolAdmin1());
		scAdmin1.setOnClickListener(this);

		schbillingno.setText(schoolContacts.getmSchoolBilling1());
		schbillingno.setOnClickListener(this);
		complContact.setText(schoolContacts.getmComplaintsContact());
		complContact.setOnClickListener(this);
		ragContactno.setText(schoolContacts.getmRaggingContact());
		ragContactno.setOnClickListener(this);
	}

	public void hideProgressDialog() {

		progressDialog.dismiss();
	}

	public void showProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading Contacts...");
		progressDialog.show();
	}

	private void initViews() {
		setschooldetails(studentInfo);

		((TextView) findViewById(R.id.action_bar_tv_student_name))
				.setText(studentInfo.getmStudentName());
		((TextView) findViewById(R.id.action_bar_tv_class_name))
				.setText("Class : " + studentInfo.getmClassName());
		((TextView) findViewById(R.id.action_bar_tv_section_name))
				.setText("Section : " + studentInfo.getmSectionName());

		adview = (ImageView) findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 17).getAds();
	}

	private void setschooldetails(Student info) {

		((TextView) findViewById(R.id.action_tv_school_name)).setText(info
				.getmSchoolShortName());

		((ImageView) findViewById(R.id.school_logo))
				.setImageDrawable(HomeActivity.drawable);
		((TextView) findViewById(R.id.mihir_productname)).setText(AuthResponse
				.getmProductName());
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contacts_tv_emergency_no:

			makecall(studentContacts.getmStudentEmergencyContactNo().trim());
		case R.id.contacts_tv_doctor_no:
			makecall(studentContacts.getmStudentDoctorno().trim());
			break;
		case R.id.contacts_tv_emergency1_no:
			makecall(schoolContacts.getmSchoolEmergencyContactno().trim());
			break;
		case R.id.contacts_tv_schooladmin1_no:
			makecall(schoolContacts.getmSchoolAdmin1().trim());
			break;
		case R.id.contacts_tv_schoolbilling1_no:
			makecall(schoolContacts.getmSchoolBilling1().trim());
			break;
		case R.id.contacts_tv_complaints_contact_no:
			makecall(schoolContacts.getmComplaintsContact().trim());
			break;
		case R.id.contacts_tv_ragging_contact_no:
			makecall(schoolContacts.getmRaggingContact().trim());
			break;
		default:
			break;
		}
	}

	private void makecall(String string) {
		if(!(string.equalsIgnoreCase("No Contact Avaliable")))
		{
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ string));
		startActivity(intent);
		}

	}

}
