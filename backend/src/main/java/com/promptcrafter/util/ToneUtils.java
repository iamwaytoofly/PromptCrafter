package com.promptcrafter.util;

import java.util.*;

/**
 * Utility class for managing communication tones.
 */
public class ToneUtils {

    /**
     * Map of tone categories and their associated tones.
     */
    private static final Map<String, List<String>> TONE_CATEGORIES = new HashMap<>();
    
    static {
        // Professional tones
        TONE_CATEGORIES.put("Professional", Arrays.asList(
            "Formal", "Authoritative", "Confident", "Analytical", "Objective", 
            "Diplomatic", "Precise", "Respectful", "Informative", "Instructional"
        ));
        
        // Persuasive tones
        TONE_CATEGORIES.put("Persuasive", Arrays.asList(
            "Convincing", "Compelling", "Urgent", "Promotional", "Assertive", 
            "Motivational", "Inspirational", "Enthusiastic", "Passionate", "Persuasive"
        ));
        
        // Conversational tones
        TONE_CATEGORIES.put("Conversational", Arrays.asList(
            "Casual", "Friendly", "Approachable", "Relatable", "Personable", 
            "Warm", "Inviting", "Engaging", "Chatty", "Informal"
        ));
        
        // Creative tones
        TONE_CATEGORIES.put("Creative", Arrays.asList(
            "Imaginative", "Playful", "Humorous", "Witty", "Quirky", 
            "Whimsical", "Entertaining", "Surprising", "Artistic", "Innovative"
        ));
        
        // Emotional tones
        TONE_CATEGORIES.put("Emotional", Arrays.asList(
            "Empathetic", "Compassionate", "Supportive", "Encouraging", "Reassuring", 
            "Sympathetic", "Caring", "Sensitive", "Heartfelt", "Sincere"
        ));
        
        // Direct tones
        TONE_CATEGORIES.put("Direct", Arrays.asList(
            "Straightforward", "Clear", "Concise", "Brief", "Blunt", 
            "Candid", "Frank", "Explicit", "Direct", "No-nonsense"
        ));
        
        // Descriptive tones
        TONE_CATEGORIES.put("Descriptive", Arrays.asList(
            "Detailed", "Vivid", "Expressive", "Elaborate", "Illustrative", 
            "Colorful", "Rich", "Evocative", "Picturesque", "Comprehensive"
        ));
        
        // Technical tones
        TONE_CATEGORIES.put("Technical", Arrays.asList(
            "Specialized", "Precise", "Factual", "Methodical", "Systematic", 
            "Logical", "Detailed", "Accurate", "Thorough", "Rigorous"
        ));
        
        // Collaborative tones
        TONE_CATEGORIES.put("Collaborative", Arrays.asList(
            "Inclusive", "Cooperative", "Supportive", "Team-oriented", "Participatory", 
            "Unifying", "Collective", "Facilitative", "Accommodating", "Consensus-building"
        ));
        
        // Urgent tones
        TONE_CATEGORIES.put("Urgent", Arrays.asList(
            "Time-sensitive", "Critical", "Immediate", "Pressing", "Crucial", 
            "Vital", "Essential", "Imperative", "Expedient", "Priority"
        ));
    }
    
    /**
     * Gets all tone categories.
     *
     * @return Map of tone categories and their associated tones
     */
    public static Map<String, List<String>> getToneCategories() {
        return Collections.unmodifiableMap(TONE_CATEGORIES);
    }
    
    /**
     * Gets all tones as a flat list.
     *
     * @return List of all tones
     */
    public static List<String> getAllTones() {
        List<String> allTones = new ArrayList<>();
        TONE_CATEGORIES.values().forEach(allTones::addAll);
        return allTones;
    }
    
    /**
     * Gets the category for a specific tone.
     *
     * @param tone The tone to find the category for
     * @return The category name, or "Uncategorized" if not found
     */
    public static String getCategoryForTone(String tone) {
        for (Map.Entry<String, List<String>> entry : TONE_CATEGORIES.entrySet()) {
            if (entry.getValue().contains(tone)) {
                return entry.getKey();
            }
        }
        return "Uncategorized";
    }
}