package com.bob.tensquare.friend.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-user")
public interface UserClient {
    @RequestMapping(value="/user/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
    public void updateFansCountAndFollowCount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
