$(document).ready(function () {


    $(".quickView").on("click",function () {
        var productId=$(this).data("id");
        console.log("id: ",productId );
        var data={
            productId:productId
        }

        $.ajax({
            url:"/api/product/quick-view",
            data: JSON.stringify(productId),
            type:"POST",
            dataType:"json",
            contentType: "application/json",
            success: function (data) {
                if (data.success === true) {
                    console.log(data.data)

                    $("#qv-image").append(renderImage(data.data.mainImage));
                    var listImage=data.data.productImageVMList;
                    console.log('item: ',listImage);

                    for (var i=0; i<listImage.length;i++) {
                        console.log('item: ',listImage[i]);
                        $("#qv-image").append(renderImage(listImage[i].link));
                    }

                }
                else {

                }
            }.bind(this),
            error: function (e) {
                console.log(e);
            }
        });
    });

    function renderImage(link) {
        var result=$("<div class='item-slick3' ></div>");
        result.attr('data-thumb',link);
        var div=$("<div class='wrap-pic-w pos-relative'></div>");
        var img=$("<img  alt='IMG-PRODUCT'>");
        img.attr('src',link);
        // console.log("img: ",img);
        var a=$("<a class='flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04'></a>")
        a.attr('href',link);
        var i=$("<i class='fa fa-expand'></i>");
        a.append(i);
        div.append(img);
        div.append(a);
        result.append(div);


        return result;
    }

    $(".reply").on("click", function () {
        var rateId=$(this).data("rate");
        $("#"+rateId+"_reply").attr("hidden",false);
    });

    $(".rateReply").on("click", function () {
        var rateId=$(this).data("rate");

        var data={};
        data.star= 0;
        data.comment=$("#"+rateId+"_reviewreply").val();
        data.productId=0;
        data.parentId=$(this).data("rate");
        if(data.comment==0){
            swal(
                'An Error Occurred',
                'You did not leave any comment',
                'error'
            );
            $("#review").focus();
            return;
        }

        NProgress.start();

        var linkPost = "/api/rate/add";

        axios.post(linkPost, data).then(function(res){
            NProgress.done();
            if(res.data.success) {
                location.reload();
                // swal({
                //         title: 'Success',
                //         text:  res.data.message,
                //         type:'success' ,
                //         timer:1500
                //     }
                //
                // ).then(function() {
                //     location.reload();
                // });
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