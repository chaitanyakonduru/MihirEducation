package com.mms.mes.model;

public class ViewContactsResp {
	private StudentContacts mStudentContacts;
	private SchoolContacts mSchoolContacts;
	private FeeDetails mFeeDetails;
	
	public static class FeeDetails
	{
		private String mFeeDueDate;
		private String mDueAmt;
		
		public FeeDetails(String mFeeDueDate, String mDueAmt) {
			super();
			this.mFeeDueDate = mFeeDueDate;
			this.mDueAmt = mDueAmt;
		}
		public String getmFeeDueDate() {
			return mFeeDueDate;
		}
		public String getmDueAmt() {
			return mDueAmt;
		}
		
		
	}
	public static class StudentContacts
	{
		private String mStudentEmergencyContactNo;
		private String mStudentDoctorno;
		
		
		public StudentContacts(String mStudentEmergencyContactNo,
				String mStudentDoctorno) {
			super();
			this.mStudentEmergencyContactNo = mStudentEmergencyContactNo;
			this.mStudentDoctorno = mStudentDoctorno;
		}


		public String getmStudentEmergencyContactNo() {
			return mStudentEmergencyContactNo;
		}


		public String getmStudentDoctorno() {
			return mStudentDoctorno;
		}
		
		
	}
	public static class SchoolContacts
	{
		
		private String mSchoolEmergencyContactno;
		private String mSchoolAdmin1;
		private String mSchoolAdmin2;
		private String mSchoolBilling1;
		private String mSchoolBilling2;
		private String mRaggingContact;
		private String mComplaintsContact;
		
		public SchoolContacts(String mSchoolEmergencyContactno,
				String mSchoolAdmin1, String mSchoolAdmin2,
				String mSchoolBilling1, String mSchoolBilling2,
				String mRaggingContact, String mComplaintsContact) {
			super();
			this.mSchoolEmergencyContactno = mSchoolEmergencyContactno;
			this.mSchoolAdmin1 = mSchoolAdmin1;
			this.mSchoolAdmin2 = mSchoolAdmin2;
			this.mSchoolBilling1 = mSchoolBilling1;
			this.mSchoolBilling2 = mSchoolBilling2;
			this.mRaggingContact = mRaggingContact;
			this.mComplaintsContact = mComplaintsContact;
		}

		public String getmSchoolEmergencyContactno() {
			return mSchoolEmergencyContactno;
		}

		public String getmSchoolAdmin1() {
			return mSchoolAdmin1;
		}

		public String getmSchoolAdmin2() {
			return mSchoolAdmin2;
		}

		public String getmSchoolBilling1() {
			return mSchoolBilling1;
		}

		public String getmSchoolBilling2() {
			return mSchoolBilling2;
		}

		public String getmRaggingContact() {
			return mRaggingContact;
		}

		public String getmComplaintsContact() {
			return mComplaintsContact;
		}
		
		
		
	}
	public ViewContactsResp(FeeDetails feeDetails,StudentContacts mStudentContacts,
			SchoolContacts mSchoolContacts) {
		super();
		this.mFeeDetails=feeDetails;
		this.mStudentContacts = mStudentContacts;
		this.mSchoolContacts = mSchoolContacts;
	}
	public StudentContacts getmStudentContacts() {
		return mStudentContacts;
	}
	public SchoolContacts getmSchoolContacts() {
		return mSchoolContacts;
	}
	
	public FeeDetails getmFeeDetails() {
		return mFeeDetails;
	}
	
	
	

}
