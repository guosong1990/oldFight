package com.dongdong.oldfight.view;
/**
 * @author qingsong1990
* ${tags}
*/
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.dongdong.oldfight.EndActivity;
import com.dongdong.oldfight.MainActivity;
import com.dongdong.oldfight.R;
import com.dongdong.oldfight.Entity.Enemy;
import com.dongdong.oldfight.Entity.Fight;
import com.dongdong.oldfight.uitl.Const;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.NetworkInfo.State;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Callback,Runnable{

	private SurfaceHolder sfh;
	private Paint paint;
	private Canvas canvas;
	private boolean flag;
	private Thread thread;
	private int screenW;
	private int screenH;
	private static int time = 60;//控制游戏的刷屏的速度
	public static int interval;//屏幕左右边距离
	private Rect rectL;
	private Rect rectR;
	private Rect fightRect;
	private Context context;
	private Bitmap fightbBitmap;
	private Bitmap enemybBitmap;
	private Bitmap wallbBitmap;

	private Fight fight;
	private int gameW;
	private List<Enemy> enemies = new ArrayList<Enemy>();//保存敌机
	private int count;
	private int fightH;//记录战机的高度
	private int distance;//记录最近一个新增敌机距离屏幕的距离，方便添加下一个敌机
	private int enemyDistance;
	public static int myPoint;
	private Thread endThread;
	private boolean end;
	private int endcount = 20;//战机碰撞后一闪的计数
	private int addCount;
	private long jishiStart;
	private long jishiEnd;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	public GameSurfaceView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		//设置背景常亮
		this.setKeepScreenOn(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screenW = this.getWidth();
		screenH = this.getHeight();
		initGame();
		flag = true;
		//实例线程
		thread = new Thread(this);
		//启动线程
		thread.start();
	}

	@SuppressLint("UseSparseArrays")
	public  void initGame() {
		// TODO Auto-generated method stub
		fightbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fight);
		enemybBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
		wallbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bord);
		interval = screenW/6;
		gameW = screenW - 2*interval;
		fightH = gameW*2/3;
		enemyDistance = screenH/15;
		rectL = new Rect(interval, screenH-gameW*2/3, gameW/2+interval, screenH);//土飞机在左边
		rectR = new Rect(interval+gameW/2, screenH-gameW*2/3, gameW+interval, screenH);//土飞机在右边
		fightRect = rectL;
		fight = new Fight(fightbBitmap, gameW, screenH);
		Enemy.speed = screenH/Enemy.speedBase;
		end = false;
		enemies.clear();
		addCount = Const.addBase;
		jishiStart = System.currentTimeMillis();
		if(Const.music){
			soundPool = new SoundPool(4,AudioManager.STREAM_MUSIC,100);
			soundPoolMap = new HashMap<Integer, Integer>();
			soundPoolMap.put(1, soundPool.load(context, R.raw.pen,1));//碰撞声
			soundPoolMap.put(2, soundPool.load(context, R.raw.endgame, 1));//游戏结束声音
			soundPoolMap.put(3, soundPool.load(context, R.raw.jishiend, 1));//计时游戏结束声音
		}


	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		initGame();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			if(!end){//不是结束执行
				long start = System.currentTimeMillis();
				logic();
				myDraw();
				long end = System.currentTimeMillis();
				if(end-start<time){
					try {
						Thread.sleep(time-(end-start));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{//结束时执行展现战机闪烁的效果
				/*
				 * 在这里记得添加碰撞的效果
				 */

				try {
					if(Const.music){
						MainActivity.mPlayer.pause();
						Thread.sleep(500);
						if(Const.model.equals("jishi")){
							soundPool.play(soundPoolMap.get(3), 1, 1, 0, 0, 1);//播放介绍声音

						}else {
							soundPool.play(soundPoolMap.get(2), 1, 1, 0, 0, 1);//播放介绍声音

						}
					}
					
					Thread.sleep(4000);//暂停一秒钟然后转发
					Intent intent = new Intent(MainActivity.instance, EndActivity.class);
					MainActivity.instance.startActivity(intent);
					flag = false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		}
	}
	
	public void logic(){
		//对屏幕的敌机进行逻辑处理，主要是下移操作
		for (Iterator iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy enemy = (Enemy) iterator.next();
			enemy.logic();
		}
		//采用iterator遍历同时删除会出异常，故采用另一只方式遍历，移除离开屏幕的
		for (int i = 0; i < enemies.size();i++ ) {
			
			if(!enemies.get(i).isLive){//离开屏幕，在链表中移除
				enemies.remove(i);
				myPoint++;//分数增加
			}
		}

		addEnemy();//添加敌机
		
		//加速判断
		addCount--;
		if(addCount==0){
			addCount = Const.addBase;
			Enemy.speed +=screenH/280;
		}
		
		
	}
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas!=null){
				canvas.drawColor(0xFFFFEFD5);
				paint.setColor(0xFFFF7F24);
				//绘制链条边界线
				canvas.drawLine(interval, 0, interval, screenH, paint);
				canvas.drawLine(interval+gameW, 0, interval+gameW, screenH, paint);
				Rect wallL = new Rect(0,0,interval,screenH);
				Rect wallR = new Rect(interval+gameW,0,screenW,screenH);
				canvas.drawBitmap(wallbBitmap, null, wallL, paint);
				canvas.drawBitmap(wallbBitmap, null, wallR, paint);

				for (int i = 0; i < enemies.size();i++ ) {
					Enemy enemy = enemies.get(i);
					enemy.draw(canvas, paint);
				
					//碰撞检测
					if(enemy.enemyRect.bottom>fight.fightreRect.top){
						if(enemy.enemyRect.right == fight.fightreRect.right||enemy.enemyRect.left == fight.fightreRect.left){
							if(Const.model.equals("jishi")){
								enemies.remove(i);
								myPoint-=2;//计时碰撞减掉2分，游戏继续
								soundPool.play(soundPoolMap.get(1), 1, 1, 0, 0, 1);
							}else {
								end = true;//游戏结束
							}
						}
					}
				}
				
				//绘制战机
				fight.draw(canvas, paint);
				//canvas.drawText("speed："+Enemy.speed, 20, 160, paint);//绘制速度
				paint.setTextSize(screenW/15);
				//计时模式的特殊处理，因为多个倒计时
				if(Const.model.equals("jishi")){
					//计时模式
					jishiEnd = System.currentTimeMillis();
					long temp = jishiEnd - jishiStart;
					long remainTime = Const.time-temp/1000;
				/*	canvas.drawText("分数:", 0, screenH/25, paint);
					canvas.drawText(myPoint+"分", 0, 2*screenH/25, paint);
					canvas.drawText("剩余:", interval+gameW, screenH/25, paint);
					canvas.drawText(remainTime+"秒", interval+gameW, 2*screenH/25, paint);*/
					canvas.drawText("分数:"+myPoint+" 剩余:"+remainTime+"秒", interval, screenH/25, paint);
					if(remainTime<=0){
						end = true;
					}	
				}else {
					canvas.drawText("分数:"+myPoint+"分", interval, screenH/25, paint);
					//canvas.drawText(myPoint+"分", 0, 2*screenH/25, paint);
				}
		
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(canvas!=null){
				sfh.unlockCanvasAndPost(canvas);
			}
		}
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		fight.onTouchEvent(event);
		return super.onTouchEvent(event);
		
	}
/*
 * 添加敌机函数
 */
	public void addEnemy(){
		count++;
		distance = Enemy.speed*count;
		if(distance>2*fightH+getRandom(enemyDistance)+screenH/30){//加screenH/20是避免随机值小于screenH/20
			count = 0;
			enemies.add(new Enemy(enemybBitmap, gameW, screenH));
		}
	}
	
	public int getRandom(int base){
		Random random = new  Random();
		return random.nextInt(base);
	}
}
