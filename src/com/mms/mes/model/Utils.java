package com.mms.mes.model;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;

public class Utils {
	
	public static  Drawable getImageDrawable(String logo_url) 
	{
		Drawable drawable=null;
		if (logo_url != null) {

			try {
				URL url = new URL(logo_url);
				InputStream inputStream = (InputStream) url
						.getContent();
				drawable = Drawable.createFromStream(
						inputStream, "imgsrc");

			} catch (Exception e) {

				drawable = null;
			}
		}
		return drawable;
	}

}
