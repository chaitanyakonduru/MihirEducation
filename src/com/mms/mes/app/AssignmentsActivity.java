package com.mms.mes.app;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mms.mes.R;
import com.mms.mes.custom.ListAssignmentAdapter;
import com.mms.mes.model.AssignmentResp;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.AssignmentResp.Assignment;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.network.Comm;
import com.mms.mes.network.SoapServiceManager;

public class AssignmentsActivity extends Activity implements
		OnItemClickListener {

	private static final String TAG = "Assignments Activity";
	private ListView assignmentsView;
	List<AssignmentResp.Assignment> assignmentList = null;
	private ProgressDialog progressDialog;
	private ImageView adview;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_assignments);
		MihirApp app = (MihirApp) getApplication();
		Student studentInfo = app.getCurStudent();
		setschooldetails(studentInfo);
		
		String student_id = String.valueOf(studentInfo.getmStudentId());
		
		((TextView)findViewById(R.id.action_bar_tv_student_name)).setText(studentInfo.getmStudentName());
		((TextView)findViewById(R.id.action_bar_tv_class_name)).setText("Class : "+studentInfo.getmClassName());
		((TextView)findViewById(R.id.action_bar_tv_section_name)).setText("Section : "+studentInfo.getmSectionName());
		assignmentsView = (ListView) findViewById(android.R.id.list);
		new AssignmentTask().execute(student_id);

	}
	private void setschooldetails(Student info) {
		
	((TextView)findViewById(R.id.action_tv_school_name)).setText(info.getmSchoolShortName());
	
	((ImageView) findViewById(R.id.school_logo)).setImageDrawable(HomeActivity.drawable);
	((TextView)findViewById(R.id.mihir_productname)).setText(AuthResponse.getmProductName());
	adview=(ImageView)findViewById(R.id.mms_ad_image);
	new GettingAds(adview, this, 10).getAds();
	}

	class AssignmentTask extends AsyncTask<String, Boolean, SoapObject> {

		protected SoapObject doInBackground(String... params) {
			return SoapServiceManager.sendAssignmentsRequest(params[0]);
		}

		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);
			hideProgress();

			if (result != null) {
				displayAssignmentTasks(result);
			} else {
				Toast.makeText(AssignmentsActivity.this,
						"Unable to process your request", Toast.LENGTH_SHORT)
						.show();
			}

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showProgress();
		}

	}

	private void hideProgress() {
		progressDialog.dismiss();
	}

	private void showProgress() {
		progressDialog = new ProgressDialog(AssignmentsActivity.this);
		progressDialog.setMessage("Loading....");
		progressDialog.show();
	}


	private void displayAssignmentTasks(SoapObject result) {

		AssignmentResp assignmentResp = Comm.parseAssignment(result);

		String assignmentStatus = assignmentResp.getmAssignmentResponseMsg();

		if (assignmentStatus== "") {
			if(assignmentResp.getmAssignmentList()!=null)
			{
				assignmentList = assignmentResp.getmAssignmentList();
				if(assignmentList!=null)
				{
					displayListView(assignmentList);
				}
			}
		} else {
			Toast.makeText(AssignmentsActivity.this,
					"You have no assignments Today", Toast.LENGTH_SHORT).show();
		}
	}

	private void displayListView(List<Assignment> assignmentList) {
ListAssignmentAdapter adapter=new ListAssignmentAdapter(AssignmentsActivity.this, 0, assignmentList);
		assignmentsView.setAdapter(adapter);
		
		/*assignmentsView.setAdapter(new ArrayAdapter<Assignment>(
				AssignmentsActivity.this, android.R.layout.simple_list_item_1,
				assignmentList));
		*/
		assignmentsView.setOnItemClickListener(AssignmentsActivity.this);
		
	}

	public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
		Intent intent = new Intent(AssignmentsActivity.this,
				AssignmentDetailsActivity.class);
		Assignment info = assignmentList.get(pos);
		intent.putExtra("assignmentObject", info);
		startActivity(intent);
	}



}
