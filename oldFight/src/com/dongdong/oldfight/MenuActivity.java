package com.dongdong.oldfight;
/**
 * @author qingsong1990
* ${tags}
*/
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.dongdong.oldfight.ad.QuitPopAd;
import com.dongdong.oldfight.uitl.Const;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener ,UpdatePointsNotifier{

	private TextView normal;
	private TextView jieji;
	private TextView jishi;
	private TextView title;
	private TextView about;
	public static MenuActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		setContentView(R.layout.menu); 
		instance = this;
		// 自定义字体文件保存在assets/fonts/目录下，w
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/qwjt.TTF");
		title = (TextView) findViewById(R.id.title);
		title.setTypeface(typeFace);
		normal = (TextView) findViewById(R.id.normal);
		jieji = (TextView)findViewById(R.id.jieji);
		jishi = (TextView)findViewById(R.id.jishi);
		about = (TextView)findViewById(R.id.about);
		normal.setOnClickListener(this);
		jieji.setOnClickListener(this);
		jishi.setOnClickListener(this);
		about.setOnClickListener(this);
		
		//广告代码
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).getPoints(this);
		
		if(Const.myJifen<20){
			AppConnect.getInstance(this).initPopAd(this);
			/*互动广告*/		
			LinearLayout adlayout =(LinearLayout)findViewById(R.id.AdLinearLayout);
			AppConnect.getInstance(this).showBannerAd(this, adlayout);
		}
		
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MenuActivity.instance,MainActivity.class);
		switch (v.getId()) {
		case R.id.normal:
			Const.model = "normal";
			Const.addBase = 100;//表示每6秒钟增加一次速度由100*60计算得来
			break;
		case R.id.jieji:
			Const.model = "jieji";
			Const.addBase = 30;//表示每3秒钟增加一次速度
			break;
		case R.id.jishi:
			Const.model = "jishi";
			Const.addBase = 100;//表示每3秒钟增加一次速度
			break;
		case R.id.about:
			Intent intent2 = new Intent(MenuActivity.instance,AboutActivity.class);
			MenuActivity.instance.startActivity(intent2);
			return;
		default:
			break;
		}
		MenuActivity.instance.startActivity(intent);

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == event.KEYCODE_BACK){
			if(Const.myJifen<20){
				QuitPopAd.getInstance().show(this);
			}else {
				MenuActivity.instance.finish();
				return true;
			}
			AppConnect.getInstance(this).close();
		}
		return true;
	}
	@Override
	public void getUpdatePoints(String arg0, int arg1) {
		// TODO Auto-generated method stub
		Const.myJifen = arg1;
		Log.e("你的分数:", ""+Const.myJifen);
	}
	@Override
	public void getUpdatePointsFailed(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AppConnect.getInstance(MenuActivity.instance).getPoints(MenuActivity.this);
		
	}
	
	
}
