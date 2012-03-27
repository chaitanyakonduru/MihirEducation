package com.mms.mes.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;
import com.mms.mes.model.Utils;
import com.mms.mes.model.AuthResponse.Student;

public class HomeActivity extends Activity implements OnClickListener{
	private static final String TAG = "Home Activity";

	private ImageView adview;

	private MihirApp app;
	public static  Drawable drawable;
	
	protected void onRestart() {
		super.onRestart();
		Log.v(TAG, "On Restart");
		
		if(!app.isIsloggedin())
		{
			finish();
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.layout_home);
	Log.v(TAG, "Oncreate");
	app=(MihirApp) getApplication();
	Student info=app.getCurStudent();
	
	if(info != null){
		Log.v(TAG, ":::::"+info.getmSchoolName()+":::::");
		setschooldetails(info);
		
	}
	initViews();
	new GettingAds(adview, this, 05).getAds();
}

private void setschooldetails(Student info) {
		
	((TextView)findViewById(R.id.action_tv_school_name)).setText(info.getmSchoolShortName());
	
	try {
		
		drawable=Utils.getImageDrawable(info.getmSchoolLogoUrl());
	} 
	catch (Exception e) {
		
		drawable=getResources().getDrawable(R.drawable.school_logo);
	}
	((ImageView) findViewById(R.id.school_logo)).setImageDrawable(drawable);
	((TextView)findViewById(R.id.mihir_productname)).setText(AuthResponse.getmProductName());
	
	}


private void initViews() {
	adview=(ImageView)findViewById(R.id.mms_ad_image);
	findViewById(R.id.home_btn_marks).setOnClickListener(HomeActivity.this);
	findViewById(R.id.home_btn_attendance).setOnClickListener(HomeActivity.this);
	findViewById(R.id.home_btn_notifications).setOnClickListener(HomeActivity.this);
	findViewById(R.id.home_btn_assignment).setOnClickListener(HomeActivity.this);
	findViewById(R.id.home_btn_antiragging).setOnClickListener(HomeActivity.this);
	findViewById(R.id.home_btn_canteen).setOnClickListener(HomeActivity.this);
	findViewById(R.id.home_btn_myaccount).setOnClickListener(HomeActivity.this);
	findViewById(R.id.home_btn_emergency).setOnClickListener(HomeActivity.this);
	
}

public void onClick(View v) {
	switch (v.getId()) {
	case R.id.home_btn_marks:
			startActivity(new Intent(HomeActivity.this,MarksActivity.class));
			break;
	case R.id.home_btn_attendance:
			startActivity(new Intent(HomeActivity.this,AttendenceActivity.class));
			break;
	case R.id.home_btn_notifications:
			startActivity(new Intent(HomeActivity.this,EventsActivity.class));
			break;
	case R.id.home_btn_assignment:
			startActivity(new Intent(HomeActivity.this, AssignmentsActivity.class));
			break;
	case R.id.home_btn_antiragging:
		displayDialog(HomeActivity.this);
			break;
	case R.id.home_btn_canteen:
		displayDialog(HomeActivity.this);
			break;
	case R.id.home_btn_myaccount:
			startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));
			break;
	case R.id.home_btn_emergency:
			startActivity(new Intent(HomeActivity.this, ImpContactsActivity.class));
			break;
	default:
		break;
	}
		
	}

private void displayDialog(final Context context) {
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder
			.setMessage(getResources().getString(R.string.det_not_available));
	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		}
	});
	builder.create().show();
}

protected void onResume() {
		super.onResume();

	Log.v(TAG, "On Resume");
}


}
