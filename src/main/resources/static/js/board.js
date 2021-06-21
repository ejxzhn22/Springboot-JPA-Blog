let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
		
		$("#btn-update").on("click", () => {
			this.update();
		});
		
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});
		
	},

	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		}
		
		$.ajax({
			type:"POST",
			url:"/api/board",
			data: JSON.stringify(data), //http body 데이터
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
			dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면 자바스크립트 오브젝트로 변경)
			
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	deleteById: function() {
		let id =$("#id").text();
		
		$.ajax({
			type:"DELETE",
			url:"/api/board/"+id,
			dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면 자바스크립트 오브젝트로 변경)
			
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		
		let id = $("#id").val();
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		}
		
		$.ajax({
			type:"PUT",
			url:"/api/board/"+id,
			data: JSON.stringify(data), //http body 데이터
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
			dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면 자바스크립트 오브젝트로 변경)
			
		}).done(function(resp){
			alert("글 수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	replySave: function() {
		let data = {
			content: $("#reply-content").val()
		};
		let boardId = $("#boardId").val();
		
		$.ajax({
			type:"POST",
			url:`/api/board/${boardId}/reply`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
			
		}).done(function(resp){
			alert("댓글작성이 완료되었습니다.");
			location.href=`/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
	},
	replyDelete: function(boardId, replyId) {

		$.ajax({
			type:"DELETE",
			url:`/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
			
		}).done(function(resp){
			alert("댓글삭제 성공.");
			location.href=`/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

index.init();