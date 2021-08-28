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
      $.cookie("token",result.data.token,{expires:1});
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
  
}
function deleteToken(){

}