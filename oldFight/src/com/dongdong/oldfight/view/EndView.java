package com.dongdong.oldfight.view;

import com.dongdong.oldfight.EndActivity;
import com.dongdong.oldfight.MainActivity;
import com.dongdong.oldfight.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class EndView extends View {
	private Paint paint;
	private int screenW;
	private int screenH;
	public EndView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(0xFFFFEFD5);
		//不能放到构造函数里，为什么？
		screenW = this.getWidth();
		screenH = this.getHeight();
		paint.setTextSize(screenW/7);
		paint.setColor(0xFFFF7F24);
		canvas.drawText("挑战结束", screenW/2-2*screenW/7, screenH/4, paint);
		paint.setTextSize(screenW/15);
		canvas.drawText("得分："+GameSurfaceView.myPoint, screenW/2-2*screenW/12, screenH/4+screenH/7, paint);
		canvas.drawText("最高分："+EndActivity.maxPoints, screenW/2-2*screenW/12, screenH/4+screenH/4, paint);
		
		paint.setTextSize(screenW/13);
		
		
/*		Rect rect1 = new Rect(screenW/2-screenW/13-screenW/4,screenH/4+screenH/3+screenH/15-screenW/13,screenW/2-screenW/13-screenW/4+2*screenW/13,screenH/4+screenH/3+screenH/15+10);
		Rect rect2 = new Rect(screenW/2-screenW/13,screenH/4+screenH/3+screenH/15-screenW/13,screenW/2-screenW/13+2*screenW/13,screenH/4+screenH/3+screenH/15+10);
		Rect rect3 = new Rect(screenW/2-screenW/13+screenW/4,screenH/4+screenH/3+screenH/15-screenW/13,screenW/2-screenW/13+screenW/4+2*screenW/13,screenH/4+screenH/3+screenH/15+10);
		canvas.drawRect(rect1, paint);
		canvas.drawRect(rect2, paint);
		canvas.drawRect(rect3, paint);*/

		canvas.drawText("返回", screenW/2-screenW/13-screenW/4, screenH/4+screenH/3+screenH/15, paint);
		canvas.drawText("复活", screenW/2-screenW/13, screenH/4+screenH/3+screenH/15, paint);
		canvas.drawText("重来", screenW/2-screenW/13+screenW/4, screenH/4+screenH/3+screenH/15, paint);
		//canvas.drawText("挑战失败", 100, 100, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		//点击了返回 有点复杂了，注意drawText中的文字的Y坐标
		if(x>screenW/2-screenW/13-screenW/4&&y>screenH/4+screenH/3+screenH/15-screenW/13&&x<screenW/2-screenW/13-screenW/4+2*screenW/13&&y<screenH/4+screenH/3+screenH/15+10){
			GameSurfaceView.myPoint = 0;
			EndActivity.instance.finish();
			MainActivity.instance.finish();
			/*Intent intent = new Intent(EndActivity.instance,MenuActivity.class);
			EndActivity.instance.startActivity(intent);*/
			
		}
		
		//点击了继续 有点复杂了，注意drawText中的文字的Y坐标
		if(x>screenW/2-screenW/13&&y>screenH/4+screenH/3+screenH/15-screenW/13&&x<screenW/2-screenW/13+2*screenW/13&&y<screenH/4+screenH/3+screenH/15+10){
			EndActivity.instance.finish();
			
		}
		//点击了重来 有点复杂了，注意drawText中的文字的Y坐标
		if(x>screenW/2-screenW/13+screenW/4&&y>screenH/4+screenH/3+screenH/15-screenW/13&&x<screenW/2-screenW/13+screenW/4+2*screenW/13&&y<screenH/4+screenH/3+screenH/15+10){
			EndActivity.instance.finish();
			GameSurfaceView.myPoint = 0;
		}
	
		
		return super.onTouchEvent(event);
	}

	
	
}
