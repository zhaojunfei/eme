package cn.xidian.web.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.xidian.entity.Clazz;
import cn.xidian.entity.EvaluateResult;
import cn.xidian.entity.ItemEvaluatePoint;
import cn.xidian.entity.ItemEvaluateScore;
import cn.xidian.entity.MaxEva;
import cn.xidian.entity.PageBean;
import cn.xidian.entity.Student;
import cn.xidian.entity.StudentCourse;
import cn.xidian.entity.StudentItem;
import cn.xidian.entity.Survey;
import cn.xidian.entity.Teacher;
import cn.xidian.entity.User;
import cn.xidian.service.StudentItemService;
import cn.xidian.service.StudentService;
import cn.xidian.service.SurveyService;
import cn.xidian.service.TeacherService;
import cn.xidian.service.TeacherStudentService;

@SuppressWarnings("serial")
@Component(value = "JsonAction")
@Scope("prototype")
public class JsonAction extends ActionSupport implements RequestAware {
	private List<ItemEvaluateScore> itemEvaluateScores;
	private List<ItemEvaluatePoint> itemEvaluatePoints;
	private ItemEvaluateScore itemEvaluateScore;
	private Integer pointId;
	private Integer itemTypeId;
	private Integer gradeId;

	// 汇总添加
	private List<Student> stus;
	private Integer clazz;
	private List<StudentItem> items;
	private StudentItemService studentItemService;
	private Clazz cla;
	private String schoolYear;
	private EvaluateResult evaluateResult;
	private Integer size;
	private List<StudentCourse> studentCourses;
	private Student s;
	private MaxEva maxEva;
	private Date startTime;
	private Date endTime;
	private String start;
	private String end;
	private Date date1;
	private Date date2;
	private Date date3;
	private Double average;
	private Integer page;
	private PageBean<EvaluateResult> pageBean;
	private PageBean<StudentCourse> pbStuCours;
	private PageBean<StudentItem> siPageBean;
	private String stuNum;
	
	//問卷添加
	private Teacher teacher;
	private PageBean<Survey> suPageBean;

	Map<String, Object> session = ActionContext.getContext().getSession();
	User tUser = (User) session.get("tUser");

	@Resource(name = "studentItemServiceImpl")
	public void setStudentItemService(StudentItemService studentItemService) {
		this.studentItemService = studentItemService;
	}

	private StudentService studentService;

	@Resource(name = "studentServiceImpl")
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	private SurveyService surveyService;

	@Resource(name = "surveyServiceImpl")
	public void set(SurveyService surveyService) {
		this.surveyService = surveyService;
	}
	
	TeacherService teacherService;

	@Resource(name = "teacherServiceImpl")
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	private TeacherStudentService teacherStudentService;

	@Resource(name = "teacherStudentServiceImpl")
	public void setTeacherStudentService(TeacherStudentService teacherStudentService) {
		this.teacherStudentService = teacherStudentService;
	}

	public String selectItemEvaScores() {
		itemEvaluateScores = studentItemService.selectItemEvaScoresByPointId(pointId);
		return "list";
	}

	public String selectItemEvaPoint() {
		itemEvaluatePoints = studentItemService.selectItemEvaPoints(itemTypeId);
		return "list";
	}

	public String selectItemEvaScore() {
		itemEvaluateScore = studentItemService.selectItemEvaScore(gradeId);
		return "list";
	}

