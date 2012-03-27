package com.mms.mes.model;

import java.util.List;

public class NotificationResp {
	String ntfy_EventsMSG;
	NotificationEvents notificationEvents;
	

	public NotificationResp(String Ntfy_EventsMSG,NotificationEvents notificationEvents) {

		this.ntfy_EventsMSG = Ntfy_EventsMSG;
		this.notificationEvents = notificationEvents;
	}

	public String getNtfy_EventsMSG() {
		return ntfy_EventsMSG;
	}
	
	public NotificationEvents getNotificationEvents() {
		return notificationEvents;
	}

	public static class NotificationEvents{
		String schoolName;
		String notifyMSG;
		String eventsMSG;
		List<Notification> notificationList;
		List<Notification> eventsList;
		public NotificationEvents(String schoolName,String notifyMSG,String eventsMSG,List<Notification> notificationList,List<Notification> eventsList) {
			this.schoolName = schoolName;
			this.notifyMSG = notifyMSG;
			this.eventsMSG = eventsMSG;
			this.notificationList = notificationList;
			this.eventsList = eventsList;
		}
		
		public String getSchoolName() {
			return schoolName;
		}
		
		public String getNotifyMSG() {
			return notifyMSG;
		}
		
		public String getEventsMSG() {
			return eventsMSG;
		}
		
		public List<Notification> getNotificationList() {
			return notificationList;
		}
		
		public List<Notification> getEventsList() {
			return eventsList;
		}
		
	}
	

}
