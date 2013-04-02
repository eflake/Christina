package com.eflake.christina;

import com.eflake.christina.FragmentOne.CallbackDelegate;
import com.eflake.provider.Model;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class FirstActivity extends FragmentActivity implements
		CallbackDelegate, OnNavigationListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		initFragment();
	}

	// 初始化ActionBar
	private void initActionBar() {
		final ActionBar actionBar = getActionBar();
		Drawable drawable = getResources().getDrawable(R.drawable.background);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setBackgroundDrawable(drawable);
		actionBar.setSplitBackgroundDrawable(drawable);
		actionBar.setTitle("");
		SpinnerAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.drop_list,
				R.layout.sp_layout);
		// 得到和SpinnerAdapter里一致的字符数组
		//String[] listNames = getResources().getStringArray(R.array.drop_list);
		actionBar.setListNavigationCallbacks(adapter,
				new OnNavigationListener() {
					@Override
					public boolean onNavigationItemSelected(int itemPosition,
							long itemId) {
						Log.d("eflake", "doit");
						switchToCorrespondFragment(itemPosition);
						return false;
					}
				});
	}

	protected void switchToCorrespondFragment(int position) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
		transaction.replace(R.id.container, FragmentFactory.product(position));
		transaction.addToBackStack(null);
		transaction.commit();
	}

	// 初始化Fragment
	private void initFragment() {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.container, FragmentFactory.product(0));
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		RefreshListAsyncTask task = new RefreshListAsyncTask(this,
				FragmentOne.simpleCursorAdapter);
		task.execute("a");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			break;
		case R.id.menu_add:
			Intent intent = new Intent(FirstActivity.this,
					CreateDiaryActivity.class);
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void delete(int index) {
		Uri uri = ContentUris.withAppendedId(Model.CONTENT_URI, index);
		getContentResolver().delete(uri, null, null);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			new AlertDialog.Builder(FirstActivity.this)
					.setIcon(R.drawable.mayuri)
					.setTitle("暂时离开？")
					.setMessage("确定不理我了？再考虑一下？")
					.setPositiveButton("不理你",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									android.os.Process
											.killProcess(android.os.Process
													.myPid());
								}
							})
					.setNegativeButton("饶了你",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).show();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void clicked(int index) {
		Intent intent = new Intent(FirstActivity.this,
				DetailDiaryActivity.class);
		intent.putExtra("index", index);
		startActivityForResult(intent, 111);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		return false;
	}

}
