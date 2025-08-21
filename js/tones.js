/**
 * Tones data for PromptCrafter
 * 
 * This file contains the tone categories and their associated tones
 * for use in the PromptCrafter application.
 */

const toneCategories = {
    "Professional": [
        "Formal", "Authoritative", "Confident", "Analytical", "Objective", 
        "Diplomatic", "Precise", "Respectful", "Informative", "Instructional"
    ],
    "Persuasive": [
        "Convincing", "Compelling", "Urgent", "Promotional", "Assertive", 
        "Motivational", "Inspirational", "Enthusiastic", "Passionate", "Persuasive"
    ],
    "Conversational": [
        "Casual", "Friendly", "Approachable", "Relatable", "Personable", 
        "Warm", "Inviting", "Engaging", "Chatty", "Informal"
    ],
    "Creative": [
        "Imaginative", "Playful", "Humorous", "Witty", "Quirky", 
        "Whimsical", "Entertaining", "Surprising", "Artistic", "Innovative"
    ],
    "Emotional": [
        "Empathetic", "Compassionate", "Supportive", "Encouraging", "Reassuring", 
        "Sympathetic", "Caring", "Sensitive", "Heartfelt", "Sincere"
    ],
    "Direct": [
        "Straightforward", "Clear", "Concise", "Brief", "Blunt", 
        "Candid", "Frank", "Explicit", "Direct", "No-nonsense"
    ],
    "Descriptive": [
        "Detailed", "Vivid", "Expressive", "Elaborate", "Illustrative", 
        "Colorful", "Rich", "Evocative", "Picturesque", "Comprehensive"
    ],
    "Technical": [
        "Specialized", "Precise", "Factual", "Methodical", "Systematic", 
        "Logical", "Detailed", "Accurate", "Thorough", "Rigorous"
    ],
    "Collaborative": [
        "Inclusive", "Cooperative", "Supportive", "Team-oriented", "Participatory", 
        "Unifying", "Collective", "Facilitative", "Accommodating", "Consensus-building"
    ],
    "Urgent": [
        "Time-sensitive", "Critical", "Immediate", "Pressing", "Crucial", 
        "Vital", "Essential", "Imperative", "Expedient", "Priority"
    ]
};

// Flatten the tones array for simple access
const allTones = Object.values(toneCategories).flat();