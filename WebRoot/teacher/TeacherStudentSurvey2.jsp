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
								<label class="text-size" style="margin-left: 40%"><s:property
										value="survey.surveyId" /> <s:property value="survey.title" /></label>
								<label class="text-size top-distance para-indent"><s:property
										value="survey.discribe" /></label>
							</div>
							<form action="" method="post" class="form-horizontal">
								<div class="div-inf">
									<div id="div-content"></div>
								</div>
							</form>
							<input type="button" name="addQues" value="添  加" class="btn"
								onclick="addQuestion()">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/include/footer.jsp"%>
	<script type="text/javascript" src="js/jquery1.12.1.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
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

		function addQuestion() {			
			var ques = document.getElementsByClassName("ques").length;
			var quesNum=ques+1;
			var selectors=document.getElementsByClassName("ST"+ ques);
			
			if(selectors.length!=0){
				var selContent=null;
				for(var i=0;i<selectors.length;i++){
					if(i!=selectors.length-1){
						selContent+=selectors[i]+"_";
					}else{
						selContent+=selectors[i];
					}
				}
				alert(selContent);
			}
			
			var div1 = document.createElement("div");
			div1.setAttribute("class", "control-group");
			div1.innerHTML += "<div class='ques'><span>Q"
					+ quesNum
					+ ":</span><input type='text' class='input-long' placeholder='问卷题目'></div><div id='sel"+quesNum+"'><input type='radio' id='selector' name='S"+quesNum+"'><input type='text'  placeholder='选项'  class='ST"+quesNum+"'><br><input type='radio' id='selector' name='S"+quesNum+"'><input type='text'  placeholder='选项'   class='ST"+quesNum+"'></div>";
			var btn = document.createElement("input");
			btn.setAttribute("type", "button");
			btn.setAttribute("class", "btn");
			btn.setAttribute("onclick", "addSelector(this)");
			btn.setAttribute("id", "" + quesNum + "");
			btn.setAttribute("value", "添加选项");
			div1.appendChild(btn);
			var section = document.createElement("section");
			section.appendChild(div1);
			var div = document.getElementById("div-content");
			div.appendChild(section);
		}
		function addSelector(obj) {
			var div = document.getElementById("sel" + obj.id);	
			div.innerHTML += "<br><input type='radio' id='selector' name='S"+obj.id+"'><input type='text'  placeholder='选项'  class='ST"+obj.id+"'>";
		}
	</script>
</body>
</html>
