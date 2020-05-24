package com.bob.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component//放入容器中
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //无论怎么样都放行，具体是否给予操作权限，在具体的业务中完成。
        //拦截只是负责把请求头中的token进行解析
        String header=request.getHeader("Authorization");
        if(header!=null && !"".equals(header)){
            //如果包含有头信息，则解析头信息，否则，直接放行
            if(header.startsWith("nice")){
                //拿到token
                String token=header.substring(4);
                //对令牌进行验证
                try{
                    //解析令牌
                    Claims claims =jwtUtil.parseJwt(token);
                    String roles=(String) claims.get("roles");
                    if(roles!=null && roles.equals("admin")){
                        //令牌验证通过则存放到Request域中，在具体的业务逻辑中，
                        // 从request域中来获取claims_admin的值。
                        request.setAttribute("claims_admin",claims);
                    }
                    if(roles!=null && roles.equals("user")){
                        //令牌验证通过则存放到Request域中，在具体的业务逻辑中，
                        // 从request域中来获取claims_admin的值。
                        request.setAttribute("claims_user",claims);
                    }

                }catch (Exception e){
                    throw new RuntimeException("令牌不正确！！");
                }
            }
        }
        return true;//放行，可以继续往下走
    }
}
