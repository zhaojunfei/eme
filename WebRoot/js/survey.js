function hidden(){
	alert("fdsfsd ");
}
function addQuestion() {
	var ques = document.getElementsByClassName("ques").length; // 已有多少个问题
	var quesNum = ques + 1; // 当前问题的序号
	var selectors = document.getElementsByClassName("ST" + ques);
	if (selectors.length != 0) {
		var selContent = "";
		for (var i = 0; i < selectors.length; i++) {
			if (i != selectors.length - 1) {
				selContent += selectors[i].value + "_";
			} else {
				selContent += selectors[i].value;
			}
		}
		document.getElementById("AST" + ques + "").value = selContent;
	}
	var div1 = document.createElement("div");
	div1.setAttribute("class", "control-group");
	div1.innerHTML += "<div class='ques'><span>Q"
			+ quesNum
			+ ":</span><input type='text' id='Q"
			+ quesNum
			+ "' class='input-long' placeholder='问卷题目'></div><div id='sel"
			+ quesNum
			+ "'><p onFocus='hidden()' class='selector"
			+ quesNum
			+ "'><input type='radio'  name='S"
			+ quesNum
			+ "'><input type='text'  placeholder='选项'  class='ST"
			+ quesNum
			+ "'><image class='image"
			+ quesNum
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer'  src='img/delsel.gif'  alt='删除' onclick='delSelector(this)'></p><p class='selector"
			+ quesNum
			+ "'><input type='radio'  name='S"
			+ quesNum
			+ "'><input type='text'  placeholder='选项'   class='ST"
			+ quesNum
			+ "'><image class='image"
			+ quesNum
			+ "' style='width:10px;height:10px;margin-left:10px;cursor:pointer' src='img/delsel.gif' alt='删除' onclick='delSelector(this)'></p></div><div><input type='hidden' id='AST"
			+ quesNum + "'><input type='button' id='" + quesNum
			+ "' class='btn' value='添加选项' onclick='addSelector(this)'></div>";
	var section = document.createElement("section");
	section.appendChild(div1);
	var div = document.getElementById("div-content");
	div.appendChild(section);
}
function addSelector(obj) {
	// 根据每一个选项的class汇总，给每一个选项添加id属性
	var selectors = document.getElementsByClassName("selector" + obj.id + "");
	var lastSelId = selectors.length + 1;
	for (var j = 0; j < selectors.length; j++) {
		var k = j + 1;
		selectors[j].setAttribute("id", "selector" + k + "");
	}
	// 根据每一个选项的class汇总，给每一个选项的删除按钮添加id属性
	var images = document.getElementsByClassName("image" + obj.id + "");
	var lastImaId = images.length + 1;
	for(var i=0;i<images.length;i++){
		var k=i+1;
		images[i].setAttribute("id",""+k+"");
	}
	// 添加选项
	var div = document.getElementById("sel" + obj.id);
	var p = document.createElement("p");
	p.setAttribute("class", "selector" + obj.id + "");
	p.setAttribute("id", "selector" + lastSelId + "");
	p.innerHTML = "<input type='radio' name='S"
			+ obj.id
			+ "'><input type='text'  placeholder='选项'  class='ST"
			+ obj.id
			+ "'><image class='image"
			+ obj.id
			+ "' id='"+lastImaId+"' style='width:10px;height:10px;margin-left:10px;cursor:pointer' src='img/delsel.gif' alt='删除' onclick='delSelector(this)'>";
	div.appendChild(p);
}

function delSelector(obj) {

	$("#selector"+obj.id+"").remove();
}

