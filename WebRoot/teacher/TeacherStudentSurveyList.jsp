<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>问卷调查设计</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.css" />
<link rel="stylesheet" href="css/common.css" />
<link rel="stylesheet" href="css/survey.css" />
<link rel="stylesheet" href="css/teacher_information.css" />
<link rel="stylesheet" href="css/teaching_management.css" />


</head>

<body>
	<%@ include file="/include/header.jsp"%>
	<%@ include file="/include/teacher_main_nav.jsp"%>
	<div class="content">
		<div class="container">
			<div class="row">
				<%@ include file="/include/tchrLeftBar.jsp"%>
				<div class="span9">
					<div class="span9 div-content-white-bgr">
						<div class="div-inf-bar">
							<label>问卷列表</label>
						</div>
						<div class="div-inf-tbl">
							<div class="div-tchr-detail">
								<a class="btn" href="teacher/TeacherStudentSurvey1.jsp">创建问卷</a>
								<a class="btn" href="teacher/TeacherStudentSurveyResult.jsp">查看结果</a>
								<table class="table table-bordered table-condensed"
									id="surveyList">
									<thead>
										<tr>
											<th width="80px">题目</th>
											<th width="80px">创建时间</th>
											<th width="80px">状态</th>
											<th width="80px">发起单位</th>
											<th width="120px">操作</th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="suPageBean.list" var="s">
											<tr>
												<td><s:property value="#s.title" /></td>
												<td><s:property
														value="%{getText('{0,date,yyyy-MM-dd}',{#s.createTime})}" /></td>
												<td><s:if test="#s.state==0">待发布</s:if>
													<s:if test="#s.state==1">已发布</s:if>
													<s:if test="#s.state==2">已结束</s:if></td>
												<td><s:property value="#s.sponsor" /></td>
												<td><a
													href="TeacherStudent_Survey_3_selectSurveyById?surveyId=<s:property value="#s.surveyId" />">详情</a>
												</td>
												<td><a
													href="TeacherStudent_Survey_Result_selectSurveyById?surveyId=<s:property value="#s.surveyId" />">详情</a>
												</td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
								<div>
									<input type=button class="btn btn-bottom" onclick="upPage()"
										id="upPage" value="上一页">&nbsp;&nbsp;<span id="page"><s:property
											value="suPageBean.page" /></span>&nbsp;&nbsp;<input type="button"
										class="btn btn-bottom" onclick="downPage()" id="downPage"
										value="下一页"><span class="left-distance">共&nbsp;&nbsp;<span
										id="totalPage"><s:property value="suPageBean.totalPage" /></span>&nbsp;&nbsp;页
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/include/footer.jsp"%>
	<script type="text/javascript" src="js/jquery1.12.1.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript" src="js/survey.js"></script>
	<script type="text/javascript">
		var msg = "${requestScope.Message}";
		if (msg != "") {
			alert(msg);
		}
	<%request.removeAttribute("Message");%>
		//显示后将request里的Message清空，防止回退时重复显示。

		$(function() {
			$(".container").css("min-height",
					$(document).height() - 90 - 88 - 41 + "px");//container的最小高度为“浏览器当前窗口文档的高度-header高度-footer高度”
		});

		function selectSurveys(page) {

			$("#surveyList tbody").html("");
			$
					.getJSON(
							"Json_selectSurveys",
							{
								page : page
							},
							function(data) {
								$("#page").html(data.suPageBean.page);
								$("#totalPage").html(data.suPageBean.totalPage);
								if (data.suPageBean.list.length == 0) {
									alert("未找到相关数据！");
								} else {
									$
											.each(
													data.suPageBean.list,
													function(i, value) {
														var str = value.createTime
																.substr(
																		0,
																		value.createTime
																				.indexOf('T'));
														var state = "";
														switch (value.state) {
														case 0:
															state = "待发布";
															break;
														case 1:
															state = "已发布";
															break;
														case 2:
															state = "已结束";
														}
														$("#surveyList")
																.append(
																		"<tr><td>"
																				+ value.title
																				+ "</td><td>"
																				+ str
																				+ "</td><td>"
																				+ state
																				+ "</td><td>"
																				+ value.sponsor
																				+ "</td><td><a href='TeacherStudent_Survey_3_selectSurveyById?surveyId="
																				+ value.surveyId
																				+ "'>详情</a></td>td><a href='TeacherStudent_Survey_Result_selectSurveyById?surveyId="
																				+ value.surveyId
																				+ "'>详情</a></td></tr>");
													});
								}

							});
		}
		function upPage() {
			var page = parseInt($("#page").html());
			if (page == 1) {
				alert("这已经是第一页！");
			} else {
				selectSurveys(page - 1);
			}
		}
		function downPage() {
			var totalPage = parseInt($("#totalPage").html());
			var page = parseInt($("#page").html());
			if (page == totalPage) {
				alert("这已经是最后一页！");
			} else {
				selectSurveys(page + 1);
			}
		}
	</script>
</body>
</html>
