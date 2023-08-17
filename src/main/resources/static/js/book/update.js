(function ($) {
	$(document).ready(function() {
		$("#nav-l").load("/js/book/sidemenu.html");
		$('#dataTable').DataTable();
	});
})(jQuery);

function deleteBook(id){
    let result = '삭제하시겠습니까?';
    if (confirm(result)){
        $.ajax({
            type: 'DELETE',
        	url : '/api/book/'+ id,
        	success: function(response){
        	    if(response.success)
        	        location.replace(location.href);
        	},
        	error: function(error){
        	    alert("오류");
        	}
        });
    }
}