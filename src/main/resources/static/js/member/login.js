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
                location.href = "/"
            },
            error : function(error){
                console.log(error)
            }
        });
    });
});