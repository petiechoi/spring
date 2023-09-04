(function ($) {
	$(document).ready(function() {
		$("#nav-l").load("/js/menu/sideMenu.html");
	});

	$('#file-create').on("click", function(e){
	    var form = $('#form')[0];
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/api/files',
	        enctype: 'multipart/form-data',
	        contentType: false,
            processData: false,
	        data : formData,
	        type : 'POST',
	        success: function(response){
                if(response.success)
                    location.replace(location.href);
                else
                    alert(response.message);
	        },
	        error : function(error){
	            alert("오류");
	        }
	    });
	});
})(jQuery);

