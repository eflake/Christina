package com.eflake.christina;

import com.eflake.provider.DbHelper;
import com.eflake.provider.Model;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentOne extends Fragment implements OnItemClickListener {
	public CallbackDelegate delegate;
	public ListView diary_list;
	public static SimpleCursorAdapter simpleCursorAdapter;
	public Cursor cursor;
	//代理接口
	public interface CallbackDelegate {
		public void clicked(int index);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		delegate = (CallbackDelegate) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fra_one, container, false);
		diary_list = (ListView) view.findViewById(R.id.listView1);
		// 查询数据
		DbHelper dbHelper = new DbHelper(getActivity());
		cursor = dbHelper.query("select * from item", null);
		// 绑定数据
		simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
								 R.layout.diary_list_layout, cursor,
				 new String[] {"diary_title", "diary_description" },
				          new int[] {R.id.title, R.id.description },
				      CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		//日志列表
		diary_list.setAdapter(simpleCursorAdapter);
		diary_list.setOnItemClickListener(this);
		registerForContextMenu(diary_list);
		return view;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderIcon(R.drawable.lingyu);
		menu.setHeaderTitle("删除");
		menu.add(200, 200, 200, "删除");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Toast.makeText(getActivity(),
				"已删除！" + Long.valueOf(info.id).toString(), Toast.LENGTH_SHORT)
				.show();
		int index = Integer.valueOf((int) info.id);
		Uri uri = ContentUris.withAppendedId(Model.CONTENT_URI, index);
		getActivity().getContentResolver().delete(uri, null, null);
		RefreshListAsyncTask task = new RefreshListAsyncTask(getActivity(),
				simpleCursorAdapter);
		task.execute("a");
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public void delect() {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("eflake", Integer.valueOf(position).toString());
		delegate.clicked(position);
	}

}
