package com.dongdong.oldfight;
/**
 * @author qingsong1990
* ${tags}
*/
import java.io.IOException;

import com.dongdong.oldfight.uitl.Const;
import com.dongdong.oldfight.view.GameSurfaceView;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	public static MainActivity instance;
	
	public static int fuhuoCount = 3;//¸´»î´ÎÊý
	public static MediaPlayer mPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
				 WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		setContentView(new GameSurfaceView(this)); 
		instance = this;
		if(Const.music){
			initGameMusic();
		}
		
	}
	public void initGameMusic(){

		try {
			if(Const.model.equals("normal")){
				mPlayer = MediaPlayer.create(this, R.raw.gamebg_music);
			}
			
			if(Const.model.equals("jieji")){
				mPlayer = MediaPlayer.create(this, R.raw.gamebg_music2);
			}
			if(Const.model.equals("jishi")){
				mPlayer = MediaPlayer.create(this, R.raw.gamebg_music3);
			}
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==event.KEYCODE_BACK){
			GameSurfaceView.myPoint = 0;
		}
		if(Const.music){
			mPlayer.pause();

		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Const.music){
			mPlayer.start();

		}
	}
	

}
