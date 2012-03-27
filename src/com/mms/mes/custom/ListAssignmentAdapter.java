package com.mms.mes.custom;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mms.mes.R;
import com.mms.mes.model.AssignmentResp.Assignment;

public class ListAssignmentAdapter extends ArrayAdapter<Assignment>{

	private Context context;
	private List<Assignment> list;
	public ListAssignmentAdapter(Context context, int textViewResourceId,
			List<Assignment> list) {
		super(context, textViewResourceId, list);
		this.list=list;
		this.context=context;
		
	}
	
	public int getCount() {
		return list.size();
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Assignment assignment=list.get(position);
		convertView=LayoutInflater.from(context).inflate(R.layout.listlayoutassignments, null);
		
		((TextView) convertView.findViewById(R.id.list_layout_tv_assignmentname)).setText(assignment.getmDetails());
		
		return convertView;
	}
	
	
	
	

}
