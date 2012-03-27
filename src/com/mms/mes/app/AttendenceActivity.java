package com.mms.mes.app;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mms.mes.R;
import com.mms.mes.model.AttendanceResp;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.AttendanceResp.Student.Month;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.network.Comm;
import com.mms.mes.network.SoapServiceManager;

public class AttendenceActivity extends Activity implements OnClickListener {

	private static final String TAG = "AttendanceActivity";
	private TextView studentname;

	
	private Button prevButton;
	private Button nextButton;
	private ViewFlipper flipper;
	private List<View> viewList;
	private Student info;
	private List<Month> attendanceList=null;
	private ImageView adview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_attendance);
		
		MihirApp app = (MihirApp) getApplication();
		info = app.getCurStudent();
	
		initViews();
		findViewById(R.id.attendance_btn_sharebtn).setOnClickListener(this);

		new attendanceRequestTask().execute(String.valueOf(info.getmStudentId()));

	}

	private void sendMail() {
		
		int index = flipper.getChildCount()
		- (flipper.getDisplayedChild() + 1);
		if(index>0)
		{
			AttendanceResp.Student.Month attendance = attendanceList.get(index);
			String mailText = "Student Name :\t"
			+ info.getmStudentName()
			+ "\n"
			+ "Month Name: \t"
			+attendance.getmMonthName()
			+ "\n"
			+ "No. of Working Days :\t"
			+attendance.getmWorkingDays()
			+ "\n"
			+ "No.of Present Days :\t"
			+ (attendance.getmWorkingDays()-attendance.getmAbsentDays())
			+ "\n"
			+ "No.of Absent Days :\t"
			+ attendance.getmAbsentDays()
			+ "\n"
			+  attendance.getmAttendancePer()
			+ "\n";
		Log.v(TAG, "Mail Text:::"+mailText);
		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, mailText);
		intent.setType("mail/Html");
		intent.putExtra(Intent.EXTRA_SUBJECT, info.getmStudentName()+"'s Attendance named"+attendance.getmMonthName());
		startActivity(Intent.createChooser(intent, "Sending....."));
		
		}
		
	}

	private void initViews() {
		setschooldetails(info);
		((TextView)findViewById(R.id.action_bar_tv_student_name)).setText(info.getmStudentName());
		((TextView)findViewById(R.id.action_bar_tv_class_name)).setText("Class : "+info.getmClassName());
		((TextView)findViewById(R.id.action_bar_tv_section_name)).setText("Section : "+info.getmSectionName());
		prevButton = (Button) findViewById(R.id.attendance_btn_prevButton);
		prevButton.setOnClickListener(AttendenceActivity.this);
		nextButton = (Button) findViewById(R.id.attendance_btn_nextButton);
		nextButton.setOnClickListener(AttendenceActivity.this);
		flipper = (ViewFlipper) findViewById(R.id.attendance_view_flipper);
		adview=(ImageView)findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 07).getAds();
		
	}
	private void setschooldetails(Student info) {
		// TODO Auto-generated method stub
		
	((TextView)findViewById(R.id.action_tv_school_name)).setText(info.getmSchoolShortName());
	
	((ImageView) findViewById(R.id.school_logo)).setImageDrawable(HomeActivity.drawable);
	((TextView)findViewById(R.id.mihir_productname)).setText(AuthResponse.getmProductName());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attendance_btn_sharebtn:
			sendMail();

			break;
		case R.id.attendance_btn_prevButton:
			nextButton.setEnabled(true);
			int count = flipper.getChildCount();
			int index = flipper.getDisplayedChild();
			if (index + 2 == count) {
				prevButton.setEnabled(false);
				nextButton.setEnabled(true);
			}
			flipper.showNext();
			setMonthName();
			break;
		case R.id.attendance_btn_nextButton:
			prevButton.setEnabled(true);
			int count1 = flipper.getChildCount();
			int index1 = flipper.getDisplayedChild();
			if (index1 + (count1 - 1) == count1) {
				nextButton.setEnabled(false);
				prevButton.setEnabled(true);
			}

			flipper.showPrevious();
			setMonthName();
			break;

		default:
			break;
		}
	}

	class attendanceRequestTask extends AsyncTask<String, Boolean, SoapObject> {

		@Override
		protected SoapObject doInBackground(String... params) {
			return getResultObject(params[0]);
		}

		@Override
		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);
			hideProgress();
			if (result != null) {
				processingResultObject(result);
			} else {
				Toast.makeText(AttendenceActivity.this, "Unable to process your request",
						Toast.LENGTH_SHORT).show();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			showProgress();
		}

	}

	public SoapObject getResultObject(String student_Id) {

		SoapObject resultObject = SoapServiceManager
				.sendAttendanceRequest(student_Id);
		return resultObject;
	}

	public void processingResultObject(SoapObject result) {
		AttendanceResp attendanceInfo=null;
		
		attendanceInfo=Comm.parseAttendance(result);
		String responseStatus=attendanceInfo.getmStudentResponseMsg();
		
		if(!(responseStatus.equals("")))
		{
			// No details Found
			
			findViewById(R.id.attendance_ll_no_details).setVisibility(
					View.VISIBLE);
		
		}
		else if(responseStatus.equals(""))
		{
			AttendanceResp.Student student=attendanceInfo.getmStudent();
		attendanceList=student.getmMonthList();
			showAttendanceDetails(attendanceList);	

		}
	}

	private void showAttendanceDetails(List<AttendanceResp.Student.Month> attendanceList) {

		int size = attendanceList.size();
		viewList = new ArrayList<View>();

		if (size == 1) {
			prevButton.setVisibility(View.INVISIBLE);
			nextButton.setVisibility(View.INVISIBLE);
		}

		for (int i = size - 1; i >= 0; i--) {
			View v1 = getView(attendanceList.get(i));
			viewList.add(v1);
			flipper.addView(v1);
		}
		setMonthName();

	}

	private void setMonthName() {
		TextView tv = (TextView) viewList.get(flipper.getDisplayedChild())
				.findViewById(R.id.attendance_tv_nameofmonth);
		TextView attendPer = (TextView) viewList.get(flipper.getDisplayedChild())
		.findViewById(R.id.attendance_tv_per);
		((TextView) findViewById(R.id.layout_attendance_tv_monthName))
				.setText(tv.getText().toString());
		((TextView) findViewById(R.id.attendance_tv_attendance_percentage))
		.setText(attendPer.getText().toString());
	}

	private View getView(AttendanceResp.Student.Month attendanceInfo) {

		Log.v(TAG,"AttendanceInfo:::"+attendanceInfo.getmAbsentDays());
		Log.v(TAG,"AttendanceInfo:::"+attendanceInfo.getmMonthName());
		Log.v(TAG,"AttendanceInfo:::"+attendanceInfo.getmWorkingDays());
		
		View v = LayoutInflater.from(AttendenceActivity.this).inflate(
				R.layout.attendanceflip_layout, null);
		((TextView) v.findViewById(R.id.attendance_tv_nameofmonth))
				.setText(attendanceInfo.getmMonthName());

		((TextView) v.findViewById(R.id.attendance_tv_no_of_workingdays))
				.setText(String.valueOf(attendanceInfo.getmWorkingDays()));
		
		((TextView) v.findViewById(R.id.attendance_tv_no_of_absentdays))
				.setText(String.valueOf(attendanceInfo.getmAbsentDays()));
		
		((TextView) v.findViewById(R.id.attendance_tv_no_of_presentdays))
		.setText(String.valueOf(attendanceInfo.getmWorkingDays()-attendanceInfo.getmAbsentDays()));
		
		((TextView) v.findViewById(R.id.attendance_tv_per))
		.setText(attendanceInfo.getmAttendancePer());
		
		return v;
	}

	public void showProgress() {
		findViewById(R.id.attendance_ll_loading).setVisibility(View.VISIBLE);
		//loadLayout.setVisibility(View.VISIBLE);

	}

	public void hideProgress() {
		findViewById(R.id.attendance_ll_loading).setVisibility(View.GONE);
	}
	

}
