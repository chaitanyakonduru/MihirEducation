package com.mms.mes.app;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.network.Comm;

public class SplashScreen extends Activity {
	
	int splashTime=3000;
	private SharedPreferences mMyPrefs;
	private String  userData;
	public static final String USERDATA = "userdata";
	public static final String LOGOUTTIME = "logouttime";
	public static final String USERNAME = "user";
	protected static final String TAG ="SplashScreen";
	protected String mUserName;
	public static List<Student> mStudentInfos;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
     
        Thread t=new Thread(){
        			public void run() 
        	{
        		super.run();
        		try
        		{
				
        			sleep(splashTime);
				}
        		
        		catch (Exception e) 
        		{
					Toast.makeText(getApplicationContext(),"Error Occured Because:"+e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
        		finally
        		{
        			mMyPrefs = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);

        			if (mMyPrefs != null) {
        				
        				userData = mMyPrefs.getString(USERDATA, null);
        				mUserName = mMyPrefs.getString(LoginActivity.USERNAME, "");
        				Long logoutTime = mMyPrefs.getLong(LOGOUTTIME, 0L);
        				
        				if (userData != null) {
        					MihirApp app=(MihirApp) getApplication();
        					app.setIsloggedin(true);
        					long currentTime = System.currentTimeMillis();

        					if (logoutTime > currentTime || logoutTime == 1L) {
        						try {
        							AuthResponse authResponse = Comm
        									.parseCachedData(userData);
        							loginStatus(authResponse);
        							Log.v(TAG, "On Create after getting the value from SharedPref");
//        							finish();
        						} catch (Exception e) {

        						}
        						finally
        						{
//        							finish();
        						}

        					}

        				
        				}
        			else
        			{
        				startActivity(new Intent(SplashScreen.this, LoginActivity.class));finish();
        			}
        			}
        			else
        			{
        				startActivity(new Intent(SplashScreen.this, LoginActivity.class));finish();
        			}
        			
					
				}
        		
        	}
        	
        	
        };
        t.start();
        
        
    }
	public void loginStatus(AuthResponse authResponse) {

		MihirApp app = (MihirApp) getApplication();
		Log.v(TAG, "In Login Activity:::" + authResponse.toString());
		String loginType = authResponse.getmLogonType();

		if (loginType.equals("Parent")) {

			mStudentInfos = authResponse.getStudents();
			int size = mStudentInfos.size();
			if (size == 1) {
				Student studentInfo = authResponse.getStudents().get(0);

				app.setCurStudent(studentInfo);
				Intent intent=new Intent(this,HomeActivity.class);
				startActivity(intent);
				finish();
			
			} else {
				startActivity(new Intent(this,
						ParentActivity.class));
				finish();
			}
		}

		else if (loginType.equalsIgnoreCase("Student")) {

			AuthResponse.Student mStudentInfo = authResponse.getStudents().get(0);
			app.setCurStudent(mStudentInfo);
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}

		else if (authResponse.getmAuthenticateMsg().equalsIgnoreCase(
				"NO_CHILDS")) {
			showDialog(SplashScreen.this,
					"No Student(s) associated with this User");
		} else {
			showDialog(SplashScreen.this, authResponse.mAuthenticateMsg);
		}
	}
	
	private void showDialog(Context context, String string) {

		AlertDialog.Builder builder=new AlertDialog.Builder(SplashScreen.this);
		builder.setMessage(string);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();	
			}
		});
		builder.create().show();
		
	}
}