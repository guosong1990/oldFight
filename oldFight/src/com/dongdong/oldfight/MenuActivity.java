package com.dongdong.oldfight;

import cn.waps.AppConnect;

import com.dongdong.oldfight.ad.QuitPopAd;
import com.dongdong.oldfight.uitl.Const;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener{

	private TextView normal;
	private TextView jieji;
	private TextView jishi;
	private TextView title;
	public static MenuActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		setContentView(R.layout.menu); 
		instance = this;
		// �Զ��������ļ�������assets/fonts/Ŀ¼�£�w
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/qwjt.TTF");
		title = (TextView) findViewById(R.id.title);
		title.setTypeface(typeFace);
		normal = (TextView) findViewById(R.id.normal);
		jieji = (TextView)findViewById(R.id.jieji);
		jishi = (TextView)findViewById(R.id.jishi);
		normal.setOnClickListener(this);
		jieji.setOnClickListener(this);
		jishi.setOnClickListener(this);
		
		//������
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).initPopAd(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MenuActivity.instance,MainActivity.class);
		switch (v.getId()) {
		case R.id.normal:
			Const.model = "normal";
			Const.addBase = 100;//��ʾÿ6��������һ���ٶ���100*60�������
			break;
		case R.id.jieji:
			Const.model = "jieji";
			Const.addBase = 30;//��ʾÿ3��������һ���ٶ�
			break;
		case R.id.jishi:
			Const.model = "jishi";
			Const.addBase = 100;//��ʾÿ3��������һ���ٶ�
		default:
			break;
		}
		MenuActivity.instance.startActivity(intent);

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == event.KEYCODE_BACK){
			QuitPopAd.getInstance().show(this);
			
		}
		return true;
	}
	
	
}
