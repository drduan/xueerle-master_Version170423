package com.neusoft.sample.View.AoSaiTest;


import com.neusoft.sample.Model.aosai_zu_Entity;

import java.util.List;

public class Aosai_Controller {
	public void load(String nub_zu,List<aosai_zu_Entity> lists){
//		System.out.println("loading..."+chapter+"|"+sect+"|"+group);
		Aosai_Resource.QUESTION_GROUP.setQuestions(Aosai_Resource.QUESTION_LIB.getAGroupOfQuesition(nub_zu,lists));

	}

	public void loadDoagain(List<aosai_zu_Entity> lists){
//		System.out.println("loading..."+chapter+"|"+sect+"|"+group);
		Aosai_Resource.QUESTION_GROUPDoAgain.setQuestions(Aosai_Resource.QUESTION_LIB.getAGroupOfQuesition("",lists));

	}


	public void submit(String myChoice){
		Aosai_Resource.QUESTION_GROUP.submit(myChoice);
	}

	public void submitDoagain(String myChoice){
		Aosai_Resource.QUESTION_GROUPDoAgain.submit(myChoice);
	}
}
