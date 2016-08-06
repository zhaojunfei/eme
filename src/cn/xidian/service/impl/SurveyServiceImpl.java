package cn.xidian.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.xidian.dao.SurveyDao;
import cn.xidian.entity.Survey;
import cn.xidian.service.SurveyService;

@Component
public class SurveyServiceImpl implements SurveyService{

	private SurveyDao surveyDao;

	@Resource(name = "surveyDaoImpl")
	public void setSurveyDao(SurveyDao surveyDao) {
		this.surveyDao = surveyDao;
	}

	@Override
	public boolean createSurvey(Survey survey) {
		// TODO Auto-generated method stub
		return surveyDao.createSurvey(survey);
	}
}
