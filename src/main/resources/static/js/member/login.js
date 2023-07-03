$(document).ready(function(){
    $('#login').on('click', function() {
        var postdata = {
            loginId: $('#exampleInputId').val(),
            loginPassword: $('#exampleInputPassword').val()
        }
        console.log(postdata.loginId)
        console.log(postdata.loginPassword)
        $.ajax({
            type: 'POST',
            contentType : 'application/json; charset=utf-8;',
            data: JSON.stringify(postdata),
            dataType: 'json',
            url : '/api/login',
            success : function(response) {
                if(response['token']){
                    alert('로그인 성공')
                    window.location.href= '/'
                }
                else {
                    alert('fail')
                }
            },
            error : function(){
                alert("error");
            }
        });
    });
});