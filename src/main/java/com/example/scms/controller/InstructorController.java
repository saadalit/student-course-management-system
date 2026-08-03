package com.example.scms.controller;

import com.example.scms.dto.InstructorRequest;
import com.example.scms.dto.InstructorResponse;
import com.example.scms.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorResponse> createInstructor(@Valid @RequestBody InstructorRequest instructorRequest) {
        InstructorResponse instructorResponse = instructorService.createInstructor(instructorRequest);
        return new ResponseEntity<>(instructorResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InstructorResponse>> getAllInstructors() {
        List<InstructorResponse> instructors = instructorService.getAllInstructors();
        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponse> getInstructorById(@PathVariable Long id) {
        InstructorResponse instructorResponse = instructorService.getInstructorById(id);
        return new ResponseEntity<>(instructorResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponse> updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorRequest instructorRequest) {
        InstructorResponse instructorResponse = instructorService.updateInstructor(id, instructorRequest);
        return new ResponseEntity<>(instructorResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return new ResponseEntity<>("Instructor deleted successfully", HttpStatus.OK);
    }
}

