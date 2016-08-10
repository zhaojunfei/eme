package cn.xidian.service;

import java.util.List;

import cn.xidian.entity.PageBean;
import cn.xidian.entity.Survey;
import cn.xidian.entity.SurveyQuestion;
import cn.xidian.entity.SurveySelector;
import cn.xidian.entity.Teacher;
import cn.xidian.entity.TextAnswer;

public interface SurveyService {

	boolean createSurvey(Survey survey);

	boolean addQuestion(List<SurveyQuestion> qs, Survey survey);
	
	PageBean<Survey> selectAllSurveys(Teacher teacher,Integer page);
	
	Survey selectSurveyById(Integer surveyId);
	
	List<SurveyQuestion> selectQuestionBysurveyId(Integer surveyId);
	
	boolean addSurveyDone(List<SurveySelector> surveySelectors,List<TextAnswer> textAnswers,Integer surveyId);
}
