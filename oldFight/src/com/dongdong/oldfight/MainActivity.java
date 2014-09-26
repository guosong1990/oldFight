package com.dongdong.oldfight;
/**
 * @author qingsong1990
* ${tags}
*/
import com.dongdong.oldfight.view.GameSurfaceView;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.os.Build;

public class MainActivity extends Activity {
	public static MainActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
				 WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		setContentView(new GameSurfaceView(this)); 
		instance = this;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==event.KEYCODE_BACK){
			GameSurfaceView.myPoint = 0;
		}
		return super.onKeyDown(keyCode, event);
	}


}
