package com.neusoft.sample.View.xel_course.miaohong;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.yangkangkang.NewWords;
import com.neusoft.sample.Ctrl.yangkangkang.ShengZiGuShi;
import com.neusoft.sample.Ctrl.yangkangkang.gridview_bean;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.Model.json_http;
import com.neusoft.sample.Model.mdata.BihuaPoint;
import com.neusoft.sample.Model.mdata.OutlinePoint;
import com.neusoft.sample.Model.mmode.HanZiModel;
import com.neusoft.sample.Model.mutils.ToastUtil;
import com.neusoft.sample.View.Adapter.yangka.GridviewAdapter;
import com.neusoft.sample.View.Mp3.MusicPlayService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HanZiShowActivity extends xel_course_learn_word_ implements View.OnClickListener, AdapterView.OnItemClickListener {
	private MusicPlayService mService;
	private App application;
	String shengzi_url= "";
	Handler handlerll;
	String tt1 = "";
	int get_gushici =  0;
	static  String fontPath = "ttf.ttf";
	private static String TAG = HanZiShowActivity.class.getSimpleName();
	private GridviewAdapter Adapter;
	TextView tv1,tv2,tv3;
	Cursor cursor;
	String zi = "0";
	String get_shici = "";
	int indext=0;
	int aaa_ = 0;
	int aa = 0;
	int qihaun =100;
	ShengZiGuShi shengzi ;
	 private List<NewWords> accept;
	List<gridview_bean> list_gridview;
	private List<Map<String,Object>> bmob_List;
	 private HanZiModel hanZiModel;
	// private int id;
	 private int index=0;
	//要显示的汉字
	private String HanZi,outline_path,fill_path,voice;
	String uri = "";
	private final static int RESULT_HANZIOUTLINE_CODE=0X001;
	private final static int RESULT_HANZIFILL_CODE=0X002;
	private OutlinePoint outlinePoint;
	private BihuaPoint bihuaPoint;
	 private MiaoHongView mMiaoHongView = null;
	 private TextView txt_content,txt_pinyin;
	 private ImageView img_previous,img_next;
	 private Button btn_show,btn_voice;
	 private GridView grv_hanzi;
	 private ProgressBar pb_bar;
	String shuju = "";
	String hanzi = "";
	String zhi = "";
	private WebView webView;
	private TextView txt_hanzi;
	private LinearLayout lay_hanzi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hanzishow);
		application = (App) getApplication();
		mService = application.getmService();
		mService = application.getmService();
		webView=(WebView)findViewById(R.id.wv1);
		context=this;
    	initView();//初始化
		Ini_accept();//处理接受的值
    	initData();
    	onClick();
		Ini_add_orther();

	}
	private void Ini_accept() {
		Intent intent_jeishou = getIntent();
		String jieshou = intent_jeishou.getStringExtra("zhi");
		try {
			JSONObject json = new JSONObject(jieshou);
			String success = json.getString("success");
			if(!success.equals("200")){
				Toast.makeText(HanZiShowActivity.this, "目前没有该资源！", Toast.LENGTH_SHORT).show();
			}
			else {
				accept = JSON.parseArray((String) json.get("data"), NewWords.class);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Ini_add();//界面显示的问题
	}
	private void Ini_add() {
		list_gridview=new ArrayList<>();
		Collections.sort(accept);//注意Conllection要在初始化之后
		for(int i=0;i < accept.size();i++)
		{
			String word = accept.get(i).getWord().replaceAll("0","");
			word = word.replaceAll("1","");
			word = word.replaceAll("2","");
			list_gridview.add(new gridview_bean(
					word
			));
		}
		Adapter = new GridviewAdapter(this,list_gridview);
		grv_hanzi.setAdapter(Adapter);
		grv_hanzi.setOnItemClickListener(this);
	}
	/***
	 * 获取汉字轮廓文件
	 * @param index
	 */
    private void getOutlinePotin(int index){
		btn_show.setVisibility(View.VISIBLE);
		final String lujing  = json_http.getlujing();
		txt_hanzi.setVisibility(View.GONE);
		lay_hanzi.setVisibility(View.VISIBLE);
		try {
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									File file = new File(lujing ,
											"out.json");
									FileInputStream is = new FileInputStream(file);
									byte[] b = new byte[is.available()];
									is.read(b);
									String result = new String(b);
									outlinePoint = new Gson().fromJson(result,OutlinePoint.class);
									Message msg = handler.obtainMessage();
									msg.what = RESULT_HANZIOUTLINE_CODE;
									handler.sendMessage(msg);
								} catch (Exception e) {
									e.printStackTrace();
								}}
						}).start();
			            pb_bar.setVisibility(View.GONE);
				} catch (Exception e) {
					ToastUtil.show(context, "未找到汉字文件!");
					pb_bar.setVisibility(View.GONE);
				}
	}
	/***
	 * 获取汉字填充文件
	 * @param index
	 */
	private void getFillPoint(int index) {
		final String lujing  = json_http.getlujing();
		pb_bar.setVisibility(View.GONE);
		lay_hanzi.setVisibility(View.VISIBLE);
		txt_hanzi.setVisibility(View.GONE);
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String result = getFromAssets(lujing);
					bihuaPoint = new Gson().fromJson(result, BihuaPoint.class);
					Message msg = handler.obtainMessage();
					msg.what = RESULT_HANZIFILL_CODE;
					handler.sendMessage(msg);
				}
			}).start();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			ToastUtil.show(context, "未找到汉字文件!");
			pb_bar.setVisibility(View.GONE);
		}
    }
    public static  String getFromAssets(String fileName) {
		 String result = "";
        try {
			File file = new File(fileName ,
					"fill.json");
			FileInputStream is = new FileInputStream(file);
			byte[] b = new byte[is.available()];
			is.read(b);
			String result_ = new String(b);
			result = result_;
			return result_;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==RESULT_HANZIOUTLINE_CODE){
		    	pb_bar.setVisibility(View.GONE);
				webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadDataWithBaseURL(null,"11","text/html","utf-8","");
				if(outlinePoint==null){ 
					mMiaoHongView.clearLine();
					return;
				} 
				mMiaoHongView.setOutlinePotin(outlinePoint); 
		    	mMiaoHongView.showOutLine();
                // TODO: 16/7/30  
            }else if(msg.what==RESULT_HANZIFILL_CODE){
		    	pb_bar.setVisibility(View.GONE);
				if(bihuaPoint==null){
					mMiaoHongView.clearMiaoHong();
					return;
				}
				mMiaoHongView.setBiHuaPotin(bihuaPoint);
				mMiaoHongView.startMiaohong();}}
    	
    };
	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
		case R.id.image_top_left:
			mMiaoHongView.stopAll();
			mMiaoHongView.stopMiaoHong();
			mService.onDestroy();
			this.finish();
			break;
		case R.id.img_previous:
			if(aa!=0){
				aa--;
				hanzi = "";
				Adapter.clearSelection(aa);
				Adapter.notifyDataSetChanged();
				Ini_add_orther();
			}else {
				Toast.makeText(context, "已经第一个汉字", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.img_next:
			if(aa!=accept.size()-1){
				aa++;
				hanzi = "";
				Adapter.clearSelection(aa);
				Adapter.notifyDataSetChanged();
				Ini_add_orther();
			}else {
				Toast.makeText(context, "已经最后一个汉字", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_voice:
			break;
		case R.id.btn_show:
			if(qihaun==100||!(qihaun==aa)){
				String zi = accept.get(aa).getWord();
				String zhi__ = zi.substring(zi.length()-1,zi.length());
				json_http.jsonhttp_fill(Consant_stringg.downlowd_out+zhi__+"_fill.json");
				pb_bar.setVisibility(View.VISIBLE);
				json_http.handler = new Handler(){
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if(msg.getData().getInt("passkey") == 1){
							getFillPoint(index);
						}
					}
				};
				qihaun = aa;
			}
			else {
				mMiaoHongView.startMiaohong();
			}

			//if (bihuaPoint == null) {

		//	} else {
		//		mMiaoHongView.startMiaohong();
		//	}
			break;
		default:
			break;
		}
	}
	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mSynListener = new SynthesizerListener() {
		@Override
		public void onSpeakBegin() {
			//ToastUtil.show(context, "开始播放");
		}
		@Override
		public void onSpeakPaused() {
		}
		@Override
		public void onSpeakResumed() {
		}
		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
		}
		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
		}
		public void onCompleted(SpeechError error) {
			if (error == null) {
			} else if (error != null) {
				ToastUtil.show(context, error.getPlainDescription(true));
			}
		}
		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
				if (SpeechEvent.EVENT_SESSION_ID == eventType) {
					String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			}
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		//移动数据统计分析
		FlowerCollector.onResume(context);
		FlowerCollector.onPageStart(TAG);
		super.onResume();
	}
	@Override
	protected void onPause() {
		//移动数据统计分析
		FlowerCollector.onPageEnd(TAG);
		FlowerCollector.onPause(context);
		super.onPause();
	}

	@Override
	public void initData() { 
		// 初始化合成对象
			//
	}
	@Override
	public void initView() {
		mMiaoHongView = (MiaoHongView)findViewById(R.id.img_hanzi);
    	txt_pinyin=(TextView)findViewById(R.id.txt_pinyin);
		txt_hanzi=(TextView)findViewById(R.id.txt_hanzi);
		txt_hanzi.setVisibility(View.GONE);
    	img_previous=(ImageView)findViewById(R.id.img_previous);
    	img_next=(ImageView)findViewById(R.id.img_next);
    	btn_show=(Button)findViewById(R.id.btn_show);
    	btn_voice=(Button)findViewById(R.id.btn_voice);
    	grv_hanzi=(GridView)findViewById(R.id.grv_hanzi);
    	pb_bar=(ProgressBar)findViewById(R.id.pb_bar);
    	grv_hanzi.setSelector(new ColorDrawable(Color.TRANSPARENT));
		lay_hanzi=(LinearLayout)findViewById(R.id.lay_hanzi);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		super.setTitle();
		Intent jj = getIntent();
		String aa = jj.getStringExtra("hou_2");
		super.txtTitle.setText("课程>语文>生字学习>第"+aa+"章");
		super.txtTitle.setTextColor(Color.BLACK);
		super.txtTitle.setTextSize(15);
		super.btn_right.setVisibility(View.INVISIBLE);
	}
	@Override
	public void onClick() {
		super.imageLeft.setOnClickListener(this);
		img_previous.setOnClickListener(this);
		img_next.setOnClickListener(this);
		btn_voice.setOnClickListener(this);
		btn_show.setOnClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Adapter.clearSelection(position);
		Adapter.notifyDataSetChanged();
		if(!(position==indext)){
			indext =position;
		}
		aa = position;
        shuju = list_gridview.get(aa).Itemchengyu;
		hanzi = shuju;
		zi = accept.get(aa).getWord();
		post_shici();//向服务器发送请求诗词
		Ini_add_orther();//点击以后的切换
	}

	private void post_shici() {
		final HashMap<String, String> map = new HashMap<>();
		if(zi.equals("")){
			zi = accept.get(0).getWord();
		}
		else {
			zi = accept.get(aa).getWord();
		}
		map.put("No",zi);
		Log.d("@@","map"+map);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.d("@@","map"+map);
					get_shici = Post_learn_rijiyuelei.getStringCha(Constant.post_get_shengzi_gushici,map);
					JSONObject json = new JSONObject(get_shici);
					Log.d("@@","get_shici"+get_shici);
					String success = json.getString("success");
					if(success.equals("200")){
						shengzi = JSON.parseObject((String) json.get("data"), ShengZiGuShi.class);
						String tt2 = shengzi.getGuShi();
						String tt3 = shengzi.getGuShiCiChuCu();
						Message msg = new Message();
						Bundle g = new Bundle();
						g.putString("key",tt2);
						g.putString("key1",tt3);
						msg.setData(g);
						handlerll.sendMessage(msg);
						Log.d("@@","存入尸体成功");
						get_gushici = 1;
					}else {
						Message msg = new Message();
						Bundle g = new Bundle();
						g.putString("key","");
						g.putString("key1","");
						msg.setData(g);
						handlerll.sendMessage(msg);
						get_gushici = 0;
						Log.d("@@","else");
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
					Log.d("@@","catch");
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("@@","catch");
				}
			}
		}).start();

		handlerll = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(get_gushici == 1) {
					String ttt = msg.getData().get("key").toString();
  					String ttt1 = msg.getData().get("key1").toString();
					tv3.setText(ttt1);
					SpannableStringBuilder style_1 = new SpannableStringBuilder(ttt);
					String ha = zi.substring(1, zi.length());
					Log.d("@@", "ha " + ha);
					int start = ttt.indexOf(ha);
					if (start < 0) {
					} else {
						style_1.setSpan(new ForegroundColorSpan(Color.RED), start, start + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
					}
					tv2.setText(style_1);
				}
				else {
					tv2.setText("");
					tv3.setText("");
				}

			}

		};


	}
	private void Ini_add_orther() {
		mService.onDestroy();
		Ini_voice();//加载音频
		post_shici();
		tt1 ="";
		String pinyin = accept.get(aa).getWordSpell();
		txt_pinyin.getPaint().setTypeface(Typeface.SANS_SERIF);
		txt_pinyin.setText(pinyin);
		String wg1 = "【"+accept.get(aa).getWordGroup1()+"】";
		String wg2 = "【"+accept.get(aa).getWordGroup2()+"】";
		String wg3 = "【"+accept.get(aa).getWordGroup3()+"】";
		String wg4 = "【"+ accept.get(aa).getWordGroup4()+"】";
		if(!wg1.equals("")){
			tt1 = tt1+wg1;
		}
		if(!accept.get(aa).getWordGroup2().equals("")){
			tt1 = tt1+wg2;
		}
		if(!accept.get(aa).getWordGroup3().equals("")){
			tt1 = tt1+wg3;
		}
		if(!accept.get(aa).getWordGroup4().equals("")){
			tt1 = tt1+wg4;
		}
		String hanzi_ = "";
		SpannableStringBuilder style=new SpannableStringBuilder(tt1);
	if(hanzi.equals("")||hanzi==""||hanzi.equals(null)){
		String zi = accept.get(aa).getWord();
		zhi = zi.substring(zi.length()-1,zi.length());
			if(zhi.equals("")){
				String zhizhi = accept.get(0).getWord();
				hanzi_ = zhizhi;
			}
			else {
				hanzi_ = zhi;
			}
		}else {
			 hanzi_ = hanzi;
		}
		int a = tt1.length();
		for (int i = 0;i<a-1;i++){
			if(tt1.substring(i,i+1).equals(hanzi_)){
				style.setSpan(new ForegroundColorSpan(Color.RED),i,i+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}
		tv1.setText(style);
				Log.d("@@","打印"+accept.get(aa).isHasOutFill());
		if(accept.get(aa).isHasOutFill()){
			String en = accept.get(aa).getWord();
			String out__ = en.substring(en.length()-1,en.length());
			json_http.jsonhttp(Consant_stringg.downlowd_out+out__+"_out.json");
			pb_bar.setVisibility(View.VISIBLE);
			json_http.handler=new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (msg.getData().getInt("passkey") == 1) {
						getOutlinePotin(index);//描红轮廓
					}
				}
					};

		}
		else {
			pb_bar.setVisibility(View.GONE);
			getHanzi();//ttf文件
		}
	}
	private void Ini_voice() {
		String zi  = accept.get(aa).getWord();
		 shengzi_url = Stitching.get_url_shengzi(zi);
		Log.d("@@","打印生子路径"+shengzi_url);
		btn_voice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(HanZiShowActivity.this, "正在加载音频，请稍后！", Toast.LENGTH_SHORT).show();
				mService.playMusics(shengzi_url);
			}
		});
	}

	private void getHanzi() {
		btn_show.setVisibility(View.GONE);
		Typeface tf = Typeface.createFromAsset(getAssets(),fontPath);
		String hanzi = accept.get(aa).getWord();
		HanZi = hanzi.substring(hanzi.length()-1,hanzi.length());
		txt_hanzi.setVisibility(View.VISIBLE);
		lay_hanzi.setVisibility(View.GONE);
		txt_hanzi.setTypeface(tf);
		txt_hanzi.setText(HanZi);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mMiaoHongView.stopAll();
			mMiaoHongView.stopMiaoHong();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
