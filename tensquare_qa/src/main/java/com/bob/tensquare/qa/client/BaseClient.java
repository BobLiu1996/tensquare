package com.bob.tensquare.qa.client;

import com.bob.tensquare.qa.client.iml.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)
public interface BaseClient {
    @RequestMapping(value ="/label/{labelId}",method= RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);
}
