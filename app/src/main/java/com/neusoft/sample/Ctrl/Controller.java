package com.neusoft.sample.Ctrl;


import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.Model.ErrorSubject_two;
import com.neusoft.sample.Model.Resource;

import java.util.List;

public class Controller {
	public void load(String nub_zu,List<CalculationTest> lists){
//		System.out.println("loading..."+chapter+"|"+sect+"|"+group);
		Resource.QUESTION_GROUP.setQuestions(Resource.QUESTION_LIB.getAGroupOfQuesition(nub_zu,lists));

	}

	public void loadDoagain(List<ErrorSubject_two> lists){
//		System.out.println("loading..."+chapter+"|"+sect+"|"+group);
		Resource.QUESTION_GROUPDoAgain.setQuestions(Resource.QUESTION_LIB.getAGroupOfQuesitionDoagain(lists));

	}


	public void submit(String myChoice){
		Resource.QUESTION_GROUP.submit(myChoice);
	}

	public void submitDoagain(String myChoice){
		Resource.QUESTION_GROUPDoAgain.submit(myChoice);
	}
}
