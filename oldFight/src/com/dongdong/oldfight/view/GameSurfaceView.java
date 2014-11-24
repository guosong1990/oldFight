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
	private static int time = 60;//������Ϸ��ˢ�����ٶ�
	public static int interval;//��Ļ���ұ߾���
	private Rect rectL;
	private Rect rectR;
	private Rect fightRect;
	private Context context;
	private Bitmap fightbBitmap;
	private Bitmap enemybBitmap;
	private Bitmap wallbBitmap;

	private Fight fight;
	private int gameW;
	private List<Enemy> enemies = new ArrayList<Enemy>();//����л�
	private int count;
	private int fightH;//��¼ս���ĸ߶�
	private int distance;//��¼���һ�������л�������Ļ�ľ��룬���������һ���л�
	private int enemyDistance;
	public static int myPoint;
	private Thread endThread;
	private boolean end;
	private int endcount = 20;//ս����ײ��һ���ļ���
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
		//���ñ�������
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
		//ʵ���߳�
		thread = new Thread(this);
		//�����߳�
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
		rectL = new Rect(interval, screenH-gameW*2/3, gameW/2+interval, screenH);//���ɻ������
		rectR = new Rect(interval+gameW/2, screenH-gameW*2/3, gameW+interval, screenH);//���ɻ����ұ�
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
			soundPoolMap.put(1, soundPool.load(context, R.raw.pen,1));//��ײ��
			soundPoolMap.put(2, soundPool.load(context, R.raw.endgame, 1));//��Ϸ��������
			soundPoolMap.put(3, soundPool.load(context, R.raw.jishiend, 1));//��ʱ��Ϸ��������
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
			if(!end){//���ǽ���ִ��
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
			}else{//����ʱִ��չ��ս����˸��Ч��
				/*
				 * ������ǵ������ײ��Ч��
				 */

				try {
					if(Const.music){
						MainActivity.mPlayer.pause();
						Thread.sleep(500);
						if(Const.model.equals("jishi")){
							soundPool.play(soundPoolMap.get(3), 1, 1, 0, 0, 1);//���Ž�������

						}else {
							soundPool.play(soundPoolMap.get(2), 1, 1, 0, 0, 1);//���Ž�������

						}
					}
					
					Thread.sleep(4000);//��ͣһ����Ȼ��ת��
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
		//����Ļ�ĵл������߼�������Ҫ�����Ʋ���
		for (Iterator iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy enemy = (Enemy) iterator.next();
			enemy.logic();
		}
		//����iterator����ͬʱɾ������쳣���ʲ�����һֻ��ʽ�������Ƴ��뿪��Ļ��
		for (int i = 0; i < enemies.size();i++ ) {
			
			if(!enemies.get(i).isLive){//�뿪��Ļ�����������Ƴ�
				enemies.remove(i);
				myPoint++;//��������
			}
		}

		addEnemy();//��ӵл�
		
		//�����ж�
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
				//���������߽���
				canvas.drawLine(interval, 0, interval, screenH, paint);
				canvas.drawLine(interval+gameW, 0, interval+gameW, screenH, paint);
				Rect wallL = new Rect(0,0,interval,screenH);
				Rect wallR = new Rect(interval+gameW,0,screenW,screenH);
				canvas.drawBitmap(wallbBitmap, null, wallL, paint);
				canvas.drawBitmap(wallbBitmap, null, wallR, paint);

				for (int i = 0; i < enemies.size();i++ ) {
					Enemy enemy = enemies.get(i);
					enemy.draw(canvas, paint);
				
					//��ײ���
					if(enemy.enemyRect.bottom>fight.fightreRect.top){
						if(enemy.enemyRect.right == fight.fightreRect.right||enemy.enemyRect.left == fight.fightreRect.left){
							if(Const.model.equals("jishi")){
								enemies.remove(i);
								myPoint-=2;//��ʱ��ײ����2�֣���Ϸ����
								soundPool.play(soundPoolMap.get(1), 1, 1, 0, 0, 1);
							}else {
								end = true;//��Ϸ����
							}
						}
					}
				}
				
				//����ս��
				fight.draw(canvas, paint);
				//canvas.drawText("speed��"+Enemy.speed, 20, 160, paint);//�����ٶ�
				paint.setTextSize(screenW/15);
				//��ʱģʽ�����⴦����Ϊ�������ʱ
				if(Const.model.equals("jishi")){
					//��ʱģʽ
					jishiEnd = System.currentTimeMillis();
					long temp = jishiEnd - jishiStart;
					long remainTime = Const.time-temp/1000;
				/*	canvas.drawText("����:", 0, screenH/25, paint);
					canvas.drawText(myPoint+"��", 0, 2*screenH/25, paint);
					canvas.drawText("ʣ��:", interval+gameW, screenH/25, paint);
					canvas.drawText(remainTime+"��", interval+gameW, 2*screenH/25, paint);*/
					canvas.drawText("����:"+myPoint+" ʣ��:"+remainTime+"��", interval, screenH/25, paint);
					if(remainTime<=0){
						end = true;
					}	
				}else {
					canvas.drawText("����:"+myPoint+"��", interval, screenH/25, paint);
					//canvas.drawText(myPoint+"��", 0, 2*screenH/25, paint);
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
 * ��ӵл�����
 */
	public void addEnemy(){
		count++;
		distance = Enemy.speed*count;
		if(distance>2*fightH+getRandom(enemyDistance)+screenH/30){//��screenH/20�Ǳ������ֵС��screenH/20
			count = 0;
			enemies.add(new Enemy(enemybBitmap, gameW, screenH));
		}
	}
	
	public int getRandom(int base){
		Random random = new  Random();
		return random.nextInt(base);
	}
}
