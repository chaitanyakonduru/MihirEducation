package com.mms.mes.model;

import java.util.List;

public class AttendanceResp {

	private Student mStudent;
	
	private String mStudentResponseMsg;
	
	public static class Student
	{
		private List<Month> mMonthList;
		private int mId;
		private String mName;
		private String mClassName;
		
		public static class Month
		{
			private int mWorkingDays;
			private int mAbsentDays;
			private String mMonthName;
			private String mAttendancePer;
			
			public Month(int mWorkingDays, int mAbsentDays, String mMonthName) {
				super();
				this.mWorkingDays = mWorkingDays;
				this.mAbsentDays = mAbsentDays;
				this.mMonthName = mMonthName;
			}
			
			public String getmAttendancePer() {
				return "Attendance :"+(100*(mWorkingDays-mAbsentDays))/mWorkingDays+"%";
			}
			
			public int getmWorkingDays() {
				return mWorkingDays;
			}
			public int getmAbsentDays() {
				return mAbsentDays;
			}
			public String getmMonthName() {
				return mMonthName;
			}
			
		}

		public Student(List<Month> mMonthList, int mId, String mName,
				String mClassName) {
			super();
			this.mMonthList = mMonthList;
			this.mId = mId;
			this.mName = mName;
			this.mClassName = mClassName;
		}

		public List<Month> getmMonthList() {
			return mMonthList;
		}

		public int getmId() {
			return mId;
		}

		public String getmName() {
			return mName;
		}

		public String getmClassName() {
			return mClassName;
		}
	
	}
	
	
	public AttendanceResp(Student mStudent, String mStudentResponseMsg) {
		super();
		this.mStudent = mStudent;
		this.mStudentResponseMsg = mStudentResponseMsg;
	}

	public Student getmStudent() {
		return mStudent;
	}

	
	public String getmStudentResponseMsg() {
		return mStudentResponseMsg;
	}
	
	

}
