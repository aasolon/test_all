//package com.example.testall.mcp.spring;
//
//import org.springframework.ai.tool.ToolCallback;
//import org.springframework.ai.tool.ToolCallbackProvider;
//import org.springframework.ai.tool.method.MethodToolCallbackProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SpringAiServerConfig {
//
//    @Bean
//    public ToolCallbackProvider myTools(WeatherService weatherService) {
//        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
//    }
//}
