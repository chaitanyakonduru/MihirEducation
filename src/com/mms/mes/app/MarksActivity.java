package com.mms.mes.app;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.StudentMarksResp;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.model.StudentMarksResp.Term;
import com.mms.mes.model.StudentMarksResp.Term.Subject;
import com.mms.mes.network.Comm;
import com.mms.mes.network.SoapServiceManager;

public class MarksActivity extends Activity implements OnClickListener {

	private Button prevButton;
	private Button nextButton;
	private TextView examName;
	private ViewFlipper marksFlipper;
	private TextView totalMarks;
	private List<Term> termsList = null;
	private String mStudentName;
	private Student studentInfo;
	private ImageView adview;

	private MihirApp app;


	private static final String TAG = "MarksActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_marks);
		app = (MihirApp) getApplication();
		studentInfo = app.getCurStudent();
		int student_Id = studentInfo.getmStudentId();
		mStudentName = studentInfo.getmStudentName();
		initViews();

		new MarksRequestAsynchtask().execute(student_Id);

	}

	private void initViews() {

		((TextView) findViewById(R.id.action_bar_tv_class_name))
				.setText("Class : " + studentInfo.getmClassName());
		((TextView) findViewById(R.id.action_bar_tv_section_name))
				.setText("Section : " + studentInfo.getmSectionName());
		((TextView) findViewById(R.id.action_bar_tv_student_name))
				.setText(mStudentName);
		prevButton = (Button) findViewById(R.id.marks_btn_prevButton);
		nextButton = (Button) findViewById(R.id.marks_btn_nextbutton);
		examName = (TextView) findViewById(R.id.marks_tv_examName);
		totalMarks = (TextView) findViewById(R.id.marks_tv_total_marks);
		marksFlipper = (ViewFlipper) findViewById(R.id.marks_flipper_marks);
		setschooldetails(studentInfo);
		prevButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		findViewById(R.id.marks_btn_share).setOnClickListener(this);
		adview = (ImageView) findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 06).getAds();
	}

	public class MarksRequestAsynchtask extends
			AsyncTask<Integer, Boolean, SoapObject> {
		protected SoapObject doInBackground(Integer... params) {
			return SoapServiceManager.sendstudentMarksRequest(params[0]);
		}

		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);

			hideProgress();
			Log.v(TAG, "POST EXECUTE");

			if (result != null) {
				findViewById(R.id.marks_btn_share).setVisibility(View.VISIBLE);
				showMarks(result);
			} else {
				findViewById(R.id.marks_btn_share).setVisibility(View.INVISIBLE);
				displayToast("Unable to process your request");
			}

		}

		protected void onPreExecute() {
			super.onPreExecute();
			showProgress();

		}
	}

	private void setschooldetails(Student info) {

		((TextView) findViewById(R.id.action_tv_school_name)).setText(info
				.getmSchoolShortName());

		((ImageView) findViewById(R.id.school_logo))
				.setImageDrawable(HomeActivity.drawable);
		((TextView) findViewById(R.id.mihir_productname)).setText(AuthResponse
				.getmProductName());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.marks_btn_prevButton:
			// nextButton.setVisibility(View.VISIBLE);
			nextButton.setEnabled(true);
			int count = marksFlipper.getChildCount();
			int index = marksFlipper.getDisplayedChild();

			if (index + 2 == count) {
				prevButton.setEnabled(false);
				nextButton.setEnabled(true);
			}

			marksFlipper.showNext();
			setExamName();
			break;

		case R.id.marks_btn_nextbutton:
			prevButton.setEnabled(true);
			int count1 = marksFlipper.getChildCount();
			int index1 = marksFlipper.getDisplayedChild();
			if (index1 + (count1 - 1) == count1) {
				nextButton.setEnabled(false);
				prevButton.setEnabled(true);
			}
			marksFlipper.showPrevious();
			setExamName();
			break;

		case R.id.marks_btn_share:
			sendMail();
			break;

		default:
			break;
		}
	}

	public void showMarks(SoapObject result) {
		StudentMarksResp resp = Comm.parseMarks(result);
		// Log.v(TAG, "Error Message is:::" + resp.getmErrorMsg());

		termsList = resp.getTerms();

		if (termsList != null) {

			int size = termsList.size();
			if (size > 0) {
				if (size == 1) {
					prevButton.setVisibility(View.INVISIBLE);
					nextButton.setVisibility(View.INVISIBLE);
				}

				for (int i = (size - 1); i >= 0; i--) {
					Term term = termsList.get(i);
					View view = getView(term);
					marksFlipper.addView(view);
				}
				setExamName();
			} else {
				findViewById(R.id.Marks_layout_NodetailsLayout).setVisibility(
						View.VISIBLE);
			}
		}

	}

	private void setExamName() {
		if (termsList != null) {
			int index = marksFlipper.getChildCount()
					- (marksFlipper.getDisplayedChild() + 1);
			Term term = termsList.get(index);
			String termName = term.getmTermName();
			String totalObtMarks = String.valueOf(term.getTotalMarksObt());
			String totalMaxMarks = String.valueOf(term.getTotalMaxMarks());
			
			if("0".equals(totalObtMarks))
			{
				totalMarks.setVisibility(View.INVISIBLE);
			}
			else
			{
				totalMarks.setVisibility(View.VISIBLE);
				totalMarks.setText("Total: " + totalObtMarks + "/" + totalMaxMarks);
			}
			
			examName.setText(termName);

		}
	}

	public void sendMail() {
		String termName = "";
		int index = marksFlipper.getChildCount()
				- (marksFlipper.getDisplayedChild() + 1);

		Term term = termsList.get(index);

		termName = term.getmTermName();
		String mailText = "";

		mailText += "Student Name :" + mStudentName + "\n";
		mailText += "Term Name :" + termName + "\n";
		List<Subject> sub_List = term.getmSubjectList();
		int size = sub_List.size();
		for (int i = 0; i < size; i++) {
			Subject subject = sub_List.get(i);
			mailText += subject.getmName() + ":" + subject.getmMarks() + "\n";

		}

		
		if(term.getTotalMaxMarks()!=0)
		{
			mailText += "Total Marks:" + term.getTotalMarksObt() + "/"
			+ term.getTotalMaxMarks();
		}

		Log.v(TAG + ":::Mail Text:::", mailText);

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, mailText);
		intent.setType("mail/Html");
		intent.putExtra(Intent.EXTRA_SUBJECT, studentInfo.getmStudentName()
				+ "'s Marks named" + termName);
		startActivity(Intent.createChooser(intent, "Sending....."));

	}

	private View getView(Term marks) {
		
		
		
		View v = LayoutInflater.from(MarksActivity.this).inflate(
				R.layout.marks_list111, null);

		LinearLayout subjectLayout = (LinearLayout) v
				.findViewById(R.id.subjectLayout);
	
		List<Subject> subjectList = marks.getmSubjectList();
		if (subjectList != null) {
			for (int i = 0; i < subjectList.size(); i++) {
				Subject subject = subjectList.get(i);
			
				View subjectView = LayoutInflater.from(MarksActivity.this)
						.inflate(R.layout.subjectmarkslayout, null);

				((TextView) subjectView
						.findViewById(R.id.marks_textview_subject1))
						.setText(subject.getmName());

				((TextView) subjectView
						.findViewById(R.id.marks_textview_subject1marks))
						.setText("" + subject.getmMarks());

				((TextView) subjectView
						.findViewById(R.id.marks_textview_subject1_maxmarks))
						.setText("" + subject.getmMaxMarks());

				((TextView) subjectView
						.findViewById(R.id.marks_textview_subject1grade))
						.setText(subject.getmGrade());
				subjectLayout.addView(subjectView);

			}
		}
		return v;
	}

	public void displayToast(String msg) {
		Toast.makeText(MarksActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	public void hideProgress() {
		findViewById(R.id.marks_ll_loadLayout).setVisibility(View.GONE);
	}

	public void showProgress() {

		findViewById(R.id.marks_ll_loadLayout).setVisibility(View.VISIBLE);

	}

}