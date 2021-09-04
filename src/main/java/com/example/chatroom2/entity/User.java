package com.example.chatroom2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@EntityListeners(AuditingEntityListener.class)//更新实体类的时间
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tbl_user")
public class User implements Serializable {

    @ApiModelProperty(value = "用户id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键选择策略
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @ApiModelProperty(value = "密码")
    @Column(name = "password", nullable = false)
    private String password;

    @ApiModelProperty(value = "头像路径")
    @Column(name = "avatar")
    private String avatar;

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
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "tbl_user_group_chat",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_chat_id"))
    private Set<GroupChat> groupChatList = new HashSet<>();


    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Friend> friendList = new HashSet<>();


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public User(Integer id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
    }
}
