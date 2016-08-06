package cn.xidian.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import cn.xidian.dao.SurveyDao;
import cn.xidian.entity.Survey;

@Component("surveyDaoImpl")
public class SurveyDaoImpl implements SurveyDao{
	private SessionFactory sessionFactory;

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean createSurvey(Survey survey) {
		// TODO Auto-generated method stub
		currentSession().save(survey);
		
		return true;
	}

}
