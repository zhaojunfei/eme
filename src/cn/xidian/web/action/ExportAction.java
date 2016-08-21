package cn.xidian.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;
import cn.xidian.entity.EvaluateResult;
import cn.xidian.service.TeacherStudentService;

@Component
@SuppressWarnings("serial")
public class ExportAction extends ActionSupport implements RequestAware {

	private InputStream excelFile;
	private EvaluateResult evaluateResult;
	private List<EvaluateResult> evaluateResults;
	private Integer claId;
	private String schoolYear;
	private String fileName;

	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}

	TeacherStudentService teacherStudentService;

	@Resource(name = "teacherStudentServiceImpl")
	public void setTeacherStudentService(TeacherStudentService teacherStudentService) {
		this.teacherStudentService = teacherStudentService;
	}

	public String ExcelExport() throws Exception {
		fileName="2015-2016学年学生模块测评结果.xls";
		evaluateResults=teacherStudentService.selectSummaryEvas(1, "2015-2016");
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("schoolExcel");
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		Row row = sheet.createRow(0); 
		row.createCell(0).setCellValue("姓名");
		row.createCell(1).setCellValue("学号");
		row.createCell(2).setCellValue("奖学金等级");
		row.createCell(3).setCellValue("学院");
		row.createCell(4).setCellValue("班级");
		row.createCell(5).setCellValue("思想道德M1");
		row.createCell(6).setCellValue("专业理论素质M2");
		row.createCell(7).setCellValue("创新与实践素质M3");
		row.createCell(8).setCellValue("文化素质M4");
		row.createCell(9).setCellValue("身心素质M5");
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		for (int i = 1; i <= evaluateResults.size(); i++) {
			evaluateResult = evaluateResults.get(i - 1);
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(evaluateResult.getStudent().getStuName());
			row.createCell(1).setCellValue(evaluateResult.getStudent().getStuSchNum());
			row.createCell(4).setCellValue(evaluateResult.getClazz().getClaName());
			row.createCell(5).setCellValue(evaluateResult.getM1());
			row.createCell(6).setCellValue(evaluateResult.getM2());
			row.createCell(7).setCellValue(evaluateResult.getM3());
			row.createCell(8).setCellValue(evaluateResult.getM4());
			row.createCell(9).setCellValue(evaluateResult.getM5());
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		excelFile = new ByteArrayInputStream(baos.toByteArray());
		baos.close();
		return "excel";
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	public EvaluateResult getEvaluateResult() {
		return evaluateResult;
	}

	public void setEvaluateResult(EvaluateResult evaluateResult) {
		this.evaluateResult = evaluateResult;
	}

	public List<EvaluateResult> getEvaluateResults() {
		return evaluateResults;
	}

	public void setEvaluateResults(List<EvaluateResult> evaluateResults) {
		this.evaluateResults = evaluateResults;
	}

	public Integer getClaId() {
		return claId;
	}

	public void setClaId(Integer claId) {
		this.claId = claId;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
