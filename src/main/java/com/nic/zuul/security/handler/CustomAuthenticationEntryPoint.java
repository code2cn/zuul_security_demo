package com.nic.zuul.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.zuul.security.bean.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
		RespBean respBean = RespBean.getInstance();
		respBean.setCode(4011);
		respBean.setMsg("尚未登录，请先登录");
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
		
	}

}
