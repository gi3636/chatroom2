package com.example.chatroom2.dao;



import com.example.chatroom2.entity.User;
import com.example.chatroom2.model.vo.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


//CrudRepository：提供标准的创建，读取，更新和删除功能，
// 其中包含诸如findOne().findAll()，save()，delete()等方法。
// PagingAndSortingRepository：它扩展了CrudRepository并添加了findAll方法。
// 它能够以分页方式对数据进行排序和检索。
// JpaRepository扩展了存储库CrudRepository和PagingAndSortingRepository。
// 它添加了特定于JPA的方法，例如flush()，以在持久性上下文上触发刷新。
@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    User findUserByUsername(String username);


    @Query("select new User(u.id,u.username,u.avatar) from User u where u.id=?1")
    User findUserInfo(Integer userId);



}
