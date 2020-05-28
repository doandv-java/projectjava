$(document).ready(function () {
   $("#submit").on("click", function () {
       var data={};
       data.email=$("#email").val();
       data.title=$("#title").val();
       data.content=$("#content").val();
        console.log("data: ", data);
       $.ajax({
           url:"/api/contact/add",
           data: JSON.stringify(data),
           type:"POST",
           dataType:"json",
           contentType: "application/json",
           success: function (data) {
               if (data.success === true) {
                   swal({
                       title:'Success',
                       text:data.message,
                       type:'success',
                       showCancelButton: false,
                       timer:1500
                       }
                   ).then(function() {
                       location.replace("/");
                   });
               } else{
                   swal(
                       'Fail',
                       data.message,
                       'error'
                   );
               }
           }.bind(this),
           error: function (e) {
               console.log(e);
           }
       });
   }) ;
});