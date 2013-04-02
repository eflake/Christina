package com.eflake.christina;

import java.util.ArrayList;
import java.util.List;

import com.eflake.provider.DbHelper;
import com.eflake.provider.Diary;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DiaryAdapter extends BaseAdapter {
	public List<Diary> diaries;
	public Context context;
	public DiaryAdapter (Context context) {
		this.context = context;
		refresh();
	}
	public void refresh(){
		 DbHelper dbHelper = new DbHelper(context);
	        Cursor cursor = dbHelper.query("select * from item", null);
	       if (cursor.moveToFirst()) {
	    	   if (cursor.moveToNext()) {
	    		   diaries = new ArrayList<Diary>();
				Diary diary = new Diary();
				diary.DIARY_ID = cursor.getInt(1);
				diary.DIARY_TITLE =cursor.getString(2);
				diary.DIARY_DESCRIPTION = cursor.getString(3);
				diary.DIARY_CONTENT =cursor.getString(5);
				diaries.add(diary);
				}
	       }
	}
	       
	@Override
	public int getCount() {
		return diaries.size();
	}

	@Override
	public Object getItem(int position) {
		return diaries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder;
		if (convertView == null) {
			holder = new Viewholder();
			convertView = LayoutInflater.from(context).inflate(R.layout.diary_list_layout, null);
			//holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.description = (TextView) convertView.findViewById(R.id.description);
			convertView.setTag(holder);
		}else {
			holder = (Viewholder)convertView.getTag();
		}
		holder.title.setText(diaries.get(position).DIARY_TITLE);
		holder.description.setText(diaries.get(position).DIARY_DESCRIPTION);
		//holder.image.setBackgroundResource(R.drawable.female_shirt);
		return convertView;
	}

	private class Viewholder{
		//private ImageView image;
		private TextView title;
		private TextView description;
	}

}
