package ru.innopolis.olympiads.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSHeaderFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //  response.addHeader("Access-Control-Allow-Origin", "http://game.local:8082");//http://10.13.1.162:5435,http://178.213.246.19:25435");
        response.addHeader("Access-Control-Allow-Origin", "*");
        //allow to send cookie,
        //response.addHeader("Access-Control-Allow-Credentials", "true");//  http://learn.javascript.ru/xhr-crossdomain
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        response.addHeader("Allow-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}