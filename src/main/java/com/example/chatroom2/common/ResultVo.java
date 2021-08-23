package com.example.chatroom2.common;

import lombok.Data;

import java.util.Map;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/18/2021 10:50 PM
 */
@Data
public class ResultVo {

    private Integer code;
    private String msg;
    private Map<String,String> data;


    public static ResultVo ok(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMsg("操作成功");
        return resultVo;
    }
    public static ResultVo error(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(201);
        resultVo.setMsg("操作失败");
        return resultVo;
    }



    public ResultVo msg(String msg){
        this.setMsg(msg);
        return this;
    }

    public ResultVo code(Integer code){
        this.setCode(code);
        return this;
    }


}
