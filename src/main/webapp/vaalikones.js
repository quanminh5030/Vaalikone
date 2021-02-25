function getAllQuestions() {

	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var list = this.responseText;

			questionList = JSON.parse(list);
			var txt = "";
	
			for (index in questionList) {
				txt += questionList[index].kysymysId + "<br>";
				txt += questionList[index].kysymys + "<br>";
			}
			document.getElementById("list").innerHTML = txt;
		}
	};
	xmlhttp.open("GET", "/rest/questionservice/getallquestions", true);
	xmlhttp.send();
}