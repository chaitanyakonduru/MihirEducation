package com.mms.mes.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.AssignmentResp.Assignment;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.network.SoapServiceManager;
public class AssignmentDetailsActivity extends Activity implements OnClickListener{
	
private static final String TAG = null;
private static String mEmailText;
private String studentName;
private String mAssignmentDetail;
private String mSubmissionDate;
private String mSubjectName;
private String id;
private Student studentInfo;
private ImageView adview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_assignment_details);
		MihirApp app=(MihirApp) getApplication();
		studentInfo=app.getCurStudent();
		studentName=studentInfo.getmStudentName();
		setschooldetails(studentInfo);
		Bundle bundle=getIntent().getExtras();
		Assignment info=(Assignment) bundle.get("assignmentObject");
		id=info.getmId();
		mAssignmentDetail=info.getmDetails();
		
		mSubjectName=info.getmType();
		t.start();
		/*SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = dateFormat.parse(info.getmDueTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateFormat=new SimpleDateFormat("dd-MM-yyyy");*/
//		mSubmissionDate=dateFormat.format(date);
		((TextView)findViewById(R.id.action_bar_tv_student_name)).setText(studentInfo.getmStudentName());
		((TextView)findViewById(R.id.action_bar_tv_class_name)).setText("Class : "+studentInfo.getmClassName());
		((TextView)findViewById(R.id.action_bar_tv_section_name)).setText("Section : "+studentInfo.getmSectionName());
		((TextView) findViewById(R.id.assignment_details_tv_subjectname)).setText(mSubjectName);
		((TextView) findViewById(R.id.assignment_details_tv_description)).setText(mAssignmentDetail);
		((TextView)findViewById(R.id.assignment_details_tv_date)).setText(info.getmDueTime());
		adview=(ImageView)findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 11).getAds();
		findViewById(R.id.assignment_det_btn_share).setOnClickListener(AssignmentDetailsActivity.this);
		mEmailText="Student Name="+studentName+"\n Subject Name= "+mSubjectName+"\n Assignment Details= "+mAssignmentDetail+"\n Submission Date= "+mSubmissionDate;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	if(v.getId()==R.id.assignment_det_btn_share)
	{
		sendMail(mEmailText);
	}
	}

	private void sendMail(String mailText) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Mail Text:"+mailText);
		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, mailText);
		intent.setType("mail/Html");
		intent.putExtra(Intent.EXTRA_SUBJECT, studentName+"'s Assignment named"+mSubjectName);
		startActivity(Intent.createChooser(intent, "Sending....."));
	}
	private void setschooldetails(Student info) {
		
	((TextView)findViewById(R.id.action_tv_school_name)).setText(info.getmSchoolShortName());
	
	((ImageView) findViewById(R.id.school_logo)).setImageDrawable(HomeActivity.drawable);
	((TextView)findViewById(R.id.mihir_productname)).setText(AuthResponse.getmProductName());
	}
	
	Thread t=new Thread(new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
		SoapServiceManager.sendUpdateReadMail(id);	
		}
	});
}
