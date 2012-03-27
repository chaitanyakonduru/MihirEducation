package com.mms.mes.model;

import java.util.List;
public class StudentMarksResp {
	
	private String mErrorMsg;
	private List<Term> mTermList;
	
	public static class Term
	{
		private String mTermName;
		private List<Subject> mSubjectList;
		private int totalMarksObt;
		private int totalMaxMarks;
		
		public static class Subject
		{
			private String mMarks;
			private String mGrade;
			private String mMaxMarks;
			private String mSubjectName;
			
			public Subject(String mMarks, String mGrade, String mMaxMarks,
					String mName) {
				super();
				this.mMarks = mMarks;
				this.mGrade = mGrade;
				this.mMaxMarks = mMaxMarks;
				this.mSubjectName = mName;
			}
			
			public String getmMarks() {
				return mMarks;
			}
			public String getmGrade() {
				return mGrade;
			}
			public String getmMaxMarks() {
				return mMaxMarks;
			}
			public String getmName() {
				return mSubjectName;
			}
			
		}

		public Term(String mTermName, List<Subject> mSubjectList,
				int totalMarksObt, int totalMaxMarks) {
			super();
			this.mTermName = mTermName;
			this.mSubjectList = mSubjectList;
			this.totalMarksObt = totalMarksObt;
			this.totalMaxMarks = totalMaxMarks;
		}

		public int getTotalMarksObt() {
			return totalMarksObt;
		}

		public int getTotalMaxMarks() {
			return totalMaxMarks;
		}

		public String getmTermName() {
			return mTermName;
		}
		public List<Subject> getmSubjectList() {
			return mSubjectList;
		}
	}

	public StudentMarksResp(String mErrorMsg,
		List<Term> mTermList) {
		super();
		this.mErrorMsg = mErrorMsg;
		this.mTermList = mTermList;
	}

	public String getmErrorMsg() {
		return mErrorMsg;
	}


	public List<Term> getTerms()
	{
		return mTermList;
	}
	
}
