package com.eflake.christina;

import java.text.SimpleDateFormat;
import com.eflake.christina.FragmentOne.CallbackDelegate;
import com.eflake.provider.Model;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.keep.AccessTokenKeeper;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.Utility;
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
import android.widget.Toast;

public class FirstActivity extends FragmentActivity implements
		CallbackDelegate, OnNavigationListener {
	  private Weibo mWeibo;
	    private static final String CONSUMER_KEY = "84951399";// �挎�涓哄������ppkey锛��濡�1646212860";
	    private static final String REDIRECT_URL = "http://www.sina.com";
	    public static Oauth2AccessToken accessToken;
	    public static final String TAG = "sinasdk";
	    SsoHandler mSsoHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
		initActionBar();
		initFragment();
		  FirstActivity.accessToken = AccessTokenKeeper.readAccessToken(this);
	        if (FirstActivity.accessToken.isSessionValid()) {
	            Weibo.isWifi = Utility.isWifi(this);
	            try {
	                Class sso = Class.forName("com.weibo.sdk.android.api.WeiboAPI");// 濡�����weiboapi���锛��绀�pi���婕�ず�ュ����
	            } catch (ClassNotFoundException e) {
	                // e.printStackTrace();
	                Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

	            }
	        }
	}

	
	// 锟斤拷始锟斤拷ActionBar
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
		// 锟矫碉拷锟斤拷SpinnerAdapter锟斤拷一锟铰碉拷锟街凤拷锟斤拷锟斤拷
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

	// 锟斤拷始锟斤拷Fragment
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
            mWeibo.authorize(FirstActivity.this, new AuthDialogListener());

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
		// 锟斤拷锟斤拷欠锟斤拷丶锟�直锟接凤拷锟截碉拷锟斤拷锟斤拷
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			new AlertDialog.Builder(FirstActivity.this)
					.setIcon(R.drawable.mayuri)
					.setTitle("锟斤拷时锟诫开锟斤拷")
					.setMessage("确锟斤拷锟斤拷锟斤拷锟斤拷锟剿ｏ拷锟劫匡拷锟斤拷一锟铰ｏ拷")
					.setPositiveButton("锟斤拷锟斤拷锟斤拷",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									android.os.Process
											.killProcess(android.os.Process
													.myPid());
								}
							})
					.setNegativeButton("锟斤拷锟斤拷锟斤拷",
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

	 class AuthDialogListener implements WeiboAuthListener {

	        @Override
	        public void onComplete(Bundle values) {
	            String token = values.getString("access_token");
	            String expires_in = values.getString("expires_in");
	            FirstActivity.accessToken = new Oauth2AccessToken(token, expires_in);
	            if (FirstActivity.accessToken.isSessionValid()) {
	                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
	                        .format(new java.util.Date(FirstActivity.accessToken
	                                .getExpiresTime()));
//	                mText.setText("璁よ����: \r\n access_token: " + token + "\r\n"
//	                        + "expires_in: " + expires_in + "\r\n������" + date);
	                try {
	                    Class sso = Class
	                            .forName("com.weibo.sdk.android.api.WeiboAPI");// 濡�����weiboapi���锛��绀�pi���婕�ず�ュ����
	                } catch (ClassNotFoundException e) {
	                    // e.printStackTrace();
	                    Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

	                }
//	                cancelBtn.setVisibility(View.VISIBLE);
	                AccessTokenKeeper.keepAccessToken(FirstActivity.this,
	                        accessToken);
	                Toast.makeText(FirstActivity.this, "success", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }

	        @Override
	        public void onError(WeiboDialogError e) {
	            Toast.makeText(getApplicationContext(),
	                    "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
	        }

	        @Override
	        public void onCancel() {
	            Toast.makeText(getApplicationContext(), "Auth cancel",
	                    Toast.LENGTH_LONG).show();
	        }

	        @Override
	        public void onWeiboException(WeiboException e) {
	            Toast.makeText(getApplicationContext(),
	                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
	                    .show();
	        }

	    }
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);

	        /**
	         * 涓��涓や釜娉ㄩ����浠ｇ�锛��褰�dk���sso�舵����
	         */
	        if (mSsoHandler != null) {
	            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
	        }
	    }
}
