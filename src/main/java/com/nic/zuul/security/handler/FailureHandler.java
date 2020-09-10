package com.nic.zuul.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.zuul.security.bean.RespBean;
@Component
public class FailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		RespBean respBean = RespBean.getInstance();
		respBean.setCode(401);
		if (exception instanceof LockedException) {
		    respBean.setMsg("账户被锁定，请联系管理员!");
		} else if (exception instanceof CredentialsExpiredException) {
		    respBean.setMsg("密码过期，请联系管理员!");
		} else if (exception instanceof AccountExpiredException) {
		    respBean.setMsg("账户过期，请联系管理员!");
		} else if (exception instanceof DisabledException) {
		    respBean.setMsg("账户被禁用，请联系管理员!");
		} else if (exception instanceof BadCredentialsException) {
		    respBean.setMsg("用户名或者密码输入错误，请重新输入!");
		}
		out.write(new ObjectMapper().writeValueAsString(respBean));
		out.flush();
		out.close();
		
	}

}
