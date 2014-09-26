package com.dongdong.oldfight.Entity;

import java.util.Random;

import com.dongdong.oldfight.view.GameSurfaceView;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Enemy {
	private Bitmap enemy;
	public Rect enemyRect;
	private Rect rectL;
	private Rect rectR;
	private int rl;
	public static int speed;//敌机的共同速度，用屏幕的高度来计算，直接赋值会出现不同屏幕像素的手机敌机速度差异很大。
	public static int speedBase = 30;//速度基数
	public boolean isLive; //判断敌机是否离开了屏幕如果是则在移除它
	private int screenH;
	public Enemy(Bitmap enemy,int gameW,int screenH){
		this.enemy = enemy;
		this.screenH = screenH;
		isLive = true;
		rectL = new Rect(GameSurfaceView.interval,-gameW*2/3+100,GameSurfaceView.interval+gameW/2,100);
		rectR = new Rect(GameSurfaceView.interval+gameW/2,-gameW*2/3+100,GameSurfaceView.interval+gameW,100);
		rl = RL(); 
		if(rl==0){
			enemyRect = rectL;
		}else {
			enemyRect = rectR;
		}
	}
	/*
	 * 绘制敌机
	 * 
	 */
	public void draw(Canvas canvas,Paint paint){
		canvas.drawBitmap(enemy, null, enemyRect, paint);
	}
	
	/*
	 * 游戏逻辑
	 */
	public void logic(){
		enemyRect.offset(0, speed);//下降
		//判断是否离开了屏幕
		if(enemyRect.top>screenH){
			isLive = false;
		}
	}
	/*
	 * 产生数字1或0表示从左还是右出现
	 */
	public int RL(){
		Random random = new Random();
		return random.nextInt(2);
	}
}
