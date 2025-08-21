/**
 * PromptCrafter Application
 * 
 * This file contains the main JavaScript functionality for the PromptCrafter application,
 * including form handling, API calls, voice input, and UI interactions.
 */

// Configuration
const API_BASE_URL = 'http://localhost:8080/api';
const ENDPOINTS = {
    GENERATE_PROMPT: `${API_BASE_URL}/prompts/generate`,
    GET_TONES: `${API_BASE_URL}/tones`,
    GET_TONE_CATEGORIES: `${API_BASE_URL}/tones/categories`
};

// DOM Elements
const elements = {
    inputText: document.getElementById('input-text'),
    voiceInputBtn: document.getElementById('voice-input-btn'),
    toneSelect: document.getElementById('tone-select'),
    generateBtn: document.getElementById('generate-btn'),
    outputSection: document.getElementById('output-section'),
    loading: document.getElementById('loading'),
    outputContent: document.getElementById('output-content'),
    generatedPrompt: document.getElementById('generated-prompt'),
    copyBtn: document.getElementById('copy-btn'),
    exportBtn: document.getElementById('export-btn')
};

// State
let isRecording = false;
let recognition = null;

/**
 * Initialize the application
 */
function initApp() {
    setupToneSelect();
    setupEventListeners();
    setupSpeechRecognition();
}

/**
 * Set up the tone selection dropdown
 */
function setupToneSelect() {
    // Initialize Select2
    $(elements.toneSelect).select2({
        placeholder: 'Select tones (optional)',
        allowClear: true,
        closeOnSelect: false,
        width: '100%'
    });

    // Populate tone options from categories
    Object.entries(toneCategories).forEach(([category, tones]) => {
        const optgroup = $('<optgroup>').attr('label', category);
        
        tones.forEach(tone => {
            optgroup.append($('<option>').val(tone).text(tone));
        });
        
        $(elements.toneSelect).append(optgroup);
    });
}

/**
 * Set up event listeners for UI elements
 */
function setupEventListeners() {
    // Generate button click
    elements.generateBtn.addEventListener('click', handleGeneratePrompt);
    
    // Voice input button click
    elements.voiceInputBtn.addEventListener('click', toggleVoiceInput);
    
    // Copy button click
    elements.copyBtn.addEventListener('click', copyToClipboard);
    
    // Export button click
    elements.exportBtn.addEventListener('click', exportAsTxt);
}

/**
 * Set up speech recognition for voice input
 */
function setupSpeechRecognition() {
    // Check if browser supports speech recognition
    if ('webkitSpeechRecognition' in window || 'SpeechRecognition' in window) {
        const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
        recognition = new SpeechRecognition();
        
        recognition.continuous = true;
        recognition.interimResults = true;
        recognition.lang = 'en-US';
        
        recognition.onstart = () => {
            isRecording = true;
            elements.voiceInputBtn.classList.add('recording');
            elements.voiceInputBtn.innerHTML = '<i class="fas fa-stop"></i>';
        };
        
        recognition.onend = () => {
            isRecording = false;
            elements.voiceInputBtn.classList.remove('recording');
            elements.voiceInputBtn.innerHTML = '<i class="fas fa-microphone"></i>';
        };
        
        recognition.onresult = (event) => {
            let interimTranscript = '';
            let finalTranscript = '';
            
            for (let i = event.resultIndex; i < event.results.length; i++) {
                const transcript = event.results[i][0].transcript;
                
                if (event.results[i].isFinal) {
                    finalTranscript += transcript;
                } else {
                    interimTranscript += transcript;
                }
            }
            
            if (finalTranscript) {
                // Append to existing text or replace if empty
                if (elements.inputText.value.trim() === '') {
                    elements.inputText.value = finalTranscript;
                } else {
                    elements.inputText.value += ' ' + finalTranscript;
                }
            }
        };
        
        recognition.onerror = (event) => {
            console.error('Speech recognition error:', event.error);
            isRecording = false;
            elements.voiceInputBtn.classList.remove('recording');
            elements.voiceInputBtn.innerHTML = '<i class="fas fa-microphone"></i>';
        };
    } else {
        // Hide voice input button if not supported
        elements.voiceInputBtn.style.display = 'none';
        console.warn('Speech recognition not supported in this browser');
    }
}

