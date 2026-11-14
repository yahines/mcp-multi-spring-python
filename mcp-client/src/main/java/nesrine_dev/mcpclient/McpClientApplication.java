package nesrine_dev.mcpclient;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class McpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(List<McpSyncClient> clients) throws Exception {
        return args -> {
            clients.forEach(client -> {
                var tools = client.listTools().tools();
                tools.forEach(tool -> {
                    System.out.println("--------------");
                    System.out.println(tool.name());
                    System.out.println(tool.description());
                    System.out.println(tool.inputSchema());
                    System.out.println("--------------");
                });
                System.out.println("**********************");

                boolean hasGetCompanyByName = tools.stream()
                        .anyMatch(t -> t.name().equals("getCompanyByName"));

                if (hasGetCompanyByName) {
                    Map<String, Object> params = Map.of("name", "Tunisie Telecom");
                    McpSchema.CallToolResult result = client.callTool(new McpSchema.CallToolRequest("getCompanyByName", params));
                    McpSchema.Content content = result.content().get(0);
                    System.out.println(content);

                    if (content.type().equals("text")) {
                        System.out.println(".......................");
                        McpSchema.TextContent textContent = (McpSchema.TextContent) content;
                        System.out.println(textContent.text());
                    }
                }
            });
        };
    }
}
