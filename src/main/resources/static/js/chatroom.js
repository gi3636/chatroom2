
//自己的资料
var user;
//全体在线用户
var onlineUsers;
//全体在线用户，但不包含自己
var onlineUserList;
var toUser=null;//要send给谁
//默认发送对象为在线的人
var flag=true;
var groupChatId=1;//群聊的ID，默认是全体群
var isPrivateChat=false;
var fromUser;


$(function () {
    $.ajax({
        type:"GET",
        url:"/getUser",
        success:function(data){
            user=data;
            console.log(JSON.stringify(user));
        },
        async:false
    });

    //建立websocket连接
    //获取host解决后端获取httpSession的空指针异常
    let host=window.location.host;
    let ws=new WebSocket("ws://"+host+"/chat");

    //给ws绑定事件
    ws.onopen=function (evt) {
        initGroupChat(1,this);
    }

    //接收到服务端推送的消息后触发
    ws.onmessage=function(evt){
        let dataStr=evt.data;
        let res=JSON.parse(dataStr);
        console.log(res);
        let time=Common.formatTime(res["sendTime"],'yyyy-MM-dd HH:mm:ss');
        if (res["isSystem"]){
            systemMessage(res,time)
        }
        //文本消息
        if(res["type"] === 1 && !res["isSystem"]){
            textMessage(res,time)
        }
        //图片信息
        else if(res["type"] === 2 && !res["isSystem"]){
        }
        //文件信息
        else if(res["type"] === 3 && !res["isSystem"]){
        }
        refreshMessage();
    }


    ws.onclose=function (e) {
        //显示离线消息
        console.log("连接断开："+e.code + " " +e.reason + " " + e.wasClean )
        console.log(e);
    }

    $("#textarea").keydown(function (e) {
        //按enter键
        if (e.keyCode === 13) {
            sendMessage();
        }
    });

    function sendMessage() {
        let data = $("#textarea").val().trim();
        let time = Common.formatTime(new Date(), 'yyyy-MM-dd HH:mm:ss');
        console.log("发出时间：" + time);
        $("#textarea").val(null);
        console.log("发送的用户：" + toUser);
        let mess = {
            toUserId: toUser,
            fromUserId: user["userId"],
            message: data,
            type: 1,
            isSystem: false,
            isPrivateMessage: isPrivateChat,
            sendTime: time,
            groupChatId: groupChatId,
        }
        let str = "<li class=\"row\" id=\"time_message\"><p class=\"d-flex w-100 justify-content-center\">" + time + "</p></li> " +
            "<li class=\"row \" id=\"self_message\">\n" +
            "     <div class=\"row d-flex  justify-content-end col-sm-11\">\n" +
            "        <div class=\"row username w-100 justify-content-end\">" + user["username"] + "</div>\n" +
            "        <div class=\"row content \">" + data + "</div>\n" +
            "     </div>\n" +
            "     <img src=\"/show/" + user["username"] + "\" width=\"50px\" height=\"50px\">\n" +
            "  </li>";
        $(".chat").append(str);

        if (isPrivateChat) {
            savePrivateMessage(toUser, str);
        } else {
            saveGroupChatMessage(groupChatId, str);
        }
        console.log("要发送的数据：" + JSON.stringify(mess))
        ws.send(JSON.stringify(mess));
        refreshMessage();
    }

    /**
     * 获取用户
     * @param fromUserId
     */
    function getFromUser(fromUserId){
        $.ajax({
            type:"GET",
            url:"/getUser/"+fromUserId,
            success:function(data){
                fromUser=data;
            },
            async:false //同步请求，只有上面好了才会接着下面
        });
    }

    function textMessage(res,time){
        getFromUser(res["fromUserId"]);
        console.log("收到信息:"+fromUser);
        let str="<li class=\"row\" id=\"time_message\"><p class=\"d-flex w-100 justify-content-center\">"+time+"</p></li>" +
            "<li class=\"row\" id=\"received_message\">\n" +
            "   <img src=\""+fromUser["avatar"]+"\" width=\"50px\" height=\"50px\">\n" +
            "    <div class=\"row col-sm-11\">\n" +
            "       <div class=\"row username col-sm-12\">"+fromUser["username"]+"</div>\n" +
            "              <div class=\"row content\">"+res["message"]+"</div>\n" +
            "          </div>\n" +
            "   </li>";

        if (res["isPrivateMessage"]){
            if (isTheSamePrivateWindow(res["fromUserId"],str)){
                $(".chat").append(str);
                console.log("显示消息")
            }
            savePrivateMessage(res["fromUserId"],str)
        }else{
            if (isTheSameGroupChatWindow(res["groupChatId"])){
                $(".chat").append(str);
            }
            saveGroupChatMessage(res["groupChatId"],str)
        }
    }

    function systemMessage(res,time){
        console.log("系统消息");
        console.log(res);
        let str="";
        str+="<li class=\"row\" id=\"time_message\"><p class=\"d-flex w-100 justify-content-center\">"+time+"</p></li>"
        str+="<li class=\"row\" id=\"system_message\"><p class=\"d-flex w-100 justify-content-center\">"+res["message"]+"</p></li>"
        //判断是否展现

        if (res["isPrivateMessage"]){
            if (isTheSamePrivateWindow(res["fromUserId"])){
                $(".chat").append(str);
            }
            savePrivateMessage(res["fromUserId"],str)
        }else{
            if (isTheSameGroupChatWindow(res["groupChatId"])){
                $(".chat").append(str);
            }
            saveGroupChatMessage(res["groupChatId"],str)
        }
    }

    function savePrivateMessage(fromUserId ,str){
        let chatData=sessionStorage.getItem("private"+fromUserId);
        if (chatData!=null){
            str=chatData+str;
        }
        sessionStorage.setItem("private"+fromUserId,str);
    }

    function saveGroupChatMessage(groupChatId ,str){
        let chatData=sessionStorage.getItem("group"+groupChatId);
        if (chatData!=null){
            str=chatData+str;
        }
        sessionStorage.setItem("group"+groupChatId,str);
    }

//判断当前开启窗口与收到信息为一致
    function isTheSamePrivateWindow(fromUserId,str){
        console.log("私聊");
        //打开窗口与收到信息一致
        if (toUser === fromUserId && toUser != null && fromUserId != null){
            console.log("显示消息")
            return true;
        }
        return false;
    }

//判断当前开启窗口与收到信息为一致
    function isTheSameGroupChatWindow(fromGroupChatId){
        console.log("群聊")
        //打开窗口与收到信息一致
        if (groupChatId === fromGroupChatId && groupChatId != null && fromGroupChatId != null){
            console.log("窗口一致");
            console.log("显示信息")
            return true;
        }
        console.log("窗口不一致");
        return false;
    }
});




