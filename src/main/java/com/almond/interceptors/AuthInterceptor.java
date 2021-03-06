package com.almond.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.almond.annotations.CheckAuth;
import com.almond.api.auth.service.AuthService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired
	private AuthService authService;
	 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		CheckAuth auth = null;
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod)handler;
			auth = method.getMethodAnnotation(CheckAuth.class);
		}
		
		if(auth == null || !auth.value()){
			return true;
		}
		
		// Token Check
    	String accessToken = request.getHeader("Authorization");
    	if (accessToken == null) {
    		return unAuthException(response);
    	}
    	
    	try {
    		DecodedJWT jwt = authService.tokenCheck(accessToken);
    		
	        Claim id = jwt.getClaim("id");
	        Claim idx = jwt.getClaim("idx");
    		
//	        System.out.println("getIssuer : " + jwt.getIssuer());
//	        System.out.println("getSubject : " + jwt.getSubject());
//	        System.out.println("getID : " + id.asString());
//	        System.out.println("getIDX : " + idx.asInt());

    		request.setAttribute("id", id.asString());
    		request.setAttribute("idx", idx.asInt());
    	} catch(JWTVerificationException exception) {
        	// Invalid Token
    		return unAuthException(response);
        }
		return true;
	}
 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}
 
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	}
	
	private boolean unAuthException(HttpServletResponse response) throws Exception {
		CommonResponse res = new CommonResponse(ResponseResult.ERROR);
		res.setMessage("유효하지 않은 토큰입니다.");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write(new ObjectMapper().writeValueAsString(res));
		response.getWriter().flush();
		response.getWriter().close();
		return false;
	}
}