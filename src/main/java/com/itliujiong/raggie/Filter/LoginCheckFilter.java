package com.itliujiong.raggie.Filter;

import com.alibaba.fastjson.JSON;
import com.itliujiong.raggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//  urlPatterns="/*"所有请求都拦截
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    //    Filter接口中有一个doFilter方法，当开发人员编写好Filter，并配置对哪个web资源进行拦截后，
//    WEB服务器每次在调用web资源的service方法之前，都会先调用一下filter的doFilter方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //获取本次请求的uri
        String uri = httpServletRequest.getRequestURI();

        log.info("@@@@@@@ " + uri);
        //判断本次请求是否需要处理
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/commmon/**",
                "/user/sendMsg",
                "/user/login"
        };
        Boolean check = check(urls, uri);
        //如果不需要处理，直接放行
        if (check) {
            log.info("本次请求不需要处理");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //判断登陆状态,如果登陆了，放行(pc端)
        if (httpServletRequest.getSession().getAttribute("employee") != null) {
            log.info("用户已登陆：" + httpServletRequest.getSession().getAttribute("employee"));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //移动端判断用户登陆状态
        if (httpServletRequest.getSession().getAttribute("user") != null) {
            log.info("用户已登陆：" + httpServletRequest.getSession().getAttribute("user"));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        log.info("用户未登陆@@@");
        //如果未登陆，则返回未登陆结果
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    //路径匹配
    public Boolean check(String[] urls, String uri) {
        for (String url : urls) {
            Boolean match = PATH_MATCHER.match(url, uri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
