package com.neusoft.sample.Model;


import com.neusoft.sample.Ctrl.Controller;
import com.neusoft.sample.Model.DoagainErrorSubject.QuestionGroupAgain;
import com.neusoft.sample.Virtual_data.QuestionLib;

public interface Resource {
	public static final QuestionLib QUESTION_LIB = new QuestionLib();
	public static final QuestionGroup QUESTION_GROUP = new QuestionGroup();
	public static final QuestionGroupAgain QUESTION_GROUPDoAgain = new QuestionGroupAgain();

	public static final Controller CONTROLLER = new Controller();
}
