package com.promptcrafter.model;

import java.util.List;
import lombok.Data;

/**
 * Represents a request to generate an AI prompt.
 */
@Data
public class PromptRequest {
    
    /**
     * The original input text provided by the user.
     */
    private String inputText;
    
    /**
     * List of selected tones to apply to the prompt.
     */
    private List<String> selectedTones;
    
    /**
     * Indicates if the input was provided via voice.
     */
    private boolean isVoiceInput;
}