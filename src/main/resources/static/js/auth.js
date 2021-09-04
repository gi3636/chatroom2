function getToken(username, password) {
  let data = {
    username:username,
    password:password
  }
  $.ajax({
    method:"POST",
    url:"/login2",
    type:"json",
    async:false,
    contentType:"application/json;charset=utf-8",
    data:JSON.stringify(data),
    success:(result)=>{
      console.log("获取token");
      $.cookie("token",result.data.token,{expires:1,path:"/"});
      console.log("cookie:"+$.cookie("token"))
      console.log(result);
    },
    error:(result)=>{
      console.log("获取token失败");
      console.log(result);
    }
  })
}

function checkToken(){
  // $.ajax({
  //   headers: {
  //     token: $.cookie("token")
  //   },
  //   method:"GET",
  //   url:"/checkToken",
  //   type:"json",
  //   contentType: "application/json;charset=utf-8",
  //   success:(result)=>{
  //     console.log(JSON.stringify(result));
  //   },
  //   error:(result)=>{
  //     if(result.code === 20021){
  //       alert("请重新登入，登入已超时");
  //       window.location.href = "/login";
  //     }
  //     alert(result.msg);
  //   }
  // })
}
function deleteToken(){

}