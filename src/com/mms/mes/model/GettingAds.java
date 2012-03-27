package com.mms.mes.model;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mms.mes.network.Comm;
import com.mms.mes.network.SoapServiceManager;



public class GettingAds{
	
	private ImageView imageView;
	private  Context context;
	private  int screenId;
	public static final String TAG="GettingAds";
	public Drawable drawable;
	
	public GettingAds(ImageView view,Context context,int screenId) {
		this.imageView=view;
		this.context=context;
		this.screenId=screenId;
	
	}
	
	public void getAds()
	{
		new AdRequestTask().execute(screenId);
	}
	
	class AdRequestTask extends AsyncTask<Integer, Boolean, SoapObject> 
	{

		protected SoapObject doInBackground(Integer... params) {
			return SoapServiceManager.sendAdRequest(params[0]);
		}
		
		protected void onPostExecute(SoapObject result) {
			super.onPostExecute(result);
			Log.v(TAG, "Post Execute in Getting Ads");
		
			if(result!=null)
			{
				try {
					processResponse(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				
			}
			
		}
		
	}
	public void processResponse(SoapObject result) throws Exception {
		final MmsAdResp adResp=Comm.parseAds(result);
		
		Log.v(TAG,adResp.getmAdImageUrl());
		drawable=Utils.getImageDrawable(adResp.getmAdImageUrl());
		if(drawable!=null)
		{
		imageView.setImageDrawable(drawable);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {

				context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(adResp.getmAdNavigationUrl())));
				
			}
		});
	
		}
	}

	

}
