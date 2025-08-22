package com.example.testall.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpStatelessServerFeatures;
import io.modelcontextprotocol.server.McpStatelessSyncServer;
import io.modelcontextprotocol.server.transport.WebMvcStatelessServerTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Configuration
public class McpServerConfig {

    @Bean
    public WebMvcStatelessServerTransport webMvcStatelessServerTransport() {
        ObjectMapper objectMapper = new ObjectMapper();
        return WebMvcStatelessServerTransport.builder()
                .objectMapper(objectMapper)
                .build();

    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(WebMvcStatelessServerTransport statelessServerTransport) {
        return statelessServerTransport.getRouterFunction();
    }

    @Bean
    public McpStatelessSyncServer mcpServer(WebMvcStatelessServerTransport statelessServerTransport, McpPaymentService mcpPaymentService) {
        // Configure server capabilities with resource support
        var capabilities = McpSchema.ServerCapabilities.builder()
                .tools(true) // Tool support with list changes notifications
                .build();

        String emptyJsonSchema = """
			{
				"$schema": "http://json-schema.org/draft-07/schema#",
				"type": "object",
				"properties": {}
			}
			""";
        McpStatelessServerFeatures.SyncToolSpecification tool1 = McpStatelessServerFeatures.SyncToolSpecification
                .builder()
                .tool(McpSchema.Tool.builder()
                        .name("tool1")
                        .description("tool1 description")
                        .inputSchema(emptyJsonSchema)
                        .build())
                .callHandler((ctx, request) -> {

                    try {
                        HttpResponse<String> response = HttpClient.newHttpClient()
                                .send(HttpRequest.newBuilder()
                                        .uri(URI.create(
                                                "https://raw.githubusercontent.com/modelcontextprotocol/java-sdk/refs/heads/main/README.md"))
                                        .GET()
                                        .build(), HttpResponse.BodyHandlers.ofString());
                        String responseBody = response.body();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    return new McpSchema.CallToolResult(List.of(new McpSchema.TextContent("CALL RESPONSE")), null);
                })
                .build();

        // Create the server with both tool and resource capabilities
        McpStatelessSyncServer mcpServer = McpServer.sync(statelessServerTransport)
                .serverInfo("MCP Test Payment Server", "1.0.0")
                .capabilities(capabilities)
                .tools(tool1) // Add @Tools
                .build();

        return mcpServer; // @formatter:on
    }
}
