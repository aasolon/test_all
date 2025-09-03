package com.example.testall.mcp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class McpController {

    @GetMapping(value = "/.well-known/oauth-protected-resource", produces = "application/json")
    public String prm() {
        return """
                {
                  "resource_name": "My Test MCP Server",
                  "resource": "http://10.237.84.197:9876",
                  "authorization_servers": [
                    "http://10.237.84.197:9876/login/oauth1"
                  ],
                  "bearer_methods_supported": [
                    "header"
                  ],
                  "scopes_supported": [
                    "repo:status",
                    "user:email"
                  ]
                }
                """;
    }
}
