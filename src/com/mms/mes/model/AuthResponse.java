package com.mms.mes.model;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class AuthResponse {
	
	private static final String DELIM_COMMA = ",";
	private static final String DELIM_ATTHERATE = "@";
	private static final String DELIM_PERCENTAGE="%";
	private static final String TAG = "Authenticate Response";
	public static StringBuilder builder = new StringBuilder();
	public String mAuthenticateMsg;
	private List<Student> mStudents;
	private static String mAccName;
	private String mLogonType;
	private int no_of_childs;
	public static String mProductName;

	public static class Student {
		private static final String TAG = "AuthResponse.Student";
		public int mStudentId;
		public String mStudentName;
		public String mSchoolName;
		public String mClassName;
		public String mschoolId;
		public String mSchoolShortName;
		public String mSchoolLogoUrl;
		public String mSectionName;
		public Drawable drawable;

		public Student(int mStudentId, String mStudentName, String mSchoolName,
				String mClassName, String mschoolId, String mSchoolShortName,
				String mSchoolLogoUrl,String section,Drawable drawable) {
			super();
			this.mStudentId = mStudentId;
			this.mStudentName = mStudentName;
			this.mSchoolName = mSchoolName;
			this.mClassName = mClassName;
			this.mschoolId = mschoolId;
			this.mSchoolShortName = mSchoolShortName;
			this.mSchoolLogoUrl = mSchoolLogoUrl;
			this.mSectionName=section;
			this.drawable = drawable;
			cacheData();
		}

		public Student(String cacheData){
		
		String [] s=cacheData.split(",");
		 this.mStudentId=Integer.parseInt(s[0]);
		 this.mStudentName=s[1];
		 this.mClassName=s[2];
		 this.mSchoolShortName=s[3];
		 this.mschoolId=s[4];
		 this.mSchoolLogoUrl=s[5];
		 this.mSectionName=s[6];
		 this.mSchoolName=s[7];
		 
		}
		
		public String getmSectionName() {
			return mSectionName;
			
		}

		public Drawable getDrawable() {
			return drawable;
		}

		public String getMschoolId() {
			return mschoolId;
		}

		public String getmSchoolShortName() {
			return mSchoolShortName;
		}

		public String getmSchoolLogoUrl() {
			return mSchoolLogoUrl;
		}

		public int getmStudentId() {
			return mStudentId;
		}

		public String getmStudentName() {
			return mStudentName;
		}

		public String getmSchoolName() {
			return mSchoolName;
		}
		public String getmClassName() {
			return mClassName;
		}

		public String toString() {
			return mStudentName;
		}

		public void cacheData(){
			
			builder.append(this.mStudentId);
			builder.append(DELIM_COMMA);
			
			builder.append(this.mStudentName);
			builder.append(DELIM_COMMA);
			
			builder.append(this.mClassName);
			builder.append(DELIM_COMMA);
			
			builder.append(this.mSchoolShortName);
			builder.append(DELIM_COMMA);
			
			builder.append(this.mschoolId);
			builder.append(DELIM_COMMA);
			
			builder.append(this.mSchoolLogoUrl);
			builder.append(DELIM_COMMA);
			
			builder.append(this.mSectionName);
			builder.append(DELIM_COMMA);
			
			builder.append(this.mSchoolName);
			builder.append(DELIM_PERCENTAGE);
			
			
			
			
		}
		
	}

	public AuthResponse(String mAuthenticateMsg, List<Student> students,
			String mLogonType, int noOfChilds,String productName,String accName) {
		super();
		this.mAuthenticateMsg = mAuthenticateMsg;
		this.mStudents = students;
		this.mLogonType = mLogonType;
		this.no_of_childs = noOfChilds;
		this.mProductName=productName;
		this.mAccName=accName;
		cacheData();
	}

	public static String getmProductName() {
		return mProductName;
	}
	

	public static String getmAccName() {
		return mAccName;
	}

	public AuthResponse(String b, List<Student> ll) {

		String s[]=b.split(",");
		this.mLogonType=s[0];
		this.mStudents=ll;
		this.no_of_childs=Integer.parseInt(s[1]); 
		this.mProductName=s[2];
		this.mAccName=s[3];
	}

	public int getNo_of_childs() {
		return no_of_childs;
	}

	public String getmAuthenticateMsg() {
		return mAuthenticateMsg;
	}

	public List<Student> getStudents() {
		return mStudents;
	}

		public String getmLogonType() {
		return mLogonType;
	}

	public void  cacheData(){
		builder.append(DELIM_ATTHERATE);
		
		builder.append(this.mLogonType);
		builder.append(DELIM_COMMA);
		builder.append(this.no_of_childs);
		builder.append(DELIM_COMMA);
		builder.append(this.mProductName);
		builder.append(DELIM_COMMA);
		builder.append(this.mAccName);
	
		Log.v(TAG, builder.toString());
	}
	
}
