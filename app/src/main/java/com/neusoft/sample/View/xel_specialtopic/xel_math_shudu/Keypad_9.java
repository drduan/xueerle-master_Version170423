package com.neusoft.sample.View.xel_specialtopic.xel_math_shudu;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import com.ncapdevi.sample.R;


public class Keypad_9 extends Dialog implements View.OnClickListener {
	
	private PuzzleView_9 fatherView;
	private Button[] key=new Button[9];

	public Keypad_9(Context context, PuzzleView_9 father) {
		super(context);
		fatherView=father;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("选择正确的答案");
		setContentView(R.layout.xel_specialtopic_math_shudu_9);
		findButtonId();
		for(int i=0;i<9;i++){
			key[i].setOnClickListener(this);
		}

	}
	
	public void findButtonId(){
		key[0]=(Button)findViewById(R.id.keypad_1);
		key[1]=(Button)findViewById(R.id.keypad_2);
		key[2]=(Button)findViewById(R.id.keypad_3);
		key[3]=(Button)findViewById(R.id.keypad_4);
		key[4]=(Button)findViewById(R.id.keypad_5);
		key[5]=(Button)findViewById(R.id.keypad_6);
		key[6]=(Button)findViewById(R.id.keypad_7);
		key[7]=(Button)findViewById(R.id.keypad_8);
		key[8]=(Button)findViewById(R.id.keypad_9);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.keypad_1:
			fatherView.setSelectTile("1");break;
		case R.id.keypad_2:
			fatherView.setSelectTile("2");break;
		case R.id.keypad_3:
			fatherView.setSelectTile("3");break;
		case R.id.keypad_4:
			fatherView.setSelectTile("4");break;
		case R.id.keypad_5:
			fatherView.setSelectTile("5");break;
		case R.id.keypad_6:
			fatherView.setSelectTile("6");break;
		case R.id.keypad_7:
			fatherView.setSelectTile("7");break;
		case R.id.keypad_8:
			fatherView.setSelectTile("8");break;
		case R.id.keypad_9:
			fatherView.setSelectTile("9");break;
		default:
			fatherView.setSelectTile(" ");break;			
		}
		dismiss();
	}

}
