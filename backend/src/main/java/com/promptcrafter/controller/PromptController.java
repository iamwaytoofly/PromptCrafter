package com.promptcrafter.controller;

import com.promptcrafter.model.PromptRequest;
import com.promptcrafter.model.PromptResponse;
import com.promptcrafter.service.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling prompt generation requests.
 */
@RestController
@RequestMapping("/api/prompts")
@CrossOrigin(origins = "*")
public class PromptController {

    private final PromptService promptService;

    @Autowired
    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    /**
     * Endpoint to generate an AI prompt from user input.
     *
     * @param request The prompt generation request containing input text and selected tones
     * @return ResponseEntity containing the generated prompt
     */
    @PostMapping("/generate")
    public ResponseEntity<PromptResponse> generatePrompt(@RequestBody PromptRequest request) {
        long startTime = System.currentTimeMillis();
        PromptResponse response = promptService.generatePrompt(request);
        
        // Add processing time to response
        long processingTime = System.currentTimeMillis() - startTime;
        response.setProcessingTimeMs(processingTime);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint.
     *
     * @return Simple status message
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("PromptCrafter API is running");
    }
}