package com.bob.tensquare.manager.filter;

import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.Request;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器链中过滤器的优先级别，数字越小，优先级别越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作，
     * setSendzullResponse(false),表示不放行，不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器");
        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request= requestContext.getRequest();

        /**
         * zuul内部的分发请求的方法是第一个请求，该请求必须放行，如果不放行，
         * 则后面的转发会导致程序终止
         */
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        /**
         * 登录不进行拦截
         */
        if(request.getRequestURI().indexOf("login")>0){
            return null;
        }

        String header=request.getHeader("Authorization");
        if(header!=null && !"".equals(header)){
            if(header.startsWith("nice")){
                String token=header.substring(4);
                try{
                    Claims claims =(Claims)jwtUtil.parseJwt(token);
                    String roles=(String)claims.get("roles");
                    if(roles.equals("admin")){
                        //请求头经过zuul网关会被丢弃，把头信息转发下去，并且放行
                        requestContext.addZuulRequestHeader("Authorization",header);
                        return null;//一定要返回一个值，任意的值都可以表示继续向下转发。
                    }
                }catch (Exception e){
                    requestContext.setSendZuulResponse(false);//终止运行
                }
            }
        }
        requestContext.setResponseStatusCode(403);//终止运行
        requestContext.setResponseBody("权限不足");
        return null;
    }
}
