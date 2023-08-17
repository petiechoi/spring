$(document).ready(function() {
  $('#dataTable').DataTable();

  $('.table-point').click(function() {
          var td = $(this).children();
          var boId = td.eq(0).val();
          var postData = {id: boId};
          let result = "상태를 변경하시겠습니까?";
           if(confirm(result))
           {
            if(td.eq(6)[0].id == 'B_APPLY'){
              $.ajax({
                  type:'POST',
                  contentType :'application/json; charset=utf-8;',
                  data:JSON.stringify(postData),
                  dataType:'json',
                  url :'/api/borrow/apply/' + boId,
                  success :function(response) {
                  if( response.success )
                    location.replace(location.href);
                  else
                    alert(response.message);
                  },
                  error:
                  function(error) {
                      alert("오류");
                  }
              });
            }
            else if(td.eq(6)[0].id == 'B_PROGRESS'){
              $.ajax({
                  type:'DELETE',
                  contentType :'application/json; charset=utf-8;',
                  data:JSON.stringify(postData),
                  dataType:'json',
                  url :'/api/borrow/' + boId,
                  success :function(response) {
                  if( response.success )
                    location.replace(location.href);
                  else
                    alert(response.message);
                  },
                  error:
                  function(error) {
                      alert("오류");
                  }
              });
            }
            else return;
           }
  });
});
