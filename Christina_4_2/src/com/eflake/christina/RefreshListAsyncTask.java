package com.eflake.christina;

import com.eflake.provider.DbHelper;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;

public class RefreshListAsyncTask extends AsyncTask<String, Integer, Cursor> {
	public Context context;
	public SimpleCursorAdapter adapter;
	public RefreshListAsyncTask(Context context,android.support.v4.widget.SimpleCursorAdapter simpleCursorAdapter){
		this.context = context;
		this.adapter = simpleCursorAdapter;
	}
	@Override
	protected Cursor doInBackground(String... params) {
		DbHelper dbHelper = new DbHelper(context);
        Cursor newCursor = dbHelper.query("select * from item", null);
		return newCursor;
	}

	@Override
	protected void onPostExecute(Cursor newCursor) {
		adapter.changeCursor(newCursor);
	}

}
