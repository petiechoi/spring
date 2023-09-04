function function1(dataUrl, filename){
    alert("zz");
    let zzz =dataUrl + '/' + filename;
    console.log(zzz);

    var xhr = new XMLHttpRequest();
//    $.ajax({
//        type:'GET',
//        url: dataUrl + '/' + filename,
//        success: function(response){
//            if(response.success){
//                console.log('dd');
//            }
//            else {
//                console.log('xx');
//            }
//        },
//        error: function(error){
//            alert("오류");
//        }
//    });
}