package com.promptcrafter.service.impl;

import com.promptcrafter.model.PromptRequest;
import com.promptcrafter.model.PromptResponse;
import com.promptcrafter.service.PromptService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Implementation of the PromptService interface.
 */
@Service
public class PromptServiceImpl implements PromptService {

    // Keywords that indicate personalized communication
    private static final Set<String> PERSONALIZED_COMMUNICATION_KEYWORDS = new HashSet<>(Arrays.asList(
        "email", "text", "message", "letter", "post", "write", "communicate", "outreach", 
        "cover letter", "social media", "linkedin", "twitter", "facebook", "instagram",
        "greeting", "response", "reply", "dm", "direct message", "chat", "conversation",
        "correspondence", "memo", "newsletter", "announcement", "invitation"
    ));
    
    // Keywords that indicate technical or instructional content
    private static final Set<String> TECHNICAL_KEYWORDS = new HashSet<>(Arrays.asList(
        "code", "program", "develop", "build", "create", "implement", "design", "architecture",
        "algorithm", "function", "class", "method", "api", "database", "query", "system",
        "technical", "instruction", "guide", "tutorial", "how to", "steps", "procedure",
        "process", "methodology", "framework", "structure", "analyze", "evaluate", "assess"
    ));

    @Override
    public PromptResponse generatePrompt(PromptRequest request) {
        String inputText = request.getInputText().trim();
        List<String> selectedTones = request.getSelectedTones();
        
        // Determine content type
        String contentType = determineContentType(inputText);
        
        // Determine if tones should be applied
        boolean shouldApplyTones = shouldApplyTones(inputText, contentType);
        
        // Generate the prompt
        String generatedPrompt = generateStructuredPrompt(inputText, selectedTones, shouldApplyTones, contentType);
        
        // Build and return the response
        return PromptResponse.builder()
                .generatedPrompt(generatedPrompt)
                .tonesApplied(shouldApplyTones)
                .appliedTones(shouldApplyTones ? selectedTones.toArray(new String[0]) : new String[0])
                .contentType(contentType)
                .build();
    }
    
    /**
     * Determines the type of content based on the input text.
     *
     * @param inputText The user's input text
     * @return The determined content type
     */
    private String determineContentType(String inputText) {
        String lowercaseInput = inputText.toLowerCase();
        
        // Check for personalized communication indicators
        for (String keyword : PERSONALIZED_COMMUNICATION_KEYWORDS) {
            if (lowercaseInput.contains(keyword)) {
                return "communication";
            }
        }
        
        // Check for technical/instructional indicators
        for (String keyword : TECHNICAL_KEYWORDS) {
            if (lowercaseInput.contains(keyword)) {
                return "technical";
            }
        }
        
        // Default to creative if no specific type is detected
        return "creative";
    }
    
    /**
     * Determines if tones should be applied based on content type and input text.
     *
     * @param inputText The user's input text
     * @param contentType The determined content type
     * @return True if tones should be applied, false otherwise
     */
    private boolean shouldApplyTones(String inputText, String contentType) {
        // Only apply tones for personalized communication
        return "communication".equals(contentType) || 
               inputText.toLowerCase().contains("tone") || 
               inputText.toLowerCase().contains("style") ||
               inputText.toLowerCase().contains("voice");
    }
    
    /**
     * Generates a structured AI prompt based on the input text and selected tones.
     *
     * @param inputText The user's input text
     * @param selectedTones The selected tones
     * @param applyTones Whether to apply tones
     * @param contentType The determined content type
     * @return The generated structured prompt
     */
    private String generateStructuredPrompt(String inputText, List<String> selectedTones, boolean applyTones, String contentType) {
        StringBuilder promptBuilder = new StringBuilder();
        
        // Add appropriate prefix based on content type
        if ("technical".equals(contentType)) {
            promptBuilder.append("# Technical Instruction\n\n");
        } else if ("communication".equals(contentType)) {
            promptBuilder.append("# Communication Request\n\n");
        } else {
            promptBuilder.append("# Creative Request\n\n");
        }
        
        // Add task description
        promptBuilder.append("## Task\n");
        promptBuilder.append(extractTaskFromInput(inputText)).append("\n\n");
        
        // Add context if available
        String context = extractContextFromInput(inputText);
        if (!context.isEmpty()) {
            promptBuilder.append("## Context\n");
            promptBuilder.append(context).append("\n\n");
        }
        
        // Add tones if applicable
        if (applyTones && selectedTones != null && !selectedTones.isEmpty()) {
            promptBuilder.append("## Tone\n");
            promptBuilder.append("Use the following tone(s): ");
            promptBuilder.append(String.join(", ", selectedTones)).append("\n\n");
        }
        
        // Add format guidelines
        promptBuilder.append("## Format\n");
        if ("technical".equals(contentType)) {
            promptBuilder.append("Provide clear, precise, and structured information. Use appropriate formatting for code, steps, or technical details.\n\n");
        } else if ("communication".equals(contentType)) {
            promptBuilder.append("Structure the content appropriately for the intended communication medium. Include all necessary components.\n\n");
        } else {
            promptBuilder.append("Present the content in a clear, engaging manner. Use appropriate structure and formatting.\n\n");
        }
        
        // Add output expectations
        promptBuilder.append("## Output Expectations\n");
        promptBuilder.append("The response should be comprehensive, accurate, and directly address the request. ");
        
        if (applyTones && selectedTones != null && !selectedTones.isEmpty()) {
            promptBuilder.append("Maintain the specified tone(s) throughout.");
        } else {
            promptBuilder.append("Focus on clarity and precision.");
        }
        
        return promptBuilder.toString();
    }
    
    /**
     * Extracts the main task from the input text.
     *
     * @param inputText The user's input text
     * @return The extracted task
     */
    private String extractTaskFromInput(String inputText) {
        // Simple extraction - in a real application, this would be more sophisticated
        // using NLP techniques to better understand the user's intent
        
        // Remove common prefixes that aren't part of the actual task
        String cleanedInput = inputText
            .replaceAll("(?i)^(can you|could you|please|i need|i want|help me|write|create|generate|make)", "")
            .trim();
            
        // If the cleaning removed too much, use the original
        if (cleanedInput.length() < inputText.length() / 2) {
            return inputText;
        }
        
        // Capitalize first letter if needed
        if (!cleanedInput.isEmpty() && Character.isLowerCase(cleanedInput.charAt(0))) {
            cleanedInput = Character.toUpperCase(cleanedInput.charAt(0)) + cleanedInput.substring(1);
        }
        
        return cleanedInput;
    }
    
    /**
     * Extracts context information from the input text.
     *
     * @param inputText The user's input text
     * @return The extracted context
     */
    private String extractContextFromInput(String inputText) {
        // Look for context indicators
        String[] contextIndicators = {"for", "because", "since", "as", "given that", "considering"};
        
        for (String indicator : contextIndicators) {
            int index = inputText.toLowerCase().indexOf(" " + indicator + " ");
            if (index > 0) {
                return inputText.substring(index + indicator.length() + 1).trim();
            }
        }
        
        return "";
    }
}