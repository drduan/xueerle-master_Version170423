package com.neusoft.sample.View.xel_specialtopic.xel_math_shudu;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.BaseActivity;

public class Game_9 extends BaseActivity {
	protected PuzzleView_9 puzzleview_9;
	public String continueString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		continueString=getPreferences(MODE_PRIVATE).getString("puzzle", "");
		int diff=getIntent().getIntExtra("difficulty", 0);

		puzzleview_9=new PuzzleView_9(this,0);
		setContentView(puzzleview_9);
		Toolbar toolbar=new Toolbar(this.getApplicationContext());
		toolbar.setNavigationIcon(R.drawable.back);
		toolbar.setTitle("数组");
		puzzleview_9.requestFocus();
	}
	
	public void showKeyPad(){
		Keypad_9 keypad9 =new Keypad_9(this, puzzleview_9);
		keypad9.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		continueString=getPreferences(MODE_PRIVATE).getString("puzzle", "");
		//music.paly(this, R.raw.yue);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		music.stop(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getPreferences(MODE_PRIVATE).edit().putString("puzzle", arraytoString()).commit();
		music.stop(this);
	}

	private String arraytoString(){
		String s1="";
		int j,k;
		for(j=0;j<4;j++)
			for(k=0;k<4;k++){
				s1+=puzzleview_9.puzzle[j][k];
			}
		return s1;
	}



	public static boolean getBackMusic(Context context){
		return	PreferenceManager.getDefaultSharedPreferences(context).getBoolean("music",true);
	}
	public static boolean getSoundSet(Context context){
		return	PreferenceManager.getDefaultSharedPreferences(context).getBoolean("sound",true);
	}

}
