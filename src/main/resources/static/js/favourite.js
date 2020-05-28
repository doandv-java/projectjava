$(document).ready(function (){
   // $(".btn-addfavourite").on("click",function () {
   //      var productId= $(this).data("product");
   //      alert("productId: ", productId);
   // }) ;
    $('.js-addwish-b2').on('click', function(e){
        e.preventDefault();
    });

    $('.js-addwish-b2').each(function(){
        var nameProduct = $(this).parent().parent().find('.js-name-b2').html();
        $(this).on('click', function(){
            var data={};
            data.productId = $(this).data("product");
            data.guid = getCookie("guid");

            NProgress.start();
            console.log("data: ",data);
            var linkPost = "/api/favourite/add";
            console.log("data: ", data);
            axios.post(linkPost, data).then(function(res){
                NProgress.done();
                if(res.data.success) {
                    swal(
                        {
                            title:'Success',
                            text:res.data.message,
                            type:'success',
                            showCancelButton: false,
                            timer:1500
                        }
                    );

                    $(this).addClass('js-addedwish-b2');
                    $(this).off('click');
                } else {
                    swal(
                        'An Error Occurred',
                        res.data.message,
                        'error'
                    );
                }
            }, function(err){
                NProgress.done();
                swal(
                    'Error',
                    'Fail',
                    'error'
                );
            });


        });
    });

});