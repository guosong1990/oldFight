package com.dongdong.oldfight.view;

import com.dongdong.oldfight.R;
import com.dongdong.oldfight.Entity.Enemy;
import com.dongdong.oldfight.Entity.Fight;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	public static final int INTERVAL  = 80;//屏幕左右边距离
	private Rect rectL;
	private Rect rectR;
	private Rect fightRect;
	private Context context;
	private Bitmap fightbBitmap;
	private Bitmap enemybBitmap;
	private Enemy enemy;
	private Fight fight;
	private int gameW;
	public GameSurfaceView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
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

	public  void initGame() {
		// TODO Auto-generated method stub
		fightbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fight);
		enemybBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
		gameW = screenW - 2*INTERVAL;
		rectL = new Rect(INTERVAL, screenH-gameW*2/3, gameW/2+INTERVAL, screenH);//土飞机在左边
		rectR = new Rect(INTERVAL+gameW/2, screenH-gameW*2/3, gameW+INTERVAL, screenH);//土飞机在右边
		fightRect = rectL;
		enemy = new Enemy(enemybBitmap, gameW);
		fight = new Fight(fightbBitmap, gameW, screenH);
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
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			if(end-start<time){
				try {
					Thread.sleep(time-(end-start));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void logic(){
		enemy.logic();
	}
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas!=null){
				canvas.drawColor(Color.WHITE);
				//canvas.drawBitmap(fight, 100, 100, paint);
				paint.setColor(Color.BLACK);
				//绘制链条边界线
				canvas.drawLine(INTERVAL, 0, INTERVAL, screenH, paint);
				canvas.drawLine(INTERVAL+gameW, 0, INTERVAL+gameW, screenH, paint);
				//canvas.drawBitmap(fightbBitmap, null, fightRect, paint);
				
				enemy.draw(canvas, paint);
				fight.draw(canvas, paint);
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

	
}
