package com.example.chatroom2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 7:23 AM
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_group_chat")
public class GroupChat implements Serializable {

    @ApiModelProperty(value = "群聊id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(name = "群主")
    @ManyToOne
    private User manager;

    @ApiModelProperty(name = "群名")
    private String groupName;

    @ApiModelProperty(name = "群头像")
    private String groupAvatar;

    @ApiModelProperty(name = "群介绍")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "groupChatList",fetch = FetchType.EAGER)
    private Set<User> users;


    @Override
    public String toString() {
        return "GroupChat{" +
                "id=" + id +
                ", manager=" + manager +
                ", groupName='" + groupName + '\'' +
                ", groupAvatar='" + groupAvatar + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
