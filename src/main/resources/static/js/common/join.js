$(document).ready(function(){
    $('.form-control').each(function(){
        $(this).focus(function(){
           hideInvalidate(this);
        });
    });

    function validate (input) {
        var ret = true;

        if($(input).attr('type') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
        return true;
    }

    function pw_validate() {
    	var InputPassword = $('#InputPassword').val(); /* 패스워드와 패스워드 확인 부분 가져오기 */
    	var RepeatPassword = $('#RepeatPassword').val();

    	if(InputPassword == RepeatPassword) {
    	    hideInvalidate($('#InputPassword'));
            hideInvalidate($('#RepeatPassword'));
            return true;
    	}
    	else{
            showInvalidate($('#InputPassword'));
            showInvalidate($('#RepeatPassword'));
            return false;
        }
    }


    function showInvalidate(input) {
        var thisAlert = $(input);
        $(thisAlert).addClass('is-invalid');
    }

    function hideInvalidate(input) {
        var thisAlert = $(input);
        $(thisAlert).removeClass('is-invalid');
    }


    $("#InputPassword, #RepeatPassword").on("change", function() {
    	var InputPassword = $('#InputPassword').val(); /* 패스워드와 패스워드 확인 부분 가져오기 */
    	var RepeatPassword = $('#RepeatPassword').val();
    	if( !InputPassword.length || !RepeatPassword.length)
    	    return;
    	pw_validate();
    });

    $('#join').on('click', function() {
        var input = $('.form-control');
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showInvalidate(input[i]);
                check=false;
            }
        }
        if(check == false)
            return;
        if(!pw_validate()){
            alert("패스워드가 일치하지 않습니다.")
            return;
        }

        var postData = {
            loginId: $('#InputId').val(),
            loginPassword: $('#InputPassword').val(),
            name: $('#Name').val(),
            email: $('#InputEmail').val()
        }
        $.ajax({
            type: 'POST',
            contentType : 'application/json; charset=utf-8;',
            data: JSON.stringify(postData),
            dataType: 'json',
            url : '/api/join',
            success : function(response) {
                if( response.success ){
                        Toast = Swal.mixin({
                          toast: true,
                          position: 'center-center',
                          showConfirmButton: false,
                          timer: 2500,
                          timerProgressBar: true,
                          didOpen: (toast) => {
                            toast.addEventListener('mouseenter', Swal.stopTimer)
                            toast.addEventListener('mouseleave', Swal.resumeTimer)
                          }
                        })
                        Toast.fire({
                          icon: 'success',
                          title: '환영합니다! 회원가입을 완료하였습니다.'
                        });
                        location.href = "/";
                }
                else{
                var data = response.data;
                console.log(data);
                let msg = response.message;
                alert(msg);
                }
            },
            error : function(error){
            console.log(error);
                // 오류
            }
        });
    });
});