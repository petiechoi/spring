(function ($) {
	$(document).ready(function() {
		$("#nav-l").load("/js/menu/sideMenu.html");
	});

    // 도서등록
	$('#book-create').click(function() {
	    var Title = $('#title').val();
	    var Author = $('#author').val();
	    var Publisher = $('#publisher').val();

	    let result = '책제목:'+ Title + '\n' +
	     '저자:' + Author + '\n' + '출판사:' + Publisher + '\n등록하시겠습니까?';
	     var postData = {
            title: Title,
            author:Author,
            publisher:Publisher
	     };
	     if(confirm(result)){
	        if(Title.length == 0 || Author.length == 0 || Publisher.length == 0){
	            alert("내용이 비어있습니다. 다시 작성해주십시오.");
	            return;
	        }

	        $.ajax({
	            type: 'POST',
	            contentType : 'application/json; charset=utf-8;',
	            data: JSON.stringify(postData),
	            dataType: 'json',
	            url : '/api/books',
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

    // 도서 수정
		$('#book-update').click(function() {
    	    var Title = $('#title').val();
    	    var Author = $('#author').val();
    	    var Publisher = $('#publisher').val();

    	    let result = '책제목:'+ Title + '\n' +
    	     '저자:' + Author + '\n' + '출판사:' + Publisher + '\n수정하시겠습니까?';
    	     var postData = {
                title: Title,
                author: Author,
                publisher: Publisher
    	     };
    	     if(confirm(result)){
    	        if(Title.length == 0 || Author.length == 0 || Publisher.length == 0){
    	            alert("내용이 비어있습니다. 다시 작성해주십시오.");
    	            return;
    	        }

    	        $.ajax({
    	            type: 'PUT',
    	            contentType : 'application/json; charset=utf-8;',
    	            data: JSON.stringify(postData),
    	            dataType: 'json',
    	            url : '/api/books/'+ $('#bid').val(),
    	            success: function(response){
    	                if(response.success)
    	                    history.back();
    	                else
    	                    alert(response.message);
    	            },
    	            error: function(error){
    	                alert("오류");
    	            }
    	        });
    	     }
    	});
})(jQuery);

