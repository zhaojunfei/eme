package cn.xidian.dao;


import cn.xidian.entity.Survey;
import cn.xidian.entity.SurveyQuestion;
import cn.xidian.entity.SurveySelector;

public interface SurveyDao {

	boolean createSurvey(Survey survey);

	boolean addQuestion(SurveyQuestion sq);
	
	boolean updateSurvey(Survey survey);
	
	boolean addSelector(SurveySelector surveySelector);
}
