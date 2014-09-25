package com.dongdong.oldfight;

import com.dongdong.oldfight.view.GameSurfaceView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener{

	private TextView normal;
	public static MenuActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		setContentView(R.layout.menu); 
		instance = this;
		normal = (TextView) findViewById(R.id.normal);
		normal.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.normal:
			Intent intent = new Intent(MenuActivity.instance,MainActivity.class);
			MenuActivity.instance.startActivity(intent);
			break;

		default:
			break;
		}
	}
	
}
