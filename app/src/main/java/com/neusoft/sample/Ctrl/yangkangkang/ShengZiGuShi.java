package com.neusoft.sample.Ctrl.yangkangkang;

/**
 * 生字古诗词
 */
public class ShengZiGuShi implements Comparable<ShengZiGuShi> {
	private String shengZi;  //生字
	private String guShi;    //古诗词
	private String guShiCiChuCu;   //古诗词出处
	
	
	public String getShengZi() {
		return shengZi;
	}
	public void setShengZi(String shengZi) {
		this.shengZi = shengZi;
	}
	public String getGuShi() {
		return guShi;
	}
	public void setGuShi(String guShi) {
		this.guShi = guShi;
	}
	public String getGuShiCiChuCu() {
		return guShiCiChuCu;
	}
	public void setGuShiCiChuCu(String guShiCiChuCu) {
		this.guShiCiChuCu = guShiCiChuCu;
	}


	@Override
	public int compareTo(ShengZiGuShi another) {

		return 0;
	}
}
