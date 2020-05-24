package com.bob.tensquare.friend.dao;


import com.bob.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    /**
     * 根据userid和friendid联合查询
     * @param userid
     * @param friendid
     * @return
     */
    public NoFriend findByUseridAndFriendid(String userid, String friendid);

    @Modifying//增删改需要用这个注解
    @Query(value = "update tb_friend set islike=? where userid=? and friendid=?",nativeQuery = true)
    public void updateIslike(String islike, String userid, String friendid);
}
