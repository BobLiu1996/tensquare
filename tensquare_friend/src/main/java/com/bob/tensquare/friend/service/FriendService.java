package com.bob.tensquare.friend.service;

import com.bob.tensquare.friend.dao.FriendDao;
import com.bob.tensquare.friend.dao.NoFriendDao;
import com.bob.tensquare.friend.pojo.Friend;
import com.bob.tensquare.friend.pojo.NoFriend;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userId, String friendId) {

        //先判断userid到friendid是否有数据，有则是重复添加好友，返回0
        Friend friend=friendDao.findByUseridAndFriendid(userId,friendId);
        if(friend!=null){
            return 0;//不用添加，已经是单向好友
        }
        //向好友表中userId到friendid方向的type为0，单向喜欢
        friend=new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");//设置单向喜欢
        friendDao.save(friend);
        //判断从friendid到userid是否有数据，如果有，把双方的状态都改为1
        if(friendDao.findByUseridAndFriendid(friendId,userId)!=null){
            friendDao.updateIslike("1",userId,friendId);
            friendDao.updateIslike("1",friendId,userId);
        }
        return 1;
    }

    public int addNoFriend(String userId, String friendId) {
        NoFriend noFriend=noFriendDao.findByUseridAndFriendid(userId,friendId);
        if(noFriend!=null){
            return 0;//已经是非好友关系
        }
        noFriend=new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     * 删除好友
     * @param friendid
     */
    public void deleteFriend(String userId,String friendid) {
        //删除好友表中userid到friendid这条数据（tb_friend）
        friendDao.deleteFriend(userId,friendid);
        //更新friendid到userid的islike为0；
        friendDao.updateIslike("0",friendid,userId);
        //向非好友表中添加数据
        NoFriend noFriend=new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
