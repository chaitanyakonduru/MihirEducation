package com.mms.mes.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Notification implements Parcelable{

		private String Type;
		private String DueTime;
		private String Details;
		private String Id;
		private String isRead;

		public Notification(String Type,String Id, 
				String Details, String DueDate ,String isRead) {
			super();
			this.Type = Type;
			this.Id = Id;
			this.Details = Details;
			this.DueTime = DueDate;
			this.isRead = isRead;
		}

		public String getType() {
			return Type;
		}
		public String getDueTime() {
			return DueTime;
		}
		public String getDetails() {
			return Details;
		}
		public String getId() {
			return Id;
		}
		public String isRead() {
			return isRead;
		}

		@Override
		public String toString() {
			return Type;
		}
		
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {

			List<String> ll=new ArrayList<String>();
			ll.add(this.Type);
			ll.add(this.Details);
			ll.add(this.DueTime);
			ll.add(this.Id);
			ll.add(this.isRead);
			dest.writeStringList(ll);	
			
		}

		public Notification(Parcel parcel)
		{
			List<String>list1=new ArrayList<String>();
			parcel.readStringList(list1);
			this.Type=list1.get(0);
			this.Details=list1.get(1);
			this.DueTime=list1.get(2);
			this.Id=list1.get(3);
			this.isRead=list1.get(4);
		}

		public static final Creator<Notification> CREATOR=new Creator<Notification>() {

			public Notification createFromParcel(Parcel source) {
				return new Notification(source);
			}

			public Notification[] newArray(int size) {
				return new Notification[size];
			}
		};
		public static Creator<Notification> getCreator() {
			return CREATOR;
		}
		

	}