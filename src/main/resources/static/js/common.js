var Common = function () {

    return {
        // 初始化各个函数及对象
        init: function () {

        },

        strIsNotEmpty: function(str) {
            if (str != null && str != undefined && str != '') {
                return true;
            }
            return false;
        },

        // 时间戳转换成指定日期格式
        formatTime: function(time, format) {
            var t = new Date(time);
            var tf = function(i){return (i < 10 ? '0' : '') + i};
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
                switch(a){
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        }


    };

}();

jQuery(document).ready(function() {
    Common.init();
});

//用来移除自己
var arrRemove = function (content, arr) {
    if (!arr || arr.length == 0) {
        return ""
    }
    let flag = arr.indexOf(content)
    if (flag > -1) {
        arr.splice(flag, 1)
        return arr
    } else {
        console.log("未查找到该元素")
    }
};

//Cookie
//写Cookie
function setCookie(name,value){
    let days = 1;
    let exp = new Date();
    exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value)+";expires ="+exp;
}

//读Cookie
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp;
}