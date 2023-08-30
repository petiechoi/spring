(function ($) {
	$(document).ready(function() {
		$("#nav-l").load("/js/menu/sideMenu.html");
	});

	$('#file-create').on("click", function(e){
	    var form = $('#form')[0];
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/file',
	        enctype: 'multipart/form-data',
	        contentType: false,
            processData: false,
	        data : formData,
	        type : 'POST',
	        success: function(result){
	            alert("성공");
	        },
	        error : function(res){
	            alert("실패");
	        }
	    });
	});
})(jQuery);

