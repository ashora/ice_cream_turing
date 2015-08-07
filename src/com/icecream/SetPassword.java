package com.icecream;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

import com.icecream.R;
import com.icecream.view.GestureLockViewGroup;
import com.icecream.view.GestureLockViewGroup.OnGestureLockViewListener;

public class SetPassword extends Activity{
	

	SharedPreferences share;         //密码简单存到showWelcomm xml文件中
	Editor editor;
	private GestureLockViewGroup mGestureLockViewGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_main);

		mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
		mGestureLockViewGroup.setAnswer(new int[] {0, 1, 2, 5, 8});
		mGestureLockViewGroup
				.setOnGestureLockViewListener(new OnGestureLockViewListener()
				{

					@Override
					public void onUnmatchedExceedBoundary()
					{
						Toast.makeText(SetPassword.this, "错误5次...",
								Toast.LENGTH_SHORT).show();
						mGestureLockViewGroup.setUnMatchExceedBoundary(5);
					}

					@Override
					public void onGestureEvent(boolean matched)
					{
						share = getSharedPreferences("showWelcomm", Context.MODE_PRIVATE);
						editor = share.edit();
						int[] me=new int[mGestureLockViewGroup.backChoose().length];
						me=mGestureLockViewGroup.backChoose();
						editor.putInt("sethand", 1);
						String strArray = new String();
						for (int index = 0; index < me.length; ++index)
						{
						    strArray+=String.valueOf(me[index]);
						}
						editor.putString("myPassWord", strArray);
						editor.commit();
						Toast.makeText(SetPassword.this, "设置成功,请记住密码，下次进入输入！",
								Toast.LENGTH_SHORT).show();
						SetPassword.this.finish();
					}

					@Override
					public void onBlockSelected(int cId)
					{
					}
				});
	}


}
