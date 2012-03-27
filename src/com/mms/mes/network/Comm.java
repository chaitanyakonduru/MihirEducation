package com.mms.mes.network;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mms.mes.model.AssignmentResp;
import com.mms.mes.model.AttendanceResp;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.MmsAdResp;
import com.mms.mes.model.Notification;
import com.mms.mes.model.NotificationResp;
import com.mms.mes.model.StudentMarksResp;
import com.mms.mes.model.Utils;
import com.mms.mes.model.ViewContactsResp;
import com.mms.mes.model.AssignmentResp.Assignment;
import com.mms.mes.model.AttendanceResp.Student.Month;
import com.mms.mes.model.AuthResponse.Student;
import com.mms.mes.model.NotificationResp.NotificationEvents;
import com.mms.mes.model.StudentMarksResp.Term;
import com.mms.mes.model.StudentMarksResp.Term.Subject;
import com.mms.mes.model.ViewContactsResp.FeeDetails;
import com.mms.mes.model.ViewContactsResp.SchoolContacts;
import com.mms.mes.model.ViewContactsResp.StudentContacts;

public class Comm {

	private static final String TAG = "Comm";
		public static final MmsAdResp parseAds(SoapObject responseObject) {
				MmsAdResp adResp=null;
		String adUrl="";
		String navUrl="";
			if(responseObject.hasProperty("Screen"))
			{
				SoapObject screenObj=(SoapObject) responseObject.getProperty("Screen");
				if(screenObj.hasProperty("AdsList"))
				{
					SoapObject adsListObj=(SoapObject) screenObj.getProperty("AdsList");
					
					if(adsListObj.hasProperty("AdDetails"))
					{
						SoapObject adDetailsObj=(SoapObject) adsListObj.getProperty("AdDetails");
						
						
						if(adDetailsObj.hasProperty("AdImageURL"))
						{
							adUrl=adDetailsObj.getPropertyAsString("AdImageURL");
						}
						
						if(adDetailsObj.hasProperty("AdNavigationalURL"))
						{
							navUrl=adDetailsObj.getPropertyAsString("AdNavigationalURL");
						}
					}
				}
			}
			
			adResp=new MmsAdResp(adUrl, navUrl);
				return adResp;
	}

	public static final ViewContactsResp parseViewContacts(
			SoapObject responseObject) {
		ViewContactsResp contactsResp = null;
		String studentEmgContact = "Not Available";
		String studentDoctor = "Not Available";
		String compalintsContact = "Not Available";
		String raggingContact = "Not Available";
		String schoolAdmin1 = "Not Available";
		String schoolAdmin2 = "Not Available";
		String schoolBilling1 = "Not Available";
		String schoolBilling2 = "Not Available";
		String schoolEmgContact = "Not Available";
		String dueDate="";
		String dueAmt="";
		StudentContacts studentContacts = null;
		FeeDetails details=null;
		ViewContactsResp.SchoolContacts schoolContacts = null;

		if (responseObject.hasProperty("StudentContacts")) {
			SoapObject studentContactsObj = (SoapObject) responseObject
					.getProperty("StudentContacts");
			if (studentContactsObj.hasProperty("StudentEmergencyContact")) {
				studentEmgContact = studentContactsObj
						.getPropertyAsString("StudentEmergencyContact");
			}
			if (studentContactsObj.hasProperty("StudentDoctor")) {
				studentDoctor = studentContactsObj
						.getPropertyAsString("StudentDoctor");
			}

			studentContacts = new StudentContacts(studentEmgContact,
					studentDoctor);

		}
		if(responseObject.hasProperty("FeeDetails"))
		{
			SoapObject feedetailsObj=(SoapObject) responseObject.getProperty("FeeDetails");
			if(feedetailsObj.hasProperty("FeesDueDate"))
			{
				dueDate=feedetailsObj.getPropertyAsString("FeesDueDate");
			}
			if(feedetailsObj.hasProperty("Amount"))
			{
				dueAmt=feedetailsObj.getPropertyAsString("Amount");
			}
			
			details=new FeeDetails(dueDate, dueAmt);
			
		}
		if (responseObject.hasProperty("SchoolContacts")) {
			SoapObject schoolContactsObj = (SoapObject) responseObject
					.getProperty("SchoolContacts");
			if (schoolContactsObj.hasProperty("SchoolEmergencyContact")) {
				schoolEmgContact = schoolContactsObj
						.getPropertyAsString("SchoolEmergencyContact");
			}
			if (schoolContactsObj.hasProperty("SchoolAdmin1")) {
				schoolAdmin1 = schoolContactsObj
						.getPropertyAsString("SchoolAdmin1");
			}
			if (schoolContactsObj.hasProperty("SchoolAdmin2")) {
				schoolAdmin2 = schoolContactsObj
						.getPropertyAsString("SchoolAdmin2");
			}
			if (schoolContactsObj.hasProperty("SchoolBilling1")) {
				schoolBilling1 = schoolContactsObj
						.getPropertyAsString("SchoolBilling1");
			}
			if (schoolContactsObj.hasProperty("SchoolBilling2")) {
				schoolBilling2 = schoolContactsObj
						.getPropertyAsString("SchoolBilling2");
			}
			if (schoolContactsObj.hasProperty("RaggingContact")) {
				raggingContact = schoolContactsObj
						.getPropertyAsString("RaggingContact");
			}
			if (schoolContactsObj.hasProperty("ComplaintsContact")) {
				compalintsContact = schoolContactsObj
						.getPropertyAsString("ComplaintsContact");
			}

			schoolContacts = new SchoolContacts(schoolEmgContact, schoolAdmin1,
					schoolAdmin2, schoolBilling1, schoolBilling2,
					raggingContact, compalintsContact);

		}

		contactsResp = new ViewContactsResp(details,studentContacts, schoolContacts);

		return contactsResp;
	}

