package com.mms.mes.network;

import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;

import android.util.Log;

public class SoapServiceManager {

	private final static String NAMESPACE = "http://mihirmobile.com/MIHIRSrv/";
	private static final String URL = "http://mihirwebsrv.elasticbeanstalk.com/services/MIHIRSrv?wsdl";
	private static final String TAG = "MihirSoapService";
	private static final String SOAP_ACTION_REGISTRATION = "http://mihirmobile.com/MIHIRSrv/Registration";
	private static final String SOAP_ACTION_STUDENTMARKS = "http://mihirmobile.com/MIHIRSrv/StudentMarks";
	private static final String SOAP_ACTION_FORGOTPASSWORD = "http://mihirmobile.com/MIHIRSrv/ForgotPassword";
	private static final String SOAP_ACTION_CHANGEPASSWORD = "http://mihirmobile.com/MIHIRSrv/ChangePassword";
	private static final String SOAP_ACTION_RAGGINGCOMPLAINT = "http://mihirmobilesolutions.com/MIHIRSrv/RaggingComplaint";
	private static final String SOAP_ACTION_AUTHENTICATE = "http://mihirmobile.com/MIHIRSrv/Authenticate";
	private static final String SOAP_ACTION_FINDCANTEENS = "http://mihirmobile.com/MIHIRSrv/FindCanteens";
	private static final String SOAP_ACTION_VIEWCANTEENMENU = "http://mihirmobile.com/MIHIRSrv/ViewCanteenMenu";
	private static final String  SOAP_ACTION_ORDER_ITEMS = "http://mihirmobile.com/MIHIRSrv/OrderItem";
	private static final String SOAP_ACTION_ASSIGNMENTS="http://mihirmobile.com/MIHIRSrv/Assignment";
	private static final String SOAP_ACTION_ATTENDANCE="http://mihirmobile.com/MIHIRSrv/Attendance";
	private static final String SOAP_ACTION_MMSADS="http://mihirmobile.com/MIHIRSrv/MMS_Advertisements";
	private static final String SOAP_ACTION_NOTIFICATIONS_EVENTS="http://mihirmobile.com/MIHIRSrv/Notification_Events";
	private static final String SOAP_ACTION_READUNREADMAIL="http://mihirmobile.com/MIHIRSrv/UpdateRead_UnRead";
	private static final String SOAP_ACTION_VIEWIMPCONTACTS="http://mihirmobile.com/MIHIRSrv/ViewIMP_Contacts";
	
	private static SoapObject callWebService(String actionName,
			SoapObject request) throws Exception {

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = false;
		envelope.bodyOut = request;
		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		int timeout=60000;
		
		HttpTransportSE httpTransportSE = new HttpTransportSE(URL,timeout);
		httpTransportSE.debug = true;
		ServiceConnection connection=new ServiceConnectionSE(URL);
		connection.connect();
		
		Log.v("request", request.toString());
		httpTransportSE.call(actionName, envelope);
		Log.v(TAG, "response::::"+(SoapObject)envelope.bodyIn);
		return (SoapObject) envelope.bodyIn;
	}
	
