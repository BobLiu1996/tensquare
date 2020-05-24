package com.bob.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.bob.tensquare.user.pojo.User;
import com.bob.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

    /**
     * 供其他微服务调用
     * 更新好友的粉丝数和用户关注数量
     */
    @RequestMapping(value="/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
    public void updateFansCountAndFollowCount(@PathVariable String userid,@PathVariable String friendid,@PathVariable int x){
        userService.updateFansCountAndFollowCount(x,userid,friendid);
    }

	@RequestMapping(value="/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		user=userService.login(user);
		if(user==null) {
			return new Result(false,StatusCode.LOGINERROR,"登录失败");
		}
		//登录成功时，生成一个令牌，返回给客户端
        String token= jwtUtil.createJwt(user.getId(),user.getMobile(),"user");
        Map<String ,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("roles","user");
		return new Result(true,StatusCode.OK,"登录成功",map);
	}

	@RequestMapping(value="/register/{code}",method = RequestMethod.POST)
	public Result register(@PathVariable String code,@RequestBody User user){
	    //获取缓存中的验证码
        String codeRedis=(String)redisTemplate.opsForValue().get("checkcode_"+user.getMobile());
        if(codeRedis.isEmpty()){
           return new Result(false,StatusCode.ERROR,"请先获取验证码！");
        }
        if(!codeRedis.equals(code)){
            return new Result(false,StatusCode.ERROR,"请输入正确的验证码！");
        }
        //开始向数据库中注册
        userService.add(user);
	    return new Result(true,StatusCode.OK,"注册成功");
    }

    /**
     * 发送短信验证码
     * @return
     */
    @RequestMapping(value="/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        return new Result(true,StatusCode.OK,"发送成功");
    }
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除,必须是admin才能删除
     * 前后端约定：前端请求微服务时需要添加请求头信息：Authorization,
     * 内容为：nice+空格+token
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
