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
<link rel="stylesheet" href="css/teaching_management.css" />
<link rel="stylesheet" type="text/css"
	href="css/pieChart/jquery.jqChart.css" />
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
							<s:iterator value="surveyQuestions" var="sq" status="status">
								<div onmouseover='showDel(this)' onmouseout='hideDel(this)'>
									<div class="question-style top-distance"
										style="display:inline-block;vertical-align:middle">
										Q
										<s:property value="%{#status.count}" />
										：
										<s:property value="#sq.content" />
										<s:if test="#sq.type==2">
											<span>（多选）</span>
										</s:if>
										<s:if test="#sq.type==1 || #sq.type==2">
											<img class="small_img show_Pie" src="img/showPie.png"
												onclick="showChart(this)"
												name="<s:property value="#sq.questionId" />"
												id="img<s:property value="#sq.questionId" />" />
											<img class="small_img show_Pie"  src="img/showPie.png"
												onclick="showPie(this)" style="display: none"
												name="<s:property value="#sq.questionId" />"
												id="image<s:property value="#sq.questionId" />" />
											<img class="small_img"  src="img/delsel.gif"
												onclick="hidePie(this)"
												name="<s:property value="#sq.questionId" />" />
										</s:if>
									</div>
									<ul class="question-style">
										<s:if test="#sq.type==1||#sq.type==2">
											<s:generator val="#sq.selectors" separator="_" id="s" />
											<s:iterator status="st" value="#request.s" id="selector">
												<li class="li_style selector-style"><s:if
														test="#sq.type==1">
														<span class="serialNumber"
															id="<s:property value="%{#st.count}" />"></span>
														<span class="left_distance"><s:property
																value="selector" /></span>
													</s:if> <s:if test="#sq.type==2">
														<span class="serialNumber"
															id="<s:property value="%{#st.count}" />"></span>
														<span class="left_distance"><s:property
																value="selector" /></span>
													</s:if></li>
											</s:iterator>
											<!-- 获取选中的选项的selectorNum -->
										</s:if>
										<s:if test="#sq.type==3">
											<li class="li_style selector-style"><textarea
													name="<s:property value="%{#status.count}" />"
													placeholder='请填写内容' class='textarea left_distance'
													style='width: 72%; height: 100px' readonly></textarea></li>
										</s:if>
									</ul>
									<div id="jqChart<s:property value="#sq.questionId"/>"
										class="pieChart_style"></div>
								</div>
							</s:iterator>
						</div>
						<input type="hidden" id="surveyId"
							value="<s:property value="survey.surveyId" />">
					</div>

				</div>
			</div>
		</div>
	</div>
	<%@ include file="/include/footer.jsp"%>
	<script type="text/javascript" src="js/jquery1.12.1.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript" src="js/survey.js"></script>
	<script src="js/pieChart/jquery.jqChart.min.js" type="text/javascript"></script>
	<script lang="javascript" type="text/javascript"
		src="js/pieChart/excanvas.js"></script>
	<script type="text/javascript">
		var msg = "${requestScope.Message}";
		if (msg != "") {
			alert(msg);
		}
		$(function() {
			$(".container").css("min-height",
					$(document).height() - 90 - 88 - 41 + "px");//container的最小高度为“浏览器当前窗口文档的高度-header高度-footer高度”
			numChangeToCode();
		});
	<%request.removeAttribute("Message");%>
		//显示后将request里的Message清空，防止回退时重复显示。

		function showChart(obj) {
			var surveyId = $("#surveyId").val();
			$("#jqChart" + obj.name + "").css("display", "block");
			$.getJSON(
							"Json_selectSurveyResult",
							{
								surveyId : surveyId,
								questionId : obj.name
							},
							function(data) {
								var arr = [];
								$
										.each(
												data.surveySelectors,
												function(i, value) {
													var selector = [];
													selector[0] = ""
															+ String
																	.fromCharCode(64 + parseInt(value.selectorNum))
															+ "";
													selector[1] = value.sumNum;
													arr[i] = selector;
												});
								drawPie(arr, obj.name);
							});
			$("#img" + obj.name + "").css("display", "none");
			$("#image" + obj.name + "").css("display", "inline");
		}

		function drawPie(data, questionId) {
			var background = {
				type : 'linearGradient',
				x0 : 0,
				y0 : 0,
				x1 : 0,
				y1 : 1,
				colorStops : [ {
					offset : 0,
					color : 'white'
				}, {
					offset : 1,
					color : 'white'
				} ]
			};

			$("#jqChart" + questionId + "")
					.jqChart(
							{
								title : {
									text : '扇形统计表'
								},
								legend : {
									title : '选项'
								},
								border : {
									strokeStyle : '#AAAAAA'
								},
								background : background,
								animation : {
									duration : 1
								},
								shadows : {
									enabled : true
								},
								series : [ {
									type : 'pie',
									fillStyles : [ '#418CF0', '#FCB441',
											'#E0400A', '#056492', '#BFBFBF',
											'#1A3B69', '#FFE382' ],
									labels : {
										stringFormat : '%.1f%%',
										valueType : 'percentage',
										font : '15px sans-serif',
										fillStyle : 'white'
									},
									explodedRadius : 10,
									explodedSlices : [ 5 ],
									data : data,
									labelsPosition : 'outside', // inside, outside
									labelsAlign : 'circle', // circle, column
									labelsExtend : 20,
									leaderLineWidth : 1,
									leaderLineStrokeStyle : 'black'
								} ]
							});
			
		}

		//显示图片
		function showDel(obj) {
			$(obj).find("img").css('visibility', 'visible');
		}
		// 隐藏图片
		function hideDel(obj) {
			$(obj).find("img").css('visibility', 'hidden');
		}

		function hidePie(obj) {
			$("#jqChart" + obj.name + "").css("display", "none");
		}
		function showPie(obj) {
			$("#jqChart" + obj.name + "").css("display", "block");
		}
		function numChangeToCode() {
			var spans = document.getElementsByClassName("serialNumber");
			for (var i = 0; i < spans.length; i++) {
				spans[i].innerHTML = String
						.fromCharCode(64 + parseInt(spans[i].id))
						+ "  ."
			}
		}
	</script>
</body>
</html>
