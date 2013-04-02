package com.eflake.christina;

import com.eflake.provider.DbHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailDiaryActivity extends Activity {
	public TextView contentTextView;
	public String title;
	public String description;
	public String content;
	public String picString;
	public int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_diary);
		final ActionBar actionBar = getActionBar();
		Drawable drawable = getResources().getDrawable(R.drawable.background);
		actionBar.setBackgroundDrawable(drawable);
		actionBar.setDisplayHomeAsUpEnabled(true);
		contentTextView = (TextView) findViewById(R.id.detail_content);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		index = bundle.getInt("index");
		Log.i("eflake detail get ", Integer.valueOf(index).toString());
		DbHelper dbHelper = new DbHelper(this);
		Cursor cursor = dbHelper.query("select * from item", null);
		cursor.moveToPosition(index);
		title = cursor.getString(2);
		description = cursor.getString(3);
		picString = cursor.getString(4);
		content = cursor.getString(5);
		contentTextView.setText(content);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			
			break;
		case android.R.id.home:
			setResult(123);
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
		case R.id.menu_edit:
			Intent intent = new Intent(DetailDiaryActivity.this,
					CreateDiaryActivity.class);
			intent.putExtra("content", content);
			intent.putExtra("title", title);
			intent.putExtra("description", description);
			intent.putExtra("picString", picString);
			intent.putExtra("index", index);
			Log.i("eflake intent send ", Integer.valueOf(index).toString());
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
