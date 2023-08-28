$(document).ready(function(){
    $('#login').on('click', function() {
        login();
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

window.addEventListener( 'keyup', e => {
    if(e.keyCode === 13){
        login();
    }
});


function login(){
        var postData = {
            loginId: $('#exampleInputId').val(),
            loginPassword: $('#exampleInputPassword').val()
        }
        if( !postData.loginId.length || !postData.loginPassword.length)
        {
            alert("아이디와 패스워드를 모두 입력해주십시오.");
            return;
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
                alert("아이디와 패스워드를 확인해주십시오");
                console.log(error);
            }
        });
}