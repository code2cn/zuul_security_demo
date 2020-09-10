package com.nic.zuul.security.handler;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	    Object principal = authentication.getPrincipal();
	    response.setContentType("application/json;charset=utf-8");
	    PrintWriter out = response.getWriter();
	    out.write(new ObjectMapper().writeValueAsString(principal));
	    out.flush();
	    out.close();
		
	}

}
