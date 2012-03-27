package com.mms.mes.custom;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mms.mes.R;
import com.mms.mes.model.AuthResponse;
import com.mms.mes.model.AuthResponse.Student;

public class listStudentsAdapter extends ArrayAdapter<AuthResponse.Student>{
	
	private Context context;
	private List<Student> studentList;

	public listStudentsAdapter(Context context, int textViewResourceId, List<Student> objects) {
		super(context, textViewResourceId, objects);
		
		this.context=context;
		studentList=objects;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return studentList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TextView studentName;
		
		final Student mStudent=studentList.get(position);
		
			convertView=LayoutInflater.from(context).inflate(R.layout.listview_text, null);
			studentName=(TextView) convertView.findViewById(R.id.student_name);
				
						studentName.setText(mStudent.getmStudentName());
		
			
		
		return convertView;
		
	}

	

	

}
