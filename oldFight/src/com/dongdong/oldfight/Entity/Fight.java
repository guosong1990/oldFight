package com.dongdong.oldfight.Entity;

import com.dongdong.oldfight.view.GameSurfaceView;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Fight {
	private Rect rectL;
	private Rect rectR;
	public Rect fightreRect; //public����Ϊ����ײ���ʱҪ��
	private Bitmap fightBitmap;
	private int gameW;
	private int screenH;
	public Fight(Bitmap fightBitmap, int gameW, int screenH){
		this.fightBitmap = fightBitmap;
		this.gameW = gameW;
		this.screenH = screenH;
		rectL = new Rect(GameSurfaceView.interval, screenH-gameW*2/3, gameW/2+GameSurfaceView.interval, screenH);//���ɻ������
		rectR = new Rect(GameSurfaceView.interval+gameW/2, screenH-gameW*2/3, gameW+GameSurfaceView.interval, screenH);//���ɻ����ұ�
		fightreRect = rectL;//Ĭ�������
	}
	
	public void draw(Canvas canvas,Paint paint){
		canvas.drawBitmap(fightBitmap, null, fightreRect, paint);
	}
	
	public void onTouchEvent(MotionEvent event){
		float x = event.getX();
		if(x<(gameW/2+GameSurfaceView.interval)){
			fightreRect = rectL;
		}else {
			fightreRect = rectR;
		}
	}
}