	public static final StudentMarksResp parseMarks(SoapObject responseObject) {
		String termName = null;
		Subject subject = null;
		String errorMsg = "";
		String maxMarks="";
		String marks="";
		

		List<Subject> subjectList = null;
		List<StudentMarksResp.Term> termList = new ArrayList<Term>();
		Term term = null;
		SoapObject termSubjectsObj = null;
		StudentMarksResp studentMarksResp = null;

		if (responseObject.hasProperty("ERRORMSG")) {
			errorMsg = responseObject.getPropertyAsString("ERRORMSG");
		}

		if (responseObject.hasProperty("TermsList")) {
			SoapObject termListObj = (SoapObject) responseObject
					.getProperty("TermsList");
			int termCount = termListObj.getPropertyCount();

			for (int i = 0; i < termCount; i++) {
				subjectList = new ArrayList<Subject>();
				int totalObt = 0;
				int totalMax = 0;

				SoapObject termObject = (SoapObject) termListObj.getProperty(i);
				if (termObject.hasProperty("TermSubjects")) {
					termSubjectsObj = (SoapObject) termObject
							.getProperty("TermSubjects");
				}

				int subjectCount = termSubjectsObj.getPropertyCount();

				for (int j = 0; j < subjectCount; j++) {
					SoapObject subjectObject = (SoapObject) termSubjectsObj
							.getProperty(j);
					if(subjectObject.hasProperty("Mark"))
					{
					marks = subjectObject
							.getPropertyAsString("Mark");
					}
					
					String grade = "";
					if (subjectObject.hasProperty("Grade")) {
						grade = subjectObject.getPropertyAsString("Grade");
					}
					
					if(subjectObject.hasProperty("MaxMarks"))
					{
					maxMarks = subjectObject
							.getPropertyAsString("MaxMarks");
					if("0".equals(maxMarks))
					{	
						marks="";
						maxMarks="";
					
					}
					else
					{
						totalObt += Integer.parseInt(marks);
						totalMax += Integer.parseInt(maxMarks);
					}
					}
					String name = subjectObject
							.getPropertyAsString("SubjectName");
					
					subject = new Subject(marks, grade, maxMarks, name);
					
					
					subjectList.add(subject);
				}

				if (termObject.hasProperty("TermName")) {
					termName = termObject.getPropertyAsString("TermName");
					Log.v(TAG, termName);
				}

				term = new StudentMarksResp.Term(termName, subjectList,
						totalObt, totalMax);
				termList.add(term);

			}

		}

		studentMarksResp = new StudentMarksResp(errorMsg, termList);
		return studentMarksResp;
	}

