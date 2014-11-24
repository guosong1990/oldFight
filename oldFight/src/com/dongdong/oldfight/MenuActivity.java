package com.dongdong.oldfight;
/**
 * @author qingsong1990
* ${tags}
*/
import java.io.IOException;

import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.dongdong.oldfight.ad.QuitPopAd;
import com.dongdong.oldfight.uitl.Const;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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
	private TextView music;
	public static MenuActivity instance;
	private MediaPlayer mPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		music = (TextView) findViewById(R.id.music);
		normal.setOnClickListener(this);
		jieji.setOnClickListener(this);
		jishi.setOnClickListener(this);
		about.setOnClickListener(this);
		music.setOnClickListener(this);
		
		//广告代码
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).getPoints(this);
		String showad = AppConnect.getInstance(this).getConfig("showad", "yes");
		if(showad.equals("yes")){
			Const.hasAd = true;
		}else {
			Const.hasAd = false;
		}
		Log.e("showad", showad);
		if(Const.myJifen<20&&Const.hasAd){
			AppConnect.getInstance(this).initPopAd(this);
			/*互动广告*/		
			LinearLayout adlayout =(LinearLayout)findViewById(R.id.AdLinearLayout);
			AppConnect.getInstance(this).showBannerAd(this, adlayout);
		}
		initMuisic();//开启音乐	
		AppConnect.getInstance(this).spendPoints(1170);
	}
	
	public void initMuisic(){
		try {
			mPlayer = MediaPlayer.create(this, R.raw.bg_music);
			if(mPlayer!=null){
				mPlayer.stop();
			}
			mPlayer.prepare();
			mPlayer.setLooping(true);
			mPlayer.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		case R.id.music:
			if(Const.music){
				Const.music = false;
				music.setText(R.string.open_music);
				mPlayer.pause();
			}else {
				Const.music = true;
				music.setText(R.string.close_music);
				mPlayer.start();
			}

			return;
		default:
			break;
		}
		mPlayer.pause();
		MenuActivity.instance.startActivity(intent);

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == event.KEYCODE_BACK){
			mPlayer.stop();
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
		if (Const.music) {
			mPlayer.start();
		}
		Log.e("开始111", "333333");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("开始111", "222222222222");
	}
	
	
}
