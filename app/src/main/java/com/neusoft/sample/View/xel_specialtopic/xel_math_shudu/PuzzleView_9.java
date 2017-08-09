package com.neusoft.sample.View.xel_specialtopic.xel_math_shudu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ncapdevi.sample.R;

public class PuzzleView_9 extends View {
	private final Rect selRect=new Rect();
	private Game_9 new_game;
	private int i,j,m=0;
	private float width,height;
	private int selX,selY;
	public String[][]  puzzle = new String[9][9];
	private String easy[][] = new String[][]{
			{"1","4","7","6"," ","3","5","9","2"},
			{"8","2","3","9","5","1","4","6","7"},
			{"5","9","6","4","2","7","8","1","3"},
			{" ","8","2","5","6","9","7","3","1"},
			{" ","3","1","7","4","8","9","2","5"},
			{" ","7","5","3","1","2","6","4","8"},
			{"2","1","4","8","7","6","3","5","9"},
			{"7","5","9","2","3","4","1","8"," "},
			{" ","6","8","1","9","5","2","7","4"},
	};
	private String medium[][] = new String[][]{
			{"6","5"," "," "," "," "," ","7"," "},
			{" "," "," ","5"," ","6"," "," "," "},
			{" ","1","4"," "," "," "," "," ","5"},
			{" ","7"," ","4","6"," "," "," ","3"},
			{" "," ","2","3","1","4","7"," "," "},
			{" "," "," ","7"," "," ","8"," "," "},
			{"5"," "," "," "," "," ","6","3"," "},
			{" ","9"," ","3"," ","1"," ","8"," "},
			{" "," "," "," "," "," ","6"," "," "},
	};
	private String hard[][] = new String[][]{
			{"1","2","3","4","5","6","7","8","9"},
			{" "," "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "," "},
	};

	public PuzzleView_9(Context context, int diff) {
		// TODO Auto-generated constructor stub
		super(context);

		new_game=(Game_9)context;
		setFocusable(true);//允许键盘事件为true   会影响该事件
		setFocusableInTouchMode(true);//允许触屏为true会影响该事件
		for(i=0;i<9;i++)
			for(j=0;j<9;j++)
			{
				switch(diff)
				{
				case 0:
					puzzle[i][j] = easy[i][j];
					break;
				case 1:
					puzzle[i][j] = medium[i][j];
					break;
				case 2:
					puzzle[i][j] = hard[i][j];
					break;
				case -1:
					puzzle[i][j] = new_game.continueString.substring(m, m+1);
					System.out.print(puzzle[i][j]);
					m++;
					break;
				}
			}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		width=w/9f;
		height=h/9f;
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

		for(i=0;i<=9;i++){
			canvas.drawLine(0, i*height, getWidth(),i*height, light);

			canvas.drawLine(i*width, 0, i*width,getHeight(), light);

		}
		for(i=0;i<=9;i=i+3){
			canvas.drawLine(0, i*height,getWidth(),i*height,line);
			canvas.drawLine(0, i*height+1, getWidth(),i*height+1,line);
			canvas.drawLine(i*width, 0,i*width,getHeight(), line);
			canvas.drawLine(i*width+1,0,i*width+1,getHeight(),line);
		}

		Paint frontPaint=new Paint();
		frontPaint.setColor(Color.WHITE);
		frontPaint.setTextSize(90);
		for(i=0;i<9;i++){
			for(j=1;j<=9;j++){
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
		if(y==-1) {selY=8;}
		else if(y==9) {selY=0;}
		else selY=y;

		if(x==-1) {selX=8;
		}else if(x==9){selX=0;
		}else selX=x;

		getRect(selX,selY,selRect);

		invalidate(selRect);
	}

	public void setSelectTile(String d)
	{
		int row,col;
		int finishflag = 0;
		String info = "";

		for(row = 0;row<9;row++)
		{
			if(puzzle[row][selX].equals(d))
			{
				info = "同一列有相同数字";
                  break;
			}
		}

		for(col = 0;col<9;col++)
		{
			if(puzzle[selY][col].equals(d))
			{
				info = "同一行有相同数字";
                  break;
			}
		}

		int x=selY/3;
		int Y=selX/3;
		for(col = x*3;col<x*3+3;col++)
			for(row = Y*3;row<Y*3+3;row++)
			{
				if(puzzle[col][row].equals(d))
				{
					info = "所在的九宫格有相同的数字";
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


		for(col=0;col<9;col++)
			for(row=0;row<9;row++)
				if(puzzle[col][row].equals(" "))
				{
					finishflag=1;
					info="九宫格内有未填写的数字";break;
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
