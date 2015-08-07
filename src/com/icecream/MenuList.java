package com.icecream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuList {

	private Context mContext;
	private SlidingMenu mMenu;
    public static final int IMAGE_CODE = 1;
    private int ITEM_COUNTS = 5;
    private ListView list;
    private String[] titles = new String[]{"一键分享", "打开相机", 
    	
    		 "设置", "联系我们", "关于我们"};
    private int images[] = new int[]{R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, 
    		
    		R.drawable.img_4, R.drawable.img_5};
 
    public MenuList(Context context, SlidingMenu menu) {
		
        mContext = context;
        list = (ListView)menu.findViewById(R.id.menu_list);
        
        //用于存储每个ListView的Item数据 
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < ITEM_COUNTS; ++i) {
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("title", titles[i]);
        	map.put("image", images[i]);
        	data.add(map);
        }
    
        SimpleAdapter adapter = new SimpleAdapter((Context)mContext, data, R.layout.list_item,
        		new String[]{"title", "image"}, new int[]{R.id.menu_item_title, R.id.menu_item_image});
       
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
					onClick(arg2);
			    
			}    
        });
    }
	public void onClick(int arg) {
		switch(arg) {
			//分享到其他app
			case 0:
				
			Intent intent7 = new Intent();
            intent7.setAction(Intent.ACTION_SEND);
            intent7.putExtra(Intent.EXTRA_TEXT, "我是可爱的智能机器人，快来和我聊天吧\nhttp://pan.baidu.com/s/1eQm5Rou");
            intent7.setType("text/plain");
            mContext.startActivity(Intent.createChooser(intent7, "Chosing a platform"));
            break;
			
            //打开相机
			case 1:
			
			Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mContext.startActivity(intent3);
			break;
			//设置
			case 2:
			new AlertDialog.Builder(mContext).setTitle(titles[2]).
			setItems(new String[]{"更换头像", "开启手势密码", "系统设置"}, new OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					switch(arg1) {
					//更滑头像
					case 0:
						Intent intent0  = new Intent();
						intent0.setType("image/*");
						intent0.setAction(Intent.ACTION_GET_CONTENT);
						((Activity) mContext).startActivityForResult(intent0,IMAGE_CODE);
						break;
					//开启手势密码
					case 1:
						Intent intent5=new Intent(mContext,SetPassword.class);
	    			    mContext.startActivity(intent5);    
		                break;
					//系统设置
					case 2:
						Intent intent = new Intent();
		                intent.setClassName("com.android.settings", "com.android.settings.Settings");
		                mContext.startActivity(intent);
		                break;
					}
				}
				
			}).show();
            break;
            
            //联系我们
			case 3:
				
			new AlertDialog.Builder(mContext).setTitle(titles[0]).setItems(new String[]{
					"拨打电话", "编辑短信", "进入主页"}, new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							switch(arg1) {
							//拨打电话
							case 0:
								Uri telUri = Uri.parse("tel:15955272317");
								Intent intent0 = new Intent(Intent.ACTION_DIAL);
								mContext.startActivity(intent0);
								break;
							//编辑短信
							case 1:
								Intent intent1 = new Intent(Intent.ACTION_VIEW);
				                intent1.setType("vnd.android-dir/mms-sms");
				                mContext.startActivity(intent1);
								break;
							//进入主页
							case 2:
								Intent intent2 = new Intent();
				                intent2.setAction(Intent.ACTION_VIEW);
				                intent2.setData(Uri.parse("https://github.com/A207-03"));
				                mContext.startActivity(intent2);
								break;
							}
						}
				
			}).show();
			break;
            
	      
		}
	}

	

}



