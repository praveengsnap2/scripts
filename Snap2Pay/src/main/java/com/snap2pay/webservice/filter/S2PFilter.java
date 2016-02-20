/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.snap2pay.webservice.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author keerthanathangaraju
 */
public class S2PFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request != null && request.getParameterValues("format") != null && request.getParameterValues("format")[0] != null && request.getParameterValues("format")[0].equalsIgnoreCase("json")) {
//			format = request.getParameterValues("format")[0].toString();
            response.setContentType("application/json");
        }
    }

    public void destroy() {
    }

}
