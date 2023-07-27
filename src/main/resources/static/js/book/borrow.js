$(function () {
$('.table-point').click(function() {
        var tr = $(this);
        var td = tr.children();
        var bookid = td.eq(0).text();
        var postData = {id:bookid};
        let result = "'" + td.eq(1).text() + "'" + "을(를) 대여하시겠습니까?";

         if(confirm(result))
         {
                    $.ajax({
                        type: 'POST',
                        contentType : 'application/json; charset=utf-8;',
                        data: JSON.stringify(postData),
                        dataType: 'json',
                        url : '/api/borrow',
                        success : function(response) {
                            Toast.fire({
                            icon:'success',
                            title:'대여 신청되었습니다.'
                            });

                            location.href = "/"
                        },
                        error : function(error){
                        // 만약 로그인 안되어있다면,,,?
                            console.log(error)
                        }
                    });
         }
});
});

