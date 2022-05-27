package com.example.testall.pathpattern;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternMain {

    public static void main(String[] args) {
        testPath();
//        String path = "http://localhost:8080/fintech/api/v1/payments/state?param1=value1";
        String path = "/v1/payments/state?param1=value1";

        String URL_REGEX = "^(([^:/?#]+):)?(//([^/?#]*))?(/[^?#]*)?/(v.)(/[^?#]*)(\\\\?([^#]*))?(#(.*))?";
        Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
        Matcher urlMatcher = URL_PATTERN.matcher(path);
        if (urlMatcher.matches()) {
            System.out.println(urlMatcher.group(6));
            System.out.println(urlMatcher.group(7));
        } else {
            System.out.println("NONE");
        }

//        String path = " /v1/payments";
////        String path = " /fintech/api/v1/payments/state";
//        PathPatternParser pathPatternParser = new PathPatternParser();
//        PathPattern pathPattern = pathPatternParser.parse("/{apiVersion}/{*urlPath}");
////        PathPattern pathPattern = pathPatternParser.parse("/fintech/api/{apiVersion}/{*urlPath}");
//        PathContainer pathContainer = PathContainer.parsePath(path);
//        PathPattern.PathMatchInfo pathMatchInfo = pathPattern.matchAndExtract(pathContainer);
//        if (pathMatchInfo != null) {
//            Map<String, String> uriVariables = pathMatchInfo.getUriVariables();
////            System.out.println(uriVariables.get("externalId"));
//            System.out.println(uriVariables.get("apiVersion"));
//            System.out.println(uriVariables.get("urlPath"));
//        }


        String path2 = "/v1/payments/state?param1=value1";
        PathPatternParser pathPatternParser = new PathPatternParser();
        PathPattern pathPattern = pathPatternParser.parse("/{apiVersion}/{*urlPath}");
        PathContainer pathContainer = PathContainer.parsePath(path2);
        PathPattern.PathMatchInfo pathMatchInfo = pathPattern.matchAndExtract(pathContainer);
        if (pathMatchInfo != null) {
            Map<String, String> uriVariables = pathMatchInfo.getUriVariables();
            System.out.println(uriVariables.get("apiVersion"));
            System.out.println(uriVariables.get("urlPath"));
        }
    }

    private static void testPath() {
        String path2 = "/v1/payments/state";
        PathPatternParser pathPatternParser = new PathPatternParser();
        PathPattern pathPattern = pathPatternParser.parse("/v1/payments/**");
        PathContainer pathContainer = PathContainer.parsePath(path2);
        boolean matches = pathPattern.matches(pathContainer);
        int i = 0;
    }
}
