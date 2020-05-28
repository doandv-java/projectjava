$(document).ready(function () {
    $("#input-nameProduct").autocomplete({
        open: function () {
            setTimeout(function () {
                $('.ui-autocomplete').css('z-index', 99999999999999);
            }, 0);
        },
        select: function (event, ui) {
            $("#input-productId").val(ui.item.id);
         //   $('#lbl').text(ui.item.label);
            alert($("#input-productId").val());
        },
        source: function (request, response) {
            //                console.log("call: " + request.term);
            $('#lbl').text('');
            $("#input-productId").val('');
            request = $.ajax({
                url: "/api/product/listSearch",
                timeout: 6000,
                data: "{ 'productName': '" + request.term + "' }",
                dataType: "json",
               type: "POST",
                contentType: "application/json; charset=utf-8",
                dataFilter: function (data) { return data; },
                success: function (data) {
                    response($.map(data.data, function (item) {
                        return {
                            label: item.id + " - " + item.name ,
                            value: item.name,
                            name: item.name,
                            code: item.id
                        }
                    }))

                    console.log("demo: ",   data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //alert(textStatus);
                    if (textStatus === 'timeout') {
                        request.abort();
                        alert('Can not find product');

                    }
                }
            });
        },
        minLength: 1
    });


    $(".detailProductEntity").click(function () {
        var pdInfo = $(this).data("product");
        $.ajax({
            url:"/api/product/AmountProductEntity/"+pdInfo,
            dataType:'json',
            type:'POST',
            data: {
                productId: pdInfo
            },
            success: function (data) {
                $("#productEntity").empty();
                var list=data.data;
                console.log("data : ",list);
                for(var i=0; i<list.length;i++){
                    var row=$("<tr style=\"height: 20px\"></tr>")
                    var row=$("<tr style=\"height: 20px\"></tr>")
                    var td1=$("<td colspan=\"1\">"+(i+1)+"</td>");
                    var td2=$("<td colspan=\"3\">"+list[i].sizeName+"</td>");
                    var td3=$("<td colspan=\"3\">"+list[i].colorName+"</td>");
                    var td4=$("<td colspan=\"5\">"+list[i].amount+"</td>");
                    row.append(td1);
                    row.append(td2);
                    row.append(td3);
                    row.append(td4);

                    $("#productEntity").append(row);
                }


            },
            error: function(xhr, status, err)  {
                console.log(err.toString());
            }
        })
    });

    $("#addProductEntity").on("click",function () {
        $("#input-product").val("");
        $("#input-color").val("");
        $("#input-size").val("");
        $("#input-amount").val("");
    });

    $(".btn-save-productEntity").on("click",function () {
        var data={};
        data.productId=$("#input-product").val();
        data.colorId=$("#input-color").val();
        data.sizeId=$("#input-size").val();
        data.amount=$("#input-amount").val();

        console.log("data: ",data);
        NProgress.start();
        var linkPost = "/api/product-entity/add";


        axios.post(linkPost, data).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    {
                        title:'Success',
                        text:res.data.message,
                        type:'success',
                        // showCancelButton: false,
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
                'Some error when saving product',
                'error'
            );l
        })
    });

    });