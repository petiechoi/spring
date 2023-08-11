(function ($) {
	$(document).ready(function() {
		$("#nav-l").load("/js/book/sidemenu.html");
	});

	$('#book-create').click(function() {
	    let result = '책제목:'+$('#title').val() + '\n' +
	     '저자:' + $('#author').val()+ '\n' + '출판사:' + $('#publisher').val() + '\n등록하시겠습니까?';
	     var postData = {
            title: $('#title').val(),
            author:$('#author').val(),
            publisher:$('#publisher').val()
	     };
	     if(confirm(result)){
	        console.log("진입");
	        $.ajax({
	            type: 'POST',
	            contentType : 'application/json; charset=utf-8;',
	            data: JSON.stringify(postData),
	            dataType: 'json',
	            url : '/api/book',
	            success: function(response){
	                if(response.success)
	                    location.replace(location.href);
	            },
	            error: function(error){
	                alert("오류");
	            }
	        });
	     }
	});
})(jQuery);
