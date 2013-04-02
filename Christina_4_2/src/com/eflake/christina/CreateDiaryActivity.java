package com.eflake.christina;

import com.eflake.provider.DbHelper;
import com.eflake.provider.Model;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreateDiaryActivity extends Activity {
	public EditText titleEditText;
	public EditText descriptionEditText;
	public EditText contentEditText;
	public String titleString = "";
	public String descriptionString = "";
	public String contentString = "";
	public int index;
	public boolean isEditMode;
	public boolean isEmptyMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_diary);
		ActionBar bar = this.getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		Drawable drawable = getResources().getDrawable(R.drawable.background);
		bar.setBackgroundDrawable(drawable);
		bar.setSubtitle("for you");
		bar.setTitle("Diary");
		initWidge();
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			Bundle bundle = intent.getExtras();
			titleEditText.setText(bundle.getString("title"));
			descriptionEditText.setText(bundle.getString("description"));
			contentEditText.setText(bundle.getString("content"));
			index = bundle.getInt("index");
			Log.i("eflake final get ", Integer.valueOf(index).toString());
			isEditMode = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_diary_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save:
			if (isEditMode) {
				updateToDB();
				if (isEmptyMode) {
				} else {
					setResult(123);
					finish();
				}
			} else {
				writeToDB();
				// Intent intent = new Intent(CreateDiaryActivity.this,
				// FirstActivity.class);
				if (isEmptyMode) {
				} else {
					setResult(123);
					finish();
				}
			}

			break;
		case android.R.id.home:
			setResult(123);
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateToDB() {
		if (!titleEditText.getText().toString().equals("")) {
			titleString = titleEditText.getText().toString();
		}
		if (!descriptionEditText.getText().toString().equals("")) {
			descriptionString = descriptionEditText.getText().toString();
		}
		if (!contentEditText.getText().toString().equals("")) {
			contentString = contentEditText.getText().toString();
		}
		if (!titleString.equals("") && !descriptionEditText.equals("")
				&& !contentEditText.equals("")) {
			update(titleString, descriptionString, contentString);
		} else {
			isEmptyMode = true;
			Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	public void initWidge() {
		titleEditText = (EditText) findViewById(R.id.edittext_title);
		descriptionEditText = (EditText) findViewById(R.id.edittext_description);
		contentEditText = (EditText) findViewById(R.id.edittext_content);
	}

	public void writeToDB() {
		if (!titleEditText.getText().toString().equals("")) {
			titleString = titleEditText.getText().toString();
		}
		if (!descriptionEditText.getText().toString().equals("")) {
			descriptionString = descriptionEditText.getText().toString();
		}
		if (!contentEditText.getText().toString().equals("")) {
			contentString = contentEditText.getText().toString();
		}
		if (!titleString.equals("") && !descriptionEditText.equals("")
				&& !contentEditText.equals("")) {
			insert(titleString, descriptionString, contentString);

		} else {
			isEmptyMode = true;
			Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	private void insert(String diary_title, String diary_description,
			String diary_content) {
		Uri uri = Model.CONTENT_URI;
		ContentValues cv = new ContentValues();
		cv.put(Model.DIARY_ID, 12);
		cv.put(Model.DIARY_TITLE, diary_title);
		cv.put(Model.DIARY_DESCRIPTION, diary_description);
		cv.put(Model.DIARY_PIC_URL, "back_path");
		cv.put(Model.DIARY_CONTENT, diary_content);

		getContentResolver().insert(uri, cv);
		Log.d("eflake", uri.toString());
	}

	private void update(String diary_title, String diary_description,
			String diary_content) {
		DbHelper dbHelper = new DbHelper(this);
		String where = Model._ID + "=?";
		String[] whereargs = { Integer.valueOf(index + 1).toString() };
		Log.i("eflake update get ", Integer.valueOf(index + 1).toString());
		ContentValues cv = new ContentValues();
		cv.put(Model.DIARY_TITLE, diary_title);
		cv.put(Model.DIARY_DESCRIPTION, diary_description);
		cv.put(Model.DIARY_CONTENT, diary_content);
		dbHelper.getWritableDatabase().update("item", cv, where, whereargs);
	}
}
