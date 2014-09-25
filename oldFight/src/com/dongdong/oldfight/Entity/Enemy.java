package com.dongdong.oldfight.Entity;

import java.util.Random;

import com.dongdong.oldfight.view.GameSurfaceView;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy {
	private Bitmap enemy;
	private Rect enemyRect;
	private Rect rectL;
	private Rect rectR;
	private int rl;
	public Enemy(Bitmap enemy,int gameW){
		this.enemy = enemy;
		rectL = new Rect(GameSurfaceView.INTERVAL,-gameW*2/3+100,GameSurfaceView.INTERVAL+gameW/2,100);
		rectR = new Rect(GameSurfaceView.INTERVAL+gameW/2,-gameW*2/3+100,GameSurfaceView.INTERVAL+gameW,100);
		rl = RL();
		if(rl==0){
			enemyRect = rectL;
		}else {
			enemyRect = rectR;
		}
	}
	/*
	 * ���Ƶл�
	 * 
	 */
	public void draw(Canvas canvas,Paint paint){
		canvas.drawBitmap(enemy, null, enemyRect, paint);
	}
	
	/*
	 * ��Ϸ�߼�
	 */
	public void logic(){
		//this.enemyRect.inset(0, -5);
		enemyRect.offset(0, 5);
	}
	/*
	 * ��������1��0��ʾ�������ҳ���
	 */
	public int RL(){
		Random random = new Random();
		return random.nextInt(2);
	}
}
