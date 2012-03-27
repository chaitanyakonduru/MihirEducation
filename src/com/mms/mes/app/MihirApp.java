package com.mms.mes.app;

import android.app.Application;

import com.mms.mes.model.AuthResponse;

public class MihirApp extends Application {

	private AuthResponse.Student curStudentInfo;
	private boolean isloggedin=false;

	public boolean isIsloggedin() {
		return isloggedin;
	}

	public void setIsloggedin(boolean isloggedin) {
		this.isloggedin = isloggedin;
	}

	public  AuthResponse.Student getCurStudent() {
		return curStudentInfo;
	}

	public void setCurStudent( AuthResponse.Student studentInfo) {
		curStudentInfo = studentInfo;
	}
	
	
	
}
