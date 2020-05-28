$(document).ready(function () {
    $(".js-show-modal1").on("click",function () {
        var productId = $(this).data("product");
        console.log("product: ",productId);

    });

    $(".showCart").on("click",function () {
       var list = vm.cartProductVMList;
       console.log("data: ",list);
    });
    // $(".showProductDetail").on("click",function () {
    //     var productId = $(this).data("product");
    //     console.log("product: ",productId);
    //
    //     $.ajax({
    //         url:"/product/"+productId,
    //         data: JSON.stringify(productImage),
    //       //  type:"POST",
    //         dataType:"json",
    //         contentType: "application/json",
    //         success: function (data) {
    //
    //         }.bind(this),
    //         error: function (e) {
    //             console.log(e);
    //         }
    //     });
    // });
});