/**
 * Toggle voice input recording
 */
function toggleVoiceInput() {
    if (!recognition) {
        alert('Speech recognition is not supported in your browser.');
        return;
    }
    
    if (isRecording) {
        recognition.stop();
    } else {
        recognition.start();
    }
}

/**
 * Handle generate prompt button click
 */
async function handleGeneratePrompt() {
    const inputText = elements.inputText.value.trim();
    
    if (!inputText) {
        alert('Please enter some text or use voice input.');
        return;
    }
    
    // Show loading state
    elements.outputSection.style.display = 'block';
    elements.loading.classList.remove('hidden');
    elements.outputContent.classList.add('hidden');
    
    try {
        const selectedTones = $(elements.toneSelect).val() || [];
        
        const response = await generatePrompt(inputText, selectedTones);
        displayGeneratedPrompt(response);
    } catch (error) {
        console.error('Error generating prompt:', error);
        alert('An error occurred while generating the prompt. Please try again.');
    } finally {
        // Hide loading state
        elements.loading.classList.add('hidden');
        elements.outputContent.classList.remove('hidden');
    }
}

/**
 * Call the API to generate a prompt
 * 
 * @param {string} inputText - The user's input text
 * @param {string[]} selectedTones - Array of selected tones
 * @returns {Promise<Object>} - The API response
 */
async function generatePrompt(inputText, selectedTones) {
    const requestData = {
        inputText: inputText,
        selectedTones: selectedTones,
        isVoiceInput: isRecording
    };
    
    const response = await fetch(ENDPOINTS.GENERATE_PROMPT, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    });
    
    if (!response.ok) {
        throw new Error(`API error: ${response.status}`);
    }
    
    return response.json();
}

/**
 * Display the generated prompt in the UI
 * 
 * @param {Object} response - The API response
 */
function displayGeneratedPrompt(response) {
    elements.generatedPrompt.textContent = response.generatedPrompt;
    
    // Add metadata as a comment at the end
    const metadata = [
        '\n\n',
        '/* PromptCrafter Metadata:',
        ` * Content Type: ${response.contentType}`,
        ` * Tones Applied: ${response.tonesApplied ? 'Yes' : 'No'}`
    ];
    
    if (response.tonesApplied && response.appliedTones && response.appliedTones.length > 0) {
        metadata.push(` * Applied Tones: ${response.appliedTones.join(', ')}`);
    }
    
    metadata.push(` * Processing Time: ${response.processingTimeMs}ms`);
    metadata.push(' */');
    
    elements.generatedPrompt.textContent += metadata.join('\n');
}

/**
 * Copy the generated prompt to clipboard
 */
function copyToClipboard() {
    const promptText = elements.generatedPrompt.textContent;
    
    navigator.clipboard.writeText(promptText)
        .then(() => {
            // Show temporary success message
            const originalText = elements.copyBtn.innerHTML;
            elements.copyBtn.innerHTML = '<i class="fas fa-check"></i> Copied!';
            
            setTimeout(() => {
                elements.copyBtn.innerHTML = originalText;
            }, 2000);
        })
        .catch(err => {
            console.error('Failed to copy text: ', err);
            alert('Failed to copy to clipboard. Please try again.');
        });
}

/**
 * Export the generated prompt as a .txt file
 */
function exportAsTxt() {
    const promptText = elements.generatedPrompt.textContent;
    const blob = new Blob([promptText], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    
    const a = document.createElement('a');
    a.href = url;
    a.download = 'prompt_' + new Date().toISOString().slice(0, 10) + '.txt';
    document.body.appendChild(a);
    a.click();
    
    // Clean up
    setTimeout(() => {
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    }, 100);
}

// Initialize the application when the DOM is ready
document.addEventListener('DOMContentLoaded', initApp);