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
//                            Toast.fire({
//                            icon:'success',
//                            title:'대여 신청되었습니다.'
//                            });

                            location.href = "/"
                        },
                        error : function(error){
                        // 만약 로그인 안되어있다면,,,?
                            console.log(error.body)
                                // 요청이 실패하거나 오류가 발생했을 때의 처리
                                console.log('오류 상태 코드:', error.status);
                                console.log('오류 상태 메시지:', error.statusText);
                                console.log('오류 응답 JSON 데이터:', error.responseJSON);
                                console.log('오류 응답 텍스트 내용:', error.responseText);
                        }
                    });
         }
});
});

