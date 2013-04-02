package com.eflake.christina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        /**
         * 使用handler来处理
         */
        new Handler().postDelayed(new Runnable(){

         @Override
         public void run() {
             Intent mainIntent = new Intent(SplashActivity.this,FirstActivity.class); 
             SplashActivity.this.startActivity(mainIntent);
             SplashActivity.this.finish(); 
         }
           
        }, SPLASH_DISPLAY_LENGHT);
    }
}
