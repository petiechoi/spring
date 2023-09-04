$(function () {
$('.table-point').click(function() {
        var td = $(this).children();
        var bId = td.eq(0).val();
        var bTitle = td.eq(1).text();
        var postData = {id: bId};
        let result = "'" + bTitle + "'" + "을(를) 대여하시겠습니까?";
         if(confirm(result))
         {
            $.ajax({
                type:'POST',
                contentType :'application/json; charset=utf-8;',
                data:JSON.stringify(postData),
                dataType:'json',
                url :'/api/borrows',
                success :function(response) {
                if (!response.success) {
                    alert(response.message);
                    location.href="/login";
                } else {
                    location.href = "/";
                }
                },
                error:
                function(error) {
                    alert("오류");
                }
            });
         }
});
});

