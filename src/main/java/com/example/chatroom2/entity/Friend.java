package com.example.chatroom2.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 11:54 AM
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_friend")
public class Friend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ApiModelProperty(value = "朋友id")
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;


}
