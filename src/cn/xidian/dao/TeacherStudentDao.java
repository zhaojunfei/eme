package cn.xidian.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;

import cn.xidian.entity.Clazz;
import cn.xidian.entity.EvaluateResult;
import cn.xidian.entity.StuEvaluateResult;
import cn.xidian.entity.Student;
import cn.xidian.entity.StudentCourse;
import cn.xidian.web.bean.AdminStuLimits;

public interface TeacherStudentDao {

	void setSessionFactory(SessionFactory sessionFactory);

	List<Clazz> selectChargeCla(Integer id);

	List<Student> selectChargeStus(Integer id);

	Set<Student> selectStuLimits(AdminStuLimits limits, List<Clazz> clazzs);

	boolean addEvaScore(EvaluateResult evaluateResult);

	Integer selectSummaryEva(Integer claId, String schoolYear);

	List<EvaluateResult> selectSummaryEvas(Integer claId, String schoolYear);

	Clazz selectClazzById(Integer id);

	boolean deleteEvas(Integer claId, String schoolYear);

	List<StudentCourse> selectStuGrades(Integer stuId, String schoolYear);

	EvaluateResult selectEvaluateResultById(Integer id);

	List<StuEvaluateResult> selectMaxEva(String schoolYear, Integer i);

	Integer findCountCid(Integer claId);

	List<EvaluateResult> findByPageCid(Integer claId, String schoolYear, Integer begin, Integer limit);

	List<StudentCourse> findStuGradesByPage(Integer stuId, String schoolYear, Integer begin, Integer limit);

	boolean addStuEvaScore(StuEvaluateResult stuEvaluateResult);

	List<StuEvaluateResult> selectSummaryStuEvas(Integer claId, String schoolYear);

	boolean deleteStuEvas(Integer claId, String schoolYear);

	List<StuEvaluateResult> findStuEvaByPageCid(Integer itemEvaTypeId,Integer claId, String schoolYear, Integer begin, Integer limit);
}
