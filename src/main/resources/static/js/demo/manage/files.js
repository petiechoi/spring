(function ($) {
	$(document).ready(function() {
		$("#nav-l").load("/js/menu/sideMenu.html");
	});

	$('#table tr').click(function() {
	    var tr = $(this);
        var td = tr.children();
	    var fid = td.eq(0).val();

	    let result = "삭제하시겠습니까?";
	    if(confirm(result))
	    {
	        $.ajax({
                  type:'DELETE',
                  url :'/api/files/' + fid,
                  success :function(response) {
                  if( response.success )
                    location.replace(location.href);
                  else
                    alert(response.message);
                  },
                  error:
                  function(error) {
                      alert("오류");
                  }
	        });
	    }
	});
})(jQuery);

