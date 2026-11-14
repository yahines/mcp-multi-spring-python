package nesrine_dev.mcpclient.controllers;

import nesrine_dev.mcpclient.agents.AIAgent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIRestController {
    private AIAgent aiAgent;

    public AIRestController(AIAgent aiAgent) {
        this.aiAgent = aiAgent;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String query,
                       @RequestParam(defaultValue = "default") String conversationId) {
        return aiAgent.askLLM(query, conversationId);
    }

}
