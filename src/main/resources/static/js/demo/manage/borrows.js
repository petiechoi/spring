$(document).ready(function() {
  $('#dataTable').DataTable();
  $("#nav-l").load("/js/menu/sideMenu.html");

  $('.table-point').click(function() {
          var td = $(this).children();
          var bid = td.eq(0).val();
          let result = "상태를 변경하시겠습니까?";
           if(confirm(result))
           {
            if(td.eq(6)[0].id == 'B_APPLY'){
              $.ajax({
                  type:'POST',
                  url :'/api/borrows/' + bid,
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
                  url :'/api/borrows/' + bid,
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
