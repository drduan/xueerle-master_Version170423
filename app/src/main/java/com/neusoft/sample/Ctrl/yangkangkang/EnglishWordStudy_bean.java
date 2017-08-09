package com.neusoft.sample.Ctrl.yangkangkang;


/**
 * 英语单词学习
 */
public class EnglishWordStudy_bean implements Comparable<EnglishWordStudy_bean>{
	private String itemNo;       		  //英语单词学习项编号
	private String word;       			  //单词
	private String soundmark;   		  //音标
	private String sound;     		  //修改后  取消判断字段   直接返回的是  音频名称
	private String kb_paraphrase;		  //课本释义词性
	private boolean wordPicture1;		  //是否有单词图片1   0无  1有
	private String translate;    		  //课本释义
	private String textbook_eg;  		  //课本释义例句
	private String translation;  		  //课本释义例句译文
	private String tz_paraphrase;		  //拓展释义词性
	private boolean wordPicture2;         //是否有单词图片2   0无  1有
	private String tuozhanshiyi;          //拓展释义
	private String tuozhanshiyi_eg;       //拓展释义例句
	private String tuozhan_translate;	  //拓展释义例句译文
	private String word1;				  //单词++1
	private String word2;				  //单词++2
	private String word3;				  //单词++3
	private String synonym;				  //近义词
	private boolean isPDF;                //是否有近义词PDF
	private String pdfName;               //近义词PDF文件名
	private String phraseStudy1;          //短语学习1
	private String phraseStudy2;          //短语学习2
	private String phraseStudy3;          //短语学习3
	private String phraseStudy4;          //短语学习4
	private String phraseStudy5;          //短语学习5
	private String phraseStudy6;          //短语学习6
	private String phraseStudy7;          //短语学习7
	private String phraseStudy8;          //短语学习8
	private String phraseStudy9;          //短语学习9
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getSoundmark() {
		return soundmark;
	}
	public void setSoundmark(String soundmark) {
		this.soundmark = soundmark;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getKb_paraphrase() {
		return kb_paraphrase;
	}
	public void setKb_paraphrase(String kb_paraphrase) {
		this.kb_paraphrase = kb_paraphrase;
	}
	public boolean isWordPicture1() {
		return wordPicture1;
	}
	public void setWordPicture1(boolean wordPicture1) {
		this.wordPicture1 = wordPicture1;
	}
	public String getTranslate() {
		return translate;
	}
	public void setTranslate(String translate) {
		this.translate = translate;
	}
	public String getTextbook_eg() {
		return textbook_eg;
	}
	public void setTextbook_eg(String textbook_eg) {
		this.textbook_eg = textbook_eg;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public String getTz_paraphrase() {
		return tz_paraphrase;
	}
	public void setTz_paraphrase(String tz_paraphrase) {
		this.tz_paraphrase = tz_paraphrase;
	}
	public boolean isWordPicture2() {
		return wordPicture2;
	}
	public void setWordPicture2(boolean wordPicture2) {
		this.wordPicture2 = wordPicture2;
	}
	public String getTuozhanshiyi() {
		return tuozhanshiyi;
	}
	public void setTuozhanshiyi(String tuozhanshiyi) {
		this.tuozhanshiyi = tuozhanshiyi;
	}
	public String getTuozhanshiyi_eg() {
		return tuozhanshiyi_eg;
	}
	public void setTuozhanshiyi_eg(String tuozhanshiyi_eg) {
		this.tuozhanshiyi_eg = tuozhanshiyi_eg;
	}
	public String getTuozhan_translate() {
		return tuozhan_translate;
	}
	public void setTuozhan_translate(String tuozhan_translate) {
		this.tuozhan_translate = tuozhan_translate;
	}
	public String getWord1() {
		return word1;
	}
	public void setWord1(String word1) {
		this.word1 = word1;
	}
	public String getWord2() {
		return word2;
	}
	public void setWord2(String word2) {
		this.word2 = word2;
	}
	public String getWord3() {
		return word3;
	}
	public void setWord3(String word3) {
		this.word3 = word3;
	}
	public String getSynonym() {
		return synonym;
	}
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}
	public boolean isPDF() {
		return isPDF;
	}
	public void setPDF(boolean isPDF) {
		this.isPDF = isPDF;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String getPhraseStudy1() {
		return phraseStudy1;
	}
	public void setPhraseStudy1(String phraseStudy1) {
		this.phraseStudy1 = phraseStudy1;
	}
	public String getPhraseStudy2() {
		return phraseStudy2;
	}
	public void setPhraseStudy2(String phraseStudy2) {
		this.phraseStudy2 = phraseStudy2;
	}
	public String getPhraseStudy3() {
		return phraseStudy3;
	}
	public void setPhraseStudy3(String phraseStudy3) {
		this.phraseStudy3 = phraseStudy3;
	}
	public String getPhraseStudy4() {
		return phraseStudy4;
	}
	public void setPhraseStudy4(String phraseStudy4) {
		this.phraseStudy4 = phraseStudy4;
	}
	public String getPhraseStudy5() {
		return phraseStudy5;
	}
	public void setPhraseStudy5(String phraseStudy5) {
		this.phraseStudy5 = phraseStudy5;
	}
	public String getPhraseStudy6() {
		return phraseStudy6;
	}
	public void setPhraseStudy6(String phraseStudy6) {
		this.phraseStudy6 = phraseStudy6;
	}
	public String getPhraseStudy7() {
		return phraseStudy7;
	}
	public void setPhraseStudy7(String phraseStudy7) {
		this.phraseStudy7 = phraseStudy7;
	}
	public String getPhraseStudy8() {
		return phraseStudy8;
	}
	public void setPhraseStudy8(String phraseStudy8) {
		this.phraseStudy8 = phraseStudy8;
	}
	public String getPhraseStudy9() {
		return phraseStudy9;
	}
	public void setPhraseStudy9(String phraseStudy9) {
		this.phraseStudy9 = phraseStudy9;
	}


	@Override
	public int compareTo(EnglishWordStudy_bean another) {

		//从下表为7开始往后
		if (Integer.valueOf(itemNo.substring(7))<Integer.valueOf(another.itemNo.substring(7)))return -1;
		else if (Integer.valueOf(itemNo.substring(7))==Integer.valueOf(another.itemNo.substring(7)))
			return 0;
		else return 1;

	}
}
