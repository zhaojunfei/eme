function showChart(obj,num) {
			var surveyId = $("#surveyId").val();
			$("#jqChart" + obj.name + "").css("display", "block");
			$
					.getJSON(
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
								if(num==1){drawPie(arr, obj.name);}
								if(num==2){drawColumn(arr, obj.name);}
								if(num==3){alert("文本题");}
								
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
									text : '扇形统计图'
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
		function drawColumn(data, questionId) {
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
									text : '柱状统计图'
								},
								animation : {
									duration : 1
								},
								shadows : {
									enabled : true
								},
								series : [ {
									type : 'column',
									title : '选项',
									fillStyles : [ '#418CF0', '#FCB441',
											'#E0400A', '#056492', '#BFBFBF',
											'#1A3B69', '#FFE382' ],
									data : data
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
