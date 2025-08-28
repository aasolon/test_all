//package com.example.testall.mcp.spring;
//
//import com.example.testall.mcp.spring.model.LocationInfo;
//import com.example.testall.mcp.spring.model.WeatherResult;
//import org.springframework.ai.chat.model.ToolContext;
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.stereotype.Service;
//
//@Service
//public class WeatherService {
//
//    @Tool(description = "Get weather information by city name")
//    public WeatherResult getWeather(LocationInfo locationInfo, ToolContext toolContext) {
//        WeatherResult weatherResult = new WeatherResult();
//        weatherResult.setInfo("Weather is good!");
//        weatherResult.setSomeFlag(true);
//        return weatherResult;
//    }
//}
