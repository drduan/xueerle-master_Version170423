package com.neusoft.sample.View.xel_specialtopic.xel_math_shudu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ncapdevi.sample.R;

public class PuzzleView_6 extends View {
	private final Rect selRect=new Rect();
	private Game_6 new_game;
	private int i,j,m=0;
	private float width,height;
	private int selX,selY;
	public String puzzle[][] = new String[][]{
			{"3","6","1","2","5","4"},
			{"5","2","4"," "," ","6"},
			{"1","5","2"," ","4","3"},
			{"4","3","6","1","2","5"},
			{"2","4","3","5"," "," "},
			{"6","1","5","4"," ","2"}

	};


	public PuzzleView_6(Context context, int diff) {
		// TODO Auto-generated constructor stub
		super(context);

		new_game=(Game_6)context;
		setFocusable(true);//允许键盘事件为true   会影响该事件
		setFocusableInTouchMode(true);//允许触屏为true会影响该事件

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		width=w/6f;
		height=h/6f;
		getRect(selX,selY,selRect);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		Paint backgroundpaint=new Paint();
		backgroundpaint.setColor(getResources().getColor(R.color.game_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundpaint);

		Paint dark = new Paint();
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		Paint light = new Paint();
		light.setColor(getResources().getColor(R.color.puzzle_light));
		Paint hilight = new Paint();
		hilight.setColor(getResources().getColor(R.color.puzzle_hilite));
		Paint line = new Paint();
		line.setColor(getResources().getColor(R.color.line));

		for(i=0;i<=6;i++){
			canvas.drawLine(0, i*height, getWidth(),i*height, light);

			canvas.drawLine(i*width, 0, i*width,getHeight(), light);

		}
		for(i=0;i<=6;i=i+3){

			canvas.drawLine(i*width, 0,i*width,getHeight(), line);
			canvas.drawLine(i*width+1,0,i*width+1,getHeight(),line);

		}
		for(i=0;i<=6; i=i+2){
			canvas.drawLine(0, i*height,getWidth(),i*height,line);
			canvas.drawLine(0, i*height+1, getWidth(),i*height+1,line);
		}

		Paint frontPaint=new Paint();
		frontPaint.setColor(Color.WHITE);
		frontPaint.setTextSize(90);
		for(i=0;i<6;i++){
			for(j=1;j<=6;j++){
				canvas.drawText(puzzle[j-1][i], i*width+(width*0.3f), j*height-(height*0.3f),frontPaint);
			}
		}

		Paint selPaint=new Paint();
		selPaint.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect,selPaint);

	}


	private void getRect(float selX,float selY,Rect r){
		r.set((int)(selX*width+1), (int)(selY*height+1), (int)(selX*width+width-1), (int)(selY*height+height-1));
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(event.getAction()!=MotionEvent.ACTION_DOWN){
			return super.onTouchEvent(event);
		}

		int x=(int)(event.getX()/width);
		int y=(int)(event.getY()/height);
		selectXY(x,y);
		new_game.showKeyPad();
		return false;
	}
	public void selectXY(int x,int y){
		invalidate(selRect);
		if(y==-1) {selY=5;}
		else if(y==6) {selY=0;}
		else selY=y;

		if(x==-1) {selX=5;
		}else if(x==6){selX=0;
		}else selX=x;

		getRect(selX,selY,selRect);

		invalidate(selRect);
	}

	public void setSelectTile(String d)
	{
		int row,col;
		int finishflag = 0;
		String info = "";
Log.d("ROW_x",selX+"");
		Log.d("ROW_y",selY+"");
		for(row = 0;row<6;row++)
		{

			if(puzzle[selY][row].equals(d))
			{
				info = "同一行有相同数字";
                  break;
			}
		}

		for(col = 0;col<6;col++)
		{


			if(puzzle[col][selX].equals(d))
			{
				info = "同一列有相同数字";
                  break;
			}
		}

		int x=selX/3;
		int Y=selY/2;


		for(row = Y*2;row<Y*2+2;row++)
			for(col = x*3;col<x*3+3;col++)
			{

				Log.d("sRow","puzzle[row][col]："+puzzle[row][col]+"匹配的"+d+"当前所属行"+row+"当前所属列"+col);
				if(puzzle[row][col].equals(d))
				{
					info = "所在的六宫格有相同的数字";
					break;
				}

			}
		if(info.equals(""))
		{
			if(d.equals("0"))
			d ="";
			puzzle[selY][selX] = d;
			invalidate(selRect);
		}
		else
		{
		Toast.makeText(this.new_game.getApplicationContext(), info, Toast.LENGTH_SHORT).show();
		}


		for(col=0;col<6;col++)
			for(row=0;row<6;row++)
				if(puzzle[col][row].equals(" "))
				{
					finishflag=1;
					info="所有六宫格内有未填写的数字";break;
				}
		if(finishflag==0)
		{
			info="恭喜您通过游戏！";
			Toast.makeText(this.new_game.getApplicationContext(), info, Toast.LENGTH_SHORT).show();
		}
		else info="请继续游戏";
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_UP:
			selectXY(selX,selY-1);
			break;

		default:
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

}
