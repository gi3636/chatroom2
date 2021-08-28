package com.example.chatroom2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 7:23 AM
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_group_chat")
public class GroupChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String groupName;
    private Date createTime;
    private Date updateTime;

    @ManyToMany(mappedBy = "groupChatList",fetch = FetchType.LAZY)
    private List<User> users;

}
