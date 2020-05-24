package com.bob.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //得到request上下文
        RequestContext currentContext =RequestContext.getCurrentContext();
        HttpServletRequest request =currentContext.getRequest();
        String header=request.getHeader("Authorization");
        //判断是否有头信息
        if(header!=null  && !"".equals(header)){
            //继续传递header，由于默认情况下是不往下面传的,经过网关之后，信息头会丢失
            currentContext.addZuulRequestHeader("Authorization",header);
        }
        return null;
    }
}
