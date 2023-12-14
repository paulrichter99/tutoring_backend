package com.paulrichter.tutoring.controller.rest;

import com.paulrichter.tutoring.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/uploads")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping(value = "/profilePicture/{imageName}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> loadProfilePicture(@PathVariable String imageName){
        try {
            return ResponseEntity.ok(storageService.load(imageName));
        } catch (Exception e) {
            return ResponseEntity.ok(storageService.load("default.jpg"));
        }

    }
}
