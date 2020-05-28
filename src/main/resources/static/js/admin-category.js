$(document).ready(function () {
    $("#exampleModalLong").on("hide.bs.modal", function () {
        $("#input-category-id").val('');
        $("#input-category-name").val('');
        $("#input-category-desc").val('');
    });

    $(".del-category").on("click", function () {
        var categoryId = $(this).data("category");
        console.log(categoryId);
        var linkPost="/api/category/delete/"+categoryId;

        swal({
            title: 'Do you want to delete this category?',
            text: "You can not restore this after deleting!",
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
                                text:'Successfully Delete',
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
                        'Failed',
                        'Error writing database',
                        'error'
                    );
                });


            }
        });

    });

    $(".edit-category").on("click", function () {
        var categoryId = $(this).data("category");
        console.log(categoryId);
        $.ajax({
            url:"/api/category/detail",
            dataType:'json',
            type:'POST',
            data: {
                categoryId: categoryId
            },
            success: function (data) {
                $("#input-category-id").val(data.id);
                $("#input-category-name").val(data.name);
                $("#input-category-desc").val(data.shortDesc);
            },
            error: function(xhr, status, err)  {
                console.log(err.toString());
            }
        })
    });

    $(".btn-save-category").on("click", function () {
        if($("#input-category-name").val() === "" || $("#input-category-desc").val() === "" ) {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        var  category={};
        category.name = $('#input-category-name').val();
        category.shortDesc = $('#input-category-desc').val();
        category.id = $("#input-category-id").val();

       // console.log(supply.id);

        // console.log(supply.id);?
        var linkPost = "/api/category/create";
        if(category.id) {
            linkPost = "/api/category/update/" + category.id;
        }
        NProgress.start();
        axios.post(linkPost, category).then(function(res){
           NProgress.done();
            if(res.data.success) {
                swal(
                    {
                        title:'Success',
                        text:'Successfully Saved Data',
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
                'Some error when saving category',
                'error'
            );
        })

    });



});