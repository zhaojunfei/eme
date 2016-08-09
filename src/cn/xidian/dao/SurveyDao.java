package cn.xidian.dao;

import java.util.List;

import cn.xidian.entity.Survey;
import cn.xidian.entity.SurveyQuestion;
import cn.xidian.entity.SurveySelector;
import cn.xidian.entity.Teacher;

public interface SurveyDao {

	boolean createSurvey(Survey survey);

	boolean addQuestion(SurveyQuestion sq);

	boolean updateSurvey(Survey survey);

	boolean addSelector(SurveySelector surveySelector);

	List<Survey> selectAllSurveys(Teacher teacher);
	
	List<Survey> findSurveys(Teacher teacher,Integer begin,Integer limit);
}
