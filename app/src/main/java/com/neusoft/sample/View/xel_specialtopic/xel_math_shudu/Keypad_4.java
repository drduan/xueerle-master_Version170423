package com.neusoft.sample.View.xel_specialtopic.xel_math_shudu;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ncapdevi.sample.R;


public class Keypad_4 extends Dialog implements View.OnClickListener  {

	private PuzzleView_4 fatherView;
	private Button[] key=new Button[4];

	public Keypad_4(Context context, PuzzleView_4 father) {
		super(context);
		fatherView=father;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("选择正确的答案");
		setContentView(R.layout.xel_specialtopic_math_shudu_4);
		findButtonId();
		for(int i=0;i<4;i++){
			key[i].setOnClickListener(this);
		}

	}
	
	public void findButtonId(){
		key[0]=(Button)findViewById(R.id.keypad_4_1);
		key[1]=(Button)findViewById(R.id.keypad_4_2);
		key[2]=(Button)findViewById(R.id.keypad_4_3);
		key[3]=(Button)findViewById(R.id.keypad_4_4);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.keypad_4_1:
			fatherView.setSelectTile("1");break;
		case R.id.keypad_4_2:
			fatherView.setSelectTile("2");break;
		case R.id.keypad_4_3:
			fatherView.setSelectTile("3");break;
		case R.id.keypad_4_4:
			fatherView.setSelectTile("4");break;
			default:
				fatherView.setSelectTile(" ");break;


		}
		dismiss();
	}

}
