package com.example.testall.mcp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class McpHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private McpContext mcpContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        int i = 0;
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        mcpContext.setAuthorizationHeader(authorizationHeader);
        return true;
//        try {
//            String accessToken = request.getHeader(AUTHORIZATION).replace(OAUTH_HEADER_NAME, "").trim();
//            CallContext callContext = new CallContext();
//            callContext.setAccessToken(accessToken);
//            CallContextHolder.setCallContext(callContext);
//            return true;
//        } catch (Exception ex) {
//            CallContextHolder.resetCallContext();
//            throw ex;
//        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        CallContextHolder.resetCallContext();
    }
}