	public static SoapObject sendViewImpContactsRequest(int Student_Id)
	{
		SoapObject results=null;
		
		String methodName="ViewIMP_Contacts";
		String actionName=SOAP_ACTION_VIEWIMPCONTACTS;
		
		SoapObject requestObject=new SoapObject(NAMESPACE, methodName);
		
		PropertyInfo info=new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("StudentID");
		info.setValue(Student_Id);
		requestObject.addProperty(info);
		try
		{
			results=callWebService(actionName, requestObject);
			Log.v(TAG, results.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	public static SoapObject sendUpdateReadMail(String notification_Id)
	{
		SoapObject results=null;
		String methodName="UpdateRead_UnRead";
		String actionName=SOAP_ACTION_READUNREADMAIL;
		
		SoapObject requestObject=new SoapObject(NAMESPACE, methodName);
		
		PropertyInfo info=new PropertyInfo();
		info.setName("Notification_ID");
		info.setValue(Integer.parseInt(notification_Id));
		info.setNamespace(NAMESPACE);
		
		requestObject.addProperty(info);
		try
		{
			results=callWebService(actionName, requestObject);
			Log.v(TAG,results.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public static SoapObject sendregisterRequest(String email) {

		SoapObject results = null;
		String methodName = "RegisterRequest";
		String actionName = SOAP_ACTION_REGISTRATION;
		SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ContctID";
		info.setValue(email);
		request.addProperty(info);

		
		try {
			results = callWebService(actionName, request);
			Log.v(TAG, "" + results.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static SoapObject sendAdRequest(int screenId)
	{
		SoapObject results=null;
		String methodName="MMS_Advertisements";
		String actionName=SOAP_ACTION_MMSADS;
		
		SoapObject request=new SoapObject(NAMESPACE, methodName);
		
		PropertyInfo info=new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("ScreenID");
		info.setValue(screenId);
		request.addProperty(info);
		
		try
		{
			results=callWebService(actionName, request);
			Log.v(TAG, results.toString());
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return results;
	}

	public static SoapObject sendraggingComplaintRequest(String student_id,
			List<String> names, String comments) {
		String s[] = new String[] { "", "", "", "" };
		int count = names.size();
		for (int i = 0; i < count; i++) {
			s[i] = names.get(i);
		}
		Log.v(TAG, s[0]);
		Log.v(TAG, s[1]);
		Log.v(TAG, s[2]);
		Log.v(TAG, s[3]);
		SoapObject results = null;
		String methodName = "RaggingComplaint";
		String actionName = SOAP_ACTION_RAGGINGCOMPLAINT;
		SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "StudentID";
		info.setValue(Integer.parseInt(student_id));
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "PersonAgainst1";
		info.setValue(s[0]);
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "PersonAgainst2";
		info.setValue(s[1]);
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "PersonAgainst3";
		info.setValue(s[2]);
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "PersonAgainst4";
		info.setValue(s[3]);
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "Comments";
		info.setValue(comments);
		request.addProperty(info);

		try {
			results = callWebService(actionName, request);
			Log.v(TAG, "" + results.toString());
		} catch (Exception e) {
			e.printStackTrace();
			// return e.toString();
		}
		return results;
	}

	public static SoapObject sendforgotPasswordRequest(String email
			) {
		SoapObject results = null;
		String methodName = "ForgotPassword";
		String actionName = SOAP_ACTION_FORGOTPASSWORD;
		SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ContctID";
		info.setValue(email);
		request.addProperty(info);

			try {
			results = callWebService(actionName, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public static SoapObject sendstudentMarksRequest(int studentId) {
		SoapObject results = null;
		String methodName = "StudentMarks";
		String actionName = SOAP_ACTION_STUDENTMARKS;
		SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "StudentID";
		info.setValue(studentId);
		request.addProperty(info);
		try {
			results = callWebService(actionName, request);
			Log.v(TAG, results.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public static SoapObject sendchangePasswordRequest(String mmsId,
			String oldPwd, String newPwd) {
		SoapObject results = null;
		String methodName = "ChangePassword";
		String actionName = SOAP_ACTION_CHANGEPASSWORD;
		SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ContctID";
		info.setValue(mmsId);
		request.addProperty(info);

		
		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "CurrentPassword";
		info.setValue(oldPwd);
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "NewPassword";
		info.setValue(newPwd);
		request.addProperty(info);
		try {
			results = callWebService(actionName, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public static SoapObject sendAuthenticateRequest(String mmsId,
			String password,String uuid) {
		Log.v(TAG,"Device Id:"+uuid);
		SoapObject results = null;
		String methodName = "Authenticate";
		String actionName = SOAP_ACTION_AUTHENTICATE;
		SoapObject requestObject = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ContctID";
		info.setValue(mmsId);
		requestObject.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "Password";
		info.setValue(password);
		requestObject.addProperty(info);
		
		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "UUID";
		info.setValue(uuid);
		requestObject.addProperty(info);
		
		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "DeviceOS";
		info.setValue(2);
		requestObject.addProperty(info);
		

		try {
			results = callWebService(actionName, requestObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public static SoapObject sendFindCanteenRequest(int std_id
			) {
		SoapObject results = null;
		String methodName = "FindCanteens";
		String actionName = SOAP_ACTION_FINDCANTEENS;
		SoapObject requestObject = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "StudentID";
		info.setValue(std_id);
		requestObject.addProperty(info);

	
		try {

			results = callWebService(actionName, requestObject);
			Log.v(TAG, "find Cateen Response" + results.toString());
		} catch (Exception e) {
		}

		return results;
	}

	public static SoapObject sendViewCanteenMenuRequest(String std_id,
			String canteen_id) {
		SoapObject results = null;
		String methodName = "ViewCanteenMenu";
		String actionName = SOAP_ACTION_VIEWCANTEENMENU;
		SoapObject requestObject = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "StudentID";
		info.setValue(Integer.parseInt(std_id));
		requestObject.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "CanteenID";
		info.setValue(Integer.parseInt(canteen_id));
		requestObject.addProperty(info);

		try {
			results = callWebService(actionName, requestObject);
			Log.v(TAG, "View CanteenMenu Response" + results.toString());
		} catch (Exception e) {
		}

		return results;
	}

	
	
public static SoapObject sendAssignmentsRequest(String student_id)
	
	{
		SoapObject results=null;
		String methodName="Assignment";
		String actionName=SOAP_ACTION_ASSIGNMENTS;
		SoapObject request=new SoapObject(NAMESPACE, methodName);
		
		PropertyInfo info=new PropertyInfo();
		info.namespace=NAMESPACE;
		info.name="StudentID";
		info.setValue(Integer.parseInt(student_id));
		request.addProperty(info);
		
		try
		{
			results=callWebService(actionName, request);
			Log.v(TAG, results.toString());
			return results;
		}
		catch (Exception e) {
			e.printStackTrace();		
		}
		
		return results;
	}

public static SoapObject sendAttendanceRequest(String student_Id)
{	SoapObject results=null;
	String methodName="Attendance";
	String actionName=SOAP_ACTION_ATTENDANCE;
	
	SoapObject requestObject=new SoapObject(NAMESPACE, methodName);
	
	PropertyInfo info=new PropertyInfo();
	info.name="StudentID";
	info.namespace=NAMESPACE;
	info.setValue(Integer.parseInt(student_Id));
	requestObject.addProperty(info);
	
	try
	{
		results=callWebService(actionName, requestObject);
		Log.v(TAG, results.toString());
		
	}catch (Exception e) {
	}
	return results;
}

public static SoapObject sendNotifications_request(int student_Id)
{
	SoapObject results=null;
	String methodName="Notification_Events";
	String actionName=SOAP_ACTION_NOTIFICATIONS_EVENTS;
	
	SoapObject requestObject=new SoapObject(NAMESPACE, methodName);
	
	PropertyInfo info=new PropertyInfo();
	info.setNamespace(NAMESPACE);
	info.setName("StudentID");
	info.setValue(student_Id);
	requestObject.addProperty(info);
	
	try
	{
		results=callWebService(actionName, requestObject);
		Log.v(TAG, results.toString());
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	
	return results;
}


	
}
