package com.mms.mes.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.Notification;
import com.mms.mes.model.AuthResponse.Student;

public class NotificationsDetailsActivity extends Activity {

	private static final String tag = "Notification Details";
	protected static final String TAG = null;
	private Notification notifications;
	private static String description="";
	private Student studentInfo;
	private ImageView adview;
	private static String dueTime="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_notifications_details);
		
		MihirApp app=(MihirApp) getApplication();
		studentInfo=app.getCurStudent();
		initViews();
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null)
		{
			
				this.notifications=bundle.getParcelable("myObject");
//				id=notifications.getId();
				description=notifications.getDetails();
				dueTime=notifications.getDueTime();
				
		}
			
		
		((TextView) findViewById(R.id.notification_details_tv_description)).setText(description);
		((TextView)findViewById(R.id.notify_details_tv_date)).setText(dueTime);
	}
	
	private void initViews() {
		setschooldetails(studentInfo);
		((TextView)findViewById(R.id.action_bar_tv_student_name)).setText(studentInfo.getmStudentName());
		((TextView)findViewById(R.id.action_bar_tv_class_name)).setText("Class : "+studentInfo.getmClassName());
		((TextView)findViewById(R.id.action_bar_tv_section_name)).setText("Section : "+studentInfo.getmSectionName());
		adview=(ImageView)findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 9).getAds();
	}
	private void setschooldetails(Student info) {
		// TODO Auto-generated method stub
		
	((TextView)findViewById(R.id.action_tv_school_name)).setText(info.getmSchoolShortName());
	
	((ImageView) findViewById(R.id.school_logo)).setImageDrawable(HomeActivity.drawable);
	((TextView)findViewById(R.id.mihir_productname)).setText(AuthResponse.getmProductName());
	}

//	Thread t=new Thread(new Runnable() {
//		
//		@Override
//		public void run() {
//
//			SoapObject object=SoapServiceManager.sendUpdateReadMail(id);
//			Log.v(TAG,object.toString());
//		}
//	});

}