	public static final AuthResponse parseAuthentication(
			SoapObject responseObject) throws Exception {
		AuthResponse authResponse = null;
		List<AuthResponse.Student> studentsList = new ArrayList<Student>();
		AuthResponse.Student student = null;
		String authMsg = "";
		String logonType = "";
		String schoolId = "";
		String schoolshortname = "";
		String schoollogourl = "";
		int no_of_childs = 0;
		int studentId=0;
		String studentName="";
		String schoolName="" ;
		Drawable drawable = null;
		String className="";
		String productName="";
		String contactName="";
		String section="";
		// Authenticate Message

		if (responseObject.hasProperty("AuthenticateMSG")) {
			Log.v(TAG, responseObject.getPropertyAsString("AuthenticateMSG"));
			authMsg = responseObject.getPropertyAsString("AuthenticateMSG");

			if (responseObject.hasProperty("Type")) {
				logonType = responseObject.getPropertyAsString("Type");
			} 
			else {
				logonType = "Invalid";
			}
			if(responseObject.hasProperty("ProductName"))
			{
				productName=responseObject.getPropertyAsString("ProductName");
			}
			if(responseObject.hasProperty("ContactName"))
			{
				contactName=responseObject.getPropertyAsString("ContactName");
			}
	
			if(responseObject.hasProperty("NoOfChilds"))
			{
				no_of_childs = Integer.parseInt(responseObject
						.getPropertyAsString("NoOfChilds"));
			}
			if (authMsg.equalsIgnoreCase("SUCCESS_WITH_CHILDS")) {
				// i.e.,if Parent Login

				if (responseObject.hasProperty("Children")) {
					SoapObject childrenObject = (SoapObject) responseObject
							.getProperty("Children");

						for (int i = 0; i < no_of_childs; i++) {
							SoapObject studentsObject = (SoapObject) childrenObject
									.getProperty(i);
							if(studentsObject.hasProperty("StudentID"))
							{
								studentId = Integer.parseInt(studentsObject
									.getPropertyAsString("StudentID"));
							}
							if(studentsObject.hasProperty("StudentName"))
							{
								studentName = studentsObject
									.getPropertyAsString("StudentName");
							}
						
							if(studentsObject.hasProperty("SchoolName"))
							{
							schoolName = studentsObject
									.getPropertyAsString("SchoolName");
							}
							
							if(studentsObject.hasProperty("ClassName"))
							{
							className = studentsObject
									.getPropertyAsString("ClassName");
							}
							
							if (studentsObject.hasProperty("SchoolID")) {
								schoolId = studentsObject
										.getPropertyAsString("SchoolID");
							}
							if (studentsObject.hasProperty("SchoolShortName")) {
								schoolshortname = studentsObject
										.getPropertyAsString("SchoolShortName");

							}
							
							if (studentsObject.hasProperty("SchoolLogo")) {
								schoollogourl = studentsObject
										.getPropertyAsString("SchoolLogo");
								
							}
							if(studentsObject.hasProperty("SectionName"))
							{
								section=studentsObject.getPropertyAsString("SectionName");
							}

							drawable=Utils.getImageDrawable(schoollogourl);
							
							AuthResponse.Student students = new Student(
									studentId, studentName, schoolName,
									className, schoolId, schoolshortname,
									schoollogourl,section,drawable);
							studentsList.add(students);
							
							

						}
				
					}
					
				}

			 else if (authMsg.equalsIgnoreCase("SUCCESS_ONLY_STUDENT")) {
				// if Student Login
				
				 if (responseObject.hasProperty("Student")) {
					SoapObject studentObject = (SoapObject) responseObject
							.getProperty("Student");
					if(studentObject.hasProperty("StudentID"))
					{
						studentId = Integer.parseInt(studentObject
							.getPropertyAsString("StudentID"));
					}
					if(studentObject.hasProperty("StudentName"))
					{
						studentName = studentObject
							.getPropertyAsString("StudentName");
					}
				
					if(studentObject.hasProperty("SchoolName"))
					{
					schoolName = studentObject
							.getPropertyAsString("SchoolName");
					}
					if(studentObject.hasProperty("ClassName"))
					{
					className = studentObject
							.getPropertyAsString("ClassName");
					}
					if (studentObject.hasProperty("SchoolID")) {
						schoolId = studentObject
								.getPropertyAsString("SchoolID");
					}
					if (studentObject.hasProperty("SchoolShortName")) {
						schoolshortname = studentObject
								.getPropertyAsString("SchoolShortName");

					}
					if (studentObject.hasProperty("SchoolLogo")) {
						schoollogourl = studentObject
								.getPropertyAsString("SchoolLogo");
					}
					if(studentObject.hasProperty("SectionName"))
					{
						section=studentObject.getPropertyAsString("SectionName");
					}
				
					drawable=Utils.getImageDrawable(schoollogourl);
					
					student = new Student(studentId, studentName, schoolName,
							className, schoolId, schoolshortname,
							schoollogourl,section,drawable);
					studentsList.add(student);
				}

			}

			else {
				logonType = "Invalid";
				// Invalid Login
			}
		}
		
	

		authResponse = new AuthResponse(authMsg, studentsList, logonType,
				no_of_childs,productName,contactName);

		return authResponse;
	}
	
