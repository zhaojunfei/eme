//显示图片
function showDel(obj) {
	$(obj).find("img").css('visibility', 'visible');
}
//隐藏图片
function hideDel(obj) {
	$(obj).find("img").css('visibility', 'hidden');
}

//创建问题
function addQuestion() {
	var ques = document.getElementsByClassName("ques").length; // 已有多少个问题
	var quesNum = ques + 1; // 当前问题的序号
	// 创建问题和前两个选项
	var div1 = document.createElement("div");
	div1.setAttribute("class", "control-group");
	div1.innerHTML += "<div class='ques' ><div onmouseover='showDel(this)' onmouseout='hideDel(this)'><span>Q"
			+ quesNum
			+ ":</span><input type='text' id='Q"
			+ quesNum
			+ "' class='input-long question-style' placeholder='问卷题目'><image id='"+quesNum+"' style='width:20px;height:20px;margin-left:10px;cursor:pointer;visibility:hidden'  src='img/delques.jpg'  alt='删除' onclick='delQuestion(this)' ></div><div id='sel"
			+ quesNum
			+ "'><div onmouseover='showDel(this)' onmouseout='hideDel(this)' class='selector"
			+ quesNum
			+ " selector-style'><input type='radio'  name='S"
			+ quesNum
			+ "'><input type='text'  placeholder='选项'  class='ST"
			+ quesNum
			+ "'><image id='"
			+ quesNum
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer;visibility:hidden'  src='img/addsel.png'  alt='添加' onclick='addSelector(this)' ><image class='image"
			+ quesNum
			+ "' name='image"
			+ quesNum
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer;visibility:hidden'  src='img/delsel.gif'  alt='删除' onclick='delSelector(this)' ></div><div onmouseover='showDel(this)' onmouseout='hideDel(this)' class='selector"
			+ quesNum
			+ " selector-style'><input type='radio'  name='S"
			+ quesNum
			+ "'><input type='text'  placeholder='选项'   class='ST"
			+ quesNum
			+ "'><image id='"
			+ quesNum
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer;visibility:hidden'  src='img/addsel.png'  alt='添加' onclick='addSelector(this)' ><image class='image"
			+ quesNum
			+ "' name='image"
			+ quesNum
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer;visibility:hidden' src='img/delsel.gif' alt='删除' onclick='delSelector(this)'></div></div><div><input type='hidden' id='AST"
			+ quesNum + "'></div></div>";
	var section = document.createElement("section");
	section.appendChild(div1);
	var div = document.getElementById("div-content");
	div.appendChild(section);
	// 创建问题和前两个选项结束
}

//添加单个选项
function addSelector(obj) {
	// 根据每一个选项的class汇总，给每一个选项添加id属性
	/*
	 * var selectors = document.getElementsByClassName("selector" + obj.id +
	 * ""); var lastSelId = selectors.length + 1; for (var j = 0; j <
	 * selectors.length; j++) { var k = j + 1; selectors[j].setAttribute("id",
	 * "selector" + k + ""); }
	 */
	// 根据每一个选项的class汇总，给每一个选项的删除按钮添加id属性
	/*
	 * var images = document.getElementsByClassName("image" + obj.id + ""); var
	 * lastImaId = images.length + 1; for (var i = 0; i < images.length; i++) {
	 * var k = i + 1; images[i].setAttribute("id", "" + k + ""); }
	 */
	// 添加选项
	var div = document.getElementById("sel" + obj.id);
	var div1 = document.createElement("div");
	div1.setAttribute("class", "selector" + obj.id + " selector-style");
	/* div1.setAttribute("id", "selector" + lastSelId + ""); */
	div1.setAttribute("onmouseover", "showDel(this)");
	div1.setAttribute("onmouseout", "hideDel(this)");
	div1.innerHTML = "<input type='radio' name='S"
			+ obj.id
			+ "'><input type='text'  placeholder='选项'  class='ST"
			+ obj.id
			+ "'><image id='"
			+ obj.id
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer;visibility:hidden'  src='img/addsel.png'  alt='添加' onclick='addSelector(this)' ><image class='image"
			+ obj.id
			+ "' name='image"
			+ obj.id
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer;visibility:hidden' src='img/delsel.gif' alt='删除' onclick='delSelector(this)'>";
	div.appendChild(div1);
}
//删除单个选项
function delSelector(obj) {
	var selectors = document.getElementsByClassName("" + obj.name + "");
	if (selectors.length > 2) {
		$(obj).parent().remove();
	} else {
		alert("至少有两个选项！");
	}
	// 移除刪除符号所在的div
	/* $("#selector" + obj.id + "").remove(); */
}

function delQuestion(obj){
	alert(obj.id);
	$(obj).parent().parent().remove();
}

//最终提交时将每一个问题的选项内容组合起来
function linksel() {
	var ques = document.getElementsByClassName("ques").length; // 已有多少个问题
	// 将所有选项用连接符链接起来
	for (var j = 1; j <= ques; j++) {
		var selectors = document.getElementsByClassName("ST" + j);
		if (selectors.length != 0) {
			var selContent = "";
			for (var i = 0; i < selectors.length; i++) {
				if (i != selectors.length - 1) {
					selContent += selectors[i].value + "_";
				} else {
					selContent += selectors[i].value;
				}
			}

			alert(selContent);
		}
		document.getElementById("AST" + j + "").value = selContent;
	}

}