	public String evaluateSummaryByClazz() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		start = simpleDateFormat.format(startTime);
		end = simpleDateFormat.format(endTime);
		try {
			date1 = simpleDateFormat.parse(start);
			date2 = simpleDateFormat.parse(end);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date2);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date3 = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stus = teacherStudentService.selectChargeStus(clazz);
		cla = teacherStudentService.selectClazzById(clazz);
		size = teacherStudentService.selectSummaryEva(clazz, schoolYear);
		if (size != 0) {
			teacherStudentService.deleteEvas(clazz, schoolYear);
		}
		for (Student element : stus) {
			Double M1 = 0.0;
			Double M2 = 0.0;
			Double M3 = 0.0;
			Double M4 = 0.0;
			Double M5 = 0.0;
			EvaluateResult evaluateResult = new EvaluateResult();
			String sch = element.getStuSchNum();
			M2 += countGrade(element, schoolYear);
			items = studentItemService.selectItemByLimitTime(sch, date1, date3);
			for (StudentItem st : items) {
				switch (st.getItemEvaluateType().getItemEvaTypeId()) {
				case 1:
					M1 += Double.parseDouble(st.getItemScore());
					break;
				case 2:
					M2 += Double.parseDouble(st.getItemScore());
					break;
				case 3:
					M3 += Double.parseDouble(st.getItemScore());
					break;
				case 4:
					M4 += Double.parseDouble(st.getItemScore());
					break;
				case 5:
					M5 += Double.parseDouble(st.getItemScore());
					break;
				}

			}
			evaluateResult.setM1(M1);
			evaluateResult.setM2(M2);
			evaluateResult.setM3(M3);
			evaluateResult.setM4(M4);
			evaluateResult.setM5(M5);
			evaluateResult.setSchoolYear(schoolYear);
			evaluateResult.setClazz(cla);
			evaluateResult.setStudent(element);
			teacherStudentService.addEvaScore(evaluateResult);
		}
		return "list";
	}

	public String selectSummaryEva() {
		pageBean = teacherStudentService.findByPageCid(clazz, schoolYear, page);// 根据一级分类查询带分页的商品
		// 将PageBean存入到值栈中
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "list";
	}

	public Double countGrade(Student stu, String schoolYear) {
		Double allCredit = 0.0;
		Double allCreditAndScore = 0.0;
		studentCourses = teacherStudentService.selectStuGrades(stu.getStuId(), schoolYear);
		for (StudentCourse st : studentCourses) {
			st.getCourse().getCursCredit();
			st.getFinEvaValue();
			allCredit += st.getCourse().getCursCredit();
			allCreditAndScore += st.getCourse().getCursCredit() * st.getFinEvaValue();
		}

		if (allCredit == 0.0) {
			average = allCreditAndScore / 1.00;
		} else {
			average = allCreditAndScore / allCredit;
		}
		return average;
	}

	public String selectStuCourseGrades() {
		String schNum = tUser.getSchNum();
		s = studentService.selectInfBySchNum(schNum);
		if (schoolYear.equals("-")) {
			pbStuCours = (studentService.selectStuAllGradesById(s.getStuId(), page));
		} else {
			pbStuCours = teacherStudentService.selectStuGradesByPage(s.getStuId(), schoolYear, page);
		}
		return "list";
	}

	public String selectEvaluateResult() {
		String schNum = tUser.getSchNum();
		s = studentService.selectInfBySchNum(schNum);
		evaluateResult = studentService.selectEvaluateResult(s.getStuId(), schoolYear);
		maxEva = teacherStudentService.selectMaxEva(schoolYear);
		return "list";
	}

	public String selectItem() {
		String schNum;
		if (stuNum == null) {
			schNum = tUser.getSchNum();
		} else {
			schNum = stuNum;
		}
		if (page == null) {
			page = 1;
		}
		siPageBean = studentItemService.selectByStuNum(schNum, page);
		return "list";
	}

	/*
	 * public String selectItem1() { if (page == null) { page = 1; }
	 * siPageBean=studentItemService.selectByStuNum(stuNum,page); return "list";
	 * }
	 */

	public String selectSurveys() {
		String tchrSchNum = tUser.getSchNum();
		teacher=teacherService.selectInfBySchNum(tchrSchNum);
		suPageBean=surveyService.selectAllSurveys(teacher,page);
		return "list";
	}

	public List<ItemEvaluateScore> getItemEvaluateScores() {
		return itemEvaluateScores;
	}

	public void setItemEvaluateScores(List<ItemEvaluateScore> itemEvaluateScores) {
		this.itemEvaluateScores = itemEvaluateScores;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}

	public Integer getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public List<ItemEvaluatePoint> getItemEvaluatePoints() {
		return itemEvaluatePoints;
	}

	public void setItemEvaluatePoints(List<ItemEvaluatePoint> itemEvaluatePoints) {
		this.itemEvaluatePoints = itemEvaluatePoints;
	}

	public ItemEvaluateScore getItemEvaluateScore() {
		return itemEvaluateScore;
	}

	public void setItemEvaluateScore(ItemEvaluateScore itemEvaluateScore) {
		this.itemEvaluateScore = itemEvaluateScore;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public List<Student> getStus() {
		return stus;
	}

	public void setStus(List<Student> stus) {
		this.stus = stus;
	}

	public Integer getClazz() {
		return clazz;
	}

	public void setClazz(Integer clazz) {
		this.clazz = clazz;
	}

	public List<StudentItem> getItems() {
		return items;
	}

	public void setItems(List<StudentItem> items) {
		this.items = items;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Clazz getCla() {
		return cla;
	}

	public void setCla(Clazz cla) {
		this.cla = cla;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Student getS() {
		return s;
	}

	public void setS(Student s) {
		this.s = s;
	}

	public List<StudentCourse> getStudentCourses() {
		return studentCourses;
	}

	public void setStudentCourses(List<StudentCourse> studentCourses) {
		this.studentCourses = studentCourses;
	}

	public EvaluateResult getEvaluateResult() {
		return evaluateResult;
	}

	public void setEvaluateResult(EvaluateResult evaluateResult) {
		this.evaluateResult = evaluateResult;
	}

	public MaxEva getMaxEva() {
		return maxEva;
	}

	public void setMaxEva(MaxEva maxEva) {
		this.maxEva = maxEva;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public PageBean<EvaluateResult> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<EvaluateResult> pageBean) {
		this.pageBean = pageBean;
	}

	public PageBean<StudentCourse> getPbStuCours() {
		return pbStuCours;
	}

	public void setPbStuCours(PageBean<StudentCourse> pbStuCours) {
		this.pbStuCours = pbStuCours;
	}

	public PageBean<StudentItem> getSiPageBean() {
		return siPageBean;
	}

	public void setSiPageBean(PageBean<StudentItem> siPageBean) {
		this.siPageBean = siPageBean;
	}

	public String getStuNum() {
		return stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public PageBean<Survey> getSuPageBean() {
		return suPageBean;
	}

	public void setSuPageBean(PageBean<Survey> suPageBean) {
		this.suPageBean = suPageBean;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