	public static final AttendanceResp parseAttendance(SoapObject responseObject) {
		Month month = null;
		List<AttendanceResp.Student.Month> monthList = new ArrayList<Month>();
		AttendanceResp attendanceResp = null;
		String respMsg = null;
		AttendanceResp.Student student = null;
		int studentId = 0;
		String studentName = null;
		String className = null;
		if (responseObject.hasProperty("Student")) {

			SoapObject studentObject = (SoapObject) responseObject
					.getProperty("Student");
			// int monthCount=studentObject.getPropertyCount();
			studentId = Integer.parseInt(studentObject
					.getPropertyAsString("StudentID"));
			studentName = studentObject.getPropertyAsString("StudentName");
			className = studentObject.getPropertyAsString("ClassName");
			if (studentObject.hasProperty("MonthsList")) {
				SoapObject monthsListObj = (SoapObject) studentObject
						.getProperty("MonthsList");
				if (monthsListObj.hasProperty("Months")) {

					int monthCount = monthsListObj.getPropertyCount();
					for (int i = 0; i < monthCount; i++) {
						SoapObject monthObject = (SoapObject) monthsListObj
								.getProperty(i);
						int workingDays = Integer.parseInt(monthObject
								.getPropertyAsString("WorkingDays"));
						int absentDays = Integer.parseInt(monthObject
								.getPropertyAsString("AbsentDays"));
						String monthName = monthObject
								.getPropertyAsString("MonthName");
						month = new Month(workingDays, absentDays, monthName);
						monthList.add(month);

					}

				}
			}

			student = new AttendanceResp.Student(monthList, studentId,
					studentName, className);

		}

		if (responseObject.hasProperty("StudentResponseMSG")) {
			respMsg = responseObject.getPropertyAsString("StudentResponseMSG");
		}

		else {
			respMsg = "";
		}

		attendanceResp = new AttendanceResp(student, respMsg);

		return attendanceResp;
	}

	public static final AssignmentResp parseAssignment(SoapObject responseObject) {
		AssignmentResp assignmentResp = null;
		Assignment assignment = null;
		List<Assignment> assignmentsList = new ArrayList<Assignment>();
		String assignmentRespMessage = "";
		String type="";
		String mId="";
		String details="";
		String dueTime="";
		String IsRead="";

		if(responseObject.hasProperty("AssignmentsList"))
		{
			SoapObject assignmentsListObj=(SoapObject) responseObject.getProperty("AssignmentsList");
			int count=assignmentsListObj.getPropertyCount();
			for(int i=0;i<count;i++)
			{
				SoapObject assignmentObj=(SoapObject) assignmentsListObj.getProperty(i);
				if(assignmentObj.hasProperty("Type"))
				{
					type=assignmentObj.getPropertyAsString("Type");
				}
				if(assignmentObj.hasProperty("ID"))
				{
					mId=assignmentObj.getPropertyAsString("ID");
				}
				if(assignmentObj.hasProperty("Details"))
				{
					details=assignmentObj.getPropertyAsString("Details");
				}
				if(assignmentObj.hasProperty("DueTime"))
				{
					dueTime=assignmentObj.getPropertyAsString("DueTime");
				}
				if(assignmentObj.hasProperty("IsRead"))
				{
					IsRead=assignmentObj.getPropertyAsString("IsRead");
				}
				assignment=new Assignment(type, mId, details, dueTime, IsRead);
				assignmentsList.add(assignment);
			}
		}
		if(responseObject.hasProperty("AssignRESPMSG"))
		{
			assignmentRespMessage=responseObject.getPropertyAsString("AssignRESPMSG");
		}
		
		assignmentResp=new AssignmentResp(assignmentRespMessage, assignmentsList);
		return assignmentResp;
	}

	