// 消息滚动条显示至底部
function  refreshMessage(){
    $(".chat-content")[0].scrollTop=$(".chat-content")[0].scrollHeight;
};

/**
 * 显示列表
 * @param data
 */
function showUserList(data){
    let str="";
    for (let user of data){
        str += "<li onclick='initPrivateChat("+user["userId"]+")'><img src=\""+user["avatar"]+" \" height=\"80%\" width=\"80%\"> <p>"+user["username"]+"</p></li>";
    }
    $(".user-detail").html(str);
}

/**
 * 点击显示聊天框
 * @param userId
 */
function initPrivateChat(userId){
    let isTrue = parseInt($('#click_side').attr('value'));
    if (isTrue === 1) {
        $('.user-detail>li').css("width", "0px");
        $('.chat-detail').css("width", "0%");
        $('#click_side').attr('value', 0);
    }
    //表示对象不是群聊
    isPrivateChat=true;
    groupChatId=null;
    toUser=userId;
    $.ajax({
        type:"GET",
        url:"/getUser/"+userId,
        success:function(data){
            console.log("get the private success")
            console.log(data);
            let str="";
            str += "<li onclick='initPrivateChat("+data["userId"]+")'><img src=\""+data["avatar"]+" \" height=\"80%\" width=\"80%\"> <p>"+data["username"]+"</p></li>";
            $(".user-detail").html(str);
            $(".chat").html("");
            $(".title-content").html("当前正与"+data["username"]+"聊天");
            showPreviousPrivateMessage()
        },
        async: false
    });
};



function initGroupChat(id,obj){
    isPrivateChat=false;
    toUser=null;
    groupChatId=id;
    $(".chat").html("");
    $(".chat-history-list li").removeClass("active");
    $(obj).addClass("active");
    showGroupChat();
    refreshMessage();
}

/**
 *
 */
function showGroupChat(){
    let str="";
    $.ajax({
        type:"GET",
        url:"/getGroupChatUserList/"+groupChatId,
        success:function(data){
            console.log("get the groupChat success")
            console.log(data);
            showPreviousGroupChatMessage();
            showUserList(data);
        },
        async: false
    });
}

/**
 * 显示私聊聊天信息
 */
function showPreviousPrivateMessage(){
    let chatData = sessionStorage.getItem("private"+toUser);
    if (chatData != null){
        $(".chat").html(chatData);
    }
    refreshMessage();
}

/**
 * 显示群聊聊天信息
 */
function showPreviousGroupChatMessage(){
    let chatData=sessionStorage.getItem("group"+groupChatId);
    if (chatData!=null){
        $(".chat").html(chatData);
    }
}

function showChatDetail() {
    let isTrue = parseInt($('#click_side').attr('value'));
    $(function() {
        if (isTrue === 0) {
            $('.chat-detail').css("width", "18%");
            $('.user-detail>li').css("width", "80px");
            $('#click_side').attr('value', 1);
        } else {
            $('.user-detail>li').css("width", "0px");
            $('.chat-detail').css("width", "0%");
            $('#click_side').attr('value', 0);
        }
        return false;
    });
}