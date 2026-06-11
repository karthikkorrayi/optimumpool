package com.OptimumPool.Authentication.Filter;

import com.OptimumPool.Authentication.Model.MQToken;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Filter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest=(HttpServletRequest) request;
        HttpServletResponse httpResponse=(HttpServletResponse) response;
        String token=httpRequest.getHeader("Authorization");
        if(token==null || !token.startsWith("Bearer")){
            throw new ServletException();
        }
        else{
            String jwtToken=token.substring(7);
            //validation
            String tokenName = Jwts.parser().setSigningKey("itcKey").parseClaimsJws(jwtToken).getBody().getSubject();
            MQToken mqt = new MQToken();
            mqt.setMqtoken(tokenName);
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
