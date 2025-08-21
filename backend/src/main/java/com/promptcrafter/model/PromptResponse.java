package com.promptcrafter.model;

import lombok.Data;
import lombok.Builder;

/**
 * Represents the response containing the generated AI prompt.
 */
@Data
@Builder
public class PromptResponse {
    
    /**
     * The generated AI prompt.
     */
    private String generatedPrompt;
    
    /**
     * Indicates if tones were applied to the prompt.
     */
    private boolean tonesApplied;
    
    /**
     * List of tones that were applied to the prompt.
     */
    private String[] appliedTones;
    
    /**
     * Type of content detected (e.g., "technical", "communication", "creative").
     */
    private String contentType;
    
    /**
     * Processing time in milliseconds.
     */
    private long processingTimeMs;
}