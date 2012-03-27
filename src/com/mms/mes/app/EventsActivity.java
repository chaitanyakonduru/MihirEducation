package com.mms.mes.app;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.mms.mes.R;
import com.mms.mes.custom.ListNotificationAdapter;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.Notification;
import com.mms.mes.model.NotificationResp;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.network.Comm;
import com.mms.mes.network.SoapServiceManager;
@SuppressWarnings({ "unused", "unchecked"})
public class EventsActivity extends TabActivity{

	private static final String TAG = "EventsActivity";
	private TabHost tabHost;
	private ListView listviewNotification;
	private ListView listviewEvents;
	
	private ProgressDialog progressDialog;
	private Student studentInfo;
	private ImageView adview;
	
	private MihirApp app=null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notifications_events);
		

		app = (MihirApp) getApplication();
		studentInfo=app.getCurStudent();
		
		initViews();
		new Notification_RequestTask().execute(app.getCurStudent()
				.getmStudentId());

		tabHost = getTabHost();
		setupTab("Events", R.id.notifications_layout_events);
		setupTab("Notifications", R.id.notifications_layout_notifications);
	
		tabHost.setCurrentTab(0);
	}

	private void initViews() {
//		setschooldetails(studentInfo);
		listviewNotification = (ListView) findViewById(R.id.notifications_list);
		listviewEvents = (ListView) findViewById(R.id.evetns_list);
		((TextView)findViewById(R.id.action_bar_tv_student_name)).setText(studentInfo.getmStudentName());
		((TextView)findViewById(R.id.action_bar_tv_class_name)).setText("Class : "+studentInfo.getmClassName());
		((TextView)findViewById(R.id.action_bar_tv_section_name)).setText("Section : "+studentInfo.getmSectionName());
		adview=(ImageView)findViewById(R.id.mms_ad_image);
		new GettingAds(adview, this, 8).getAds();
	}

	private void setschooldetails(Student info) {
		
	((TextView)findViewById(R.id.action_tv_school_name)).setText(info.getmSchoolShortName());
	
	((ImageView) findViewById(R.id.school_logo)).setImageDrawable(HomeActivity.drawable);
	((TextView)findViewById(R.id.mihir_productname)).setText(AuthResponse.getmProductName());
	}
	

	private void setupTab(String tag, int resId) {

		TabSpec spec = tabHost.newTabSpec(tag).setIndicator(maketab(tag))
				.setContent(resId);
		tabHost.addTab(spec);
	 	
	}

	private View maketab(String text)
	{
		int bgId;
		if("Events".equalsIgnoreCase(text)){
			bgId = R.drawable.right_tab_indicator;
		}else{
			bgId = R.drawable.left_tab_indicator;
		}
		
		LayoutInflater inflater =LayoutInflater.from(this);
		View tabView = inflater.inflate(R.layout.tab_indicator, null);
		tabView.setBackgroundResource(bgId);
		((TextView)tabView.findViewById(R.id.text)).setText(""+text);

		return tabView;
	}

	class Notification_RequestTask extends
			AsyncTask<Integer, Boolean, SoapObject> {

		protected SoapObject doInBackground(Integer... params) {
			return SoapServiceManager.sendNotifications_request(params[0]);
		}

		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);
			hideProgress();
			if (result != null) {
				processingResponse(result);
			} else {
			
				Toast.makeText(EventsActivity.this, "Unable to process your request",
						Toast.LENGTH_SHORT).show();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			showProgress();
		}

	}

	private void hideProgress() {
		progressDialog.dismiss();
	}
	
	
	private void displayNotificationListView(List<Notification> notiList) {

		listviewNotification.setAdapter(new ListNotificationAdapter(EventsActivity.this, 0, notiList));
	}
	
	private void displayEventsListView(List<Notification> eventsList) {
		listviewEvents.setAdapter(new ListNotificationAdapter(EventsActivity.this, 0, eventsList));
	}

	
	public void processingResponse(SoapObject result) {
		List<Notification> eventsList=null;
		List<Notification> notifyList=null;
		NotificationResp notificationResp=Comm.parseNotificationResp(result);
		
		if(notificationResp.getNotificationEvents()!=null && notificationResp.getNotificationEvents().getEventsList()!=null)
		{
			eventsList=notificationResp.getNotificationEvents().getEventsList();
		}
		
		if(notificationResp.getNotificationEvents()!=null  &&notificationResp.getNotificationEvents().getNotificationList()!=null)
		{
			notifyList=notificationResp.getNotificationEvents().getNotificationList();
		}
		
		if(notificationResp.getNtfy_EventsMSG().equals(""))
		{
			if(eventsList!=null &&eventsList.size()>0)
			{
				displayEventsListView(eventsList);
			}
			if(notifyList!=null)
			{
				displayNotificationListView(notifyList);
			}
			}
		else
		{
			dialog("No new Notifications and Events Found");
		}
		

}

	void dialog(String msg) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
//				finish();
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void showProgress() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Checking for Updates...");
		progressDialog.show();

	}




}
