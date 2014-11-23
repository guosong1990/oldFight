package com.dongdong.oldfight;
/**
 * @author qingsong1990
* ${tags}
*/
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class EndActivity extends Activity implements UpdatePointsNotifier{

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
		
		if(Const.myJifen<20){
			/*互动广告*/		
			LinearLayout adlayout = new LinearLayout(this);
			adlayout.setGravity(Gravity.CENTER_HORIZONTAL);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			AppConnect.getInstance(this).showBannerAd(this, adlayout);
			layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM);//此代码可设置顶端或低端
			this.addContentView(adlayout, layoutParams);
			
			AppConnect.getInstance(this).showPopAd(this);
		}

		
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
			MainActivity.fuhuoCount = 3;
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see cn.waps.UpdatePointsNotifier#getUpdatePoints(java.lang.String, int)
	 */
	@Override
	public void getUpdatePoints(String arg0, int arg1) {
		// TODO Auto-generated method stub
		Const.myJifen = arg1;
		Log.e("我的分数", arg0+"");
	}

	/* (non-Javadoc)
	 * @see cn.waps.UpdatePointsNotifier#getUpdatePointsFailed(java.lang.String)
	 */
	@Override
	public void getUpdatePointsFailed(String arg0) {
		// TODO Auto-generated method stub
		
	}	
	
}
