$(function() {　　 //显示隐藏的文件名并上传状态切换
    $("#upload").on("change", "input[type='file']", function() {
        var filePath = $(this).val();
        console.log(filePath);
        //如果仅上传图片 if(filePath.indexOf("jpg") != -1 || filePath.indexOf("png") != -1) {
        if (filePath.indexOf("jpg") != -1 || filePath.indexOf("png") != -1) {
            var arr = filePath.split('\\');
            var fileName = arr[arr.length - 1];
            $('.showFileName').show();
            $('#uploadBtn').show();
            $(".showFileName").html("已选择文件名：" + fileName);
            // console.log($(".changed-image")[0].src);
            // $(".changed-image").attr("src", filePath);
            $('#upload').hide();
        } else {
            $(".showFileName").html("");
            alert("您未上传文件，或者您上传文件类型有误！");
            return false;
        }
    });
});

function showItem() {
    $('.showFileName').val("");
    $('.showFileName').hide();
    $('#uploadBtn').hide();
    $('#upload').show();
}


function uploadImg(){
    var formData=new FormData($(".img-form")[0])
    $.ajax({
        type:"POST",
        // dataType:"json",//服务器返回数据类型,不写就自动选择
        /**
         *必须false才会自动加上正确的Content-Type
         */
        data: formData,
        contentType: false,
        url:"/uploadImage",
        /**
         * 必须false才会避开jQuery对 data 的默认处理
         * XMLHttpRequest会对 data 进行正确的处理
         */
        processData: false,
        cache:false,
        success:function(result){
            if(result!=null){
                console.log(JSON.stringify(result));
                console.log("照片："+result);
                alert("上传成功，刷新即可");
                $(".changed-image").attr("src", result);
                $(".change-avatar>img").attr("src", result);
            }else{
                alert("上传失败");
            }
        },
        error:function () {
            alert("异常");
        }
    })
}
