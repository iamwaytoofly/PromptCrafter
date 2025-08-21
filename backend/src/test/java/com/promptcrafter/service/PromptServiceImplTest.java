package com.promptcrafter.service;

import com.promptcrafter.model.PromptRequest;
import com.promptcrafter.model.PromptResponse;
import com.promptcrafter.service.impl.PromptServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PromptServiceImplTest {

    private PromptService promptService;

    @BeforeEach
    void setUp() {
        promptService = new PromptServiceImpl();
    }

    @Test
    void testGeneratePromptForTechnicalContent() {
        // Arrange
        PromptRequest request = new PromptRequest();
        request.setInputText("Create a Java function to calculate Fibonacci numbers");
        request.setSelectedTones(Arrays.asList("Professional", "Precise"));
        request.setVoiceInput(false);

        // Act
        PromptResponse response = promptService.generatePrompt(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isTonesApplied());
        assertEquals("technical", response.getContentType());
        assertTrue(response.getGeneratedPrompt().contains("Technical Instruction"));
    }

    @Test
    void testGeneratePromptForCommunicationContent() {
        // Arrange
        PromptRequest request = new PromptRequest();
        request.setInputText("Write an email to my boss asking for a raise");
        request.setSelectedTones(Arrays.asList("Professional", "Respectful"));
        request.setVoiceInput(false);

        // Act
        PromptResponse response = promptService.generatePrompt(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.isTonesApplied());
        assertEquals("communication", response.getContentType());
        assertTrue(response.getGeneratedPrompt().contains("Communication Request"));
        assertTrue(response.getGeneratedPrompt().contains("Professional, Respectful"));
    }

    @Test
    void testGeneratePromptWithNoTones() {
        // Arrange
        PromptRequest request = new PromptRequest();
        request.setInputText("Write an email to my boss asking for a raise");
        request.setSelectedTones(Collections.emptyList());
        request.setVoiceInput(false);

        // Act
        PromptResponse response = promptService.generatePrompt(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.isTonesApplied());
        assertEquals("communication", response.getContentType());
        assertFalse(response.getGeneratedPrompt().contains("Tone"));
    }
}