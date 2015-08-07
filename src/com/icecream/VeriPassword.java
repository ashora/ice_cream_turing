package com.icecream;

import com.icecream.R;
import com.icecream.view.GestureLockViewGroup;
import com.icecream.view.GestureLockViewGroup.OnGestureLockViewListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

public class VeriPassword extends Activity{


	private int myPassword[];
	SharedPreferences share;         //从showWelcomm xml文件中取出密码
	Editor editor;
	private GestureLockViewGroup mGestureLockViewGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_main);
		share = getSharedPreferences("showWelcomm", Context.MODE_PRIVATE);
		editor = share.edit();
		String word = share.getString("myPassWord","");
		myPassword=new int[word.length()];
		for (int index = 0; index < word.length(); ++index)
		{
		    myPassword[index]= Integer.parseInt(word.substring(index, index+1));
		}
		mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
		mGestureLockViewGroup.setAnswer(myPassword);
		mGestureLockViewGroup
				.setOnGestureLockViewListener(new OnGestureLockViewListener()
				{

					@Override
					public void onUnmatchedExceedBoundary()
					{
						Toast.makeText(VeriPassword.this, "错误5次...",
								Toast.LENGTH_SHORT).show();
						mGestureLockViewGroup.setUnMatchExceedBoundary(5);
					}

					@Override
					public void onGestureEvent(boolean matched)
					{
						
						
						
						if(matched == true)
						{
							Toast.makeText(VeriPassword.this, "验证成功",
									Toast.LENGTH_SHORT).show();
							VeriPassword.this.finish();
							Intent intent = new Intent(VeriPassword.this,
									MainActivity.class);
							startActivity(intent);
						}
						else 
						{
							Toast.makeText(VeriPassword.this, "验证失败,请重新输入！",
									Toast.LENGTH_SHORT).show();
						}
						 
					}

					@Override
					public void onBlockSelected(int cId)
					{
					}
				});
	}

}
