package com.dongdong.oldfight;

import com.dongdong.oldfight.uitl.Const;

import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class AboutActivity extends Activity implements OnClickListener{

	private TextView noad;
	private TextView shuoming_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
				 WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		setContentView(R.layout.about);
		noad = (TextView) findViewById(R.id.noadw);
		shuoming_content = (TextView) findViewById(R.id.shuoming);
		if(!Const.hasAd){
			shuoming_content.setText("还记得小时候的小霸王游戏机吗，除了俄罗斯还记得土飞机吗？重温童年美好时光，还原童年的游戏，土飞机，一个简单，益智的小游戏，让你反应实现自我挑战，勤俭练习必能成为一代霸主，本游戏可为休闲时玩耍，操作简单，但是十分有趣，让你重温了儿时游戏的同时也能感受到革新，欢迎你的下载。");
			noad.setText("返回游戏");
		}
		noad.setOnClickListener(this);
	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == event.KEYCODE_BACK){
			AboutActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.noadw){
			if(Const.hasAd){
				AppConnect.getInstance(AboutActivity.this).showAppOffers(AboutActivity.this);
			}else {
				AboutActivity.this.finish();
			}
		}
	}

	
}
