package com.mms.mes.network;

import org.ksoap2.serialization.SoapObject;

public class ResponseHandler {   

	private int reqId = 0;
	public ResponseHandler( int reqId) {
		this.reqId = reqId;
	}
	
	public Object onHandleResponse(SoapObject soapObject ){
		
		return null;
	}
	
	
}
