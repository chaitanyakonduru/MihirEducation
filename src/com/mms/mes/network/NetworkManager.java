package com.mms.mes.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {

	
	public static boolean checkInternetConnection(Context context) {
		boolean isConnected = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState() == NetworkInfo.State.CONNECTED
				|| connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {

			isConnected = true;
		} else {
			
			isConnected=false;
		}
		return isConnected;
	}

}
