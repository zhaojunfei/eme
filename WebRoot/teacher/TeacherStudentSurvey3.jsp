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
							<label>创建全新问卷</label>
						</div>
						<div class="div-inf-tbl">
							<div>
								<h3 class="title_center">
									<s:property value="survey.title" />
								</h3>
								<p class="top-distance para-indent">
									<s:property value="survey.discribe" />
								</p>
								<p class="top-distance para-indent align_right">
									<s:property value="survey.sponsor" />
								</p>
							</div>
							<hr />
							<form
								action="TeacherStudent_Survey_List_addSurveyDone?surveyId=<s:property
										value="survey.surveyId" />"
								method="post" class="form-horizontal">
								<s:iterator value="surveyQuestions" var="sq" status="status">
									<ul class="question-style top-distance">
										<label>Q<s:property value="%{#status.count}" />： <s:property
												value="#sq.content" /></label>
										<s:if test="#sq.type==1||#sq.type==2">
											<s:generator val="#sq.selectors" separator="_" id="s" />
											<s:iterator status="st" value="#request.s" id="selector">
												<li class="li_style selector-style"><s:if
														test="#sq.type==1">
														<input type="radio" class="radio"
															name="<s:property value="%{#status.count}" />"
															id="<s:property value="%{#st.count}" />">
														<span class="left_distance"><s:property
																value="selector" /></span>
													</s:if> <s:if test="#sq.type==2">
														<input type="checkbox" class="checkbox"
															name="<s:property value="%{#status.count}" />"
															id="<s:property value="%{#st.count}" />">
														<span class="left_distance"><s:property
																value="selector" /></span>
													</s:if></li>
											</s:iterator>
											<input type="hidden" class="selected"
												id="seled<s:property value="%{#status.count}" />"
												value="<s:property value="#sq.questionId" />">
											<!-- 获取选中的选项的selectorNum -->
										</s:if>
										<s:if test="#sq.type==3">
											<li class="li_style selector-style"><textarea
													name="<s:property value="%{#status.count}" />"
													placeholder='请填写内容' class='textarea left_distance'
													style='width: 72%; height: 100px'></textarea></li>
											<input type="text" class="textAnswer"
												id="text<s:property value="%{#status.count}" />"
												value="<s:property value="#sq.questionId" />">
										</s:if>
									</ul>

								</s:iterator>
								<%-- <input type="text" name="survey.surveyId"
									value="<s:property value="survey.surveyId" />">
								<!-- 获取问卷的ID --> --%>

								<input type="submit" class="btn" value="提交问卷"
									onclick="linkSelSubmit()">
							</form>
						</div>
						<input type="button" class="btn" value="提交"
							onclick="linkSelSubmit()">
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

		function linkSelSubmit() {
			//获取选中的单选
			var radios = document.getElementsByClassName("radio");
			for (var i = 0; i < radios.length; i++) {
				if (radios[i].checked) {
					document.getElementById("seled" + radios[i].name + "").value += "#"
							+ radios[i].id;
				}
			}
			//获取选中的多选
			var checkboxs = document.getElementsByClassName("checkbox");
			for (var j = 0; j < checkboxs.length; j++) {
				if (checkboxs[j].checked) {
					document.getElementById("seled" + checkboxs[j].name + "").value += "#"
							+ checkboxs[j].id;
				}
			}

			//获取文字问题的内容
			var texts = document.getElementsByClassName("textarea");
			alert(texts.length);
			for (var m = 0; m < texts.length; m++) {
				document.getElementById("text" + texts[m].name + "").value += "#"
						+ texts[m].value;
			}
			//设置选择题name属性
			var selected = document.getElementsByClassName("selected");
			for (var k = 0; k < selected.length; k++) {
				selected[k].setAttribute("name", "surveySelectors[" + k
						+ "].remark");
			}
			//设置文本问题的name属性
			var textAnswer = document.getElementsByClassName("textAnswer");
			for (var n = 0; n < textAnswer.length; n++) {
				textAnswer[n].setAttribute("name", "textAnswers[" + n
						+ "].remark");
			}

		}
	</script>
</body>
</html>
