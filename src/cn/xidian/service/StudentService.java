package cn.xidian.service;


import java.util.List;

import cn.xidian.entity.EvaluateResult;
import cn.xidian.entity.PageBean;
import cn.xidian.entity.StuEvaluateResult;
import cn.xidian.entity.Student;
import cn.xidian.entity.StudentCourse;

public interface StudentService {

	boolean loginValidate(String stuSchNum, String stuPwd);

	Student selectInfBySchNum(String stuSchNum);

	boolean updateInfBySchNum(Student student);
	
	boolean updateGoal(Student student);
	
	boolean modifyPassword(String schNum,String pwd);

	PageBean<StudentCourse> selectStuAllGradesById(Integer id,Integer page);
	
	EvaluateResult selectEvaluateResult(Integer stuId,String schoolYear);
	
	List<StuEvaluateResult> selectStuEvaluateResults(Integer stuId,String schoolYear);
	
}