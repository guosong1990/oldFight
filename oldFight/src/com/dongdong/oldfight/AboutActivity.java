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
			shuoming_content.setText("���ǵ�Сʱ���С������Ϸ���𣬳��˶���˹���ǵ����ɻ�������ͯ������ʱ�⣬��ԭͯ�����Ϸ�����ɻ���һ���򵥣����ǵ�С��Ϸ�����㷴Ӧʵ��������ս���ڼ���ϰ���ܳ�Ϊһ������������Ϸ��Ϊ����ʱ��ˣ�������򵥣�����ʮ����Ȥ�����������˶�ʱ��Ϸ��ͬʱҲ�ܸ��ܵ����£���ӭ������ء�");
			noad.setText("������Ϸ");
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
