package com.icecream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class FirstActivity extends Activity {

	SharedPreferences share;         //是否首次登录简单存到showWelcomm xml文件中
	Editor editor;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //去除标题
		share = getSharedPreferences("showWelcomm", Context.MODE_PRIVATE);
		editor = share.edit();
		// 判断是否首次登录程序
		if (share.contains("shownum")) 
		{
			setContentView(R.layout.welcom);
		    int num = share.getInt("shownum", 0);
		    editor.putInt("shownum", num++);
		    editor.commit();
			if(!share.contains("sethand"))
			{
			    skipLoginActivity(2);
			}
			else 
			{
				int num2 = share.getInt("sethand", 0);
			    editor.putInt("shownum", num2++);
			    editor.commit();
			    skipHandActivity(2);
			}
		} else 
		{
			setContentView(R.layout.welcom);
			editor.putInt("shownum", 1);
			editor.commit();
			skipWelcomeActivity(2);
			
		}
	}
	
	/**
	 * 延迟多少秒进入欢迎界面
	 * @param min 秒
	 */
	private void skipWelcomeActivity(int min) {
		new Handler().postDelayed(new Runnable() {

			public void run() {
				Intent intent = new Intent(FirstActivity.this,
						GuideActivity.class);
				startActivity(intent);
				FirstActivity.this.finish();
			}
		}, 1000*min);
	}

	/**
	 * 延迟多少秒进入主界面
	 * @param min 秒
	 */
	private void skipLoginActivity(int min) {
		new Handler().postDelayed(new Runnable() {

			public void run() {
				Intent intent = new Intent(FirstActivity.this,
						MainActivity.class);
				startActivity(intent);
				FirstActivity.this.finish();
			}
		}, 1000*min);
	}
	
	/**
	 * 延迟多少秒进入手势界面
	 * @param min 秒
	 */
	private void skipHandActivity(int min) {
		new Handler().postDelayed(new Runnable() {

			public void run() {
				Intent intent = new Intent(FirstActivity.this,
						VeriPassword.class);
				startActivity(intent);
				FirstActivity.this.finish();
			}
		}, 1000*min);
	}

}
