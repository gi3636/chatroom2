package com.example.chatroom2.entity;

import com.example.chatroom2.constant.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 3:49 PM
 */

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_message")
public class Message implements Serializable {
    @ApiModelProperty(value = "信息id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "接收者")
    private Integer sendTo;

    @ApiModelProperty(value = "发送者")
    private Integer fromUser;

    @ApiModelProperty(value = "消息类型")
    private String msgType;

    @ApiModelProperty(value = "聊天类型")
    private String chatType;

    @ApiModelProperty(value = "信息状态：未读/已读")
    private Integer status = MessageType.MSG_UNREAD;

    @ApiModelProperty(value = "群id")
    private Integer groupId;


    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sendTo=" + sendTo +
                ", fromUser=" + fromUser +
                ", msgType='" + msgType + '\'' +
                ", chatType='" + chatType + '\'' +
                ", status=" + status +
                ", groupId=" + groupId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

