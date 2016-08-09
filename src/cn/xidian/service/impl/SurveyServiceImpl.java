package cn.xidian.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.xidian.dao.SurveyDao;
import cn.xidian.entity.CourseSpecificContent;
import cn.xidian.entity.Survey;
import cn.xidian.entity.SurveyQuestion;
import cn.xidian.entity.SurveySelector;
import cn.xidian.service.SurveyService;

@Component
public class SurveyServiceImpl implements SurveyService {

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

	@Override
	public boolean addQuestion(List<SurveyQuestion> qs, Survey survey) {
		// TODO Auto-generated method stub
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(survey.getEndTime());
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		survey.setEndTime(calendar.getTime()); // 这个时间就是日期往后推一天的结果
		surveyDao.updateSurvey(survey);
		Iterator<SurveyQuestion> itqs = qs.iterator();
		while (itqs.hasNext()) {
			SurveyQuestion sq = new SurveyQuestion();
			sq = itqs.next();
			sq.setSurvey(survey);
			surveyDao.addQuestion(sq);
			String[] chrstr = sq.getSelectors().split("_");
			for (int i = 0; i < chrstr.length; i++) {
				SurveySelector surveySelector = new SurveySelector();
				surveySelector.setContent(chrstr[i]);
				surveySelector.setSelectorNum(i+1);
				surveySelector.setSurvey(survey);
				surveySelector.setSurveyQuestion(sq);
				surveyDao.addSelector(surveySelector);
			}
		}
		return true;
	}
}
