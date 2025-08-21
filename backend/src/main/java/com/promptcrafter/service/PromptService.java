package com.promptcrafter.service;

import com.promptcrafter.model.PromptRequest;
import com.promptcrafter.model.PromptResponse;

/**
 * Service interface for prompt generation functionality.
 */
public interface PromptService {
    
    /**
     * Generates an AI prompt based on user input and selected tones.
     *
     * @param request The prompt generation request
     * @return The generated prompt response
     */
    PromptResponse generatePrompt(PromptRequest request);
}