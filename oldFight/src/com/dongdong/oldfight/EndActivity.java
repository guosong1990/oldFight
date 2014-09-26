package com.dongdong.oldfight;

import com.dongdong.oldfight.uitl.Const;
import com.dongdong.oldfight.view.EndView;
import com.dongdong.oldfight.view.GameSurfaceView;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class EndActivity extends Activity{

	public static int maxPoints;
	public static EndActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(new EndView(this));
		doPoints();
		instance = this;
	}
	
	/*
	 * 对分数进行处理，储蓄最大的数
	 */
	public void doPoints(){
		SharedPreferences sharedPreferences = getSharedPreferences("oldFightNum", 0);
		maxPoints = sharedPreferences.getInt(Const.model, 0);
		if(GameSurfaceView.myPoint>maxPoints){
			Editor editor = sharedPreferences.edit();
			maxPoints = GameSurfaceView.myPoint;
			editor.putInt(Const.model, maxPoints);
			editor.commit();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == event.KEYCODE_BACK){
			GameSurfaceView.myPoint = 0;
			finish();
			MainActivity.instance.finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
