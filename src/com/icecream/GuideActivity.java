package com.icecream;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
/**
 * @author manymore13
 */
@SuppressLint("HandlerLeak")
public class GuideActivity extends Activity {

	// 到达最后一张
	private static final int TO_THE_END = 0;   
	// 离开最后一张
	private static final int LEAVE_FROM_END = 1; 

	// 只需在这里添加删除图片即可
	private int[] ids = { R.drawable.s1,
			R.drawable.s2, R.drawable.s3,
			 };
			
	private List<View> guides = new ArrayList<View>();
	private ViewPager pager;         
	private ImageView start;          // 点击体验
	private ImageView curDot;         //当前图片
	private LinearLayout dotContain; // 存储当前点的容器
	private int offset;              // 位移量
	private int curPos = 0;          // 记录当前的位置
	     

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //去除标题
		setContentView(R.layout.main);
		init();         // 功能介绍界面的初始化
		
	}
	
	private ImageView buildImageView(int id)
	{
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		iv.setLayoutParams(params);
		iv.setScaleType(ScaleType.FIT_XY);
		return iv;
	}
		
	// 功能介绍界面的初始化
	private void init()
	{
		this.getView();
		initDot();
		ImageView iv = null;
		guides.clear();
		for (int i = 0; i < ids.length; i++) {
			iv = buildImageView(ids[i]);
			guides.add(iv);  //添加到List里
		}
		
		//System.out.println("guild_size="+guides.size());

		// 当curDot的所在的树形层次将要被绘出时此方法被调用
		curDot.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						// 获取ImageView的宽度也就是当前点图片的宽度
						offset = curDot.getWidth();
						return true;
					}
				});

		final GuidePagerAdapter adapter = new GuidePagerAdapter(guides);
		// ViewPager设置数据适配器，这个类似于使用ListView时用的adapter
		pager.setAdapter(adapter);
		pager.clearAnimation();
		// 为Viewpager添加事件监听器 OnPageChangeListener
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position)
			{
		
				int pos = position % ids.length;
				
				moveCursorTo(pos);
				
				
				if (pos == ids.length-1) {// 到最后一张了
					handler.sendEmptyMessageDelayed(TO_THE_END, 500);					
					
				} else if (curPos == ids.length - 1) {
					handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
				}
				curPos = pos;     //返回上一个界面时体验按钮可以影藏
				super.onPageSelected(position);
			}
		});
		
	}
	
	/**
	 *  在layout中实例化一些View
	 */
	private void getView()
	{
		dotContain = (LinearLayout)this.findViewById(R.id.dot_contain);
		pager = (ViewPager) findViewById(R.id.contentPager);
		curDot = (ImageView) findViewById(R.id.cur_dot);
		start = (ImageView) findViewById(R.id.open);
		start.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				skipLoginActivity(2);  //响应体验按钮
			}
		});
	}
	
	
	/**
	 * 延迟多少秒进入主界面
	 * @param min 秒
	 */
	private void skipLoginActivity(int min) {
		new Handler().postDelayed(new Runnable() {

			public void run() {
				Intent intent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(intent);
				GuideActivity.this.finish();
			}
		}, 1000*min);
	}
	
	/**
	 * 初始化点 ImageVIew
	 * @return 返回true说明初始化点成功，否则实例化失败
	 */
	private boolean initDot()
	{
		
		if(ids.length > 0){
			ImageView dotView ;
			for(int i=0; i<ids.length; i++)
			{
				dotView = new ImageView(this);
				dotView.setImageResource(R.drawable.dot1_w);
				dotView.setLayoutParams(new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
				
				dotContain.addView(dotView);
			}
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 移动指针到相邻的位置 动画
	 * @param position
	 * 指针的索引值
	 * */
	private void moveCursorTo(int position) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation tAnim = 
				new TranslateAnimation(offset*curPos, offset*position, 0, 0);
		animationSet.addAnimation(tAnim);
		animationSet.setDuration(300);
		animationSet.setFillAfter(true);
		curDot.startAnimation(animationSet);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == TO_THE_END)
				start.setVisibility(View.VISIBLE);
			else if (msg.what == LEAVE_FROM_END)
				start.setVisibility(View.GONE);
		}
	};
	
	
	
	// ViewPager 适配器GuidePagerAdapter，继承PagerAdapter类
	class GuidePagerAdapter extends PagerAdapter{
		
		private List<View> views;
		
		public GuidePagerAdapter(List<View> views){
			this.views=views;
		}
		
		@Override
		public void destroyItem(View v, int position, Object arg2) {
			//从ViewGroup中移出当前View,方面下一个VIew显示
			((ViewPager) v).removeView(views.get(position % views.size()));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			// 获取当前窗体界面数,这里返回一个稍微大点值,不然滑到顶就滑不动了
			return views.size();
		}

		@Override
		public Object instantiateItem(View v, int position) {
			//return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
			Log.e("tag", "instantiateItem = "+position);
			((ViewPager) v).addView(views.get(position % views.size()),0);
			return views.get(position % views.size());
		}

		@Override
		public boolean isViewFromObject(View v, Object object) {   //用于判断是否由对象生成界面
			return v == (object);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			
		}
		

	}
	
}