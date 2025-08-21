package com.promptcrafter.controller;

import com.promptcrafter.util.ToneUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for tone-related endpoints.
 */
@RestController
@RequestMapping("/api/tones")
@CrossOrigin(origins = "*")
public class ToneController {

    /**
     * Get all available tones as a flat list.
     *
     * @return List of all tones
     */
    @GetMapping
    public ResponseEntity<List<String>> getAllTones() {
        return ResponseEntity.ok(ToneUtils.getAllTones());
    }

    /**
     * Get all tone categories with their associated tones.
     *
     * @return Map of categories and their tones
     */
    @GetMapping("/categories")
    public ResponseEntity<Map<String, List<String>>> getToneCategories() {
        return ResponseEntity.ok(ToneUtils.getToneCategories());
    }
}