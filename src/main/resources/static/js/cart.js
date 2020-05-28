$(document).ready(function () {
    $(".change-product-amount").on("change",function () {

        var data={};
        data.id=$(this).data('id');
        data.amount= $(this).val();
        NProgress.start();

        var linkPost = "/api/cart-product/update";

        axios.post(linkPost, data).then(function(res){
            NProgress.done();
            if(res.data.success) {
                location.reload();
            } else {
                swal(
                    'Fail',
                    res.data.message,
                    'error'
                ).then(function() {
                    location.reload();
                });
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


    $('.deleteCartProduct').on("click", function () {
        var id=$(this).data('id');

        swal({
            title: 'Delete from cart?',
            text: "You can not restore this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Delete',
            cancelButtonText:'Cancel'
        }).then(function(result)  {
            if (result.value) {
                NProgress.start();
                var linkGet = "/api/cart-product/delete/"+id;
                axios.get(linkGet).then(function(res){
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
                        ).then(function() {
                            location.reload();
                        });
                    } else {
                        swal(
                            'Fail',
                            res.data.message,
                            'error'
                        );
                    }
                }, function(err){
                    NProgress.done();
                    swal(
                        'Fail',
                        'An Error Occurred',
                        'error'
                    );
                });
        }
        });




    });


    // $('#checkout-sonnx').on("click", function () {
    //     alert("ahihi");
    //     var order ={};
    //     order.customerName=$("#input-name").val();
    //     order.email=$("#input-email").val();
    //     order.phoneNumber=$("#input-phone-number").val();
    //     order.address=$("#input-address").val();
    //     console.log("data: ", order);
    //
    //     NProgress.start();
    //
    //     var linkPost = "/order/checkout";
    //     axios.post(linkPost, order).then(function(res){
    //         NProgress.done();
    //         if(res.data.success) {
    //             console.log("data: ", res.data);
    //          //   location.reload();
    //             window.location.href = '/order/history';
    //         } else {
    //
    //         }
    //     }, function(err){
    //         NProgress.done();
    //         swal(
    //             'Error',
    //             'Fail',
    //             'error'
    //         );
    //     });
    // });
});