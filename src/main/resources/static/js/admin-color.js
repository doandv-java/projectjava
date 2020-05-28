$(document).ready(function () {

    $("#exampleModalLong").on("hide.bs.modal", function () {
        $("#input-id").val('');
        $("#input-name").val('');
        $("#input-desc").val('');
    });


    $(".del-color").on("click", function () {
        var colorId = $(this).data("color");
        var linkPost="/api/color/delete/"+colorId;
        swal({
            title: 'Do you want to delet this color?',
            text: "You can not restore after deleting!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Delete',
            cancelButtonText:'Cancel'
        }).then(function(result)  {
            if (result.value) {
                NProgress.start();
                axios.post(linkPost).then(function(res){
                    NProgress.done();
                    if(res.data.success) {
                        swal(
                            {
                                title:'Success',
                                text:'Successfully',
                                type:'success',
                                showCancelButton: false,
                                timer:1500
                            }
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
                        'Some error when deleting color',
                        'error'
                    );
                });


            }
        });

    });

    $(".edit-color").on("click", function () {
        var colorId = $(this).data("color");
        $.ajax({
            url:"/api/color/detail",
            dataType:'json',
            type:'POST',
            data: {
                colorId: colorId
            },
            success: function (data) {
                console.log(data);
                $("#input-id").val(data.id);
                $("#input-name").val(data.name);
                $("#input-desc").val(data.shortDesc);
            },
            error: function(xhr, status, err)  {
                console.log(err.toString());
            }
        })
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

        var  color={};
        color.name = $('#input-name').val();
        color.shortDesc = $('#input-desc').val();
        color.id = $("#input-id").val();

       // console.log(supply.id);

        // console.log(supply.id);?
        var linkPost = "/api/color/create";
        if(color.id) {
            linkPost = "/api/color/update/" + color.id;
        }
        NProgress.start();
        axios.post(linkPost, color).then(function(res){
           NProgress.done();
            if(res.data.success) {
                swal(
                    {
                        title:'Success',
                        text:'Updated Successfully',
                        type:'success',
                        showCancelButton: false,
                        timer:1500
                    }
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
                'Some error when saving color',
                'error'
            );
        })

    });



});