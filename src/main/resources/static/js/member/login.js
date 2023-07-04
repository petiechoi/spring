//$(document).ready(function(){
//    $('#login').on('click', function() {
//        var postdata = {
//            loginId: $('#exampleInputId').val(),
//            loginPassword: $('#exampleInputPassword').val()
//        }
//        console.log(postdata.loginId)
//        console.log(postdata.loginPassword)
//        $.ajax({
//            type: 'POST',
//            contentType : 'application/json; charset=utf-8;',
//            data: JSON.stringify(postdata),
//            dataType: 'json',
//            url : '/login',
//            success : function(response) {
//             window.location.href= '/'
////                alert(response); 무슨 괴물을 만든거야..zzzzzzzzz일단 해보고있겠음ㅅㅂ ㅠㅎ ㄱㄷ ㄱㄱ
////                if(response['modim']){
////                    alert('로그인 성공')
////                    console.log('fucking suc')
////                    window.location.href= '/'
////                }
////                else {
////                    console.log('fucking fail')
////                    alert('fail')
////                }
//            },
//            error : function(error){
////                alert("error");
//                console.log(error)
//                alert(error)
//            }
//        });
//    });
//});