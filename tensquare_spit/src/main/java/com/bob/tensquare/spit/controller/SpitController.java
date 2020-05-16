package com.bob.tensquare.spit.controller;


import com.bob.tensquare.spit.pojo.Spit;
import com.bob.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    @RequestMapping(value="/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据父类ID查询数据
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pages= spitService.findByParentId(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult(pages.getTotalElements(),pages.getContent()));
    }


    /**
     * 吐槽点赞数
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        //控制用户不能重复点赞，由于暂时没有登录认证，所以，先固定用户
        String userid="111";
        //使用redis来标识该用户是否已经点赞
        if(redisTemplate.opsForValue().get("thumbup_"+userid)!=null){
            return new Result(true,StatusCode.REPERROR,"重复操作");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid,1);//先redis中添加
        return new Result(true,StatusCode.OK,"点赞成功");
    }

}