	public static final NotificationResp parseNotificationResp(
			SoapObject soapObject) {
		NotificationEvents notificationEvents = null;
		Notification notification = null;
		List<Notification> notificationsList = new ArrayList<Notification>();
		List<Notification> eventsList = new ArrayList<Notification>();
		String ntfy_EventsMSG = "";
		String schoolName="";
		String notifyMSG="";
		String eventsMSG="";

		try {
			ntfy_EventsMSG = soapObject.getProperty("Ntfy_EventsMSG").toString();
		} catch (Exception e) {
			ntfy_EventsMSG = "";
		}
		
		SoapObject notificationEventsObject = null;
		try {
			notificationEventsObject = (SoapObject) soapObject
					.getProperty("NotificationEvents");
		} catch (Exception e) {
			notificationEventsObject = null;
		}
		if (notificationEventsObject != null) {

			try {
				schoolName = notificationEventsObject
						.getProperty("SchoolName").toString();
			} catch (Exception e) {
				schoolName = "";
			}
			
			try {
				notifyMSG = notificationEventsObject
						.getProperty("NtfyMSG").toString();
			} catch (Exception e) {
				notifyMSG = "";
			}
			
			try {
				eventsMSG = notificationEventsObject
						.getProperty("EventsMSG").toString();
			} catch (Exception e) {
				eventsMSG = "";
			}
			
			SoapObject notificationsListObject = null;
			try {
				notificationsListObject = (SoapObject) notificationEventsObject.getProperty("NotificationsList");
			} catch (Exception e) {
				notificationsListObject = null;
			}

			if (notificationsListObject != null) {
				int count = notificationsListObject.getPropertyCount();
				if (count != 0) {
					for (int i = 0; i < count; i++) {
						SoapObject notificationObject = null;
						try {
							notificationObject = (SoapObject) notificationsListObject
									.getProperty(i);
						} catch (Exception e) {
							notificationObject = null;
						}
						if (notificationObject != null) {
							String Type = "";
							try {
								Type = notificationObject
										.getProperty("Type")
										.toString();
							} catch (Exception e) {
								Type = "";
							}

							String ID = "";
							try {
								ID = notificationObject.getProperty("ID")
										.toString();
							} catch (Exception e) {
								ID = "";
							}

							String Details = "";
							try {
								Details = notificationObject.getProperty(
										"Details").toString();
							} catch (Exception e) {
								Details = "";
							}
							String DueTime = "";
							try {
								DueTime = notificationObject.getProperty(
										"DueTime").toString();
							} catch (Exception e) {
								DueTime = "";
							}
							String isRead = "";
							try {
								isRead = notificationObject.getProperty(
										"IsRead").toString();
							} catch (Exception e) {
								isRead = "";
							}

							notification = new Notification(Type,
									ID, Details, DueTime,
									isRead);
							notificationsList.add(notification);

						}
					}
				}
			}
			
			SoapObject eventsListObject = null;
			try {
				eventsListObject = (SoapObject) notificationEventsObject.getProperty("EventsList");
			} catch (Exception e) {
				eventsListObject = null;
			}

			if (eventsListObject != null) {
				int count = eventsListObject.getPropertyCount();
				if (count != 0) {
					for (int i = 0; i < count; i++) {
						SoapObject eventObject = null;
						try {
							eventObject = (SoapObject) eventsListObject
									.getProperty(i);
						} catch (Exception e) {
							eventObject = null;
						}
						if (eventObject != null) {
							String Type = "";
							try {
								Type = eventObject
										.getProperty("Type")
										.toString();
							} catch (Exception e) {
								Type = "";
							}

							String ID = "";
							try {
								ID = eventObject.getProperty("ID")
										.toString();
							} catch (Exception e) {
								ID = "";
							}

							String Details = "";
							try {
								Details = eventObject.getProperty(
										"Details").toString();
							} catch (Exception e) {
								Details = "";
							}
							String DueTime = "";
							try {
								DueTime = eventObject.getProperty(
										"DueTime").toString();
							} catch (Exception e) {
								DueTime = "";
							}
							String isRead = "";
							try {
								isRead = eventObject.getProperty(
										"IsRead").toString();
							} catch (Exception e) {
								isRead = "";
							}

							notification = new Notification(Type,
									ID, Details, DueTime,
									isRead);
							eventsList.add(notification);

						}
					}
				}
			}
			
			notificationEvents = new NotificationEvents(schoolName, notifyMSG, eventsMSG, notificationsList, eventsList);

		}// end of school Notification parsing


		return new NotificationResp(ntfy_EventsMSG, notificationEvents);
	}

	public static final AuthResponse parseCachedData(String persistentInfo)
	{
		AuthResponse authResponse=null;
		
		String s[] = persistentInfo.split("@");
		String a = s[0];
		String b = s[1];
		List<Student> ll = new ArrayList<Student>();
		
		String c[] = a.split("%");
		
		for (int j = 0; j < c.length; j++) {
			String l = c[j];
			AuthResponse.Student student = new Student(l);
			ll.add(student);
		}
			authResponse = new AuthResponse(b,ll);
		

		
		return authResponse;
	}
}
