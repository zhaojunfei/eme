package cn.xidian.service;

import java.util.List;
import java.util.Set;

import cn.xidian.entity.Clazz;
import cn.xidian.entity.EvaluateResult;
import cn.xidian.entity.MaxEva;
import cn.xidian.entity.PageBean;
import cn.xidian.entity.Student;
import cn.xidian.entity.StudentCourse;
import cn.xidian.web.bean.AdminStuLimits;


public interface TeacherStudentService {

	List<Clazz> findChargeCla(Integer id);
	
	List<Student> selectChargeStus(Integer id);
	
	Set<Student> selectStuLimits(AdminStuLimits limits, List<Clazz> clazzs);
	
	boolean addEvaScore(EvaluateResult evaluateResult);
	
	Integer selectSummaryEva(Integer claId,String schoolYear);
	
	Clazz selectClazzById(Integer id);
	
	boolean deleteEvas(Integer claId,String schoolYear);
	
	List<StudentCourse> selectStuGrades(Integer stuId,String schoolYear);
	
	EvaluateResult selectEvaluateResultById(Integer id);
	
	MaxEva selectMaxEva(String schoolYear);
	
	PageBean<EvaluateResult> findByPageCid(Integer claId,String schoolYear,Integer page);
	
	PageBean<StudentCourse> selectStuGradesByPage(Integer stuId,String schoolYear,Integer page);
}
