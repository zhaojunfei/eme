package cn.xidian.service;

import java.util.List;

import cn.xidian.entity.Survey;
import cn.xidian.entity.SurveyQuestion;

public interface SurveyService {

	boolean createSurvey(Survey survey);

	boolean addQuestion(List<SurveyQuestion> qs, Survey survey);
}
