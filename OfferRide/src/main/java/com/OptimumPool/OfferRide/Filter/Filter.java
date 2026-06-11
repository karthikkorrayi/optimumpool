package com.OptimumPool.OfferRide.Filter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class Filter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  httpRequest = (HttpServletRequest) request;
        HttpServletResponse  httpResponse= (HttpServletResponse) response;

        String token = httpRequest.getHeader("Authorization");
        if(token==null || !token.startsWith("Bearer")) {
            throw new ServletException();
        }
        else {
            String jwttoken = token.substring(7);
            String name = Jwts.parser().setSigningKey("itccar").parseClaimsJws(jwttoken).getBody().getSubject();
            chain.doFilter(httpRequest, httpResponse);

        }

        // TODO Auto-generated method stub

    }

    }
