package com.mms.mes.app;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mms.mes.R;
import com.mms.mes.custom.listStudentsAdapter;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.GettingAds;

public class ParentActivity extends ListActivity implements OnItemClickListener{
	
	private static final String TAG = "ParentACtivity";
	private List<AuthResponse.Student> list;
	
	private ImageView adImage;
	private MihirApp app;
	
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "On Start");
	}
	
	protected void onStop() {
		super.onStop();
		Log.v(TAG, "On Stop");
	}
	
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"On Destroy");
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parent_screen);
		adImage=(ImageView) findViewById(R.id.mms_ad_image);
		
		ListView listView=(ListView) findViewById(android.R.id.list);
		list=new ArrayList<AuthResponse.Student>();
		if(SplashScreen.mStudentInfos!=null && SplashScreen.mStudentInfos.size()>0)
		{
			list=SplashScreen.mStudentInfos;
		}
		else
		{
		list=LoginActivity.mStudentInfos;
		}
		
		new GettingAds(adImage, ParentActivity.this, 4).getAds();
		Log.v(TAG,"OnCreate");
		if(list!=null)
		{
			if(list.size()>0)
			{
				listView.setAdapter(new listStudentsAdapter(ParentActivity.this,0, list));
				
				listView.setOnItemClickListener(ParentActivity.this);
			}
		}
		else
		{
			Toast.makeText(this, "No Childrens", Toast.LENGTH_SHORT).show();
		}
		
	}
	

	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

		app=(MihirApp) getApplication();
		Intent intent=new Intent(this,HomeActivity.class);
		AuthResponse.Student studentInfo=list.get(pos);
		Log.v(TAG, "Student ID is:"+studentInfo.getmStudentId());
		Log.v(TAG,"Student Name is:"+studentInfo.getmStudentName());
		app.setCurStudent(studentInfo);
		startActivity(intent);
		
	}
		protected void onResume() {
		super.onResume();
		Log.v(TAG, "On Resume");
	}
	
	protected void onPause() {
		super.onPause();
		Log.v(TAG, "On Pause");
	}
	
	protected void onRestart() {
		super.onRestart();
		if(!app.isIsloggedin())
		{
			app=null;
			finish();
		}
		
		Log.v(TAG, "On Restart");
	}

}
