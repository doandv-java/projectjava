$(document).ready(function () {
    $('.date').datetimepicker({
        format: 'DD/MM/YYYY',
        keepInvalid: true
    });

    $(".edit-promotion").on("click", function () {
        var promotionId = $(this).data("promotion");
        console.log(promotionId);
        $.ajax({
            url:"/api/promotion/detail",
            dataType:'json',
            type:'POST',
            data: {
                promotionId: promotionId
            },
            success: function (data) {
                console.log(data);
                $("#input-id").val(data.id);
                $("#input-name").val(data.name);
                $("#input-desc").val(data.shortDesc);
                $("#input-discount").val(data.discount);
                $("#input-beginDate").val(new Date(data.beginDate));
                $("#input-endDate").val(new Date(data.endDate));
                console.log(new Date(data.endDate));
            },
            error: function(xhr, status, err)  {
                console.log(err.toString());
            }
        })
    });

    $(".del-promotion").on("click", function () {
        var promotionId = $(this).data("promotion");
        console.log(promotionId);
        var linkPost="/api/promotion/delete/"+promotionId;
        NProgress.start();
        axios.post(linkPost).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Some error when deleting promotion',
                'error'
            );
        });
    });

    $(".btn-save").on("click", function () {
        if($("#input-name").val() === "" || $("#input-desc").val() === "" ) {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        var  promotion={};
        promotion.name = $('#input-name').val();
        promotion.shortDesc = $('#input-desc').val();
        promotion.id = $("#input-id").val();
        promotion.discount=$('#input-discount').val();
        promotion.beginDate=$('#input-beginDate').val();
        promotion.endDate=$('#input-endDate').val();

        console.log(promotion);

        var linkPost = "/api/promotion/create";
        if(promotion.id) {
            linkPost = "/api/promotion/update/" + promotion.id;
        }
        NProgress.start();
        axios.post(linkPost, promotion).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Some error when saving promotion',
                'error'
            );
        })

    });



});