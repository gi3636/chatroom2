/**
 * 初始化，验证token以及获取当前用户的数据
 */
function init() {
  let user = null;
  $.ajax({
    headers: {
      token: $.cookie("token")
    },
    async: false,
    method: "GET",
    url: "/checkToken",
    type: "json",
    contentType: "application/json;charset=utf-8",
    success: (result) => {
      if (result["code"] === 20021) {
        alert("请重新登入，登入已超时");
        window.location.href = "/login";
      }
      user = result.data.user;
    },
    error: (result) => {
      if (result["code"] === 20021) {
        alert("请重新登入，登入已超时");
        window.location.href = "/login";
      }
      alert(result["msg"]);
    }
  });
  return user;
}

/**
 * 获取用户资料
 * @param userId
 */
function getUserInfo(userId) {
  let user = null;
  $.ajax({
    headers: {
      token: $.cookie("token")
    },
    async: false,
    method: "GET",
    url: "/getUserInfo/" + userId,
    type: "json",
    content: "application/json;charset=utf-8",
    success: function(result){
      user = result.data["userVo"];
    },
    error: (result) => {
      console.log(("查询用户资料失败：" + JSON.stringify(result)));
    }
  });
  return user;
}

/**
 * 获取用户所有聊天信息
 */
function getAllChatHistory() {
  let data = null;
  $.ajax({
    headers:{
      token:$.cookie("token")
    },
    method:"GET",
    url:"/history/user",
    type:"json",
    content:"application/json;charset=utf-8",
    success:(result)=>{
      console.log("获取用户所有聊天信息："+ JSON.stringify(result));
      data = result;
    },
    error:(result)=>{
      console.log("获取用户聊天信息失败："+JSON.stringify(result));
    }
  });
  return data;
}

/**
 * 获取群的用户
 */
function getGroupUserList(groupId){
  let data = null;
  $.ajax({
    headers:{
      token:$.cookie("token")
    },
    method:"GET",
    url:"/getGroupUserList/"+groupId,
    type:"json",
    content:"application/json;charset=utf-8",
    async:false,
    success:(result)=>{
      console.log("获取群用户："+ JSON.stringify(result));
      data = result.data["userList"];
    },
    error:(result)=>{
      console.log("获取群用户失败："+JSON.stringify(result));
    }
  });
  return data;
}
