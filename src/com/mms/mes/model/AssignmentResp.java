package com.mms.mes.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class AssignmentResp {

	private String mAssignmentResponseMsg;
	List<Assignment> mAssignmentList=new ArrayList<Assignment>();
	
		public static class Assignment implements Parcelable
		{
			private static final String TAG = null;
			private String mType;
			private String mId;
			private String mDetails;
			private String mDueTime;
			private String mIsRead;

			public Assignment(String mType, String mId,String mDetails,
					String mDueTime,String mIsRead) {
				super();
				this.mType=mType;
				this.mId=mId;
				this.mDetails = mDetails;
				this.mDueTime = mDueTime;
				this.mIsRead = mIsRead;
			}
			
			public String getmId() {
				return mId;
			}

			public String getmIsRead() {
				return mIsRead;
			}

			public static Creator<Assignment> getCREATOR() {
				return CREATOR;
			}

			public String getmType() {
				return mType;
			}

			public String getmDetails() {
				return mDetails;
			}

			public String getmDueTime() {
				return mDueTime;
			}
			
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return mDetails;
			}
			
			public int describeContents() {
				return 0;
			}

			
			public void writeToParcel(Parcel dest, int flags) {
			
				ArrayList<String> list=new ArrayList<String>();
				list.add(mType);
				list.add(mId);
				list.add(mDetails);
				list.add(mDueTime);
				list.add(mIsRead);
				dest.writeStringList(list);
			}
			public Assignment(Parcel source)
			{
				Log.v(TAG, "I am in Assignment Info");
				List<String> list=new ArrayList<String>();
				source.readStringList(list);
				
				this.mType=list.get(0);
				this.mId=list.get(1);
				this.mDetails=list.get(2);
				this.mDueTime=list.get(3);
				this.mIsRead=list.get(4);
				
			}
			
			public static Creator<Assignment> CREATOR=new Creator<Assignment>() {
				
				public Assignment[] newArray(int size) {

					return new Assignment[size];
				}
				public Assignment createFromParcel(Parcel source) {
					return new Assignment(source);
				}
			};

		}

		public AssignmentResp(String mAssignmentResponseMsg,
				List<Assignment> mAssignmentList) {
			super();
			this.mAssignmentResponseMsg = mAssignmentResponseMsg;
			this.mAssignmentList = mAssignmentList;
		}

		public String getmAssignmentResponseMsg() {
			return mAssignmentResponseMsg;
		}

		public List<Assignment> getmAssignmentList() {
			return mAssignmentList;
		}

}
