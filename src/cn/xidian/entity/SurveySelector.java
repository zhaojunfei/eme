package cn.xidian.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="surveyselector")
public class SurveySelector {
private Integer selectorId;
private Integer selectorNum;
private String content;
private String remark;
private SurveyQuestion surveyQuestion;
private Survey survey;


@Id
@GeneratedValue
public Integer getSelectorId() {
	return selectorId;
}
public void setSelectorId(Integer selectorId) {
	this.selectorId = selectorId;
}
public Integer getSelectorNum() {
	return selectorNum;
}
public void setSelectorNum(Integer selectorNum) {
	this.selectorNum = selectorNum;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}

@ManyToOne
@JoinColumn(name="questionId")
public SurveyQuestion getSurveyQuestion() {
	return surveyQuestion;
}
public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
	this.surveyQuestion = surveyQuestion;
}

@ManyToOne
@JoinColumn(name="surveyId")
public Survey getSurvey() {
	return survey;
}
public void setSurvey(Survey survey) {
	this.survey = survey;
}


}
