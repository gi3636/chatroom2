$(function () {
  var user = init();
  var toGroupId = 1;
  var toUserId = null;
  var chatType = "group";

  //建立websocket连接
  let host = window.location.host;//获取host解决后端获取httpSession的空指针异常
  console.log("id:" + user.id);
  let ws = new WebSocket("ws://" + host + "/websocket/" + user.id);

  /**
   * 建立websocket连接时候调用
   * @param e
   */
  ws.onopen = function (e) {
    let res = JSON.parse(e.data);
    console.log(res);

    //初始化信息
  }

  /**
   * 接收服务器发送的信息
   * @param e
   */
  ws.onmessage = function (e) {
    let res = JSON.parse(e.data);
    console.log(res);
    //处理信息
    handleMessage(res);

    //判断信息
    //是否显示

  }


  /**
   * 结束服务时触发
   * @param e
   */
  ws.onclose = function (e) {

  }

  /**
   * 触发事件
   */
  $("#textarea").keydown(function (e) {
    //按enter键
    if (e.keyCode === 13) {
      sendMessage();
    }
  });

  /**
   * 发送信息
   */
  function sendMessage() {
    let data = $("#textarea").val().trim();
    let time = Common.formatTime(new Date(), 'yyyy-MM-dd HH:mm:ss');
    console.log("发出时间：" + time);
    $("#textarea").val(null);
    console.log("发送的用户：" + toUserId);
    let mess = {
      sendTo: toUserId,
      fromUser: user.id,
      groupId: toGroupId,
      msgType: "text",
      chatType: chatType,
      content: data
    }

    let str = "<li class=\"row\" id=\"time_message\"><p class=\"d-flex w-100 justify-content-center\">" + time + "</p></li> " +
        "<li class=\"row \" id=\"self_message\">\n" +
        "     <div class=\"row d-flex  justify-content-end col-sm-11\">\n" +
        "        <div class=\"row username w-100 justify-content-end\">" + user["username"] + "</div>\n" +
        "        <div class=\"row content \">" + data + "</div>\n" +
        "     </div>\n" +
        "     <img src=" + user["avatar"] + " width=\"50px\" height=\"50px\">\n" +
        "  </li>";
    $(".chat").append(str);

    saveMessage(mess,str);

    console.log("要发送的数据：" + JSON.stringify(mess));
    ws.send(JSON.stringify(mess));
    scrollDown();
  }



  /**
   * 处理接收信息
   * @param message
   */
  function handleMessage(message) {
    switch (message["msgType"]) {
      case "text":
        showTextMessage(message);
        break;
      case "img":
        break;
      case "file":
        break;
      case "notice":
        showNoticeMessage(message);
        break;
      default:
        break;
    }
  }


  /**
   * 显示一般文本信息
   * @param message
   */
  function showTextMessage(message) {
    let fromUser = getUserInfo(message["fromUser"])
    let time = Common.formatTime(message["createTime"], 'yyyy-MM-dd HH:mm:ss');
    console.log("成功查询用户信息:" + JSON.stringify(fromUser));
    console.log("显示文字信息：" + message);
    let str = "<li class=\"row\" id=\"time_message\"><p class=\"d-flex w-100 justify-content-center\">" + time + "</p></li>" +
        "<li class=\"row\" id=\"received_message\">\n" +
        "   <img src=\"" + fromUser["avatar"] + "\" width=\"50px\" height=\"50px\">\n" +
        "    <div class=\"row col-sm-11\">\n" +
        "       <div class=\"row username col-sm-12\">" + fromUser["username"] + "</div>\n" +
        "              <div class=\"row content\">" + message["content"] + "</div>\n" +
        "          </div>\n" +
        "   </li>";
    showMessage(message, str);
    saveMessage(message, str);
  }

  /**
   * 显示系统消息信息
   * @param message
   */
  function showNoticeMessage(message) {
    console.log("显示系统消息：" + message);
    let time = Common.formatTime(message["createTime"], 'yyyy-MM-dd HH:mm:ss');
    console.log(time)
    let str = "";
    str += "<li class=\"row\" id=\"time_message\"><p class=\"d-flex w-100 justify-content-center\">" + time + "</p></li>"
    str += "<li class=\"row\" id=\"system_message\"><p class=\"d-flex w-100 justify-content-center\">" + message["content"] + "</p></li>"
    showMessage(message, str);
    saveMessage(message, str);
  }


  /**
   * 在界面显示信息
   * @param message
   * @param str
   */
  function showMessage(message, str) {
    if (isSingleMessage(message)) {
      if (isSameSingleWindow(message)) {
        $(".chat").append(str);
        scrollDown();
      }
    } else {
      if (isSameGroupWindow(message)) {
        $(".chat").append(str);
        scrollDown();
      }
    }
  }


  /**
   * 判断当前打开的是否是私聊窗口
   * @param message
   * @returns {boolean}
   */
  function isSameSingleWindow(message) {
    if (message["sendTo"] === toUserId && toUserId != null) {
      console.log("显示私聊信息");
      return true;
    }
    return false;
  }

  /**
   * 判断当前打开的是否是群聊窗口
   * @param message
   * @returns {boolean}
   */
  function isSameGroupWindow(message) {
    if (message["groupId"] === toGroupId && toGroupId != null) {
      console.log("显示群聊信息");
      return true;
    }
    return false;
  }


  /**
   * 判断当前信息是否为私聊
   * @param message
   * @returns {boolean}
   */
  function isSingleMessage(message) {
    if (message["chatType"] === "single") {
      console.log("是私聊信息");
      return true;
    }
    console.log("是群聊信息");
    return false;
  }

  /**
   * 消息滚动条显示至底部
   */
  function scrollDown() {
    $(".chat-content")[0].scrollTop = $(".chat-content")[0].scrollHeight;
  };

  /**
   * 存储信息去session
   */
  function saveMessage(message, str) {
    let key = message["chatType"] + message["fromUser"];
    let chatHistory = sessionStorage.getItem(key);
    if (chatHistory != null) {
      str = chatHistory + str;
    }
    sessionStorage.setItem(key, str);
  }


  /**
   * 显示历史信息
   * @param msgType
   * @param id
   */
  function showHistoryMessage(msgType, id) {
    let key = msgType + id;
    let chatData = sessionStorage.getItem(key);
    if (chatData != null) {
      $(".chat").html(chatData);
    }
  }







});