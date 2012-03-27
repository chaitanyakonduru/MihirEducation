package com.mms.mes.custom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mms.mes.R;
import com.mms.mes.app.NotificationsDetailsActivity;
import com.mms.mes.model.Notification;

public class ListNotificationAdapter extends ArrayAdapter<Notification> {

	private Context context;
	private List<Notification> ll=new ArrayList<Notification>();
	public ListNotificationAdapter(Context context, int resource, List<Notification> nList) {
		super(context, resource, nList);
		this.context=context;
		this.ll=nList;
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return ll.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TextView details;
		
		final Notification notification=ll.get(position);
		
			convertView=LayoutInflater.from(context).inflate(R.layout.list_layout_notifications, null);
			details=(TextView) convertView.findViewById(R.id.list_layout_tv_eventtitle);
		
			
						details.setText(notification.getDetails());
//			((TextView) convertView.findViewById(R.id.list_layout_tv_date)).setText(notification.getDueTime());
		
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				
				Intent intent=new Intent(context,NotificationsDetailsActivity.class);
				intent.putExtra("myObject", notification);
				
				context.startActivity(intent);
				}
			});
		
		return convertView;
		
	}

	public static String formatDate(String date) {
		Date d=null;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			d=dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dateFormat=new SimpleDateFormat("dd-MM-yyyy"+","+"hh:mm");
		return dateFormat.format(d);
		
	}

	

}
