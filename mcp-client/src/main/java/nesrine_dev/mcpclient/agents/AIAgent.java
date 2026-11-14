package nesrine_dev.mcpclient.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.BaseChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class AIAgent {
    private final ChatClient chatClient;
    private final MessageWindowChatMemory memory = MessageWindowChatMemory.builder().maxMessages(20).build();

    public AIAgent(ChatClient.Builder chatClient, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClient
                .defaultSystem("Answer the user question using provided tools")
                .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
//              .defaultAdvisors(MessageChatMemoryAdvisor.builder(
//                        MessageWindowChatMemory.builder().maxMessages(20).build()).build())
                .build();
    }

    public String askLLM(String query, String conversationId) {
        return chatClient.prompt()
                .user(query)
                .advisors(MessageChatMemoryAdvisor.builder(memory).build())
                .advisors(a -> a.param("chat_memory_conversation_id",conversationId))
                .call().content();
    }

}
