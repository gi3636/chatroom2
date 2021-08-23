package com.example.chatroom2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@EntityListeners(AuditingEntityListener.class)//更新实体类的时间
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;


}
