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
	public static int speed;//�л��Ĺ�ͬ�ٶȣ�����Ļ�ĸ߶������㣬ֱ�Ӹ�ֵ����ֲ�ͬ��Ļ���ص��ֻ��л��ٶȲ���ܴ�
	public static int speedBase = 30;//�ٶȻ���
	public boolean isLive; //�жϵл��Ƿ��뿪����Ļ����������Ƴ���
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
		enemyRect.offset(0, speed);//�½�
		//�ж��Ƿ��뿪����Ļ
		if(enemyRect.top>screenH){
			isLive = false;
		}
	}
	/*
	 * ��������1��0��ʾ�������ҳ���
	 */
	public int RL(){
		Random random = new Random();
		return random.nextInt(2);
	}
}
