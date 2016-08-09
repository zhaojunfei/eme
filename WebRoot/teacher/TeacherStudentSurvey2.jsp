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
								<h3 class="title_center"><s:property
										value="survey.title" /></h3> <p
									class="top-distance para-indent"><s:property
										value="survey.discribe" /></p> <p
									class="top-distance para-indent align_right"><s:property
										value="survey.sponsor" />发</p>
							</div>
							<hr class="red_line"/>
							<form action="TeacherStudent_Survey_3_addQuestion" method="post"
								class="form-horizontal" onsubmit="javascript:return isEmpty()">
								<div class="div-inf">
									<div id="div-content" class="top-distance"></div>
								</div>
								<div class="submit_top">
									<input type="hidden" name="survey.surveyId"
										value="<s:property
										value="survey.surveyId" />">
									<input type="date" name="survey.startTime" id="startTime">至
									<input type="date" name="survey.endTime" id="endTime">
									<input type="submit" class="btn" name="submit" id="submit"
										value="提交" onclick="linksel()">
								</div>
							</form>
							<input type="button" name="addQues" id="1" value="单  选"
								class="btn" onclick="addQuestion(this)"> <input
								type="button" name="addQues" id="2" value="多  选" class="btn"
								onclick="addQuestion(this)"><input type="button"
								name="addQues" id="3" value="文字题" class="btn"
								onclick="addQuestion(this)">
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
	</script>
</body>
</html>
