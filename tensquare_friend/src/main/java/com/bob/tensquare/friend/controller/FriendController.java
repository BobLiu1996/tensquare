package com.bob.tensquare.friend.controller;

import com.bob.tensquare.friend.client.UserClient;
import com.bob.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired(required = false)
    private UserClient userClient;

    /**
     * 增加好友，或者添加非好友
     * @param friendId
     * @param type
     * @return
     */
    @RequestMapping(value = "like/{friendId}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendId,@PathVariable String type){
        //1.验证是否登录，并且拿到当前的登录用户id，friendId作为参数传进来
        Claims claims=(Claims) request.getAttribute("claims_user");
        if(claims==null ){
            return new Result(false,StatusCode.ERROR,"权限不足");
        }
        //2.1得到当前用户ID
        String userId=claims.getId();
        //2.判断添加好友还是添加非好友
        if(type!=null){
            if(type.equals("1")){
                //添加好友
                int flag =friendService.addFriend(userId,friendId);

                if(flag==0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加好友");
                }
                if(flag==1){
                    /**
                     * 调用tensquare-user微服务中方法，添加当前用户的关注数和其关注的好友的粉丝数
                     */
                    userClient.updateFansCountAndFollowCount(userId,friendId,1);
                    return new Result(true,StatusCode.OK,"添加好友成功");
                }
            }else if(type.equals("2")){
                //添加非好友
                int flag =friendService.addNoFriend(userId,friendId);
                if(flag==0){
                    return new Result(false,StatusCode.OK,"已经是非好友关系，不能重复添加");
                }
                if(flag==1){
                    return new Result(true,StatusCode.OK,"添加非好友成功");
                }
            }
        }else{
            return new Result(false, StatusCode.ERROR,"参数异常");
        }
        return new Result(true,StatusCode.OK,"好友添加成功");
    }

    /**
     * 删除好友
     * @param friendid
     * @return
     */
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){

        //1.验证是否登录，并且拿到当前的登录用户id，friendId作为参数传进来
        Claims claims=(Claims) request.getAttribute("claims_user");
        if(claims==null ){
            return new Result(false,StatusCode.ERROR,"权限不足");
        }
        //2.1得到当前用户ID
        String userId=claims.getId();
        //2.2删除好友
        friendService.deleteFriend(userId,friendid);
        userClient.updateFansCountAndFollowCount(userId,friendid,-1);//当前用户的关注数和好友的粉丝数-1
        return new Result(true,StatusCode.OK,"好友删除成功");
    }
}
