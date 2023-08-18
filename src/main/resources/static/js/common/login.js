$(document).ready(function(){
    $('#login').on('click', function() {
        var postData = {
            loginId: $('#exampleInputId').val(),
            loginPassword: $('#exampleInputPassword').val()
        }
        $.ajax({
            type: 'POST',
            contentType : 'application/json; charset=utf-8;',
            data: JSON.stringify(postData),
            dataType: 'json',
            url : '/api/login',
            success : function(response) {
                location.href = "/"
            },
            error : function(error){
                console.log(error)
            }
        });
    });

    // 비밀번호 찾기 임시조치
    $(document).on('click', '#forget-pw', function(e) {
      swal(
        'Error!',
        '잘못된 접근입니다.',
        'error'
      )
    });
});